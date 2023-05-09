package com.Chatable.service.impl;

import com.Chatable.domain.*;
import com.Chatable.service.IFriendService;
import com.Chatable.util.CopyUtil;
import com.Chatable.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import com.Chatable.bean.CodeMsg;
import com.Chatable.dao.FriendMapper;
import com.Chatable.dao.GroupItemMapper;
import com.Chatable.dao.UserMapper;
import com.Chatable.domain.*;
import com.Chatable.dto.FriendDTO;
import com.Chatable.dto.GroupDTO;
import com.Chatable.dto.ResponseDTO;
import com.Chatable.dto.UserDTO;
import com.Chatable.service.IGroupService;
import com.Chatable.service.IUserService;
import com.Chatable.util.CommonUtil;
import com.Chatable.util.ValidateEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserMapper userMapper;

    @Resource
    private IFriendService friendService;

    @Resource
    private FriendMapper friendMapper;

    @Resource
    private GroupItemMapper groupItemMapper;

    @Resource
    private IGroupService groupService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public ResponseDTO<UserDTO> webLogin(UserDTO userDTO) {
        // 进行是否为空判断
        if(CommonUtil.isEmpty(userDTO.getUsername())){
            return ResponseDTO.errorByMsg(CodeMsg.USERNAME_EMPTY);
        }
        if(CommonUtil.isEmpty(userDTO.getPassword())){
            return ResponseDTO.errorByMsg(CodeMsg.PASSWORD_EMPTY);
        }
        // 对比昵称和密码是否正确
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(userDTO.getUsername()).andPasswordEqualTo(userDTO.getPassword());
        List<User> userList = userMapper.selectByExample(userExample);
        if(userList == null || userList.size() != 1){
            return ResponseDTO.errorByMsg(CodeMsg.USERNAME_PASSWORD_ERROR);
        }
        // 生成登录token并存入Redis中
        UserDTO selectedUserDto = CopyUtil.copy(userList.get(0), UserDTO.class);
        String token = UuidUtil.getShortUuid();
        selectedUserDto.setToken(token);
        //把token存入redis中 有效期1小时
        stringRedisTemplate.opsForValue().set("USER_" + token, JSON.toJSONString(selectedUserDto), 3600, TimeUnit.SECONDS);
        return ResponseDTO.successByMsg(selectedUserDto, "登录成功！");
    }

    /**
     * 检查用户是否登录
     * @param userDTO
     * @return
     */
    public ResponseDTO<UserDTO> checkLogin(UserDTO userDTO){
        if(userDTO == null || CommonUtil.isEmpty(userDTO.getToken())){
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        ResponseDTO<UserDTO> responseDTO = getLoginUser(userDTO.getToken());
        if(responseDTO.getCode() != 0){
            return responseDTO;
        }
        logger.info("获取到的登录信息={}", responseDTO.getData());
        return ResponseDTO.success(responseDTO.getData());
    }

    /**
     * 根据id获取用户信息
     * @param userDTO
     * @return
     */
    @Override
    public ResponseDTO<UserDTO> selectUserById(UserDTO userDTO) {
        if(userDTO == null || CommonUtil.isEmpty(userDTO.getId())){
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        User user = userMapper.selectByPrimaryKey(userDTO.getId());
        return ResponseDTO.success(CopyUtil.copy(user, UserDTO.class));
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public ResponseDTO<UserDTO> getLoginUser(String token){
        String value = stringRedisTemplate.opsForValue().get("USER_" + token);
        if(CommonUtil.isEmpty(value)){
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        UserDTO selectedUserDTO = JSON.parseObject(value, UserDTO.class);
        return ResponseDTO.success(selectedUserDTO);
    }

    /**
     * 退出登录操作
     * @param userDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> logout(UserDTO userDTO) {
        if(!CommonUtil.isEmpty(userDTO.getToken())){
            // token不为空  清除redis中数据
            stringRedisTemplate.delete("USER_" + userDTO.getToken());
        }
        return ResponseDTO.successByMsg(true, "退出登录成功！");
    }

    /**
     * 用户注册操作
     * @param userDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> register(UserDTO userDTO) {
        if(userDTO == null){
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 进行统一表单验证
        CodeMsg validate = ValidateEntityUtil.validate(userDTO);
        if(!validate.getCode().equals(CodeMsg.SUCCESS.getCode())){
            return ResponseDTO.errorByMsg(validate);
        }
        // 判断重复密码是否正确
        if(CommonUtil.isEmpty(userDTO.getRePassword())){
            return ResponseDTO.errorByMsg(CodeMsg.REPASSWORD_EMPTY);
        }
        if(!userDTO.getPassword().equals(userDTO.getRePassword())){
            return ResponseDTO.errorByMsg(CodeMsg.REPASSWORD_ERROR);
        }
        User user = CopyUtil.copy(userDTO, User.class);
        // 判断用户昵称是否存在
        if(isUsernameExist(user, "")){
            return ResponseDTO.errorByMsg(CodeMsg.USERNAME_EXIST);
        }
        // 设置主键id
        user.setId(UuidUtil.getShortUuid());
        // 保存数据到数据库中
        if(userMapper.insertSelective(user) == 0){
            return ResponseDTO.errorByMsg(CodeMsg.USER_REGISTER_ERROR);
        }
        return ResponseDTO.successByMsg(true, "注册成功，快登录体验吧！");
    }

    /**
     * 更新个人信息操作
     * @param userDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> save(UserDTO userDTO) {
        if(CommonUtil.isEmpty(userDTO.getId())){
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 判断是否登录了
        String value = stringRedisTemplate.opsForValue().get("USER_" + userDTO.getToken());
        if(CommonUtil.isEmpty(value)){
            return ResponseDTO.errorByMsg(CodeMsg.USER_SESSION_EXPIRED);
        }
        // 进行统一表单验证
        CodeMsg validate = ValidateEntityUtil.validate(userDTO);
        if(!validate.getCode().equals(CodeMsg.SUCCESS.getCode())){
            return ResponseDTO.errorByMsg(validate);
        }
        User user = CopyUtil.copy(userDTO, User.class);
        // 判断用户昵称是否存在
        if(isUsernameExist(user, user.getId())){
            return ResponseDTO.errorByMsg(CodeMsg.USERNAME_EXIST);
        }
        // 修改数据库中数据
        if(userMapper.updateByPrimaryKeySelective(user) == 0){
            return ResponseDTO.errorByMsg(CodeMsg.USER_EDIT_ERROR);
        }
        // 更细Redis中数据
        stringRedisTemplate.opsForValue().set("USER_" + userDTO.getToken(), JSON.toJSONString(userMapper.selectByPrimaryKey(user.getId())), 3600, TimeUnit.SECONDS);
        return ResponseDTO.successByMsg(true, "保存成功！");
    }

    /**
     * 注销账号操作
     * @param userDTO
     * @return
     */
    @Override
    public ResponseDTO<Boolean> cancelUser(UserDTO userDTO) {
        if(CommonUtil.isEmpty(userDTO.getId())) {
            return ResponseDTO.errorByMsg(CodeMsg.DATA_ERROR);
        }
        // 删除该用户的好友信息
        FriendExample friendExample = new FriendExample();
        FriendExample.Criteria c1 = friendExample.createCriteria();
        c1.andReceiveUserEqualTo(userDTO.getId());
        FriendExample.Criteria c2 = friendExample.createCriteria();
        c2.andApplyUserEqualTo(userDTO.getId());
        friendExample.or(c2);
        List<Friend> friendList = friendMapper.selectByExample(friendExample);
        for(Friend friend : friendList) {
            friendService.deleteFriend(CopyUtil.copy(friend, FriendDTO.class));
        }
        // 删除群聊数据
        GroupItemExample groupItemExample = new GroupItemExample();
        groupItemExample.createCriteria().andUserIdEqualTo(userDTO.getId());
        List<GroupItem> groupItemList = groupItemMapper.selectByExample(groupItemExample);
        List<String> groupIdList = groupItemList.stream().distinct().map(e -> e.getGroupId()).collect(Collectors.toList());
        for(String groupId : groupIdList) {
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setId(groupId);
            groupDTO.setUserId(userDTO.getId());
            groupService.exitGroup(groupDTO);
        }
        // 删除用户数据
        if(userMapper.deleteByPrimaryKey(userDTO.getId()) == 0) {
            return ResponseDTO.errorByMsg(CodeMsg.USER_DELETE_ERROR);
        }
        return ResponseDTO.successByMsg(true, "注销用户成功！");
    }

    /**
     * 判断用户昵称是否重复
     * @param user
     * @param id
     * @return
     */
    public Boolean isUsernameExist(User user, String id) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> selectedUserList = userMapper.selectByExample(userExample);
        if(selectedUserList != null && selectedUserList.size() > 0) {
            if(selectedUserList.size() > 1){
                return true; //出现重名
            }
            if(!selectedUserList.get(0).getId().equals(id)) {
                return true; //出现重名
            }
        }
        return false;//没有重名
    }
}
