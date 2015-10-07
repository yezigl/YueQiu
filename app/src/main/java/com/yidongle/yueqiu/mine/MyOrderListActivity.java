package com.yidongle.yueqiu.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.BaseListFragment;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.loader.MyOrderListLoader;
import com.yidongle.yueqiu.model.Order;
import com.yidongle.yueqiu.model.OrderStatus;
import com.yidongle.yueqiu.widget.BaseArrayAdapter;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

import butterknife.InjectView;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class MyOrderListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myorder_list);

        getFragmentManager().beginTransaction().add(R.id.content, new MyOrderListFragment()).commit();
    }

    @Override
    public void back(View v) {
        Intent intent = new Intent(this, MineActivity.class);
        startActivity(intent);
    }

    public static class MyOrderListFragment extends BaseListFragment<Order> {

        @InjectView(R.id.tabs)
        TabLayout mTabLayout;

        private int status;

        @Override
        protected BaseArrayAdapter<Order> getAdapter() {
            return new MyOrderListAdapter(getActivity(), null);
        }

        @Override
        protected BaseAsyncTaskLoader<List<Order>> getLoader() {
            return new MyOrderListLoader(getActivity()).params(status);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return createView(inflater, container, R.layout.activity_myorder_list_fragment);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mTabLayout.addTab(getTab(OrderStatus.ALL));
            mTabLayout.addTab(getTab(OrderStatus.UNPAY));
            mTabLayout.addTab(getTab(OrderStatus.PAYED));
            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    status = (int) tab.getTag();
                    getLoaderManager().restartLoader(LIST_LOADER_ID, null, MyOrderListFragment.this);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }

        private TabLayout.Tab getTab(OrderStatus status) {
            return mTabLayout.newTab().setText(status.text).setTag(status.code);
        }
    }

}
