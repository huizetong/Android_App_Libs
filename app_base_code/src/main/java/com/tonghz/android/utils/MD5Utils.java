package com.tonghz.android.utils;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class MD5Utils {
    /**
     * 加密
     *
     * @param str 字符串明文
     * @return 密文
     */
    public static String encryptMD5(String str) {
        char[] hexDigits = { // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] tmp = mdTemp.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            int len = tmp.length;
            char[] strs = new char[len * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置

            for (int i = 0; i < len; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                strs[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            return new String(strs); // 换后的结果转换为字符串
        } catch (Exception e) {
            return null;
        }
    }


    public static void main(String[] args) {
        System.out.println(encryptMD5("20150514"));
    }
}
