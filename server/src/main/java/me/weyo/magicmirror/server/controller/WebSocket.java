package me.weyo.magicmirror.server.controller;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/websocket")
public class WebSocket {
    /** 在线客户端计数 */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();
    private static final Logger LOG = LoggerFactory.getLogger(WebSocket.class);

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    public static int getOnlineCount() {
        return onlineCount.get();
    }
    
    public static CopyOnWriteArraySet<WebSocket> getWebSocketSet() {
        return webSocketSet;
    }

    /**
     * 连接建立成功调用的方法
     * 
     * @param session
     *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this); // 加入set中
        onlineCount.incrementAndGet(); // 在线数加1
        LOG.debug("有新连接加入！当前在线人数为" + onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        onlineCount.decrementAndGet(); // 在线数减1
        LOG.debug("有一连接关闭！当前在线人数为" + onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     * 
     * @param message
     *            客户端发送过来的消息
     * @param session
     *            可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOG.info("来自客户端的消息:" + message);
    }

    /**
     * 发生错误时调用
     * 
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOG.error("发生错误", error);
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * 
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}