package me.weyo.magicmirror.server.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.weyo.magicmirror.server.controller.AiController;

/**
 * @author WeYo
 */
public class AiService {
    
    private static final Logger LOG = LoggerFactory.getLogger(AiService.class);
    private Future<String> ai;

    public void request(String info) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        this.ai = exec.submit(new AiController(info));
        exec.shutdown();
    }

    public String getResponse() {
        try {
            return ai.get();
        } catch (InterruptedException e) {
            LOG.error("AiService 中断异常", e);
        } catch (ExecutionException e) {
            LOG.error("AiService 线程异常", e);
        }
        return null;
    }

}
