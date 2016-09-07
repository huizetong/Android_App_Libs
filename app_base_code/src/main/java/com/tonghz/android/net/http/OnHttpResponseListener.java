package com.tonghz.android.net.http;


import com.yolanda.nohttp.rest.Response;

/**
 * Http响应监听
 * Created by TongHuiZe on 2016/3/30.
 */
public interface OnHttpResponseListener<T> {

    void onSucceed(int what, Response<T> response);

    void onFailed(int what, Response<T> response);
}
