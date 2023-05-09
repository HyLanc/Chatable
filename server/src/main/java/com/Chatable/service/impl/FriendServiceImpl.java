package com.Chatable.service.impl;

import com.Chatable.bean.CodeMsg;
import com.Chatable.dao.ChatMapper;
import com.Chatable.dao.FriendMapper;
import com.Chatable.dao.UserMapper;
import com.Chatable.domain.*;
import com.Chatable.dto.ChatDTO;
import com.Chatable.dto.FriendDTO;
import com.Chatable.dto.ResponseDTO;
import com.Chatable.dto.UserDTO;
import com.Chatable.enums.ChatTypeEnum;
import com.Chatable.enums.FriendStateEnum;
import com.Chatable.service.IChatService;
import com.Chatable.service.IFriendService;
import com.Chatable.service.IUserService;
import com.Chatable.util.CommonUtil;
import com.Chatable.util.CopyUtil;
import com.Chatable.util.UuidUtil;
import com.Chatable.util.ValidateEntityUtil;
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
public class FriendServiceImpl implements IFriendService {

    @Resource
    private FriendMapper friendMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ChatMapper chatMapper;

    @Resource
    private IChatService chatService;

    @Resource
    private IUserService userService;

    @Resource
    private ChatEndpoint chatEndpoint;

