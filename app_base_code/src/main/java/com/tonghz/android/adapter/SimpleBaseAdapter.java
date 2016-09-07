package com.tonghz.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用适配器（继承自BaseAdapter）
 * Created by TongHuiZe on 2016/3/30.
 */
public abstract class SimpleBaseAdapter<E> extends BaseAdapter {

    /**
     * LayoutInflater对象
     */
    private LayoutInflater mLayoutInflater;

    /**
     * 布局资源Id
     */
    private int mLayoutResId;

    /**
     * 数据源
     */
    protected List<E> mList = new ArrayList<>();

    public SimpleBaseAdapter(Context context) {
        // 初始化LayoutInflater对象
        mLayoutInflater = LayoutInflater.from(context);

        /**
         * 初始化布局资源
         */
        mLayoutResId = initLayoutRes();

        /**
         * 初始化基础数据
         */
        initBasicData(context);
    }

    @Override
    public int getCount() {
        return (mList == null || mList.isEmpty()) ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList != null && !mList.isEmpty()) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;
        if (convertView == null) {
            rootView = mLayoutInflater.inflate(mLayoutResId, parent, false);
        } else {
            rootView = convertView;
        }

        return getViewByViewHolder(position, rootView, parent);
    }

    /**
     * 初始化基础数据
     *
     * @param context 上下文对象
     */
    protected void initBasicData(Context context) {

    }

    /**
     * 添加数据
     *
     * @param list 数据集合
     */
    public void appendToList(List<E> list) {
        appendToList(false, list);
    }

    /**
     * 添加数据
     *
     * @param isClear 是否清除
     * @param list    数据集合
     */
    public void appendToList(boolean isClear, List<E> list) {
        if (isClear) {
            this.mList.clear();
        }

        this.mList.addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * inflate Layout
     *
     * @return 布局资源
     */
    public abstract int initLayoutRes();

    /**
     * getViewByViewHolder()抽象方法（子类重写）
     *
     * @param position    指针
     * @param convertView View对象
     * @param parent      父视图容器
     * @return View对象
     */
    public abstract View getViewByViewHolder(int position, View convertView, ViewGroup parent);
}
