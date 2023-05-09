package com.Chatable.domain;

import java.util.Date;

public class Chat {
    private String id;

    private String sender;

    private String receiver;

    private Date lastTime;

    private Integer senderShow;

    private Integer receiverShow;

    private Integer unreadSender;

    private Integer unreadReceiver;

    private Integer chatType;

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

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
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
        sb.append(", senderShow=").append(senderShow);
        sb.append(", receiverShow=").append(receiverShow);
        sb.append(", unreadSender=").append(unreadSender);
        sb.append(", unreadReceiver=").append(unreadReceiver);
        sb.append(", chatType=").append(chatType);
        sb.append("]");
        return sb.toString();
    }
}