package com.tonghz.android.utils;

import android.util.Base64;

import java.net.URLEncoder;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES加密解密工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class DES3Utils {
    /**
     * 密钥
     */
    private final static String SECRET_KEY = "!#aijiugo9988@100$#365#$*";

    /**
     * 向量
     */
    private final static String IV = "01234567";

    /**
     * 编码格式
     */
    private final static String CHARSET_NAME = "UTF-8";

    /**
     * 3DES加密
     *
     * @param originalText 原文
     * @return 密文
     * @throws Exception
     */
    public static String encode(String originalText, String secretKey) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        Key desKey = keyFactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
        byte[] encryptData = cipher.doFinal(originalText.getBytes(CHARSET_NAME));
        return Base64.encodeToString(encryptData, Base64.NO_WRAP);
    }

    /**
     * 3DES解密
     *
     * @param ciphertext 密文
     * @param secretKey  密钥
     * @return 明文
     * @throws Exception
     */
    public static String decode(String ciphertext, String secretKey) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        Key desKey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, desKey, ips);

        byte[] decryptData = cipher.doFinal(Base64.decode(ciphertext, Base64.NO_WRAP));

        return new String(decryptData, CHARSET_NAME);
    }

    public static void main(String[] args) {
        String str = "{\"pageSize\":5,\"userid\":\"150\",\"page\":2}";
        String str1 = "EC48fQszSgb8Jv0aQrrELYRiSQjn+eQikm3gctt+v0Wf3yKl89HZcg==";
        try {
            System.out.println(URLEncoder.encode(encode(str, SECRET_KEY)));
            System.out.println(encode(str, SECRET_KEY));
            System.out.println(decode(str1, SECRET_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}