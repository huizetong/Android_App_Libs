package com.tonghz.android.utils;

import android.os.Environment;

import java.io.File;

/**
 * SDCard工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class SDCardUtils {
    /**
     * 判断SDCard是否可用
     *
     * @return SDCard是否可用
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SDCard根目录
     *
     * @return SDCard根目录
     */
    public static String getSDCardRootDir() {
        if (isSDCardAvailable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 删除SDCard中文件
     *
     * @param filePath 文件路径
     * @return 是否删除
     */
    public static boolean deleteFileFromSDCard(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                file.delete();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
