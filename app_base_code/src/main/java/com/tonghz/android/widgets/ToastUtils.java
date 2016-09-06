package com.tonghz.android.widgets;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tonghz.android.app.BaseApplication;

/**
 * Toast工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class ToastUtils {
    private static Toast mToast;

    private ToastUtils() {
    }

    private static void show(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void showToast(Context context, CharSequence text) {
        if (context == null)
            context = BaseApplication.getContext();
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, @StringRes int resId) {
        if (context == null)
            context = BaseApplication.getContext();
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, CharSequence text, int duration) {
        if (context == null)
            context = BaseApplication.getContext();
        show(context, text, duration);
    }

    /**
     * 带图片的Toast
     *
     * @param context     上下文对象
     * @param text        提示文字
     * @param duration    时间长短
     * @param orientation 方向
     * @param iconResId   图片资源Id
     * @param gravity     位置
     */
    /*
     * ToastUtils.toastWithIcon(this, "带图片的Toast！", Toast.LENGTH_SHORT, LinearLayout.VERTICAL, R.drawable.ic_launcher,
	 * Gravity.CENTER_HORIZONTAL);
	 */
    public static void toastWithIcon(Context context, CharSequence text, int duration, int orientation, int iconResId, int gravity) {
        if (context == null) {
            context = BaseApplication.getContext();
        }

        Toast toast = Toast.makeText(context, text, duration);
        // 获取Toast的View
        LinearLayout rootLayout = (LinearLayout) toast.getView();
        rootLayout.setOrientation(orientation);
        // 新建ImageView对象，并设置图片
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(iconResId);

        rootLayout.setGravity(gravity);
        rootLayout.addView(imageView, 0);

        toast.show();
    }
}
