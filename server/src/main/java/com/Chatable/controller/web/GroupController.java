package com.Chatable.controller.web;

import com.Chatable.dto.ChatDTO;
import com.Chatable.dto.GroupDTO;
import com.Chatable.dto.ResponseDTO;
import com.Chatable.service.IGroupService;
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
@RestController("WebGroupController")
@RequestMapping("/web/group")
public class GroupController {

    @Resource
    private IGroupService groupService;

    /**
     * 创建群聊操作
     * @param groupDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseDTO<Boolean> createGroup(@RequestBody GroupDTO groupDTO) {
        return groupService.createGroup(groupDTO);
    }

    /**
     * 发起群聊会话操作
     * @param groupDTO
     * @return
     */
    @PostMapping("/start")
    public ResponseDTO<ChatDTO> startGroupChat(@RequestBody GroupDTO groupDTO) {
        return groupService.startGroupChat(groupDTO);
    }

    /**
     * 获取当前登录用户的群聊列表
     * @param groupDTO
     * @return
     */
    @PostMapping("/list")
    public ResponseDTO<List<GroupDTO>> listGroupByToken(@RequestBody GroupDTO groupDTO) {
        return groupService.listGroupByToken(groupDTO);
    }

    /**
     * 根据id获取群聊信息
     * @param groupDTO
     * @return
     */
    @PostMapping("/get")
    public ResponseDTO<GroupDTO> getGroupById(@RequestBody GroupDTO groupDTO) {
        return groupService.getGroupById(groupDTO);
    }

    /**
     * 邀请用户加入群聊
     * @param groupDTO
     * @return
     */
    @PostMapping("/invite")
    public ResponseDTO<GroupDTO> inviteGroupUser(@RequestBody GroupDTO groupDTO) {
        return groupService.inviteGroupUser(groupDTO);
    }

    /**
     * 退出或解散群聊操作
     * @param groupDTO
     * @return
     */
    @PostMapping("/exit")
    public ResponseDTO<GroupDTO> exitGroup(@RequestBody GroupDTO groupDTO) {
        return groupService.exitGroup(groupDTO);
    }

    /**
     * 更新群聊信息操作
     * @param groupDTO
     * @return
     */
    @PostMapping("/save")
    public ResponseDTO<GroupDTO> saveGroup(@RequestBody GroupDTO groupDTO) {
        return groupService.save(groupDTO);
    }
}
