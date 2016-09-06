package com.tonghz.android.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密解密工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class DESUtils {
    /**
     * 密钥
     */
    public static final String KEY = "!@#$%^&*";

    // 初始化向量，随机填充
    // private static byte[] vectorByte = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /**
     * 加密
     *
     * @param encryptString 待加密明文字符串
     * @param encryptKey    密钥KEY
     * @return 密文
     * @throws Exception
     */
    @SuppressLint("TrulyRandom")
    public static String encrypt(String encryptString, String encryptKey) throws Exception {
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        // IvParameterSpec zeroIv = new IvParameterSpec(iv);
        IvParameterSpec zeroIv = new IvParameterSpec(encryptKey.getBytes());
        // 实例化SecretKeySpec类，根据字节数组来构造SecretKey
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用秘钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        // 执行加密操作
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

        // Base64.encodeToString(encryptedData, Base64.DEFAULT);// Base64.DEFAULT结尾处会有"\n"换行符
        return Base64.encodeToString(encryptedData, Base64.NO_WRAP);
    }

    /**
     * 解密
     *
     * @param decryptString 待解密密文字符串
     * @param decryptKey    密钥KEY
     * @return 明文
     * @throws Exception
     */
    public static String decrypt(String decryptString, String decryptKey) throws Exception {

        byte[] byteMi = Base64.decode(decryptString, Base64.NO_WRAP);
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        // IvParameterSpec zeroIv = new IvParameterSpec(iv);
        IvParameterSpec zeroIv = new IvParameterSpec(decryptKey.getBytes());

        // 实例化SecretKeySpec类，根据字节数组来构造SecretKey
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用秘钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        // 执行解密操作
        byte[] decryptedData = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }

    public static void main(String[] args) {
        String text = "12345678";

        try {
            String result1 = DESUtils.encrypt(text, DESUtils.KEY);
            String result2 = DESUtils.decrypt(result1, DESUtils.KEY);
            // a5xtQU9Dt/O+LSHC4Vw1aQ==
            System.out.println("加密：" + result1);
            System.out.println("解密：" + result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
