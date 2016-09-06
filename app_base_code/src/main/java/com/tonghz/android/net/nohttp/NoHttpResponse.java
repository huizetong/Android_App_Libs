package com.tonghz.android.net.nohttp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.tonghz.android.app.WebAlertDialog;
import com.tonghz.android.utils.StringUtils;
import com.tonghz.android.widgets.ToastUtils;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ParseError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.StringRequest;
import com.yolanda.nohttp.tools.HeaderUtil;

import java.net.ProtocolException;

/**
 * NoHttp网络响应
 * Created by TongHuiZe on 2016/3/30.
 */
public class NoHttpResponse<T> implements OnResponseListener<T> {
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
     * @param isCancel  是否可取消进度条
     * @param isShowing 是否显示进度对话框
     */
    public NoHttpResponse(Context context, Request<T> request, OnHttpResponseListener<T> listener, boolean isCancel, boolean isShowing) {
        this.mContext = context;
        this.mRequest = request;
        this.mResponseListener = listener;
        this.isShowing = isShowing;

        /*
         * 创建ProgressDialog
         */
        if (isShowing)
            createDialog(isCancel, null);
    }

    /**
     * 构造函数
     *
     * @param context   上下文对象
     * @param request   网络请求
     * @param listener  响应监听
     * @param message   提示信息
     * @param isCancel  是否可取消进度条
     * @param isShowing 是否显示进度对话框
     */
    public NoHttpResponse(Context context, Request<T> request, OnHttpResponseListener<T> listener, CharSequence message, boolean isCancel, boolean isShowing) {
        this.mContext = context;
        this.mRequest = request;
        this.mResponseListener = listener;
        this.isShowing = isShowing;

        /*
         * 创建ProgressDialog
         */
        if (isShowing)
            createDialog(isCancel, message);
    }

    @Override
    public void onStart(int what) {
        if (isShowing && mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        int responseCode = response.getHeaders().getResponseCode();
        if (responseCode > 400) {
            if (responseCode == 405) {// 405表示服务器不支持这种请求方法，比如GET、POST、TRACE中的TRACE就很少有服务器支持。
                showMessageDialog("请求失败", "请求的方法被不服务器允许!");
            } else {
                // 但是其它400+的响应码服务器一般会有流输出。
                showWebDialog(response);
            }
        }

        if (mResponseListener != null)
            mResponseListener.onSucceed(what, response);
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        if (exception instanceof NetworkError) {// 网络不好
            ToastUtils.showToast(null, "请检查网络。");
        } else if (exception instanceof TimeoutError) {// 请求超时
            ToastUtils.showToast(null, "请求超时，网络不好或者服务器不稳定。");
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            ToastUtils.showToast(null, "未发现指定服务器，清切换网络后重试。");
        } else if (exception instanceof URLError) {// URL是错的
            ToastUtils.showToast(null, "URL错误。");
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            ToastUtils.showToast(null, "没有找到缓存。");
        } else if (exception instanceof ProtocolException) {
            ToastUtils.showToast(null, "系统不支持的请求方法。");
        } else if (exception instanceof ParseError) {
            ToastUtils.showToast(null, "解析数据时发生错误。");
        } else {
            ToastUtils.showToast(null, "未知错误！");
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
     * @param isCancel 是否可取消
     * @param message  提示信息
     */
    private void createDialog(boolean isCancel, CharSequence message) {
        if (mContext == null)
            return;

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
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

    public void showWebDialog(Response<?> response) {
        if (mContext == null)
            return;
        String result = StringRequest.parseResponseString(response.getHeaders(), response.getByteArray());
        WebAlertDialog webDialog = new WebAlertDialog(mContext);
        String contentType = response.getHeaders().getContentType();
        webDialog.loadUrl(result, contentType, HeaderUtil.parseHeadValue(contentType, "charset", "utf-8"));
        webDialog.show();
    }

    public void showMessageDialog(CharSequence title, CharSequence message) {
        if (mContext == null)
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
