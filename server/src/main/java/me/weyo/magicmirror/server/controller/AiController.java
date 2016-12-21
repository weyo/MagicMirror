package me.weyo.magicmirror.server.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.Callable;

import org.json.JSONObject;

import me.weyo.magicmirror.server.Parameters;

/**
 * @author WeYo
 */
public class AiController implements Callable<String> {
    
    private String message;
    
    private Map<String, String> aiParams = Parameters.getAiParams();

    public AiController(String message) {
        this.message = message;
    }

    @Override
    public String call() throws Exception {
        String rurl = "http://www.tuling123.com/openapi/api?key="
                + aiParams.get("key")
                + "&info=" + message
                + "&loc=" + aiParams.get("loc")
                + "&userid=" + aiParams.get("id");
        
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
        
        JSONObject jsonObj = new JSONObject(sb.toString());
        
        String ret = null;
        switch (jsonObj.getInt("code")) {
        case 100000:
            ret = "0|" + jsonObj.getString("text");
            break;
        case 200000:
            ret = "2|" + jsonObj.getString("text") + "|" + jsonObj.getString("url");
            break;
        default:
            ret = "0|对不起，亲，我现在还听不懂这句话";
            break;
        }
        
        return ret;
    }

}
