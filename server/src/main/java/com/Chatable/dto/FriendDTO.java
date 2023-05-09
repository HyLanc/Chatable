package com.Chatable.dto;

import com.Chatable.annotation.ValidateEntity;

import java.util.Date;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public class FriendDTO {

    private String id;

    private String applyUser;

    private UserDTO applyUserDTO;

    @ValidateEntity(required=true,requiredMaxLength=true,requiredMinLength=true,maxLength=8,minLength=1,errorRequiredMsg="用户编号不能为空！",errorMaxLengthMsg="用户编号长度不能大于8！",errorMinLengthMsg="用户编号不能为空！")
    private String receiveUser;

    private UserDTO receiveUserDTO;

    private Date createTime;

    @ValidateEntity(required=true,requiredMaxLength=true,requiredMinLength=true,maxLength=64,minLength=1,errorRequiredMsg="好友申请备注不能为空！",errorMaxLengthMsg="好友申请备注长度不能大于64！",errorMinLengthMsg="好友申请备注不能为空！")
    private String remark;

    private Integer state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public UserDTO getApplyUserDTO() {
        return applyUserDTO;
    }

    public void setApplyUserDTO(UserDTO applyUserDTO) {
        this.applyUserDTO = applyUserDTO;
    }

    public UserDTO getReceiveUserDTO() {
        return receiveUserDTO;
    }

    public void setReceiveUserDTO(UserDTO receiveUserDTO) {
        this.receiveUserDTO = receiveUserDTO;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applyUser=").append(applyUser);
        sb.append(", receiveUser=").append(receiveUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", remark=").append(remark);
        sb.append(", state=").append(state);
        sb.append(", applyUserDTO=").append(applyUserDTO);
        sb.append(", receiveUserDTO=").append(receiveUserDTO);
        sb.append("]");
        return sb.toString();
    }
}
