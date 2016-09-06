package com.tonghz.android.app;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 网页对话框
 * Created by TongHuiZe on 2016/8/13.
 */
public class WebAlertDialog extends AlertDialog.Builder {
    /**
     * AlertDialog对象
     */
    private AlertDialog alertDialog;

    /**
     * WebView对象
     */
    private WebView webView;

    public WebAlertDialog(Context context) {
        super(context);
        webView = new WebView(context);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    view.loadUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        setView(webView);
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public void loadUrl(String data, String mimeType, String encoding) {
        webView.loadData(data, mimeType, encoding);
    }

    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }

    public void cancel() {
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.cancel();
    }

    @Override
    public AlertDialog show() {
        if (alertDialog == null) {
            alertDialog = create();
            alertDialog.setTitle(null);
        }
        if (!alertDialog.isShowing())
            alertDialog.show();
        return alertDialog;
    }
}
