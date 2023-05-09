package com.Chatable.service;

import com.Chatable.dto.ChatDTO;
import com.Chatable.dto.ResponseDTO;
import com.Chatable.dto.GroupDTO;

import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public interface IGroupService {

    // 创建群聊操作
    ResponseDTO<Boolean> createGroup(GroupDTO groupDTO);

    // 获取当前登录用户的群聊列表
    ResponseDTO<List<GroupDTO>> listGroupByToken(GroupDTO groupDTO);

    // 发起群聊会话操作
    ResponseDTO<ChatDTO> startGroupChat(GroupDTO groupDTO);

    // 根据id获取群聊信息
    ResponseDTO<GroupDTO> getGroupById(GroupDTO groupDTO);

    // 邀请用户加入群聊
    ResponseDTO<GroupDTO> inviteGroupUser(GroupDTO groupDTO);

    // 退出或解散群聊操作
    ResponseDTO<GroupDTO> exitGroup(GroupDTO groupDTO);

    // 更新群聊信息
    ResponseDTO<GroupDTO> save(GroupDTO groupDTO);

}
