package com.Chatable.service;

import com.Chatable.dto.ChatDTO;
import com.Chatable.dto.ResponseDTO;
import com.Chatable.dto.UserDTO;

import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public interface IChatService {

    // 发起新会话
    ResponseDTO<String> startNewChat(ChatDTO chatDTO);

    // 获取当前登录用户的会话信息
    ResponseDTO<List<ChatDTO>> getChatList(UserDTO userDTO);

    // 标记会话为已读
    ResponseDTO<Boolean> markChatRead(ChatDTO chatDTO);

    // 删除会话信息
    ResponseDTO<Boolean> deleteChat(ChatDTO chatDTO);

    // 隐藏会话信息
    ResponseDTO<Boolean> hideChat(ChatDTO chatDTO);

}
