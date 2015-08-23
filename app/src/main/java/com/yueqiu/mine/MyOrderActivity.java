package com.yueqiu.mine;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yueqiu.BaseActivity;
import com.yueqiu.R;
import com.yueqiu.index.PaymentActivity;
import com.yueqiu.loader.OrderDetailLoader;
import com.yueqiu.model.Order;
import com.yueqiu.model.OrderStatus;
import com.yueqiu.utils.Constants;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/6/21.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class MyOrderActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Order> {

    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.address)
    TextView mAddress;
    @InjectView(R.id.date)
    TextView mDate;
    @InjectView(R.id.rule)
    TextView mRule;
    @InjectView(R.id.order_time)
    TextView mOrderTime;
    @InjectView(R.id.order_amount)
    TextView mOrderAmount;
    @InjectView(R.id.button_payment)
    View mPayment;

    String oid;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myorder);

        oid = getIntent().getStringExtra(Constants.INTENT_ORDER_ID);

        getLoaderManager().restartLoader(hashCode(), null, this);
    }

    @Override
    public Loader<Order> onCreateLoader(int id, Bundle args) {
        return new OrderDetailLoader(this).params(oid);
    }

    @Override
    public void onLoadFinished(Loader<Order> loader, Order data) {
        if (data != null) {
            order = data;
            mTitle.setText(data.getActivity().getTitle());
            mAddress.setText(data.getActivity().getStadium().getName());
            mDate.setText(data.getActivity().getDate());
            mRule.setText(data.getActivity().getStadium().getSize() + "人制");
            mOrderTime.setText("下单时间：" + data.getCreateTime());
            mOrderAmount.setText("订单总价：" + data.getAmount());
            if (data.getStatus() == OrderStatus.UNPAY.code) {
                mPayment.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Order> loader) {
        loader.stopLoading();
    }

    @OnClick(R.id.button_payment)
    public void gotopay(View v) {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(Constants.INTENT_ORDER, order);
        startActivity(intent);
    }
}
