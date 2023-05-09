package com.Chatable.dto;

import com.Chatable.annotation.ValidateEntity;

import java.util.Date;
import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public class GroupDTO {

    private String id;

    private String groupPic;

    @ValidateEntity(required=true,requiredMaxLength=true,requiredMinLength=true,maxLength=16,minLength=1,errorRequiredMsg="群聊名称不能为空！",errorMaxLengthMsg="群聊名称长度不能大于8！",errorMinLengthMsg="群聊名称不能为空！")
    private String name;

    @ValidateEntity(required=true,requiredMaxLength=true,requiredMinLength=true,maxLength=512,minLength=1,errorRequiredMsg="群聊介绍不能为空！",errorMaxLengthMsg="群聊介绍长度不能大于512！",errorMinLengthMsg="群聊介绍不能为空！")
    private String info;

    @ValidateEntity(required=true,errorRequiredMsg="群聊创建者不能为空！")
    private String userId;

    private UserDTO userDTO;

    private Date createTime;

    private List<GroupItemDTO> groupItemDTOList;

    private String token;

    private String chatId;

    private Integer unreadCount; // 未读消息数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupPic() {
        return groupPic;
    }

    public void setGroupPic(String groupPic) {
        this.groupPic = groupPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<GroupItemDTO> getGroupItemDTOList() {
        return groupItemDTOList;
    }

    public void setGroupItemDTOList(List<GroupItemDTO> groupItemDTOList) {
        this.groupItemDTOList = groupItemDTOList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", groupPic=").append(groupPic);
        sb.append(", name=").append(name);
        sb.append(", info=").append(info);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", groupItemDTOList=").append(groupItemDTOList);
        sb.append(", token=").append(token);
        sb.append(", chatId=").append(chatId);
        sb.append(", unreadCount=").append(unreadCount);
        sb.append(", userDTO=").append(userDTO);
        sb.append("]");
        return sb.toString();
    }
}
