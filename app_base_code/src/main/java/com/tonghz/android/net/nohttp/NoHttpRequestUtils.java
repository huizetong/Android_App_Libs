package com.tonghz.android.net.nohttp;

import android.content.Context;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * NoHttp网络请求工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class NoHttpRequestUtils {
    public static final int HTTP_WHAT_DEFAULT = 0x88;

    /**
     * NoHttpRequestUtils实例
     */
    private volatile static NoHttpRequestUtils mInstance;

    /**
     * 请求队列
     */
    private RequestQueue mRequestQueue;

    /**
     * 下载队列
     */
    private static DownloadQueue mDownloadQueue;

    /**
     * 构造函数（私有化）
     */
    private NoHttpRequestUtils() {
        mRequestQueue = NoHttp.newRequestQueue();
    }

    /**
     * 获取NoHttpRequestUtils实例（单例模式）
     *
     * @return NoHttpRequestUtils对象
     */
    public static NoHttpRequestUtils getInstance() {
        if (mInstance == null) {
            synchronized (NoHttpRequestUtils.class) {
                if (mInstance == null)
                    mInstance = new NoHttpRequestUtils();
            }
        }
        return mInstance;
    }

    /**
     * 获取下载队列.
     */
    public static DownloadQueue getDownloadQueue() {
        if (mDownloadQueue == null) {
            synchronized (NoHttpRequestUtils.class) {
                if (mDownloadQueue == null)
                    mDownloadQueue = NoHttp.newDownloadQueue();
            }
        }
        return mDownloadQueue;
    }

    /**
     * 添加请求到请求队列
     *
     * @param context   上下文对象
     * @param request   请求
     * @param listener  响应监听
     * @param isShowing 是否显示进度条
     * @param isCancel  是否取消进度条
     * @param <T>       泛型类型
     */
    public <T> void add(Context context, Request<T> request, OnHttpResponseListener<T> listener,
                        boolean isShowing, boolean isCancel) {
        add(context, HTTP_WHAT_DEFAULT, request, listener, isShowing, isCancel);
    }

    /**
     * 添加请求到请求队列
     *
     * @param context   上下文对象
     * @param what      请求标志
     * @param request   请求
     * @param listener  响应监听
     * @param isShowing 是否显示进度条
     * @param isCancel  是否取消进度条
     * @param <T>       泛型类型
     */
    public <T> void add(Context context, int what, Request<T> request, OnHttpResponseListener<T> listener,
                        boolean isShowing, boolean isCancel) {
        mRequestQueue.add(what, request, new NoHttpResponseListener<>(context, request, listener, isShowing, isCancel));
    }

    /**
     * 添加请求到请求队列
     *
     * @param context   上下文对象
     * @param request   请求
     * @param listener  响应监听
     * @param isShowing 是否显示进度条
     * @param message   提示信息
     * @param isCancel  是否取消进度条
     * @param <T>       泛型类型
     */
    public <T> void add(Context context, Request<T> request, OnHttpResponseListener<T> listener,
                        boolean isShowing, CharSequence message, boolean isCancel) {
        add(context, HTTP_WHAT_DEFAULT, request, listener, isShowing, message, isCancel);
    }

    /**
     * 添加请求到请求队列
     *
     * @param context   上下文对象
     * @param what      请求标志
     * @param request   请求
     * @param listener  响应监听
     * @param isShowing 是否显示进度条
     * @param message   提示信息
     * @param isCancel  是否取消进度条
     * @param <T>       泛型类型
     */
    public <T> void add(Context context, int what, Request<T> request, OnHttpResponseListener<T> listener,
                        boolean isShowing, CharSequence message, boolean isCancel) {
        mRequestQueue.add(what, request, new NoHttpResponseListener<>(context, request, listener, isShowing, message, isCancel));
    }

    /**
     * 取消指定的请求
     *
     * @param sign 标志
     */
    public void cancelBySign(Object sign) {
        mRequestQueue.cancelBySign(sign);
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        mRequestQueue.cancelAll();
    }

    /**
     * 停止请求队列
     */
    public void stopQueue() {
        mRequestQueue.stop();
    }

}
