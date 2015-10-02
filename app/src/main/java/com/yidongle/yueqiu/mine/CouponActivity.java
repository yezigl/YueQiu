package com.yidongle.yueqiu.mine;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;

import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.loader.CouponLoader;
import com.yidongle.yueqiu.model.Coupon;

import java.util.List;

import butterknife.InjectView;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class CouponActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<Coupon>> {

    @InjectView(R.id.list)
    ListView mList;

    CouponAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coupon);

        mAdapter = new CouponAdapter(this, null);
        mList.setAdapter(mAdapter);

        getLoaderManager().restartLoader(hashCode(), null, this);
    }

    @Override
    public Loader<List<Coupon>> onCreateLoader(int id, Bundle args) {
        return new CouponLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Coupon>> loader, List<Coupon> data) {
        if (data != null) {
            mAdapter.load(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Coupon>> loader) {
        loader.stopLoading();
    }
}
