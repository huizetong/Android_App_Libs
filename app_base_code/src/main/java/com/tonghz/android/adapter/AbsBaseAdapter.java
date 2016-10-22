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
public abstract class AbsBaseAdapter<E> extends BaseAdapter {
    /**
     * LayoutInflater对象
     */
    protected LayoutInflater mLayoutInflater;

    /**
     * 数据源
     */
    protected List<E> mList;

    public AbsBaseAdapter(Context context) {
        this.mList = new ArrayList<>();

        // 初始化LayoutInflater对象
        this.mLayoutInflater = LayoutInflater.from(context);

        // 初始化基础数据
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
            rootView = getConvertView(parent);
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
     * 获取Item View
     *
     * @param parent 父容器
     * @return Item View
     */
    protected abstract View getConvertView(ViewGroup parent);

    /**
     * 添加数据
     *
     * @param element 元素（泛型类型）
     */
    protected void add(E element) {
        if (element != null && mList != null) {
            mList.add(element);
        }

        this.notifyDataSetChanged();
    }

    /**
     * 添加数据列表
     *
     * @param list 数据列表
     */
    public void addAll(List<E> list) {
        addAll(false, list);
    }

    /**
     * 添加数据列表
     *
     * @param isClear 是否清除
     * @param list    数据列表
     */
    public void addAll(boolean isClear, List<E> list) {
        if (isClear)
            this.mList.clear();

        if (list != null && !list.isEmpty())
            this.mList.addAll(list);

        this.notifyDataSetChanged();
    }

    /**
     * 移除某条数据
     *
     * @param position 指针
     */
    public void removeItem(int position) {
        if (mList != null && !mList.isEmpty()) {
            if (position < mList.size())
                mList.remove(position);

            this.notifyDataSetChanged();
        }
    }

    /**
     * 清除数据
     */
    public void clear() {
        if (mList != null && !mList.isEmpty())
            this.mList.clear();

        this.notifyDataSetChanged();
    }

    /**
     * getViewByViewHolder()抽象方法（子类重写）
     *
     * @param position    指针
     * @param convertView View对象
     * @param parent      父视图容器
     * @return View对象
     */
    protected abstract View getViewByViewHolder(int position, View convertView, ViewGroup parent);

}
