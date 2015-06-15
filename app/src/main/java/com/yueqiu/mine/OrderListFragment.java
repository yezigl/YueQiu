package com.yueqiu.mine;

import com.yueqiu.BaseListFragment;
import com.yueqiu.model.Order;
import com.yueqiu.widget.BaseArrayAdapter;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class OrderListFragment extends BaseListFragment<Order> {

    @Override
    protected BaseArrayAdapter<Order> getAdapter() {
        return null;
    }

    @Override
    protected BaseAsyncTaskLoader<List<Order>> getLoader() {
        return null;
    }
}
