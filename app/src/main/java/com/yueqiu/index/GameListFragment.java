package com.yueqiu.index;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueqiu.BaseListFragment;
import com.yueqiu.R;
import com.yueqiu.loader.GameListLoader;
import com.yueqiu.model.DateType;
import com.yueqiu.model.Game;
import com.yueqiu.widget.BaseArrayAdapter;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

import butterknife.InjectView;

/**
 * Created on 15/6/4.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class GameListFragment extends BaseListFragment<Game> {

    @InjectView(R.id.tabs)
    TabLayout mTabLayout;

    private int dateType = DateType.ALL.type;

    public static Fragment newInstance() {
        return new GameListFragment();
    }

    @Override
    protected BaseArrayAdapter<Game> getAdapter() {
        return new GameListAdapter(getActivity(), null);
    }

    @Override
    protected BaseAsyncTaskLoader<List<Game>> getLoader() {
        return new GameListLoader(getActivity()).params(dateType);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.createView(inflater, container, R.layout.fragment_game_list);
        mTabLayout.addTab(getTab(DateType.ALL));
        mTabLayout.addTab(getTab(DateType.TODAY));
        mTabLayout.addTab(getTab(DateType.TOMORROW));
        mTabLayout.addTab(getTab(DateType.LATER));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                dateType = (int) tab.getTag();
                getLoaderManager().restartLoader(LIST_LOADER_ID, null, GameListFragment.this);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    private TabLayout.Tab getTab(DateType dateType) {
        return mTabLayout.newTab().setText(dateType.text).setTag(dateType.type);
    }

}
