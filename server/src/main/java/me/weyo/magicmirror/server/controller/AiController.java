package me.weyo.magicmirror.server.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

import org.json.JSONObject;

/**
 * @author WeYo
 */
public class AiController implements Callable<String> {
    
    private String message;

    public AiController(String message) {
        this.message = message;
    }

    @Override
    public String call() throws Exception {
        JSONObject jsonObj = new JSONObject(message);
        String rurl = "http://www.tuling123.com/openapi/api?key="
                + jsonObj.getString("key") 
                + "&info=" + jsonObj.getString("info")
                + "&loc=" + jsonObj.getString("loc")
                + "&userid=" + jsonObj.getInt("id");
        
        URL url = new URL(rurl);
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(
                           new InputStreamReader(conn.getInputStream()));

        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine + "\n");
        }
        br.close();
        jsonObj = new JSONObject(sb.toString());
        
        String ret = null;
        switch (jsonObj.getInt("code")) {
        case 100000:
            ret = "0|" + jsonObj.getString("text");
            break;
        case 200000:
            ret = "2|" + jsonObj.getString("text") + "|" + jsonObj.getString("url");
            break;
        default:
            ret = "0|" + jsonObj.getString("对不起，亲，我现在还听不懂这句话");
            break;
        }
        
        return ret;
    }

}
