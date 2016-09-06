package com.tonghz.android.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * App工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class AppUtils {
    /**
     * UI设计的基准宽度.
     */
    public static int UI_WIDTH = 1080;

    /**
     * UI设计的基准高度.
     */
    public static int UI_HEIGHT = 1920;

    /**
     * 图片压缩质量
     */
    public static int IMG_QUALITY = 90;

    /**
     * 获取APP版本名称
     *
     * @param context 上下文对象
     * @return App版本号
     */
    public static String getVersionName(Context context) {
        if (context == null) {
            return "-1.0";
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "-1.0";
    }

    /**
     * 获取APP版本号
     *
     * @param context 上下文对象
     * @return App版本号
     */
    public static int getVersionCode(Context context) {
        if (context == null) {
            return -1;
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
