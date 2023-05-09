package com.Chatable.service;

import com.Chatable.dto.FriendDTO;
import com.Chatable.dto.ResponseDTO;
import com.Chatable.dto.UserDTO;

import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
public interface IFriendService {

    // 发送好友申请
    ResponseDTO<Boolean> applyFriend(FriendDTO friendDTO);

    // 获取好友申请列表
    ResponseDTO<List<FriendDTO>> getApplyFriendList(UserDTO userDTO);

    // 同意好友申请
    ResponseDTO<Boolean> agreeApplyFriend(FriendDTO friendDTO);

    // 拒绝好友请求
    ResponseDTO<Boolean> refuseApplyFriend(FriendDTO friendDTO);

    // 根据用户id查询好友列表
    ResponseDTO<List<FriendDTO>> selectFriendListByUserId(UserDTO userDTO);

    // 删除好友操作
    ResponseDTO<Boolean> deleteFriend(FriendDTO friendDTO);
}
