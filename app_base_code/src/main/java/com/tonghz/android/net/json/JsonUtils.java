package com.tonghz.android.net.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tonghz.android.utils.LogUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON工具类（使用Google GSON库）
 * Created by TongHuiZe on 2016/3/30.
 */
public class JsonUtils {
    /**
     * Object转JSON
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String objectToJson(Object obj) {
        String json = null;
        try {
            Gson gson = new Gson();
            json = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * JSON解析成Java Bean
     *
     * @param jsonStr  JSON字符串
     * @param classOfT 类型
     * @param <T>      泛型类型
     * @return Java Bean
     */
    public static <T> T jsonToObject(String jsonStr, Class<T> classOfT) {
        T result = null;
        try {
            Gson gson = new Gson();
            result = gson.fromJson(jsonStr, classOfT);
        } catch (Exception e) {
            LogUtils.e(JsonUtils.class, "JSON解析异常！！！");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * JSON解析成指定的泛型类型
     *
     * @param jsonStr JSON字符串
     * @param typeOfT Type类型
     * @param <T>     泛型
     * @return 指定的泛型类型
     */
    public static <T> T jsonToObject(String jsonStr, Type typeOfT) {
        T result = null;
        try {
            Gson gson = new Gson();
            result = gson.fromJson(jsonStr, typeOfT);
        } catch (Exception e) {
            LogUtils.e(JsonUtils.class, "JSON解析异常！！！");
            e.printStackTrace();
        }
        return result;
    }

    @Deprecated
    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = new HashMap<>();
        try {
            Gson gson = new Gson();
            map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Deprecated
    public static List<Map<String, Object>> jsonToList(String json) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
