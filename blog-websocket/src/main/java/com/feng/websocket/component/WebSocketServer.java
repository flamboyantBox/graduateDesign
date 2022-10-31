package com.feng.websocket.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.Objects;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.common.util.HTMLUtils;
import com.feng.common.util.IpUtils;
import com.feng.websocket.client.ChatRecordClient;
import com.feng.websocket.constant.ChatTypeEnum;
import com.feng.websocket.pojo.dto.ChatRecordDTO;
import com.feng.websocket.pojo.dto.RecallMessageDTO;
import com.feng.websocket.pojo.dto.WebSocketMessageDTO;
import com.feng.websocket.pojo.entity.ChatRecord;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mr.Feng
 * @date 10/20/2022 6:36 PM
 */
@ServerEndpoint(value = "/websocket", configurator = WebSocketServer.ChatConfigurator.class)
@Component
public class WebSocketServer {

    private static ChatRecordClient chatRecordClient;

    @Autowired
    public void setChatRecordClient(ChatRecordClient chatRecordClient) {
        WebSocketServer.chatRecordClient = chatRecordClient;
    }

//    @PostConstruct
//    public void init(){
//        webSocketServer = this;
//    }

    /**
     * 用户session
     */
    private Session session;

    /**
     * 用户session集合
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws IOException {
        System.out.println("加入连接");
        this.session = session;
        webSocketSet.add(this);
        System.out.println("当前用户人数=" + webSocketSet.size());
        // 更新在线人数
        updateOnlineCount();

        // 加载历史聊天记录
        ChatRecordDTO chatRecordDTO = listChatRecords(endpointConfig);

        // 发送消息
        WebSocketMessageDTO messageDTO = WebSocketMessageDTO.builder()
                .type(ChatTypeEnum.HISTORY_RECORD.getType())
                .data(chatRecordDTO)
                .build();
        broadcastMessage(messageDTO);
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        // 更新在线人数
        webSocketSet.remove(this);
        updateOnlineCount();
    }
    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        WebSocketMessageDTO messageDTO = JSON.parseObject(message, WebSocketMessageDTO.class);
        switch (Objects.requireNonNull(ChatTypeEnum.getChatType(messageDTO.getType()))) {
            case SEND_MESSAGE:
                // 发送消息
                ChatRecord chatRecord = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), ChatRecord.class);
                // 过滤html标签
                chatRecord.setContent(HTMLUtils.deleteTag(chatRecord.getContent()));
                chatRecordClient.saveChatRecord(chatRecord);
                messageDTO.setData(chatRecord);
                // 广播消息
                broadcastMessage(messageDTO);
                break;
            case RECALL_MESSAGE:
                // 撤回消息
                RecallMessageDTO recallMessage = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), RecallMessageDTO.class);
                // 删除记录
                chatRecordClient.deleteChatRecord(recallMessage.getId());
                // 广播消息
                broadcastMessage(messageDTO);
                break;
            case HEART_BEAT:
                // 心跳消息
                messageDTO.setData("pong");
                session.getBasicRemote().sendText(JSON.toJSONString(JSON.toJSONString(messageDTO)));
            default:
                break;
        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 获取客户端真实ip
     */
    public static class ChatConfigurator extends ServerEndpointConfig.Configurator {

        public static String HEADER_NAME = "X-Real-IP";

        @Override
        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
            try {
                String firstFoundHeader = request.getHeaders().get(HEADER_NAME.toLowerCase()).get(0);
                sec.getUserProperties().put(HEADER_NAME, firstFoundHeader);
            } catch (Exception e) {
                sec.getUserProperties().put(HEADER_NAME, "未知ip");
            }
        }
    }

    /**
     * 更新在线人数
     *
     * @throws IOException io异常
     */
    @Async
    public void updateOnlineCount() throws IOException {
        // 获取当前在线人数
        WebSocketMessageDTO messageDTO = WebSocketMessageDTO.builder()
                .type(ChatTypeEnum.ONLINE_COUNT.getType())
                .data(webSocketSet.size())
                .build();
        // 广播消息
        broadcastMessage(messageDTO);
    }

    /**
     * 广播消息 推送给所有的用户
     *
     * @param messageDTO 消息dto
     * @throws IOException io异常
     */
    private void broadcastMessage(WebSocketMessageDTO messageDTO) throws IOException {
        for (WebSocketServer webSocketServer : webSocketSet) {
            synchronized (webSocketServer.session) {
                webSocketServer.session.getBasicRemote().sendText(JSON.toJSONString(messageDTO));
            }
        }
    }

    /**
     * 加载历史聊天记录
     *
     * @param endpointConfig 配置
     * @return 加载历史聊天记录
     */
    private ChatRecordDTO listChatRecords(EndpointConfig endpointConfig) {
        // 获取聊天历史记录
        List<ChatRecord> chatRecordList = WebSocketServer.chatRecordClient.chatRecordList();
        // 获取当前用户ip
        String ipAddress = endpointConfig.getUserProperties().get(ChatConfigurator.HEADER_NAME).toString();
        return ChatRecordDTO.builder()
                .chatRecordList(chatRecordList)
                .ipAddress(ipAddress)
                .ipSource(IpUtils.getIpSource(ipAddress))
                .build();
    }
}
