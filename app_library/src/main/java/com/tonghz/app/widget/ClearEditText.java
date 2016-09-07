package com.tonghz.app.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tonghz.app.R;

/**
 * 自定义带清除功能的EditText
 * Created by TongHuiZe on 2016/6/2.
 */
public class ClearEditText extends RelativeLayout {
    /**
     * 内容
     */
    private EditText mContentEt;

    /**
     * 删除按钮
     */
    private ImageButton mClearIbtn;

    public ClearEditText(Context context) {
        this(context, null, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*
         * 初始化View
		 */
        initViews(context);

		/*
         * 设置监听
		 */
        setListener();
    }

    private void initViews(Context context) {
        View.inflate(context, R.layout.view_clear_edit_text, this);
        mContentEt = (EditText) findViewById(R.id.et_content);
        mClearIbtn = (ImageButton) findViewById(R.id.ibtn_clear);
        mClearIbtn.setVisibility(View.GONE);
    }

    /**
     * 设置监听
     */
    private void setListener() {
		/*
		 * 添加文本编辑框变化监听
		 */
        mContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mClearIbtn.setEnabled(false);
                mClearIbtn.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    mClearIbtn.setEnabled(true);
                    mClearIbtn.setVisibility(VISIBLE);
                }
            }
        });
    }

    /**
     * 设置文字
     *
     * @param text 文本内容
     */
    public void setText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            mContentEt.setText(text);
        }
    }

    /**
     * 获取文本信息
     *
     * @return 文本内容
     */
    public CharSequence getText() {
        return mContentEt.getText();
    }

    /**
     * 设置提示文字
     *
     * @param hintText 提示文本
     */
    public void setHintText(CharSequence hintText) {
        if (!TextUtils.isEmpty(hintText)) {
            mContentEt.setHint(hintText);
        }
    }

    /**
     * 设置光标位置
     *
     * @param index 位置
     */
    public void setSelection(int index) {
        mContentEt.setSelection(index);
    }

    /**
     * 清除文本框中的内容
     */
    public void clear() {
        if (mContentEt != null) {
            mContentEt.setText(null);
        }
    }

    /**
     * 设置清除按钮监听
     *
     * @param clearListener 清除按钮监听
     */
    public void setOnClearClickListener(OnClickListener clearListener) {
        mClearIbtn.setOnClickListener(clearListener);
    }

    /**
     * 设置输入类型
     *
     * @param type 输入类型
     */
    public void setInputType(int type) {
        if (mContentEt != null) {
            mContentEt.setInputType(type);
        }
    }
}
