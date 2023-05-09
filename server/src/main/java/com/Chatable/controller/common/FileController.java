package com.Chatable.controller.common;

import com.Chatable.bean.CodeMsg;
import com.Chatable.dao.*;
import com.Chatable.domain.*;
import com.Chatable.dto.ResponseDTO;
import com.Chatable.enums.ChatShowEnum;
import com.Chatable.enums.ChatTypeEnum;
import com.Chatable.enums.MessageTypeEnum;
import com.Chatable.util.CommonUtil;
import com.Chatable.util.NonStaticResourceHttpRequestHandler;
import com.Chatable.util.UuidUtil;
import com.Chatable.ws.ChatEndpoint;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */
@RequestMapping("/file")
@RestController
public class FileController {

    @Value("${yjq.upload.file.path}")
    private String uploadFilePath;//文件保存位置

    @Resource
    private ChatMapper chatMapper;

    @Resource
    private GroupItemMapper groupItemMapper;

    @Resource
    private FileMessageMapper fileMessageMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private ChatEndpoint chatEndpoint;

    @Resource
    private MessageMapper messageMapper;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    public FileController(NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler) {
        this.nonStaticResourceHttpRequestHandler = nonStaticResourceHttpRequestHandler;
    }


    /**
     * 上传文件统一处理
     * @param file
     * @param userId
     * @param toId
     * @param chatId
     * @param request
     * @return
     */
    @PostMapping(value="/upload")
    public ResponseDTO<String> uploadPhoto(MultipartFile file, String userId, String toId, String chatId, HttpServletRequest request){
        if(file == null){
            return ResponseDTO.errorByMsg(CodeMsg.PHOTO_EMPTY);
        }
        //检查上传文件大小 不能超过300MB
        if(file.getSize() > 300 * 1024 * 1024) {
            return ResponseDTO.errorByMsg(CodeMsg.FILE_SURPASS_MAX_SIZE);
        }
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        String savePath = uploadFilePath + CommonUtil.getFormatterDate(new Date(), "yyyyMMdd") + "\\";
        File savePathFile = new File(savePath);
        if(!savePathFile.exists()){
            //若不存在改目录，则创建目录
            savePathFile.mkdir();
        }
        String filename = new Date().getTime()+"."+suffix;
        logger.info("保存文件的路径:{}",savePath + filename);
        String filepath = CommonUtil.getFormatterDate(new Date(), "yyyyMMdd") + "/" + filename;
        try {
            //将文件保存至指定目录
            file.transferTo(new File(savePath + filename));
            // 更新聊天和消息
            updateChatMessage(chatId, toId, userId, file, filepath);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.errorByMsg(CodeMsg.SAVE_FILE_EXCEPTION);
        }

        return ResponseDTO.success("文件上传成功！");
    }


    /**
     * 文件上传之后的聊天和消息的更新处理
     * @param chatId
     * @param toId
     * @param userId
     * @param file
     * @param filepath
     */
    public void updateChatMessage(String chatId, String toId, String userId, MultipartFile file, String filepath) {
        // 获取当前聊天信息
        Chat chat = chatMapper.selectByPrimaryKey(chatId);
        String messageId = UuidUtil.getShortUuid();
        // 存储文件消息
        FileMessage fileMessage = new FileMessage();
        fileMessage.setMessageId(messageId);
        fileMessage.setId(UuidUtil.getShortUuid());
        fileMessage.setName(file.getOriginalFilename());
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        fileMessage.setSuffix(suffix);
        fileMessage.setSize(file.getSize());
        fileMessage.setUrl(filepath);
        fileMessageMapper.insertSelective(fileMessage);
        // 根据单聊和群聊更新会话信息
        Message message = new Message();
        message.setId(messageId);
        message.setCreateTime(new Date());
        message.setContent(file.getOriginalFilename());
        message.setChatId(chatId);
        message.setMessageType(MessageTypeEnum.FILE.getCode());
        message.setSender(userId);
        if(ChatTypeEnum.SINGLE.getCode().equals(chat.getChatType())) {
            // 存储消息内容
            message.setReceiver(toId);
            messageMapper.insertSelective(message);
            // 单聊
            chat.setReceiverShow(ChatShowEnum.YES.getCode());
            chat.setSenderShow(ChatShowEnum.YES.getCode());
            // 增加未读消息数
            if(userId.equals(chat.getSender())) {
                chat.setUnreadReceiver(chat.getUnreadReceiver() + 1);
            } else if (userId.equals(chat.getReceiver())) {
                chat.setUnreadSender(chat.getUnreadSender() + 1);
            }
            chat.setLastTime(new Date());
            chatMapper.updateByPrimaryKey(chat);
            // 发送系统消息，让接受者发起者刷新聊天页面信息
            chatEndpoint.sendSystemMessage(chat.getReceiver());
            chatEndpoint.sendSystemMessage(chat.getSender());
        } else if (ChatTypeEnum.GROUP.getCode().equals(chat.getChatType())) {
            // 存储消息内容
            messageMapper.insertSelective(message);
            // 群聊 给此群其他成员增加未读消息数
            chat.setLastTime(new Date());
            chatMapper.updateByPrimaryKey(chat);
            GroupExample groupExample = new GroupExample();
            groupExample.createCriteria().andChatIdEqualTo(chat.getId());
            List<Group> groupList = groupMapper.selectByExample(groupExample);
            if(groupList == null || groupList.size() == 0) {
                return;
            }
            Group group = groupList.get(0);
            GroupItemExample groupItemExample = new GroupItemExample();
            groupItemExample.createCriteria().andUserIdNotEqualTo(userId).andGroupIdEqualTo(group.getId());
            List<GroupItem> groupItemList = groupItemMapper.selectByExample(groupItemExample);
            for(GroupItem groupItem : groupItemList) {
                groupItem.setUnreadCount(groupItem.getUnreadCount() + 1);
                groupItem.setShowChat(ChatShowEnum.YES.getCode());
                groupItemMapper.updateByPrimaryKeySelective(groupItem);
                // 发送系统消息，让群内所有用户刷新聊天页面信息
                chatEndpoint.sendSystemMessage(groupItem.getUserId());
            }
            chatEndpoint.sendSystemMessage(userId);
        }
    }

    /**
     * 文件统一下载类
     * @param id
     * @param response
     * @return
     */
    @GetMapping(value="/download")
    public void downloadFile(HttpServletResponse response, String id){
        FileMessage fileMessage = fileMessageMapper.selectByPrimaryKey(id);
        if(fileMessage == null) {
            return;
        }
        try {
            File file = new File(uploadFilePath, fileMessage.getUrl());
            response.setHeader("Content-Disposition","attachment;filename=" + new String(fileMessage.getName().getBytes("UTF-8"), "ISO8859-1"));
            writeFile(response, file);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印到浏览器上下载
     * @param response
     * @param file
     */
    public void writeFile(HttpServletResponse response, File file) {
        ServletOutputStream sos = null;
        FileInputStream aa = null;
        try {
            aa = new FileInputStream(file);
            sos = response.getOutputStream();
            // 读取文件问字节码
            byte[] data = new byte[(int) file.length()];
            IOUtils.readFully(aa, data);
            // 将文件流输出到浏览器
            IOUtils.write(data, sos);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                sos.close();
                aa.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/video")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="filename",required=true)String filename) throws Exception {
        String realPath = uploadFilePath + filename;

        Path filePath = Paths.get(realPath );
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!CommonUtil.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }
}
