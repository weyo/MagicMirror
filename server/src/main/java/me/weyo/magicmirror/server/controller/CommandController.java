package me.weyo.magicmirror.server.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author WeYo
 */
public class CommandController extends HttpServlet {
    
    /** Serial ID */
    private static final long serialVersionUID = 7116249330326069458L;
    private static final Logger LOG = LoggerFactory.getLogger(CommandController.class);

    public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");
        String decodeMessage = new String(message.getBytes("ISO-8859-1"), "utf-8");
        LOG.info(decodeMessage);
    }

}
