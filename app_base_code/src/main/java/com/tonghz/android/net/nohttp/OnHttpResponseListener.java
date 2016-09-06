package com.tonghz.android.net.nohttp;


import com.yolanda.nohttp.rest.Response;

/**
 * Http响应监听
 * Created by TongHuiZe on 2016/3/30.
 */
public interface OnHttpResponseListener<T> {

    void onSucceed(int what, Response<T> response);

    void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis);
}
