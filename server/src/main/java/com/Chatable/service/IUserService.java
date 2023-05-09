package com.Chatable.service;

import com.Chatable.dto.ResponseDTO;
import com.Chatable.dto.UserDTO;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public interface IUserService {

    // 前台登录操作
    ResponseDTO<UserDTO> webLogin(UserDTO userDTO);

    // 检查用户是否登录
    ResponseDTO<UserDTO> checkLogin(UserDTO userDTO);

    // 根据用户id获取用户信息
    ResponseDTO<UserDTO> selectUserById(UserDTO userDTO);

    // 获取当前登录用户
    ResponseDTO<UserDTO> getLoginUser(String token);

    // 退出登录操作
    ResponseDTO<Boolean> logout(UserDTO userDTO);

    // 用户注册操作
    ResponseDTO<Boolean> register(UserDTO userDTO);

    // 更新个人信息操作
    ResponseDTO<Boolean> save(UserDTO userDTO);

    // 注销账号操作
    ResponseDTO<Boolean> cancelUser(UserDTO userDTO);
}
