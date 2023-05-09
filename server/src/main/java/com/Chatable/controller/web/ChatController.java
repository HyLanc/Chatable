package com.Chatable.controller.web;

import com.Chatable.dto.ResponseDTO;
import com.Chatable.service.IChatService;
import com.Chatable.dto.ChatDTO;
import com.Chatable.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
@RestController("WebChatController")
@RequestMapping("/web/chat")
public class ChatController {

    @Resource
    private IChatService chatService;

    /**
     * 发起新会话
     * @param chatDTO
     * @return
     */
    @PostMapping("/start_chat")
    public ResponseDTO<String> startNewChat(@RequestBody ChatDTO chatDTO){
        return chatService.startNewChat(chatDTO);
    }

    /**
     * 获取当前登录用户的会话信息
     * @param userDTO
     * @return
     */
    @PostMapping("/get")
    public ResponseDTO<List<ChatDTO>> getChatList(@RequestBody UserDTO userDTO){
        return chatService.getChatList(userDTO);
    }

    /**
     * 标记会话为已读
     * @param chatDTO
     * @return
     */
    @PostMapping("/read")
    public ResponseDTO<Boolean> markChatRead(@RequestBody ChatDTO chatDTO){
        return chatService.markChatRead(chatDTO);
    }

    /**
     * 删除聊天
     * @param chatDTO
     * @return
     */
    @PostMapping("/delete")
    public ResponseDTO<Boolean> deleteChat(@RequestBody ChatDTO chatDTO){
        return chatService.hideChat(chatDTO);
    }
}
