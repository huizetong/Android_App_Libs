package com.tonghz.android.utils;

import android.text.TextUtils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param str CharSequence对象
     * @return 是否为空
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0 || TextUtils.equals("null", str);
    }

    /**
     * 密码校验
     *
     * @param password 密码
     * @return 是否符合格式
     */
    public static boolean isPasswordValid(String password) {
        boolean isPassword;
        if (isEmpty(password)) {
            return false;
        }

        try {
            Pattern p = Pattern.compile("^[0-9a-zA-Z]{6,16}$");
            Matcher m = p.matcher(password);
            isPassword = m.matches();
        } catch (Exception e) {
            isPassword = false;
            e.printStackTrace();
        }
        return isPassword;
    }

    /**
     * 手机号码
     *
     * @param phone 手机号
     * @return 手机号是否符合格式
     */
    public static boolean isPhoneValid(String phone) {
        boolean isPhone;
        if (isEmpty(phone)) {
            return false;
        }

        try {
            Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[\\d]))\\d{8}$");
            Matcher m = p.matcher(phone);
            isPhone = m.matches();
        } catch (Exception e) {
            isPhone = false;
            e.printStackTrace();
        }
        return isPhone;
    }


    /**
     * Email是否合法
     *
     * @param email 邮箱
     * @return 邮箱是否符合格式
     */
    public static boolean isEmailValid(String email) {
        boolean isEmail;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern pattern = Pattern.compile(check);
            isEmail = pattern.matcher(email).matches();
        } catch (Exception e) {
            isEmail = false;
            e.printStackTrace();
        }
        return isEmail;
    }

    /**
     * 是否是数字
     *
     * @param str 字符串
     * @return 是否为数字
     */
    public static boolean isNumberValid(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 格式化为两位小数的字符串
     *
     * @param str 字符串
     * @return 保留两位小数字符串
     */
    public static String formatTwoDecimalPlaces(String str) {
        if (StringUtils.isEmpty(str)) {
            return "0.00";
        }
        return String.format(Locale.CHINESE, "%.2f", Double.parseDouble(str));
    }

    /**
     * 去除小数点（四舍五入）
     *
     * @param str 原字符串
     * @return 四舍五入后的字符串
     */
    public static String subDecimalPoint(String str) {
        if (isEmpty(str)) {
            return "0";
        }
        return String.format(Locale.CHINESE, "%.0f", Double.parseDouble(str));
    }

    /**
     * 去除多余的零和点
     *
     * @param str 原字符串
     * @return 去除多余的零和小数点后的字符串
     */
    public static String subDecimalZeroAndPoint(String str) {
        if (isEmpty(str)) {
            return "0";
        }

        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");// 去掉多余的0
            str = str.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return str;
    }

    /**
     * 隐藏中间的字符串
     *
     * @param str   原字符串
     * @param start 起始指针
     * @param end   终止指针
     * @return 隐藏后的新字符串
     */
    public static String hideMiddleStrings(String str, int start, int end) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        result.append(str.substring(0, start));
        result.append("***");
        result.append(str.substring(end, str.length()));
        return result.toString();
    }
}
