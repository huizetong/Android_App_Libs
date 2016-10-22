package com.tonghz.android.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.tonghz.android.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 响应实体
 * Created by TongHuiZe on 2016/3/30.
 */
public class ResponseBean<T> {
    private String describe;
    private T obj;
    private String objcount;
    private List<T> objlist;
    private String status;

    public static <T> T parseObject(String jsonStr, Type typeOfT) {
        // 判断json字符串是否为空
        if (StringUtils.isEmpty(jsonStr))
            return null;

        T result;
        Gson gson = new Gson();
        try {
            result = gson.fromJson(jsonStr, typeOfT);
        } catch (Exception localException) {
            localException.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("status", "-1");
            map.put("describe", "数据解析异常！");
            map.put("obj", null);
            map.put("objlist", null);
            map.put("objcount", null);
            result = gson.fromJson(gson.toJson(map), typeOfT);
        }
        return result;
    }

    public boolean isSuccess() {
        return TextUtils.equals("1", getStatus());
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getObjCount() {
        return objcount;
    }

    public void setObjCount(String objCount) {
        this.objcount = objCount;
    }

    public List<T> getObjList() {
        return objlist;
    }

    public void setObjList(List<T> objList) {
        this.objlist = objList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "describe='" + describe + '\'' +
                ", obj=" + obj +
                ", objcount='" + objcount + '\'' +
                ", objlist=" + objlist +
                ", status='" + status + '\'' +
                '}';
    }
}
