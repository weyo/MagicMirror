package me.weyo.magicmirror.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WeYo
 */
public abstract class BaseController extends HttpServlet {

    /** Serial ID */
    private static final long serialVersionUID = 4773003481384602251L;
    
    public String contentType = "text/html";

    public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(this.contentType + ";charset=UTF-8");
        PrintWriter out = response.getWriter();
        URL url = new URL(request.getParameter("url"));
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(
                           new InputStreamReader(conn.getInputStream()));

        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            out.write(inputLine + "\n");
            sb.append(inputLine + "\n");
        }

        out.flush();
        out.close();
        br.close();
        
        doLog(sb.toString(), null);
    }

    public abstract void doLog(String msg, Object object);
    
}