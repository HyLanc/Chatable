package com.Chatable.controller.web;

import com.Chatable.dto.ResponseDTO;
import com.Chatable.service.IMessageService;
import com.Chatable.dto.ChatDTO;
import com.Chatable.dto.MessageDTO;
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
@RestController("WebMessageController")
@RequestMapping("/web/message")
public class MessageController {

    @Resource
    private IMessageService messageService;

    /**
     * 发送消息
     * @param messageDTO
     * @return
     */
    @PostMapping("/send")
    public ResponseDTO<Boolean> sendMessage(@RequestBody MessageDTO messageDTO){
        return messageService.sendMessage(messageDTO);
    }

    /**
     * 获取聊天记录
     * @param chatDTO
     * @return
     */
    @PostMapping("/get")
    public ResponseDTO<List<MessageDTO>> getMessageList(@RequestBody ChatDTO chatDTO){
        return messageService.getMessageList(chatDTO);
    }
}
