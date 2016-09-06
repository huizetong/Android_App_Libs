package com.tonghz.android.utils;

import java.math.BigDecimal;

/**
 * 双精度浮点数工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class DoubleUtils {
    /**
     * 获取两位小数
     *
     * @param str 字符串
     * @return 两位小数
     */
    public static double getTwoDecimalPlaces(String str) {
        double result = 0.00;
        if (StringUtils.isEmpty(str)) {
            return result;
        }
        BigDecimal bigDecimal = new BigDecimal(str);
        result = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
}
