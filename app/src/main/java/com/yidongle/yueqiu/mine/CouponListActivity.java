package com.yidongle.yueqiu.mine;

import android.os.Bundle;

import com.yidongle.yueqiu.BaseListActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.loader.CouponListLoader;
import com.yidongle.yueqiu.model.Coupon;
import com.yidongle.yueqiu.widget.BaseArrayAdapter;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class CouponListActivity extends BaseListActivity<Coupon> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coupon_list);
        setLoadNoneText(R.string.coupon_list_none);
    }

    @Override
    protected BaseArrayAdapter<Coupon> getAdapter() {
        return new CouponListAdapter(this, null);
    }

    @Override
    protected BaseAsyncTaskLoader<List<Coupon>> getLoader() {
        return new CouponListLoader(this);
    }
}
