package com.tonghz.android.net.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.tonghz.android.utils.LogUtils;
import com.tonghz.android.utils.StringUtils;
import com.tonghz.android.widgets.ToastUtils;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ParseError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.net.ProtocolException;
import java.util.Locale;

/**
 * NoHttp网络响应
 * Created by TongHuiZe on 2016/3/30.
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {
    private Context mContext;
    private Request mRequest;
    private OnHttpResponseListener<T> mResponseListener;

    /**
     * ProgressDialog进度条
     */
    private ProgressDialog mProgressDialog;

    /**
     * 构造函数
     *
     * @param context   上下文对象
     * @param request   网络请求
     * @param listener  响应监听
     * @param isShowing 是否显示进度对话框
     * @param isCancel  是否可取消进度条
     */
    public HttpResponseListener(Context context, Request<T> request, OnHttpResponseListener<T> listener,
                                boolean isShowing, boolean isCancel) {
        this.mContext = context;
        this.mRequest = request;
        /*
         * 创建ProgressDialog
         */
        if (context != null && isShowing)
            createDialog(context, isCancel, null);

        this.mResponseListener = listener;
    }

    /**
     * 构造函数
     *
     * @param activity  上下文对象
     * @param request   网络请求
     * @param listener  响应监听
     * @param isShowing 是否显示进度对话框
     * @param message   提示信息
     * @param isCancel  是否可取消进度条
     */
    public HttpResponseListener(Activity activity, Request<T> request, OnHttpResponseListener<T> listener,
                                boolean isShowing, CharSequence message, boolean isCancel) {
        this.mContext = activity;
        this.mRequest = request;
        /*
         * 创建ProgressDialog
         */
        if (activity != null && isShowing)
            createDialog(activity, isCancel, message);

        this.mResponseListener = listener;
    }

    @Override
    public void onStart(int what) {
        if (mProgressDialog != null && (mContext instanceof Activity && !((Activity) mContext).isFinishing())
                && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        int responseCode = response.getHeaders().getResponseCode();
        if (responseCode > 400 && mContext != null) {
            if (responseCode == 405) {
                // 405表示服务器不支持这种请求方法，比如GET、POST、TRACE中的TRACE就很少有服务器支持。
                showMessageDialog("请求成功", "请求的方法被不服务器允许。");
            } else {
                // 但是其它400+的响应码服务器一般会有流输出。
                String title = String.format(Locale.getDefault(), "服务器响应码%1$d", responseCode);
                String content = String.format(Locale.getDefault(),
                        "服务器响应码%1$d，如果你们服务器在这种情况下也返回数据你可以处理。", responseCode);
                showMessageDialog(title, content);
            }
        }

        if (mResponseListener != null)
            mResponseListener.onSucceed(what, response);
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            ToastUtils.showToast(mContext, "请检查网络。");
        } else if (exception instanceof TimeoutError) {// 请求超时
            ToastUtils.showToast(mContext, "请求超时，网络不好或者服务器不稳定。");
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            ToastUtils.showToast(mContext, "未发现指定服务器，清切换网络后重试。");
        } else if (exception instanceof URLError) {// URL是错的
            ToastUtils.showToast(mContext, "URL错误。");
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            ToastUtils.showToast(mContext, "没有找到缓存。");
        } else if (exception instanceof ProtocolException) {
            ToastUtils.showToast(mContext, "系统不支持的请求方法。");
        } else if (exception instanceof ParseError) {
            ToastUtils.showToast(mContext, "解析数据时发生错误。");
        } else {
            ToastUtils.showToast(mContext, "未知错误。");
        }
        LogUtils.e(getClass().getSimpleName(), "错误：" + exception.getMessage());

        if (mResponseListener != null)
            mResponseListener.onFailed(what, response);
    }

    @Override
    public void onFinish(int what) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
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
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(context);

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

    public void showMessageDialog(CharSequence title, CharSequence message) {
        if (mContext != null && (mContext instanceof Activity && !((Activity) mContext).isFinishing())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

}
