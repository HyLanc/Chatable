package com.Chatable.controller.web;

import com.Chatable.service.IFriendService;
import com.Chatable.dto.FriendDTO;
import com.Chatable.dto.ResponseDTO;
import com.Chatable.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
@RestController("WebFriendController")
@RequestMapping("/web/friend")
public class FriendController {

    @Resource
    private IFriendService friendService;

    /**
     * 发送好友申请
     * @param friendDTO
     * @return
     */
    @PostMapping("/apply")
    public ResponseDTO<Boolean> applyFriend(@RequestBody FriendDTO friendDTO){
        return friendService.applyFriend(friendDTO);
    }

    /**
     * 获取好友申请列表
     * @param userDTO
     * @return
     */
    @PostMapping("/get_apply")
    public ResponseDTO<List<FriendDTO>> getApplyFriendList(@RequestBody UserDTO userDTO){
        return friendService.getApplyFriendList(userDTO);
    }

    /**
     * 同意好友申请
     * @param friendDTO
     * @return
     */
    @PostMapping("/agree_apply")
    public ResponseDTO<Boolean> agreeApplyFriend(@RequestBody FriendDTO friendDTO){
        return friendService.agreeApplyFriend(friendDTO);
    }

    /**
     * 拒绝好友请求
     * @param friendDTO
     * @return
     */
    @PostMapping("/refuse_apply")
    public ResponseDTO<Boolean> refuseApplyFriend(@RequestBody FriendDTO friendDTO){
        return friendService.refuseApplyFriend(friendDTO);
    }

    /**
     * 获取当前登录用户的好友列表
     * @param userDTO
     * @return
     */
    @PostMapping("/get_friend")
    public ResponseDTO<List<FriendDTO>> selectFriendListByUserId(@RequestBody UserDTO userDTO){
        return friendService.selectFriendListByUserId(userDTO);
    }

    /**
     * 删除好友操作
     * @param friendDTO
     * @return
     */
    @PostMapping("/delete")
    public ResponseDTO<Boolean> deleteFriend(@RequestBody FriendDTO friendDTO){
        return friendService.deleteFriend(friendDTO);
    }
}
