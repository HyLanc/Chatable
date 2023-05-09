package com.Chatable.service.impl;

import com.Chatable.bean.CodeMsg;
import com.Chatable.dao.ChatMapper;
import com.Chatable.dao.GroupItemMapper;
import com.Chatable.dao.GroupMapper;
import com.Chatable.dao.UserMapper;
import com.Chatable.domain.*;
import com.Chatable.dto.*;
import com.Chatable.enums.ChatShowEnum;
import com.Chatable.enums.ChatTypeEnum;
import com.Chatable.service.IChatService;
import com.Chatable.service.IGroupService;
import com.Chatable.service.IUserService;
import com.Chatable.util.CommonUtil;
import com.Chatable.util.CopyUtil;
import com.Chatable.util.UuidUtil;
import com.Chatable.util.ValidateEntityUtil;
import com.Chatable.ws.ChatEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
@Service
@Transactional
public class GroupServiceImpl implements IGroupService {

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private GroupItemMapper groupItemMapper;

    @Resource
    private IUserService userService;

    @Resource
    private ChatMapper chatMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private IChatService chatService;

    @Resource
    private ChatEndpoint chatEndpoint;

    /**
     * 创建群聊操作
     * @param groupDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> createGroup(GroupDTO groupDTO) {
        if(groupDTO == null) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 进行统一表单验证
        CodeMsg validate = ValidateEntityUtil.validate(groupDTO);
        if (!validate.getCode().equals(CodeMsg.SUCCESS.getCode())) {
            return ResponseDTO.errorByMsg(validate);
        }
        // 判断是否选择邀请好友
        List<GroupItemDTO> groupItemDTOList = groupDTO.getGroupItemDTOList();
        if(groupItemDTOList == null || groupItemDTOList.size() == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.GROUP_ITEM_EMPTY);
        }
        // 创建群聊会话信息
        Chat chat = new Chat();
        chat.setId(UuidUtil.getShortUuid());
        chat.setLastTime(new Date());
        chat.setChatType(ChatTypeEnum.GROUP.getCode());
        if(chatMapper.insertSelective(chat) == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.CHAT_ADD_ERROR);
        }
        // 添加群聊数据
        groupDTO.setId(UuidUtil.getShortUuid());
        groupDTO.setCreateTime(new Date());
        Group group = CopyUtil.copy(groupDTO, Group.class);
        group.setChatId(chat.getId());
        if(groupMapper.insertSelective(group) == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.GROUP_ADD_ERROR);
        }
        // 添加群聊详情数据
        for(GroupItemDTO groupItemDTO : groupItemDTOList) {
            GroupItem groupItem = CopyUtil.copy(groupItemDTO, GroupItem.class);
            groupItem.setCreateTime(new Date());
            groupItem.setId(UuidUtil.getShortUuid());
            groupItem.setGroupId(group.getId());
            if(groupItemMapper.insertSelective(groupItem) == 0) {
                throw new RuntimeException("群聊详情数据添加出现异常！");
            }
        }
        return ResponseDTO.successByMsg(true, "创建群聊成功！");
    }

    /**
     * 获取当前登录用户的群聊列表
     * @param groupDTO
     * @return
     */
    @Override
    public ResponseDTO<List<GroupDTO>> listGroupByToken(GroupDTO groupDTO) {
        if(groupDTO == null || CommonUtil.isEmpty(groupDTO.getToken())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 获取当前登录用户信息
        ResponseDTO<UserDTO> loginUser = userService.getLoginUser(groupDTO.getToken());
        if(!loginUser.getCode().equals(CodeMsg.SUCCESS.getCode())) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        UserDTO loginUserDTO = loginUser.getData();
        if(loginUserDTO == null) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        List<GroupDTO> groupDTOList = new ArrayList<>();
        GroupItemExample groupItemExample = new GroupItemExample();
        groupItemExample.createCriteria().andUserIdEqualTo(loginUserDTO.getId());
        List<GroupItem> groupItemList = groupItemMapper.selectByExample(groupItemExample);
        for(GroupItem groupItem : groupItemList) {
            Group group = groupMapper.selectByPrimaryKey(groupItem.getGroupId());
            groupDTOList.add(CopyUtil.copy(group, GroupDTO.class));
        }
        // 获取每个群的详情信息
        for(GroupDTO g : groupDTOList) {
            GroupItemExample gie = new GroupItemExample();
            gie.createCriteria().andGroupIdEqualTo(g.getId());
            g.setGroupItemDTOList(CopyUtil.copyList(groupItemMapper.selectByExample(gie), GroupItemDTO.class));
        }
        return ResponseDTO.success(groupDTOList);
    }

    /**
     * 发起群聊会话操作
     * @param groupDTO
     * @return
     */
    @Override
    public ResponseDTO<ChatDTO> startGroupChat(GroupDTO groupDTO) {
        if(CommonUtil.isEmpty(groupDTO.getId()) || CommonUtil.isEmpty(groupDTO.getToken())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 获取当前登录用户信息
        ResponseDTO<UserDTO> loginUser = userService.getLoginUser(groupDTO.getToken());
        if(!loginUser.getCode().equals(CodeMsg.SUCCESS.getCode())) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        UserDTO loginUserDTO = loginUser.getData();
        if(loginUserDTO == null) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        Group group = groupMapper.selectByPrimaryKey(groupDTO.getId());
        GroupItemExample groupItemExample = new GroupItemExample();
        groupItemExample.createCriteria().andGroupIdEqualTo(groupDTO.getId()).andUserIdEqualTo(loginUserDTO.getId());
        List<GroupItem> groupItemList = groupItemMapper.selectByExample(groupItemExample);
        if(groupItemList == null || groupItemList.size() == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.GROUP_NOT_JOIN);
        }
        GroupItem groupItem = groupItemList.get(0);
        groupItem.setShowChat(ChatShowEnum.YES.getCode());
        if(groupItemMapper.updateByPrimaryKeySelective(groupItem) == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.GROUP_START_CHAT_ERROR);
        }
        Chat chat = chatMapper.selectByPrimaryKey(group.getChatId());
        return ResponseDTO.success(CopyUtil.copy(chat, ChatDTO.class));
    }


