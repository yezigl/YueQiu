package com.yueqiu.index;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueqiu.BaseListFragment;
import com.yueqiu.R;
import com.yueqiu.loader.GameListLoader;
import com.yueqiu.model.Game;
import com.yueqiu.widget.BaseArrayAdapter;
import com.yueqiu.widget.BaseAsyncTaskLoader;
import com.yueqiu.widget.DropdownView;

import java.util.Arrays;
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

    @InjectView(R.id.tab1)
    DropdownView mTab1;
    @InjectView(R.id.tab2)
    DropdownView mTab2;
    @InjectView(R.id.tab3)
    DropdownView mTab3;

    public static Fragment newInstance() {
        return new GameListFragment();
    }

    @Override
    protected BaseArrayAdapter<Game> getAdapter() {
        return new GameListAdapter(getActivity(), null);
    }

    @Override
    protected BaseAsyncTaskLoader<List<Game>> getLoader() {
        return new GameListLoader(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.createView(inflater, container, R.layout.fragment_game_list);
        mTab1.setSimpleDropdown(Arrays.asList("今天", "明天", "两天后"));
        return view;
    }
}
