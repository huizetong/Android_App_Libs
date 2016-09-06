/**
 * @Project: AppBase_Code
 * @Title: ViewUtils.java
 * @Package com.dmss.android.views
 * @Description: View工具类
 * @author ※点墨书生※
 * @date 2015年4月7日 下午10:06:12
 * @Copyright: (C) 2015 http://www.chinasofti.com Inc. All rights reserved.
 * @version V1.0
 */
package com.tonghz.android.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * View工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class ViewUtils {

    /**
     * 动态设置ListView的高度
     *
     * @param listView ListView对象
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null)
            return;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}
