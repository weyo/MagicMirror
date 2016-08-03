package me.weyo.magicmirror.server.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.weyo.magicmirror.server.service.AiService;
import me.weyo.magicmirror.server.service.CommandService;
import me.weyo.magicmirror.server.service.WebSocketService;
import me.weyo.magicmirror.server.speech.Recorder;

/**
 * @author WeYo
 */
public class InitController extends HttpServlet {

    /** Serial ID */
    private static final long serialVersionUID = 3827530190760653764L;

    private static final Logger LOG = LoggerFactory.getLogger(InitController.class);

    public void init() throws ServletException {

        ExecutorService exec = Executors.newSingleThreadExecutor();
        Recorder recorder = new Recorder();
        recorder.registerWebSocketService(new WebSocketService()).registerAiService(new AiService())
                .registerCommandService(CommandService.INSTANCE);
        exec.execute(recorder);
        exec.shutdown();
        
        LOG.info("初始化完成");
    }

}
