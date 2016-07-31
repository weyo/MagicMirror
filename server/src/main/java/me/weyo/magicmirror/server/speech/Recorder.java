package me.weyo.magicmirror.server.speech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.weyo.magicmirror.server.service.AiService;
import me.weyo.magicmirror.server.service.WebSocketService;

/**
 * @author WeYo
 */
public class Recorder implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Recorder.class);

    private WebSocketService webSocketService;
    private AiService aiService;

    public Recorder(WebSocketService wsService, AiService aiService) {
        this.webSocketService = wsService;
        this.aiService = aiService;
    }

    @Override
    public void run() {
        while (true) {
            String[] ins = { "0|你好，我是你的智能机器人小镜，很高兴为你服务。", 
                    "1|帮我查一下后天从上海到北京的机票",
                    "0|对不起，我现在还不能回答这个问题，但请给我一些时间来写一个很长但最后什么都没有的剧本。", 
                    "2|http://touch.qunar.com/h5/train/trainList?startStation=%E4%B8%8A%E6%B5%B7&endStation=%E5%8C%97%E4%BA%AC&searchType=stasta&date=2016-07-25&sort=3&wakeup=1&scheme=9ba0d4a5501039c836e0f6ebd7591c3cdf530395e34774be803ca3aa5d8d7dd8cac60ac41d638ae883c0e0ee879395d55d2b3c5e92a67cf5b9ad2b2ca2ee2fbef6d0f1b5db757f11d64b5df0cfa472a6b1cdf31734bf69946633d45fd62711279bb591494637864aa80089a15ef4dc4c823dadfcf4b93547ce15b49a1d54cb3f",
                    "1|你是谁？"};
            String info = ins[(int)(Math.random() * 5)];
            aiService.request(info);
            webSocketService.sendMessage(info);
            webSocketService.sendMessage(aiService.getResponse());
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                LOG.info("WebSocket 消息线程异常", e);
            }
        }
    }

}
