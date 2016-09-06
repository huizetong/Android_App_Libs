package com.tonghz.android.utils;

import java.util.regex.Pattern;

/**
 * int型工具类
 * Created by TongHuiZe on 2016/8/14.
 */
public class IntegerUtils {
    /**
     * 是否为数值
     *
     * @param numberStr 数值字符串
     * @return 是否为数值
     */
    public static boolean isNumber(String numberStr) {
        return Pattern.compile("^[0-9]*[1-9][0-9]*$").matcher(numberStr).matches();
    }

}
