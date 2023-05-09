package com.Chatable.service.impl;

import com.Chatable.bean.CodeMsg;
import com.Chatable.dao.*;
import com.Chatable.domain.*;
import com.Chatable.dto.*;
import com.Chatable.enums.ChatShowEnum;
import com.Chatable.enums.ChatTypeEnum;
import com.Chatable.enums.MessageTypeEnum;
import com.Chatable.service.IMessageService;
import com.Chatable.service.IUserService;
import com.Chatable.util.*;
import com.Chatable.ws.ChatEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
@Service
@Transactional
public class MessageServiceImpl implements IMessageService {

    @Resource
    private ChatMapper chatMapper;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private FileMessageMapper fileMessageMapper;

    @Resource
    private ChatEndpoint chatEndpoint;

    @Resource
    private IUserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private GroupItemMapper groupItemMapper;

    /**
     * 发送消息
     * @param messageDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> sendMessage(MessageDTO messageDTO) {
        if(messageDTO == null) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 判断会话是否失效
        if(CommonUtil.isEmpty(messageDTO.getToken())) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        // 获取当前登录用户信息
        ResponseDTO<UserDTO> loginUser = userService.getLoginUser(messageDTO.getToken());
        if(!loginUser.getCode().equals(CodeMsg.SUCCESS.getCode())) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        UserDTO loginUserDTO = loginUser.getData();
        if(loginUserDTO == null) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        // 进行统一表单验证
        CodeMsg validate = ValidateEntityUtil.validate(messageDTO);
        if (!validate.getCode().equals(CodeMsg.SUCCESS.getCode())) {
            return ResponseDTO.errorByMsg(validate);
        }
        // 存储消息内容
        Message message = CopyUtil.copy(messageDTO, Message.class);
        message.setId(UuidUtil.getShortUuid());
        message.setCreateTime(new Date());
        message.setContent(EmojiConverterUtil.emojiConvert(message.getContent()));
        if(messageMapper.insertSelective(message) == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.MESSAGE_SEND_ERROR);
        }
        // 更改会话显示状态，让消息接收者弹出对应的会话
        String chatId = messageDTO.getChatId();
        Chat chat = chatMapper.selectByPrimaryKey(chatId);
        if(ChatTypeEnum.SINGLE.getCode().equals(chat.getChatType())) {
            // 单聊
            chat.setReceiverShow(ChatShowEnum.YES.getCode());
            chat.setSenderShow(ChatShowEnum.YES.getCode());
            // 增加未读消息数
            if(loginUserDTO.getId().equals(chat.getSender())) {
                chat.setUnreadReceiver(chat.getUnreadReceiver() + 1);
            } else if (loginUserDTO.getId().equals(chat.getReceiver())) {
                chat.setUnreadSender(chat.getUnreadSender() + 1);
            }
            chat.setLastTime(new Date());
            chatMapper.updateByPrimaryKey(chat);
            // 发送系统消息，让接受者发起者刷新聊天页面信息
            chatEndpoint.sendSystemMessage(chat.getReceiver());
            chatEndpoint.sendSystemMessage(chat.getSender());
        } else if (ChatTypeEnum.GROUP.getCode().equals(chat.getChatType())) {
            chat.setLastTime(new Date());
            chatMapper.updateByPrimaryKey(chat);
            // 群聊 给此群其他成员增加未读消息数
            GroupExample groupExample = new GroupExample();
            groupExample.createCriteria().andChatIdEqualTo(chat.getId());
            List<Group> groupList = groupMapper.selectByExample(groupExample);
            if(groupList == null || groupList.size() == 0) {
                return ResponseDTO.errorByMsg(CodeMsg.CHAT_NOT_EXIST);
            }
            Group group = groupList.get(0);
            GroupItemExample groupItemExample = new GroupItemExample();
            // 判断当前用户是否在群聊中
            groupItemExample.createCriteria().andUserIdEqualTo(loginUserDTO.getId()).andGroupIdEqualTo(group.getId());
            List<GroupItem> selectGroupItem = groupItemMapper.selectByExample(groupItemExample);
            if(selectGroupItem == null || selectGroupItem.size() == 0) {
                return ResponseDTO.errorByMsg(CodeMsg.GROUP_NOT_JOIN);
            }
            groupItemExample = new GroupItemExample();
            // 通知其他群成员新消息
            groupItemExample.createCriteria().andUserIdNotEqualTo(loginUserDTO.getId()).andGroupIdEqualTo(group.getId());
            List<GroupItem> groupItemList = groupItemMapper.selectByExample(groupItemExample);
            for(GroupItem groupItem : groupItemList) {
                groupItem.setUnreadCount(groupItem.getUnreadCount() + 1);
                groupItem.setShowChat(ChatShowEnum.YES.getCode());
                groupItemMapper.updateByPrimaryKeySelective(groupItem);
                // 发送系统消息，让群内所有用户刷新聊天页面信息
                chatEndpoint.sendSystemMessage(groupItem.getUserId());
            }
        }
        return ResponseDTO.success(true);
    }

    /**
     * 获取聊天记录
     * @param chatDTO
     * @return
     */
    @Override
    public ResponseDTO<List<MessageDTO>> getMessageList(ChatDTO chatDTO) {
        if(chatDTO == null || CommonUtil.isEmpty(chatDTO.getId())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        Chat chat = chatMapper.selectByPrimaryKey(chatDTO.getId());
        if(chat == null) {
            return ResponseDTO.errorByMsg(CodeMsg.CHAT_NOT_EXIST);
        }
        // 封装当前会话的所有消息
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andChatIdEqualTo(chat.getId());
        messageExample.setOrderByClause("create_time asc");
        List<Message> messageList = messageMapper.selectByExample(messageExample);
        List<MessageDTO> messageDTOList = CopyUtil.copyList(messageList, MessageDTO.class);
        for(MessageDTO messageDTO : messageDTOList) {
            if(ChatTypeEnum.SINGLE.getCode().equals(chat.getChatType())) {
                // 单聊
                User receiver = userMapper.selectByPrimaryKey(messageDTO.getReceiver());
                messageDTO.setReceiverDTO(CopyUtil.copy(receiver, UserDTO.class));
            }
            User sender = userMapper.selectByPrimaryKey(messageDTO.getSender());
            messageDTO.setSenderDTO(CopyUtil.copy(sender, UserDTO.class));
            messageDTO.setContent(EmojiConverterUtil.emojiRecovery(messageDTO.getContent()));
            if(MessageTypeEnum.FILE.getCode().equals(messageDTO.getMessageType())) {
                // 消息是文件类型消息
                FileMessageExample  fileMessageExample = new FileMessageExample();
                fileMessageExample.createCriteria().andMessageIdEqualTo(messageDTO.getId());
                List<FileMessage> fileMessageList = fileMessageMapper.selectByExample(fileMessageExample);
                if(fileMessageList != null && fileMessageList.size() != 0) {
                    FileMessage fileMessage = fileMessageList.get(0);
                    FileMessageDTO fileMessageDTO = CopyUtil.copy(fileMessage, FileMessageDTO.class);
                    String strSize = CommonUtil.convertFileSize(fileMessageDTO.getSize());
                    fileMessageDTO.setStrSize(strSize);
                    messageDTO.setFileMessageDTO(fileMessageDTO);
                }
            }
        }
        return ResponseDTO.success(messageDTOList);
    }
}
