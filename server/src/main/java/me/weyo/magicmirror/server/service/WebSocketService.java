package me.weyo.magicmirror.server.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.weyo.magicmirror.server.controller.WebSocket;

/**
 * @author WeYo
 */
public class WebSocketService {
    
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketService.class);
    
    public void sendMessage(String message) {
        if (WebSocket.getOnlineCount() > 0) {
            for (WebSocket item : WebSocket.getWebSocketSet()) {
                try {
                    if (message != null) {
                        item.sendMessage(message);
                    }
                } catch (IOException e) {
                    LOG.error("WebSocketService 发送消息异常", e);
                }
            }
        } else {
            LOG.info("Nobody here...");
        }
    }

}