    /**
     * 发送好友申请
     * @param friendDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> applyFriend(FriendDTO friendDTO) {
        if(friendDTO == null) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 判断是否登录
        if(CommonUtil.isEmpty(friendDTO.getApplyUser())) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        // 进行统一表单验证
        CodeMsg validate = ValidateEntityUtil.validate(friendDTO);
        if (!validate.getCode().equals(CodeMsg.SUCCESS.getCode())) {
            return ResponseDTO.errorByMsg(validate);
        }
        // 判断这个用户编号是否存在
        User user = userMapper.selectByPrimaryKey(friendDTO.getReceiveUser());
        if(user == null) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_NOT_EXIST);
        }
        // 判断是否自己加自己
        if(friendDTO.getReceiveUser().equals(friendDTO.getApplyUser())) {
            return ResponseDTO.errorByMsg(CodeMsg.NO_PERMIT_ADD);
        }
        // 判断用户是否已经发出过申请或者是否已经是好友
        FriendExample friendExample = new FriendExample();
        FriendExample.Criteria c1 = friendExample.createCriteria();
        c1.andStateEqualTo(FriendStateEnum.APPLY.getCode())
                .andApplyUserEqualTo(friendDTO.getApplyUser())
                .andReceiveUserEqualTo(friendDTO.getReceiveUser());
        FriendExample.Criteria c2 = friendExample.createCriteria();
        c2.andStateEqualTo(FriendStateEnum.NORMAL.getCode())
                .andApplyUserEqualTo(friendDTO.getApplyUser())
                .andReceiveUserEqualTo(friendDTO.getReceiveUser());
        friendExample.or(c2);
        List<Friend> friendList = friendMapper.selectByExample(friendExample);
        if(friendList != null && friendList.size() > 0) {
            return ResponseDTO.errorByMsg(CodeMsg.FRIEND_APPLY_EXIST);
        }
        // 准备添加数据库
        Friend friend = CopyUtil.copy(friendDTO, Friend.class);
        friend.setId(UuidUtil.getShortUuid());
        friend.setCreateTime(new Date());
        friend.setState(FriendStateEnum.APPLY.getCode());
        if(friendMapper.insertSelective(friend) == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.FRIEND_APPLY_ERROR);
        }
        chatEndpoint.sendSystemMessage(friendDTO.getReceiveUser());
        return ResponseDTO.successByMsg(true, "发送好友申请成功！");
    }

    /**
     * 获取好友申请列表
     * @param userDTO
     * @return
     */
    @Override
    public ResponseDTO<List<FriendDTO>> getApplyFriendList(UserDTO userDTO) {
        if(userDTO == null || CommonUtil.isEmpty(userDTO.getToken())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 获取当前登录用户信息
        ResponseDTO<UserDTO> loginUser = userService.getLoginUser(userDTO.getToken());
        if(loginUser.getCode() != 0) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        UserDTO loginUserDTO = loginUser.getData();
        if(loginUserDTO == null) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        // 查询当前登录用户收到的好友申请
        FriendExample friendExample = new FriendExample();
        friendExample.createCriteria().andStateEqualTo(FriendStateEnum.APPLY.getCode()).andReceiveUserEqualTo(loginUserDTO.getId());
        List<Friend> friendList = friendMapper.selectByExample(friendExample);
        List<FriendDTO> friendDTOList = CopyUtil.copyList(friendList, FriendDTO.class);
        for(FriendDTO friendDTO : friendDTOList) {
            User user = userMapper.selectByPrimaryKey(friendDTO.getApplyUser());
            friendDTO.setApplyUserDTO(CopyUtil.copy(user, UserDTO.class));
        }
        return ResponseDTO.success(friendDTOList);
    }

    /**
     * 同意好友申请
     * @param friendDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> agreeApplyFriend(FriendDTO friendDTO) {
        if(friendDTO == null || CommonUtil.isEmpty(friendDTO.getId())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 更改记录状态
        Friend friend = friendMapper.selectByPrimaryKey(friendDTO.getId());
        friend.setState(FriendStateEnum.NORMAL.getCode());
        if(friendMapper.updateByPrimaryKeySelective(friend) == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.AGREE_FRIEND_ERROR);
        }
        // 建立双向好友关系
        Friend newFriend = new Friend();
        newFriend.setApplyUser(friend.getReceiveUser());
        newFriend.setReceiveUser(friend.getApplyUser());
        newFriend.setId(UuidUtil.getShortUuid());
        newFriend.setCreateTime(new Date());
        newFriend.setState(FriendStateEnum.NORMAL.getCode());
        newFriend.setRemark("同意好友请求！");
        if(friendMapper.insertSelective(newFriend) == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.AGREE_FRIEND_ERROR);
        }
        return ResponseDTO.successByMsg(true, "操作成功！");
    }

    /**
     * 拒绝好友请求
     * @param friendDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> refuseApplyFriend(FriendDTO friendDTO) {
        if(friendDTO == null || CommonUtil.isEmpty(friendDTO.getId())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 删除好友申请记录
        if(friendMapper.deleteByPrimaryKey(friendDTO.getId()) == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.REFUSE_FRIEND_ERROR);
        }
        return ResponseDTO.successByMsg(true, "操作成功！");
    }

    /**
     * 获取当前登录用户的好友列表
     * @param userDTO
     * @return
     */
    @Override
    public ResponseDTO<List<FriendDTO>> selectFriendListByUserId(UserDTO userDTO) {
        if(userDTO == null || CommonUtil.isEmpty(userDTO.getToken())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 通过用户token获取当前登录用户信息
        ResponseDTO<UserDTO> loginUser = userService.getLoginUser(userDTO.getToken());
        if(loginUser.getCode() != 0) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        UserDTO loginUserDTO = loginUser.getData();
        if(loginUserDTO == null) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        FriendExample friendExample = new FriendExample();
        // select * from friend where apply_user = ? and state = ?
        friendExample.createCriteria().andApplyUserEqualTo(loginUserDTO.getId()).andStateEqualTo(FriendStateEnum.NORMAL.getCode());
        List<Friend> friendList = friendMapper.selectByExample(friendExample);
        List<FriendDTO> friendDTOList = CopyUtil.copyList(friendList, FriendDTO.class);
        for(FriendDTO friendDTO : friendDTOList) {
            User user = userMapper.selectByPrimaryKey(friendDTO.getReceiveUser());
            friendDTO.setReceiveUserDTO(CopyUtil.copy(user, UserDTO.class));
        }
        return ResponseDTO.success(friendDTOList);
    }

    /**
     * 删除好友操作
     * @param friendDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> deleteFriend(FriendDTO friendDTO) {
        if(CommonUtil.isEmpty(friendDTO.getReceiveUser()) || CommonUtil.isEmpty(friendDTO.getApplyUser())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 删除好友数据
        FriendExample friendExample = new FriendExample();
        FriendExample.Criteria c1 = friendExample.createCriteria();
        c1.andApplyUserEqualTo(friendDTO.getReceiveUser()).andReceiveUserEqualTo(friendDTO.getApplyUser());
        FriendExample.Criteria c2 = friendExample.createCriteria();
        c2.andApplyUserEqualTo(friendDTO.getApplyUser()).andReceiveUserEqualTo(friendDTO.getReceiveUser());
        friendExample.or(c2);
        friendMapper.deleteByExample(friendExample);
        // 删除相关聊天数据
        ChatExample chatExample = new ChatExample();
        ChatExample.Criteria c3 = chatExample.createCriteria();
        c3.andSenderEqualTo(friendDTO.getReceiveUser()).andReceiverEqualTo(friendDTO.getApplyUser()).andChatTypeEqualTo(ChatTypeEnum.SINGLE.getCode());
        ChatExample.Criteria c4 = chatExample.createCriteria();
        c4.andSenderEqualTo(friendDTO.getApplyUser()).andReceiverEqualTo(friendDTO.getReceiveUser()).andChatTypeEqualTo(ChatTypeEnum.SINGLE.getCode());
        chatExample.or(c4);
        List<Chat> chatList = chatMapper.selectByExample(chatExample);
        for(Chat chat : chatList) {
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setId(chat.getId());
            chatService.deleteChat(chatDTO);
        }
        // 发送系统消息
        chatEndpoint.sendSystemMessage(friendDTO.getApplyUser());
        chatEndpoint.sendSystemMessage(friendDTO.getReceiveUser());
        return ResponseDTO.successByMsg(true, "删除好友成功！");
    }
}
