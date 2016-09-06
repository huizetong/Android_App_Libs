package com.tonghz.android.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * 密度相关工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class DensityUtils {
    /**
     * dp转px
     *
     * @param context 上下文对象
     * @param dpVal   数值（单位：dp）
     * @return px值
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context 上下文对象
     * @param spVal   数值（单位：sp）
     * @return px值
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context 上下文对象
     * @param pxVal   数值（单位：px）
     * @return dp值
     */
    public static int px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param context 上下文对象
     * @param pxVal   数值（单位：px）
     * @return sp值
     */
    public static float px2sp(Context context, float pxVal) {
        return (int) (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
