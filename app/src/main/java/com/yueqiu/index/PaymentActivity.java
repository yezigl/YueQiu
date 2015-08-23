package com.yueqiu.index;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.android.iconify.Iconify;
import com.yueqiu.BaseActivity;
import com.yueqiu.R;
import com.yueqiu.model.Order;
import com.yueqiu.model.PayType;
import com.yueqiu.utils.Constants;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class PaymentActivity extends BaseActivity {

    @InjectView(R.id.alipay_checked)
    TextView mAlipayChecked;
    @InjectView(R.id.weixin_checked)
    TextView mWeixinChecked;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.amount)
    TextView mAmount;

    Order order;
    String title;
    PayType payType = PayType.ALIPAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);

        order = (Order) getIntent().getSerializableExtra(Constants.INTENT_ORDER);
        title = getIntent().getStringExtra(Constants.INTENT_TITLE);

        mTitle.setText("订单名称:  " + (order != null ? order.getActivity().getTitle() : title));
        mAmount.setText("订单价格:  " + order.getAmount() + "元");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Iconify.addIcons(mAlipayChecked, mWeixinChecked);
    }

    @OnClick({R.id.layout_alipay, R.id.layout_weixin})
    public void checked(View v) {
        if (v.getId() == R.id.layout_alipay) {
            mAlipayChecked.setVisibility(View.VISIBLE);
            mWeixinChecked.setVisibility(View.INVISIBLE);
            payType = PayType.ALIPAY;
        } else if (v.getId() == R.id.layout_weixin) {
            mAlipayChecked.setVisibility(View.INVISIBLE);
            mWeixinChecked.setVisibility(View.VISIBLE);
            payType = PayType.WEIXIN;
        }
    }

    @OnClick(R.id.button_payment)
    public void pay(View v) {

    }
}

