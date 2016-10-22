package com.tonghz.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tonghz.android.interfaces.OnDataListener;
import com.tonghz.android.interfaces.OnViewListener;
import com.tonghz.android.utils.LogUtils;

/**
 * Activity基类
 * Created by TongHuiZe on 2016/3/30.
 */
public abstract class AbsBaseActivity extends AppCompatActivity implements OnViewListener, OnDataListener {
    protected AbsBaseActivity mActivity = this;

    /**
     * TAG标签
     */
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "Activity onCreate(Bundle savedInstanceState) is execute!");

        onCreateActivity(savedInstanceState);

        // 初始化View（绑定控件，设置监听）
        initViews();

        // 设置数据内容
        setContentData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.i(TAG, "Activity onStart() is execute!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i(TAG, "Activity onResume() is execute!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.i(TAG, "Activity onPause() is execute!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i(TAG, "Activity onStop() is execute!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.i(TAG, "Activity onRestart() is execute!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "Activity onDestroy() is execute!");
    }

    /**
     * 创建Activity
     *
     * @param savedInstanceState Bundle对象
     */
    protected abstract void onCreateActivity(Bundle savedInstanceState);

    /**
     * 获取View
     *
     * @param id  控件Id
     * @param <T> 泛型类型
     * @return View子类
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(@IdRes int id) {
        return (T) this.findViewById(id);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void setContentData() {

    }

    /**
     * Activity间跳转
     *
     * @param cls 目标Activity
     */
    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(mActivity, cls));
    }

    /**
     * Activity间跳转（传值）
     *
     * @param cls    目标Activity
     * @param bundle 传递的数据
     */
    protected void startActivity(Class<?> cls, @Nullable Bundle bundle) {
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null)
            intent.putExtras(bundle);// 添加数据
        startActivity(intent);
    }

    /**
     * Activity间跳转（带返回值）
     *
     * @param cls         目标Activity
     * @param requestCode 请求码
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(new Intent(mActivity, cls), requestCode);
    }

    /**
     * Activity间跳转（带返回值且传值）
     *
     * @param cls         目标Activity
     * @param requestCode 请求码
     * @param bundle      传递的数据
     */
    protected void startActivityForResult(Class<?> cls, int requestCode, @Nullable Bundle bundle) {
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null)
            intent.putExtras(bundle);// 添加数据
        startActivityForResult(intent, requestCode);
    }
}
