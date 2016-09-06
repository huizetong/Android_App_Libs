package com.tonghz.android.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class ViewHolder {

    /**
     * 根据Id获取View对象
     *
     * @param convertView 父View对象
     * @param id          Id
     * @param <T>         泛型类型
     * @return View对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(View convertView, int id) {
        SparseArray<View> viewsHolder = (SparseArray<View>) convertView.getTag();
        if (viewsHolder == null) {
            viewsHolder = new SparseArray<>();
            convertView.setTag(viewsHolder);
        }

        View childView = viewsHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewsHolder.put(id, childView);
        }
        return (T) childView;
    }
}
