package com.Chatable.dto;

import com.Chatable.annotation.ValidateEntity;

import java.util.Date;
import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public class ChatDTO {
    private String id;

    @ValidateEntity(required=true,errorRequiredMsg="聊天发起者不能为空！")
    private String sender;

    private UserDTO senderDTO;

    @ValidateEntity(required=true,errorRequiredMsg="聊天接受者不能为空！")
    private String receiver;

    private UserDTO receiverUserDTO;

    private Date lastTime;

    private String showDate;

    private Integer senderShow;

    private Integer receiverShow;

    private Integer unreadSender;

    private Integer unreadReceiver;

    private List<MessageDTO> messageDTOList;

    private String token;

    private String lastMessage; // 最近一条消息

    private Integer chatType;

    private GroupDTO groupDTO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public UserDTO getReceiverUserDTO() {
        return receiverUserDTO;
    }

    public void setReceiverUserDTO(UserDTO receiverUserDTO) {
        this.receiverUserDTO = receiverUserDTO;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public Integer getSenderShow() {
        return senderShow;
    }

    public void setSenderShow(Integer senderShow) {
        this.senderShow = senderShow;
    }

    public Integer getReceiverShow() {
        return receiverShow;
    }

    public void setReceiverShow(Integer receiverShow) {
        this.receiverShow = receiverShow;
    }

    public UserDTO getSenderDTO() {
        return senderDTO;
    }

    public void setSenderDTO(UserDTO senderDTO) {
        this.senderDTO = senderDTO;
    }

    public List<MessageDTO> getMessageDTOList() {
        return messageDTOList;
    }

    public void setMessageDTOList(List<MessageDTO> messageDTOList) {
        this.messageDTOList = messageDTOList;
    }

    public Integer getUnreadSender() {
        return unreadSender;
    }

    public void setUnreadSender(Integer unreadSender) {
        this.unreadSender = unreadSender;
    }

    public Integer getUnreadReceiver() {
        return unreadReceiver;
    }

    public void setUnreadReceiver(Integer unreadReceiver) {
        this.unreadReceiver = unreadReceiver;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public GroupDTO getGroupDTO() {
        return groupDTO;
    }

    public void setGroupDTO(GroupDTO groupDTO) {
        this.groupDTO = groupDTO;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sender=").append(sender);
        sb.append(", receiver=").append(receiver);
        sb.append(", lastTime=").append(lastTime);
        sb.append(", receiverUserDTO=").append(receiverUserDTO);
        sb.append(", showDate=").append(showDate);
        sb.append(", senderShow=").append(senderShow);
        sb.append(", receiverShow=").append(receiverShow);
        sb.append(", senderDTO=").append(senderDTO);
        sb.append(", messageDTOList=").append(messageDTOList);
        sb.append(", unreadReceiver=").append(unreadReceiver);
        sb.append(", unreadSender=").append(unreadSender);
        sb.append(", token=").append(token);
        sb.append(", lastMessage=").append(lastMessage);
        sb.append(", chatType=").append(chatType);
        sb.append(", groupDTO=").append(groupDTO);
        sb.append("]");
        return sb.toString();
    }
}
