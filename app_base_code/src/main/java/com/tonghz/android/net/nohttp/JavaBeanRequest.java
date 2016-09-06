package com.tonghz.android.net.nohttp;

import com.tonghz.android.bean.ResponseBean;
import com.tonghz.android.utils.LogUtils;
import com.tonghz.android.utils.StringUtils;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import java.lang.reflect.Type;

/**
 * Java Bean请求
 * Created by TongHuiZe on 2016/5/5.
 */
public class JavaBeanRequest<T> extends RestRequest<T> {
    private Type type;
    private String mRequestTag = JavaBeanRequest.class.getSimpleName();

    public JavaBeanRequest(String url, Type type) {
        this(url, RequestMethod.GET, type);
    }

    public JavaBeanRequest(String url, RequestMethod requestMethod, Type type) {
        super(url, requestMethod);
        this.type = type;

        // 设置接收数据类型
        setAccept(Headers.HEAD_VALUE_ACCEPT_APPLICATION_JSON);
    }


    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) {
        T result = null;
        String jsonStr = StringRequest.parseResponseString(responseHeaders, responseBody);
        LogUtils.i(getRequestTag(), "jsonStr=" + jsonStr);
        if (!StringUtils.isEmpty(jsonStr))
            result = ResponseBean.parseObject(jsonStr, this.type);
        return result;
    }

    public String getRequestTag() {
        return mRequestTag;
    }

    public void setRequestTag(String requestTag) {
        this.mRequestTag = requestTag;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();

    }

}
