package com.Chatable.dto;

import com.Chatable.annotation.ValidateEntity;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public class UserDTO {
    private String id;

    @ValidateEntity(required=true,requiredMaxLength=true,requiredMinLength=true,maxLength=8,minLength=1,errorRequiredMsg="用户昵称不能为空！",errorMaxLengthMsg="用户昵称长度不能大于8！",errorMinLengthMsg="用户昵称不能为空！")
    private String username;

    @ValidateEntity(required=true,requiredMaxLength=true,requiredMinLength=true,maxLength=16,minLength=6,errorRequiredMsg="用户密码不能为空！",errorMaxLengthMsg="用户密码长度不能大于16！",errorMinLengthMsg="用户密码长度不能小于6！")
    private String password;

    private String rePassword;

    @ValidateEntity(requiredMaxLength=true,maxLength=256,errorMaxLengthMsg="个人简介长度不能大于256！")
    private String info;

    @ValidateEntity(required=true,requiredMaxLength=true,requiredMinLength=true,maxLength=11,minLength=11,errorRequiredMsg="用户手机号码不能为空！",errorMaxLengthMsg="请输入11位用户手机号码！",errorMinLengthMsg="请输入11位用户手机号码！")
    private String phone;

    @ValidateEntity(requiredMaxLength=true,maxLength=64,errorMaxLengthMsg="所在城市长度不能大于64！")
    private String city;

    private String headPic;

    private Integer sex;

    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", info=").append(info);
        sb.append(", phone=").append(phone);
        sb.append(", city=").append(city);
        sb.append(", headPic=").append(headPic);
        sb.append(", sex=").append(sex);
        sb.append(", token=").append(token);
        sb.append(", rePassword=").append(rePassword);
        sb.append("]");
        return sb.toString();
    }
}
