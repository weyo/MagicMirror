package me.weyo.magicmirror.server.service;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 基于枚举的单例模式
 * 
 * @author WeYo
 */
public enum CommandService {
    
    INSTANCE;

    private static PriorityBlockingQueue<String> cmds = new PriorityBlockingQueue<String>();
    
    public void execute(String command) {
        cmds.put(command);
    }
    
    public String takeCommand() throws InterruptedException {
        return cmds.poll(2, TimeUnit.SECONDS);
    }

}
