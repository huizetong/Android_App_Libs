package com.tonghz.android.widgets;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.tonghz.android.utils.LogUtils;

/**
 * 自定义GridView
 * 添加isOnMeasure属性，用于解决getView方法重复调用的问题
 * Created by TongHuiZe on 2016/3/30.
 */
public class ScrollGridView extends GridView {
    public boolean isOnMeasure = false;

    public ScrollGridView(Context context) {
        super(context);
    }

    public ScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 重写onMeasure方法
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.d(ScrollGridView.class.getSimpleName(), "onMeasure is execute!!!");
        isOnMeasure = true;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 重写onLayout方法
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtils.d(ScrollGridView.class.getSimpleName(), "onLayout is execute!!!");
        isOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }

}
