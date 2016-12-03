package me.weyo.magicmirror.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数配置
 * 
 * @author WeYo
 */
public class Parameters {

    private static Map<String, String> weatherParams = new HashMap<String, String>();
    private static Map<String, String> aqiParams = new HashMap<String, String>();
    private static Map<String, String> aiParams = new HashMap<String, String>();

    public static Map<String, String> getWeatherParams() {
        return weatherParams;
    }

    public static Map<String, String> getAqiParams() {
        return aqiParams;
    }

    public static Map<String, String> getAiParams() {
        return aiParams;
    }

}
