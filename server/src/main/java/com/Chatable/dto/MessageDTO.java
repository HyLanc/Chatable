package com.Chatable.dto;

import com.Chatable.annotation.ValidateEntity;

import java.util.Date;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public class MessageDTO {

    private String id;

    private Date createTime;

    private UserDTO senderDTO;

    private UserDTO receiverDTO;

    private FileMessageDTO fileMessageDTO;

    private String sender;

    private String receiver;

    private Integer messageType;

    private String chatId;

    @ValidateEntity(required=true,requiredMaxLength=true,requiredMinLength=true,maxLength=512,minLength=1,errorRequiredMsg="消息内容不能为空！",errorMaxLengthMsg="消息内容长度不能大于512！",errorMinLengthMsg="消息内容不能为空！")
    private String content;

    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getSenderDTO() {
        return senderDTO;
    }

    public void setSenderDTO(UserDTO senderDTO) {
        this.senderDTO = senderDTO;
    }

    public UserDTO getReceiverDTO() {
        return receiverDTO;
    }

    public void setReceiverDTO(UserDTO receiverDTO) {
        this.receiverDTO = receiverDTO;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public FileMessageDTO getFileMessageDTO() {
        return fileMessageDTO;
    }

    public void setFileMessageDTO(FileMessageDTO fileMessageDTO) {
        this.fileMessageDTO = fileMessageDTO;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", messageType=").append(messageType);
        sb.append(", chatId=").append(chatId);
        sb.append(", content=").append(content);
        sb.append(", token=").append(token);
        sb.append(", senderDTO=").append(senderDTO);
        sb.append(", receiverDTO=").append(receiverDTO);
        sb.append(", receiver=").append(receiver);
        sb.append(", sender=").append(sender);
        sb.append(", fileMessageDTO=").append(fileMessageDTO);
        sb.append("]");
        return sb.toString();
    }
}
