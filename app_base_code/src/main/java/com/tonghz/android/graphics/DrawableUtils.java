package com.tonghz.android.graphics;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import com.tonghz.android.utils.StringUtils;

/**
 * Drawable工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class DrawableUtils {
    /**
     * 获取GradientDrawable对象
     *
     * @param radius     圆角半径
     * @param colorValue 色值
     * @return GradientDrawable对象
     */
    public static GradientDrawable getGradientDrawable(float radius, int colorValue) {
        if (colorValue == 0) {
            return null;
        }
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(colorValue);
        return drawable;
    }

    /**
     * 获取GradientDrawable对象
     *
     * @param radius   圆角半径
     * @param colorStr 色值（字符串类型）
     * @return GradientDrawable对象
     */
    public static GradientDrawable getGradientDrawable(float radius, String colorStr) {
        if (StringUtils.isEmpty(colorStr)) {
            return null;
        }
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(Color.parseColor(colorStr));
        return drawable;
    }
}
