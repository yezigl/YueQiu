package com.yidongle.yueqiu.mine;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.MainActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.loader.OrderDetailLoader;
import com.yidongle.yueqiu.model.Order;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.model.Signin;
import com.yidongle.yueqiu.pay.PaymentActivity;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;

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
    @InjectView(R.id.order_paytime)
    TextView mPayTime;
    @InjectView(R.id.order_paytype)
    TextView mPayType;
    @InjectView(R.id.button_payment)
    View mPayment;
    @InjectView(R.id.layout_order_paytime)
    View mLayoutPayTime;
    @InjectView(R.id.layout_order_paytype)
    View mLayoutPayType;
    @InjectView(R.id.button_signin)
    Button mSignin;

    @InjectView(R.id.layout_swiperefresh)
    SwipeRefreshLayout mSwipeRefresh;

    String oid;
    Order order;
    boolean fromPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myorder);

        oid = getIntent().getStringExtra(Constants.INTENT_ORDER_ID);
        fromPay = getIntent().getBooleanExtra(Constants.INTENT_FROMPAY, false);

        getLoaderManager().restartLoader(hashCode(), null, this);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setEnabled(false);
                getLoaderManager().restartLoader(hashCode(), null, MyOrderActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (fromPay) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Loader<Order> onCreateLoader(int id, Bundle args) {
        return new OrderDetailLoader(this).params(oid);
    }

    @Override
    public void onLoadFinished(Loader<Order> loader, Order data) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
            mSwipeRefresh.setEnabled(true);
        }
        if (data != null) {
            order = data;
            mTitle.setText(data.getActivity().getTitle());
            mAddress.setText(data.getActivity().getStadium().getName());
            mDate.setText(data.getActivity().getDate());
            mRule.setText(data.getActivity().getStadium().getSize() + "人制");
            mOrderTime.setText("下单时间: " + data.getCreateTime());
            mOrderAmount.setText("订单总价: " + data.getAmount());
            if (data.isPayed()) {
                mLayoutPayTime.setVisibility(View.VISIBLE);
                mLayoutPayType.setVisibility(View.VISIBLE);
                mPayTime.setText("支付时间: " + data.getPayTime());
                mPayType.setText("支付方式: " + data.getPayTypeCN());
            }
            if (data.isCreated()) {
                mPayment.setVisibility(View.VISIBLE);
            } else if (data.isPayed()) {
                //mSignin.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Order> loader) {
        loader.stopLoading();
    }

    @OnClick(R.id.button_payment)
    public void payment(View v) {
        mPreferences.edit().putString(Constants.PREF_ORDERID, order.getId()).apply();
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(Constants.INTENT_ORDER, order);
        startActivity(intent);
    }

    @OnClick(R.id.button_signin)
    public void signin(View v) {
        showProgressDialog("签到...");
        new AsyncTask<Void, Void, Representation<Signin>>() {

            @Override
            protected Representation<Signin> doInBackground(Void... params) {
                String ret = HttpUtils.post(API.SIGN_IN.token(MyOrderActivity.this, order.getId()));
                return JsonUtils.fromJson(ret, new TypeToken<Representation<Signin>>() {
                }.getType());
            }

            @Override
            protected void onPostExecute(Representation<Signin> data) {
                hideProgressDialog();
                Signin signin = getData(data);
                if (signin != null) {
                    mSignin.setText("已签到");
                    mSignin.setEnabled(false);
                }
            }
        }.execute();
    }
}
