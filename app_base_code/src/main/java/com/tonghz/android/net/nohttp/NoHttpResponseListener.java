package com.tonghz.android.net.nohttp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.tonghz.android.utils.StringUtils;
import com.tonghz.android.widgets.ToastUtils;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.net.ProtocolException;

/**
 * NoHttp网络响应
 * Created by TongHuiZe on 2016/3/30.
 */
public class NoHttpResponseListener<T> implements OnResponseListener<T> {
    private Context mContext;
    private Request mRequest;
    private OnHttpResponseListener<T> mResponseListener;

    /**
     * ProgressDialog进度条
     */
    private ProgressDialog mProgressDialog;

    /**
     * 是否显示
     */
    private boolean isShowing;

    /**
     * 构造函数
     *
     * @param context   上下文对象
     * @param request   网络请求
     * @param listener  响应监听
     * @param isShowing 是否显示进度对话框
     * @param isCancel  是否可取消进度条
     */
    public NoHttpResponseListener(Context context, Request<T> request, OnHttpResponseListener<T> listener,
                                  boolean isShowing, boolean isCancel) {
        mContext = context;
        this.mRequest = request;
        /*
         * 创建ProgressDialog
         */
        if (context != null && isShowing)
            createDialog(context, isCancel, null);

        this.mResponseListener = listener;
        this.isShowing = isShowing;
    }

    /**
     * 构造函数
     *
     * @param context   上下文对象
     * @param request   网络请求
     * @param listener  响应监听
     * @param isShowing 是否显示进度对话框
     * @param message   提示信息
     * @param isCancel  是否可取消进度条
     */
    public NoHttpResponseListener(Context context, Request<T> request, OnHttpResponseListener<T> listener,
                                  boolean isShowing, CharSequence message, boolean isCancel) {
        mContext = context;
        this.mRequest = request;
        /*
         * 创建ProgressDialog
         */
        if (context != null && isShowing)
            createDialog(context, isCancel, message);

        this.mResponseListener = listener;
        this.isShowing = isShowing;
    }

    @Override
    public void onStart(int what) {
        if (isShowing && mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (mResponseListener != null)
            mResponseListener.onSucceed(what, response);
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        if (exception instanceof NetworkError) {
            // 网络不好
            ToastUtils.showToast(mContext, "请检查网络。");
        } else if (exception instanceof TimeoutError) {
            // 请求超时
            ToastUtils.showToast(mContext, "请求超时，网络不好或者服务器不稳定。");
        } else if (exception instanceof UnKnownHostError) {
            // 找不到服务器
            ToastUtils.showToast(mContext, "未发现指定服务器。");
        } else if (exception instanceof URLError) {
            // URL是错的
            ToastUtils.showToast(mContext, "URL错误。");
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            ToastUtils.showToast(mContext, "没有发现缓存。");
        } else if (exception instanceof ProtocolException) {
            ToastUtils.showToast(mContext, "系统不支持的请求方式。");
        } else {
            ToastUtils.showToast(mContext, "未知错误。");
        }
        Logger.e("错误：" + exception.getMessage());

        if (mResponseListener != null)
            mResponseListener.onFailed(what, url, tag, exception, responseCode, networkMillis);
    }

    @Override
    public void onFinish(int what) {
        if (isShowing && mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    /**
     * 创建ProgressDialog
     *
     * @param context  上下文
     * @param isCancel 是否可取消
     * @param message  提示信息
     */
    private void createDialog(Context context, boolean isCancel, CharSequence message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
        }
        mProgressDialog.setCancelable(isCancel);
        if (isCancel) {
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mRequest != null)
                        mRequest.cancel();
                }
            });
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(StringUtils.isEmpty(message) ? "正在请求，请稍候..." : message);
    }

}