    /**
     * 根据id获取群聊信息
     * @param groupDTO
     * @return
     */
    @Override
    public ResponseDTO<GroupDTO> getGroupById(GroupDTO groupDTO) {
        if(groupDTO == null || CommonUtil.isEmpty(groupDTO.getId())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        Group group = groupMapper.selectByPrimaryKey(groupDTO.getId());
        if(group == null) {
            return ResponseDTO.success(new GroupDTO());
        }
        User user = userMapper.selectByPrimaryKey(group.getUserId());
        UserDTO userDTO = CopyUtil.copy(user, UserDTO.class);
        GroupDTO selectGroupDTO = CopyUtil.copy(group, GroupDTO.class);
        selectGroupDTO.setUserDTO(userDTO);
        // 获取群聊详情信息
        GroupItemExample groupItemExample = new GroupItemExample();
        groupItemExample.createCriteria().andGroupIdEqualTo(selectGroupDTO.getId());
        List<GroupItem> groupItemList = groupItemMapper.selectByExample(groupItemExample);
        List<GroupItemDTO> groupItemDTOList = CopyUtil.copyList(groupItemList, GroupItemDTO.class);
        for(GroupItemDTO groupItemDTO : groupItemDTOList) {
             groupItemDTO.setUserDTO(CopyUtil.copy(userMapper.selectByPrimaryKey(groupItemDTO.getUserId()), UserDTO.class));
        }
        selectGroupDTO.setGroupItemDTOList(groupItemDTOList);
        return ResponseDTO.success(selectGroupDTO);
    }

    /**
     * 邀请用户加入群聊
     * @param groupDTO
     * @return
     */
    @Override
    public ResponseDTO<GroupDTO> inviteGroupUser(GroupDTO groupDTO) {
        if(CommonUtil.isEmpty(groupDTO.getId()) || CommonUtil.isEmpty(groupDTO.getUserId())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        User user = userMapper.selectByPrimaryKey(groupDTO.getUserId());
        if(user == null) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_NOT_EXIST);
        }
        // 判断当前用户是否在群里
        GroupItemExample groupItemExample = new GroupItemExample();
        groupItemExample.createCriteria().andGroupIdEqualTo(groupDTO.getId()).andUserIdEqualTo(groupDTO.getUserId());
        List<GroupItem> groupItemList = groupItemMapper.selectByExample(groupItemExample);
        if(groupItemList != null && groupItemList.size() != 0) {
            return ResponseDTO.errorByMsg(CodeMsg.GROUP_ITEM_EXIST);
        }
        // 没有，那么就实现邀请
        GroupItem groupItem = new GroupItem();
        groupItem.setShowChat(ChatShowEnum.NO.getCode());
        groupItem.setUnreadCount(0);
        groupItem.setUserId(user.getId());
        groupItem.setGroupId(groupDTO.getId());
        groupItem.setCreateTime(new Date());
        groupItem.setId(UuidUtil.getShortUuid());
        if(groupItemMapper.insertSelective(groupItem) == 0){
            return ResponseDTO.errorByMsg(CodeMsg.GROUP_INVITE_ERROR);
        }
        return getGroupById(groupDTO);
    }

