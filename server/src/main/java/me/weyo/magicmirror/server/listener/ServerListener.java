package me.weyo.magicmirror.server.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.weyo.magicmirror.server.service.AiService;
import me.weyo.magicmirror.server.service.CommandService;
import me.weyo.magicmirror.server.service.WebSocketService;
import me.weyo.magicmirror.server.speech.Recorder;  

/**
 * @author WeYo
 */
public class ServerListener implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(ServerListener.class);
    private static Recorder recorder;
    private ExecutorService exec;
    
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        recorder.shutdown();

        exec.shutdown();
        try {
            exec.awaitTermination(3, TimeUnit.SECONDS);
            LOG.info("魔镜已关闭。");
        } catch (InterruptedException e) {
            LOG.error("魔镜主线程退出异常", e);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        exec = Executors.newSingleThreadExecutor();
        recorder = new Recorder();
        recorder.registerWebSocketService(new WebSocketService())
                .registerAiService(new AiService())
                .registerCommandService(CommandService.INSTANCE);
        exec.execute(recorder);
    }

}
