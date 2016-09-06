package com.tonghz.android.widgets;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.tonghz.android.utils.LogUtils;

/**
 * TextWatcher工具类
 * 注：TextWatcher工具类，用于监测EditText内容变化时，按钮（下一步操作）的可操作性
 * Created by TongHuiZe on 2016/3/30.
 */
public class TextWatcherUtils implements TextWatcher {
    /**
     * 上下文对象
     */
    private Context context;

    /**
     * 目标按钮
     */
    private View targetView;

    /**
     * 可用时资源图片
     */
    private int availableResId;

    /**
     * 不可用时资源图片
     */
    private int unavailableResId;

    public TextWatcherUtils(Context context, View targetView) {
        this.context = context;
        this.targetView = targetView;
    }

    public TextWatcherUtils(Context context, View targetView, int availableResId, int unavailableResId) {
        this(context, targetView);
        this.availableResId = availableResId;
        this.unavailableResId = unavailableResId;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        targetView.setClickable(false);
        if (availableResId != 0 && unavailableResId != 0) {
            targetView.setBackgroundResource(unavailableResId);
        } else {
            targetView.setVisibility(View.GONE);
        }

        LogUtils.d(TextWatcherUtils.class.getSimpleName(), "beforeTextChanged");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s != null && s.length() > 0) {
            /**
             * 按钮可用或显示
             */
            targetView.setClickable(true);
            if (availableResId != 0 && unavailableResId != 0) {
                targetView.setBackgroundResource(availableResId);
            } else {
                targetView.setVisibility(View.VISIBLE);
            }
        } else {
            /**
             * 按钮不可用或隐藏
             */
            targetView.setClickable(false);
            if (availableResId != 0 && unavailableResId != 0) {
                targetView.setBackgroundResource(unavailableResId);
            } else {
                targetView.setVisibility(View.GONE);
            }
        }
    }
}
