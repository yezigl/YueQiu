package com.yidongle.yueqiu;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;

import com.yidongle.yueqiu.widget.BaseArrayAdapter;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;
import com.yidongle.yueqiu.widget.LoadView;

import java.util.List;

import butterknife.InjectView;

/**
 * Created on 15/10/18.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseListActivity<T> extends BaseActivity implements LoaderManager.LoaderCallbacks<List<T>> {

    public static final int LOAD = 0;

    public static final int REFRESH = 1;

    public static final int MORE = 2;

    protected final int LIST_LOADER_ID = 1001;

    @InjectView(R.id.list)
    ListView mListView;
    @InjectView(R.id.layout_swiperefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @InjectView(R.id.load)
    LoadView mLoad;

    BaseArrayAdapter<T> mAdapter;

    int mState = LOAD;
    int mOffset;

    protected abstract BaseArrayAdapter<T> getAdapter();

    protected abstract BaseAsyncTaskLoader<List<T>> getLoader();

    protected void setLoadNoneText(int resourceId) {
        mLoad.setNoneText(resourceId);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mAdapter = getAdapter();

        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount > totalItemCount) {
                    mState = MORE;
                    getLoaderManager().restartLoader(LIST_LOADER_ID, null, BaseListActivity.this);
                }
            }
        });

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setEnabled(false);
                mState = REFRESH;
                getLoaderManager().restartLoader(LIST_LOADER_ID, null, BaseListActivity.this);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(LIST_LOADER_ID, null, this);
    }

    protected void setDivider(Drawable drawable, int height) {
        mListView.setDivider(drawable);
        mListView.setDividerHeight(height);
    }

    @Override
    public Loader<List<T>> onCreateLoader(int id, Bundle args) {
        //if (mState == LOAD) {
        mAdapter.clear();
        //}
        if (mState == LOAD || mState == REFRESH) {
            mLoad.loading(mListView);
        }
        return getLoader().setOffset(mOffset);
    }

    @Override
    public void onLoadFinished(Loader<List<T>> loader, List<T> data) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
            mSwipeRefresh.setEnabled(true);
        }
        if (data != null) {
            if (data.isEmpty()) {
                mLoad.none(mListView);
            } else {
                if (mState == REFRESH || mState == LOAD) {
                    mOffset = mAdapter.load(data);
                } else if (mState == MORE) {
                    mOffset = mAdapter.more(data);
                }
                mLoad.success(mListView);
            }
        } else {
            mLoad.fail();
        }
        mState = LOAD;
    }

    @Override
    public void onLoaderReset(Loader<List<T>> loader) {
        loader.stopLoading();
    }
}
