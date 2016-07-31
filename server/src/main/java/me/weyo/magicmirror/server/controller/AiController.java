package me.weyo.magicmirror.server.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

/**
 * @author WeYo
 */
public class AiController implements Callable<String> {

    @Override
    public String call() throws Exception {
        // TODO Auto-generated method stub
        String ret = null;
        
//        URL url = new URL(null);
//        URLConnection conn = url.openConnection();
//        BufferedReader br = new BufferedReader(
//                           new InputStreamReader(conn.getInputStream()));
//
//        String inputLine;
//        StringBuilder sb = new StringBuilder();
//        while ((inputLine = br.readLine()) != null) {
//            sb.append(inputLine + "\n");
//        }
//        br.close();
//        ret = sb.toString();
        
        return ret;
    }

}
