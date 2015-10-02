package com.yidongle.yueqiu.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ListView}使用的{@code ListAdapter}的一个实现，基于{@code ArrayAdapter}，已实现{@link #getCount()}、{@link #getItem(int)}方法<br>
 * 继承此类的子类只需要实现 {@link #getView(int, android.view.View, android.view.ViewGroup)}即可<br>
 * 同时可直接使用使用{@link #mContext}、{@link #mInflater}
 *
 * @author lidehua
 * @version 1.0
 * @since 2014年10月22日
 */
public abstract class BaseArrayAdapter<T> extends ArrayAdapter<T> {

    /**
     * {@link Context}
     */
    protected Activity mContext;

    protected List<T> mList;

    /**
     * {@link LayoutInflater}
     */
    protected LayoutInflater mInflater;

    public BaseArrayAdapter(Activity context, int resource, List<T> list) {
        super(context, resource, list == null ? (list = new ArrayList<T>()) : list);
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    /**
     * 下拉刷新和第一次加载使用
     *
     * @param list
     * @return 列表长度
     */
    public int load(List<T> list) {
        if (list == null || list.isEmpty()) {
            return getCount();
        }
        if (this.mList.isEmpty()) {
            this.mList.addAll(list);
        } else {
            this.mList.clear();
            this.mList.addAll(list);
        }
        notifyDataSetChanged();
        return getCount();
    }

    /**
     * 滚动加载使用
     *
     * @param list
     * @return 列表长度
     */
    public int more(List<T> list) {
        if (list == null || list.isEmpty()) {
            return getCount();
        }
        this.mList.addAll(list);
        notifyDataSetChanged();
        return getCount();
    }

    public List<T> getList() {
        return mList;
    }
}
