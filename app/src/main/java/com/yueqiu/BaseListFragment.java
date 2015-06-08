/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.yueqiu.widget.BaseArrayAdapter;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

import butterknife.InjectView;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public abstract class BaseListFragment<T> extends BaseFragment implements LoaderManager.LoaderCallbacks<List<T>> {

    public static final int LOAD = 0;

    public static final int REFRESH = 1;

    public static final int MORE = 2;

    protected String TAG = getClass().getSimpleName();

    protected static final int LIST_LOADER_ID = 1;

    @InjectView(R.id.list)
    ListView mListView;
    @InjectView(R.id.layout_swiperefresh)
    SwipeRefreshLayout mSwipeRefresh;

    BaseArrayAdapter<T> mAdapter;

    int mState = LOAD;
    int mOffset;

    protected abstract BaseArrayAdapter<T> getAdapter();

    protected abstract BaseAsyncTaskLoader<List<T>> getLoader();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.createView(inflater, container, R.layout.fragment_base_list);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = getAdapter();

        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount < totalItemCount) {
                    mState = MORE;
                    getLoaderManager().restartLoader(LIST_LOADER_ID, null, BaseListFragment.this);
                }
            }
        });

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setEnabled(false);
                mState = REFRESH;
                getLoaderManager().restartLoader(LIST_LOADER_ID, null, BaseListFragment.this);
            }
        });

        getLoaderManager().initLoader(LIST_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<T>> onCreateLoader(int id, Bundle args) {
        return getLoader().setOffset(mOffset);
    }

    @Override
    public void onLoadFinished(Loader<List<T>> loader, List<T> data) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
            mSwipeRefresh.setEnabled(true);
        }
        Log.d(TAG, data + " test");
        if (data != null) {
            if (mState == REFRESH || mState == LOAD) {
                mOffset = mAdapter.load(data);
            } else if (mState == MORE) {
                mOffset = mAdapter.more(data);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<T>> loader) {
        loader.stopLoading();
    }
}
