package com.tonghz.android.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonghz.android.interfaces.OnDataListener;
import com.tonghz.android.interfaces.OnViewListener;
import com.tonghz.android.utils.LogUtils;

/**
 * Fragment基类
 * Created by TongHuiZe on 2016/3/30.
 */
public abstract class AbsBaseFragment extends Fragment implements OnViewListener, OnDataListener {
    /**
     * 布局资源Id
     */
    private int mLayoutResId;

    /**
     * 根视图
     */
    protected View mRootView;

    /**
     * TAG标签
     */
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i(TAG, "Fragment onAttach() is execute!");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "Fragment onCreate() is execute!");

        // 创建Fragment
        onCreateFragment(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "Fragment onCreateView() is execute!");
        if (this.mLayoutResId != 0 && mRootView == null) {
            mRootView = inflater.inflate(this.mLayoutResId, container, false);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.i(TAG, "Fragment onActivityCreated() is execute!");

        // 初始化View（绑定控件，设置监听）
        initViews();

        // 设置数据内容
        setContentData();
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "Fragment onStart() is execute!");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "Fragment onResume() is execute!");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "Fragment onPause() is execute!");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "Fragment onStop() is execute!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(TAG, "Fragment onDestroyView() is execute!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "Fragment onDestroy() is execute!");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.i(TAG, "Fragment onDetach() is execute!");
    }

    protected abstract void onCreateFragment(Bundle savedInstanceState);

    /**
     * 获取View
     *
     * @param id  控件Id
     * @param <T> 泛型类型
     * @return View子类
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(@IdRes int id) {
        if (mRootView == null)
            return null;
        return (T) mRootView.findViewById(id);
    }

    /**
     * 设置视图内容
     *
     * @param layoutResId 布局资源Id
     */
    protected void setContentView(@LayoutRes int layoutResId) {
        if (layoutResId != 0)
            this.mLayoutResId = layoutResId;
    }

    /**
     * 设置视图内容
     *
     * @param view View对象
     */
    protected void setContentView(View view) {
        if (view != null)
            this.mRootView = view;
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
        if (this.getActivity() != null)
            startActivity(new Intent(this.getActivity(), cls));
    }

    /**
     * Activity间跳转（传值）
     *
     * @param cls    目标Activity
     * @param bundle 传递的数据
     */
    protected void startActivity(Class<?> cls, @Nullable Bundle bundle) {
        if (getActivity() != null) {
            Intent intent = new Intent(this.getActivity(), cls);
            intent.setClass(getContext(), cls);
            intent.putExtras(bundle);// 添加数据
            startActivity(intent);
        }
    }

    /**
     * Activity间跳转（带返回值）
     *
     * @param cls         目标Activity
     * @param requestCode 请求码
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        if (this.getActivity() != null)
            startActivityForResult(new Intent(this.getActivity(), cls), requestCode);
    }

    /**
     * Activity间跳转（带返回值且传值）
     *
     * @param cls         目标Activity
     * @param requestCode 请求码
     * @param bundle      传递的数据
     */
    protected void startActivityForResult(Class<?> cls, int requestCode, @Nullable Bundle bundle) {
        if (this.getActivity() != null) {
            Intent intent = new Intent(this.getActivity(), cls);
            intent.putExtras(bundle);// 添加数据
            startActivityForResult(intent, requestCode);
        }
    }
}
