package me.weyo.magicmirror.server.speech;

import org.json.JSONException;
import org.json.JSONObject;
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

    @Override
    public void run() {
        while (true) {
            String cmd = null;
            try {
                cmd = cmdService.takeCommand();
                aiService.request(cmd);
                JSONObject jsonObj = new JSONObject(cmd);
                webSocketService.sendMessage("1|" + jsonObj.getString("info"));
                webSocketService.sendMessage(aiService.getResponse());
            } catch (InterruptedException e) {
                LOG.error("WebSocket 消息线程异常", e);
            } catch (JSONException e) {
                LOG.error("Recorder 线程 JSON 解析异常", e);
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

}
