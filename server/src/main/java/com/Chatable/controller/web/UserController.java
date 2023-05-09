package com.Chatable.controller.web;

import com.Chatable.dto.ResponseDTO;
import com.Chatable.dto.UserDTO;
import com.Chatable.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
@RestController("WebUserController")
@RequestMapping("/web/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 前台登录操作处理
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public ResponseDTO<UserDTO> webLogin(@RequestBody UserDTO userDTO){
        return userService.webLogin(userDTO);
    }

    /**
     * 前台检查用户是否登录
     * @param userDTO
     * @return
     */
    @PostMapping("/check_login")
    public ResponseDTO<UserDTO> checkLogin(@RequestBody UserDTO userDTO){
        return userService.checkLogin(userDTO);
    }

    /**
     * 根据id获取用户信息
     * @param userDTO
     * @return
     */
    @PostMapping("/get")
    public ResponseDTO<UserDTO> selectUserById(@RequestBody UserDTO userDTO){
        return userService.selectUserById(userDTO);
    }

    /**
     * 用户退出登录
     * @return
     */
    @PostMapping("/logout")
    public ResponseDTO<Boolean> logout(@RequestBody UserDTO userDTO){
        return userService.logout(userDTO);
    }

    /**
     * 用户注册操作
     * @return
     */
    @PostMapping("/register")
    public ResponseDTO<Boolean> register(@RequestBody UserDTO userDTO){
        return userService.register(userDTO);
    }

    /**
     * 更新个人信息操作
     * @return
     */
    @PostMapping("/save")
    public ResponseDTO<Boolean> saveUser(@RequestBody UserDTO userDTO){
        return userService.save(userDTO);
    }

    /**
     * 注销账户操作
     * @param userDTO
     * @return
     */
    @PostMapping("/cancel")
    public ResponseDTO<Boolean> cancelUser(@RequestBody UserDTO userDTO){
        return userService.cancelUser(userDTO);
    }

}
