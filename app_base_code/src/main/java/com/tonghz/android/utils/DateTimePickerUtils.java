package com.tonghz.android.utils;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.ArrayList;

/**
 * 日期&时间选择器工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class DateTimePickerUtils {

    /**
     * 调整DatePicker和TimePicker的大小
     *
     * @param fl FrameLayout对象
     */
    public static void resizePicker(FrameLayout fl) {
        ArrayList<NumberPicker> pickerList = findNumberPicker(fl);
        for (NumberPicker picker : pickerList) {
            resizeNumberPicker(picker);
        }
    }

    /**
     * 获取DatePicker和TimePicker中的NumberPicker
     *
     * @param viewGroup 布局容器
     * @return NumberPicker集合
     */
    private static ArrayList<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        ArrayList<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    ArrayList<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    /**
     * 调整NumberPicker大小
     *
     * @param picker NumberPicker对象
     */
    @SuppressLint("NewApi")
    private static void resizeNumberPicker(NumberPicker picker) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(72, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 0, 8, 0);
        picker.setLayoutParams(params);
    }
}
