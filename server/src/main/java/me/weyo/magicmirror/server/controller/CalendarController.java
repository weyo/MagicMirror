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

public class CalendarController extends HttpServlet {

    /** Serial ID */
    private static final long serialVersionUID = 6683891537044452693L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/calendar");
        PrintWriter out = response.getWriter();
        String calendarUrl = request.getParameter("url");
        URL url = new URL(calendarUrl);
        URLConnection conn = url.openConnection();

        BufferedReader br = new BufferedReader(
                           new InputStreamReader(conn.getInputStream()));

        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            out.write(inputLine + "\n");
        }

        out.flush();
        out.close();
        br.close();

        System.out.println("Done ** " + new java.util.Date());
    }
}
