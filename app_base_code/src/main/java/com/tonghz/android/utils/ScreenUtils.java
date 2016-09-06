package com.tonghz.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 屏幕工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class ScreenUtils {
    /**
     * 获取屏幕显示度量，通过WindowManager方式
     *
     * @param context 上下文对象
     * @return DisplayMetrics对象
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 获取屏幕显示度量，通过Resources方式
     *
     * @param context 上下文对象
     * @return DisplayMetrics对象
     */
    public static DisplayMetrics getDisplayMetricsByResources(Context context) {
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        // DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5, xdpi=160.421, ydpi=159.497}
        // DisplayMetrics{density=2.0, width=720, height=1280, scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        return resources.getDisplayMetrics();
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文对象
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics outMetrics = getDisplayMetrics(context);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context 上下文对象
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics outMetrics = getDisplayMetrics(context);
        return outMetrics.heightPixels;
    }
}
