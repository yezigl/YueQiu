package com.yueqiu.index;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.android.iconify.Iconify;
import com.yueqiu.BaseActivity;
import com.yueqiu.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
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
        } else if (v.getId() == R.id.layout_weixin) {
            mAlipayChecked.setVisibility(View.INVISIBLE);
            mWeixinChecked.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.order_pay)
    public void pay(View v) {

    }
}

