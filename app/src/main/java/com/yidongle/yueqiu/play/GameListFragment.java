package com.yidongle.yueqiu.play;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidongle.yueqiu.BaseListFragment;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.loader.GameListLoader;
import com.yidongle.yueqiu.mine.MineActivity;
import com.yidongle.yueqiu.model.DateType;
import com.yidongle.yueqiu.model.Game;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.widget.BaseArrayAdapter;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

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
    @InjectView(R.id.location)
    TextView mLocation;

    private int dateType = DateType.ALL.type;

    private int LOCATION_REQCODE = 0x1006;

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
        // 定位
        String selectCity = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(Constants.PREF_LOCATION_CITY, null);
        if (selectCity != null) {
            mLocation.setText(selectCity);
        } else {
            mLocation.setText("北京市");
        }
        return view;
    }

    private TabLayout.Tab getTab(DateType dateType) {
        return mTabLayout.newTab().setText(dateType.text).setTag(dateType.type);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LOCATION_REQCODE && data != null) {
                mLocation.setText(data.getStringExtra(Constants.INTENT_LOCATION_CITY));
            }
        }
    }

    @OnClick(R.id.location)
    public void location(View v) {
        Intent intent = new Intent(getActivity(), LocationActivity.class);
        startActivityForResult(intent, LOCATION_REQCODE);
    }

    @OnClick(R.id.me)
    public void me(View v) {
        Intent intent = new Intent(getActivity(), MineActivity.class);
        startActivity(intent);
    }
}
