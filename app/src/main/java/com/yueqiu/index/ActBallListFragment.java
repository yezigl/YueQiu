package com.yueqiu.index;

import android.app.Fragment;

import com.yueqiu.BaseListFragment;
import com.yueqiu.loader.ActBallListLoader;
import com.yueqiu.model.ActBall;
import com.yueqiu.widget.BaseArrayAdapter;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

/**
 * Created on 15/6/4.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class ActBallListFragment extends BaseListFragment<ActBall> {

    public static Fragment newInstance() {
        return new ActBallListFragment();
    }

    @Override
    protected BaseArrayAdapter<ActBall> getAdapter() {
        return new ActBallListAdapter(getActivity(), null);
    }

    @Override
    protected BaseAsyncTaskLoader<List<ActBall>> getLoader() {
        return new ActBallListLoader(getActivity());
    }

}
