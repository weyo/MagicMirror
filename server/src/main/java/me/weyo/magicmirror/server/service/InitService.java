package me.weyo.magicmirror.server.service;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.weyo.magicmirror.server.Parameters;

/**
 * 将 js 中配置参数传入后台
 * 
 * @author WeYo
 */
public class InitService {

    private static final Logger LOG = LoggerFactory.getLogger(InitService.class);
    private Map<String, String> aiParams = Parameters.getAiParams();

    public void initAi(String decodedAi) throws JSONException {
        JSONObject jsonObj = new JSONObject(decodedAi);
        aiParams.put("key", jsonObj.getString("key"));
        aiParams.put("loc", jsonObj.getString("loc"));
        aiParams.put("id", jsonObj.getString("id"));
        
        LOG.debug("ai 参数配置完成。");
    }

}
