package me.weyo.magicmirror.server.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.weyo.magicmirror.server.service.CommandService;

/**
 * 处理通过 Web 端访问的 AI 命令请求
 * 
 * @author WeYo
 */
public class HttpCommandController extends HttpServlet {
    
    /** Serial ID */
    private static final long serialVersionUID = 7116249330326069458L;
    private static final Logger LOG = LoggerFactory.getLogger(HttpCommandController.class);
    private CommandService cmdService;
    
    public void init() throws ServletException {
        this.cmdService = CommandService.INSTANCE;
    }

    public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");
        String command = new String(message.getBytes("ISO-8859-1"), "utf-8");
        LOG.debug("Web 端收到命令: " + command);
        
        cmdService.execute(command);
    }

}
