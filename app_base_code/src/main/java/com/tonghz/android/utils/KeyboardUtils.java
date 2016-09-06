/**
 * @Project: AppBase_Code
 * @Title: KeyboardUtils.java
 * @Package com.dmss.android.utils
 * @Description: 软键盘工具类
 * @author ※点墨书生※
 * @date 2015年4月5日 下午9:56:20
 * @Copyright: (C) 2015 http://www.chinasofti.com Inc. All rights reserved.
 * @version V1.0
 */
package com.tonghz.android.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class KeyboardUtils {

    /**
     * 隐藏软键盘
     *
     * @param context 上下文对象
     * @param view    View对象
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
