package me.weyo.magicmirror.server.speech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.weyo.magicmirror.server.service.AiService;
import me.weyo.magicmirror.server.service.CommandService;
import me.weyo.magicmirror.server.service.WebSocketService;

/**
 * @author WeYo
 */
public class Recorder implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Recorder.class);

    private WebSocketService webSocketService;
    private AiService aiService;
    private CommandService cmdService;
    private volatile boolean isRunning = true;

    @Override
    public void run() {
        while (isRunning) {
            String cmd = null;
            try {
                cmd = cmdService.takeCommand();
                if (cmd == null) {
                    continue;
                }
                if (cmd.equals("")) {
                    webSocketService.sendMessage("1|" + cmd);
                    webSocketService.sendMessage("0|不说话的孩子不乖哦");
                    continue;
                }
                aiService.request(cmd);
                webSocketService.sendMessage("1|" + cmd);
                webSocketService.sendMessage(aiService.getResponse());
            } catch (InterruptedException e) {
                LOG.error("WebSocket 消息线程异常|cmd:" + cmd, e);
            }
            LOG.debug("已处理一次请求:" + cmd);
        }
    }

    public Recorder registerWebSocketService(WebSocketService wsService) {
        this.webSocketService = wsService;
        return this;
    }

    public Recorder registerAiService(AiService aiService) {
        this.aiService = aiService;
        return this;
    }

    public Recorder registerCommandService(CommandService commandService) {
        this.cmdService = commandService;
        return this;
    }

    public void shutdown() {
        this.isRunning = false;
    }

}
