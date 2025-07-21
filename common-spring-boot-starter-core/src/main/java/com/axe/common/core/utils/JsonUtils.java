package com.axe.common.core.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.Collection;
import java.util.Map;

/**
 * @Description: TODO
 * @Date: 2025/7/16
 * @Author: Sxt
 * @Version: v1.0
 */
public class JsonUtils {
    private JsonUtils(){}

    public static String convertMapToJson(Map<?,?> map){
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toJSONString();
    }


    public static String convertCollectionToJson(Collection<?> collection){
        JSONArray json = new JSONArray(collection);
        return json.toString();
    }
}
