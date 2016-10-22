package com.tonghz.android.content;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import com.tonghz.android.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SharedPreferences工具类（基类）
 * Created by TongHuiZe on 2016/3/30.
 */
public class BaseSharedPrefs {

    /**
     * 添加数据
     *
     * @param sharedPrefs SharedPreferences对象
     * @param key         键
     * @param value       值
     */
    protected void putObject(SharedPreferences sharedPrefs, String key, Object value) {
        if (sharedPrefs == null || StringUtils.isEmpty(key)) {
            return;
        }

        if (value != null) {
            Editor editor = sharedPrefs.edit();
            if (sharedPrefs.contains(key)) {
                editor.remove(key);
            }

            /**
             * 类型判断
             */
            if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            }
            editor.commit();
        }
    }

    /**
     * 获取数据
     *
     * @param sharedPrefs SharedPreferences对象
     * @param key         键
     * @param defValue    默认值（根据默认值判断类型，若默认值为null，当作String类型处理）
     * @return 对象
     */
    protected Object getObject(SharedPreferences sharedPrefs, String key, Object defValue) {
        Object result = null;
        if (sharedPrefs == null || StringUtils.isEmpty(key)) {
            return null;
        }

        if (defValue != null) {
            if (defValue instanceof Long) {
                result = sharedPrefs.getLong(key, (Long) defValue);
            } else if (defValue instanceof String) {
                result = sharedPrefs.getString(key, (String) defValue);
            } else if (defValue instanceof Integer) {
                result = sharedPrefs.getInt(key, (Integer) defValue);
            } else if (defValue instanceof Float) {
                result = sharedPrefs.getFloat(key, (Float) defValue);
            } else if (defValue instanceof Boolean) {
                result = sharedPrefs.getBoolean(key, (Boolean) defValue);
            }
        } else {
            result = sharedPrefs.getString(key, null);
        }
        return result;
    }

    /**
     * 添加数据实体（混淆会无法使用）
     *
     * @param sharedPrefs SharedPreferences对象
     * @param t           数据实体
     * @param key         键
     * @param <T>         泛型
     */
    protected <T> void putEntity(SharedPreferences sharedPrefs, T t, String key) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            // 将实体对象保存在ObjectOutputStream对象中
            oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            // 将实体对象转换成byte数组，并将其进行Base64编码
            String entityStr = new String(Base64.encode(bos.toByteArray(), Base64.DEFAULT));
            putObject(sharedPrefs, key, entityStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();

                if (oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取数据实体（混淆会无法使用）
     *
     * @param sharedPrefs SharedPreferences对象
     * @param key         键
     * @param defValue    默认值
     * @return 数据实体
     */
    protected Object getEntity(SharedPreferences sharedPrefs, String key, String defValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Object result = null;// 结果
        String entityStr = (String) getObject(sharedPrefs, key, defValue);
        // 将Base64格式的字符串还原成byte数组
        byte[] bytes = Base64.decode(entityStr.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bas = null;
        ObjectInputStream ois = null;
        try {
            bas = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bas);
            try {
                result = ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bas != null)
                    bas.close();

                if (ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据key清除对应的数据
     *
     * @param sharedPrefs SharedPreferences对象
     * @param key         键
     */
    protected void remove(SharedPreferences sharedPrefs, String key) {
        if (sharedPrefs == null || StringUtils.isEmpty(key)) {
            return;
        }
        Editor editor = sharedPrefs.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     *
     * @param sharedPrefs SharedPreferences对象
     */
    protected void clear(SharedPreferences sharedPrefs) {
        if (sharedPrefs == null)
            return;

        Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
    }
}
