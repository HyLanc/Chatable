package com.Chatable.service;

import com.Chatable.dto.ChatDTO;
import com.Chatable.dto.MessageDTO;
import com.Chatable.dto.ResponseDTO;

import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public interface IMessageService {

    // 发送消息
    ResponseDTO<Boolean> sendMessage(MessageDTO messageDTO);

    // 获取聊天记录
    ResponseDTO<List<MessageDTO>> getMessageList(ChatDTO chatDTO);

}
