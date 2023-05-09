package com.Chatable.ws;

import com.Chatable.domain.GroupItemExample;
import com.Chatable.enums.ChatTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author 杨杨吖
 * @QQ 823208782
 * @WX yjqi12345678
 * @create 2021-02-24 23:05
 */

/**
 * 服务端WebSocket对象
 */
@ServerEndpoint(value = "/chat/{param}")
@Component
public class ChatEndpoint {

    //用来存储每一个客户端对象对应的ChatEndpoint对象  ConcurrentHashMap效率高，线程安全，但是key和value都不能为null
//    public static Map<String, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    public static CopyOnWriteArraySet<ChatEndpoint> webSocketCopyOnWriteArraySet = new CopyOnWriteArraySet<>();

    private String userId;

    //声明Session对象，通过该对象可以发送消息给指定的用户
    private Session session;


    /**
     * 连接建立时被调用
     * @param session
     * @param param
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("param")String param){
        //将局部的session对象赋值给成员session
        this.session = session;
        this.userId = param;
        //将当前登录用户存储到容器中
        webSocketCopyOnWriteArraySet.add(this);
    }


    /**
     * 接收到客户端发来的消息时被调用
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        try{
            JSONObject messageObject = JSON.parseObject(message);
            String receiver = messageObject.getString("receiver");
            String groupId = messageObject.getString("groupId");
            Integer chatType = messageObject.getInteger("chatType");
            String groupItem = messageObject.getString("groupItem");
            if(ChatTypeEnum.SINGLE.getCode().equals(chatType)) {
                // 单聊
                for(ChatEndpoint chatEndpoint : webSocketCopyOnWriteArraySet){
                    if(chatEndpoint.userId.equals(receiver)){
                        chatEndpoint.session.getBasicRemote().sendText(JSONObject.toJSONString(messageObject));
                    }
                }
            } else if(ChatTypeEnum.GROUP.getCode().equals(chatType)) {
                // 群聊
                GroupItemExample groupItemExample = new GroupItemExample();
                groupItemExample.createCriteria().andGroupIdEqualTo(groupId);
                String[] groupItemList = groupItem.split(",");
                // 给群里每个人发送消息
                for(String userId : groupItemList) {
                    for(ChatEndpoint chatEndpoint : webSocketCopyOnWriteArraySet){
                        if(chatEndpoint.userId.equals(userId)){
                            chatEndpoint.session.getBasicRemote().sendText(JSONObject.toJSONString(messageObject));
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    /**
     * 发送系统消息
     * @param receiverId
     */
    public void sendSystemMessage(String receiverId) {
        try{
            JSONObject messageObject = new JSONObject();
            for(ChatEndpoint chatEndpoint : webSocketCopyOnWriteArraySet){
                if(chatEndpoint.userId.equals(receiverId)){
                    chatEndpoint.session.getBasicRemote().sendText(JSONObject.toJSONString(messageObject));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭时被调用
     */
    @OnClose
    public void onClose(){
        webSocketCopyOnWriteArraySet.remove(this);
    }
}