    /**
     * 退出或解散群聊操作
     * @param groupDTO
     * @return
     */
    @Override
    public ResponseDTO<GroupDTO> exitGroup(GroupDTO groupDTO) {
        if(CommonUtil.isEmpty(groupDTO.getId()) || CommonUtil.isEmpty(groupDTO.getUserId())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        Group group = groupMapper.selectByPrimaryKey(groupDTO.getId());
        if(group == null) {
            return ResponseDTO.errorByMsg(CodeMsg.GROUP_NOT_EXIST);
        }
        GroupItemExample selectGroupItemExample = new GroupItemExample();
        selectGroupItemExample.createCriteria().andGroupIdEqualTo(group.getId());
        List<GroupItem> groupItemList = groupItemMapper.selectByExample(selectGroupItemExample);
        // 判断是否是群主，若是，则解散群聊
        if(groupDTO.getUserId().equals(group.getUserId())) {
            // 是群主 删除会话 群聊 群聊详情信息
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setId(group.getChatId());
            chatService.deleteChat(chatDTO);
            // 删除群聊详情
            GroupItemExample groupItemExample = new GroupItemExample();
            groupItemExample.createCriteria().andGroupIdEqualTo(group.getId());
            groupItemMapper.deleteByExample(groupItemExample);
            // 删除群聊
            groupMapper.deleteByPrimaryKey(group.getId());
        } else {
            // 不是群主
            GroupItemExample groupItemExample = new GroupItemExample();
            groupItemExample.createCriteria().andGroupIdEqualTo(groupDTO.getId()).andUserIdEqualTo(groupDTO.getUserId());
            if(groupItemMapper.deleteByExample(groupItemExample) == 0) {
                return ResponseDTO.errorByMsg(CodeMsg.GROUP_EXIT_ERROR);
            }
        }
        // 发送系统消息通知
        for(GroupItem groupItem : groupItemList) {
            chatEndpoint.sendSystemMessage(groupItem.getUserId());
        }
        return getGroupById(groupDTO);
    }

    /**
     * 更新群聊信息操作
     * @param groupDTO
     * @return
     */
    @Override
    public ResponseDTO<GroupDTO> save(GroupDTO groupDTO) {
        if(CommonUtil.isEmpty(groupDTO.getId())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 进行统一表单验证
        CodeMsg validate = ValidateEntityUtil.validate(groupDTO);
        if(!validate.getCode().equals(CodeMsg.SUCCESS.getCode())){
            return ResponseDTO.errorByMsg(validate);
        }
        Group group = CopyUtil.copy(groupDTO, Group.class);
        // 修改数据库中数据
        if(groupMapper.updateByPrimaryKeySelective(group) == 0){
            return ResponseDTO.errorByMsg(CodeMsg.GROUP_EDIT_ERROR);
        }
        GroupItemExample selectGroupItemExample = new GroupItemExample();
        selectGroupItemExample.createCriteria().andGroupIdEqualTo(group.getId());
        List<GroupItem> groupItemList = groupItemMapper.selectByExample(selectGroupItemExample);
        // 发送系统消息通知
        for(GroupItem groupItem : groupItemList) {
            chatEndpoint.sendSystemMessage(groupItem.getUserId());
        }
        return getGroupById(groupDTO);
    }
}
