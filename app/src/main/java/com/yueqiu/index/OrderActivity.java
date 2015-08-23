package com.yueqiu.index;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yueqiu.BaseActivity;
import com.yueqiu.LoginActivity;
import com.yueqiu.R;
import com.yueqiu.mine.CouponActivity;
import com.yueqiu.model.Coupon;
import com.yueqiu.model.Login;
import com.yueqiu.model.Order;
import com.yueqiu.model.Representation;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.HttpUtils;
import com.yueqiu.utils.JsonUtils;
import com.yueqiu.utils.Logger;
import com.yueqiu.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/6/17.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class OrderActivity extends BaseActivity {

    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.price)
    TextView mPrice;
    @InjectView(R.id.total)
    TextView mTotal;
    @InjectView(R.id.amount)
    TextView mAmount;
    @InjectView(R.id.number)
    EditText mNumber;
    @InjectView(R.id.button_increase)
    View incButton;
    @InjectView(R.id.button_decrease)
    View decButton;
    @InjectView(R.id.coupon)
    TextView mCoupon;

    String activityId;
    String couponId;
    String title;
    float price;
    Coupon coupon;

    int maxnum = 10;
    int number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

        activityId = getIntent().getStringExtra(Constants.INTENT_ACTIVITY_ID);
        title = getIntent().getStringExtra(Constants.INTENT_TITLE);
        price = getIntent().getFloatExtra(Constants.INTENT_PRICE, 0);

        mTitle.setText(title);
        mPrice.setText(price + "元");
        mTotal.setText(price * number + "元");
        mAmount.setText(price * number + "元");
        decButton.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == COUPON_REQCODE && data != null) {
                coupon = (Coupon) data.getSerializableExtra(Constants.INTENT_COUPON);
                if (coupon != null) {
                    couponId = coupon.getId();
                    mCoupon.setText(coupon.getName());
                }
            }
        }
    }

    @OnClick(R.id.button_payment)
    public void gotopay(View v) {
        if (!Login.isLogin(this)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        showProgressDialog("提交订单...");
        number = Integer.parseInt(mNumber.getText().toString());
        new AsyncTask<Void, Void, Representation<Order>>() {

            @Override
            protected Representation<Order> doInBackground(Void... params) {
                Map<String, Object> reqParams = new HashMap<String, Object>();
                reqParams.put("activityId", activityId);
                reqParams.put("couponId", StringUtils.defaultIfBlank(couponId, ""));
                reqParams.put("quantity", String.valueOf(number));
                Logger.debug(reqParams);
                String ret = HttpUtils.post(getTokenUrl(Constants.API_HOST + "/1/orders"), reqParams);
                Logger.debug(ret);
                return JsonUtils.fromJson(ret, new TypeToken<Representation<Order>>() {
                }.getType());
            }

            @Override
            protected void onPostExecute(Representation<Order> orderRepresentation) {
                hideProgressDialog();
                Order order = getData(orderRepresentation);
                if (order != null) {
                    Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
                    intent.putExtra(Constants.INTENT_ORDER, order);
                    startActivity(intent);
                }
            }
        }.execute();

    }

    @OnClick(R.id.button_decrease)
    public void decrease(final View v) {
        try {
            number = Integer.parseInt(mNumber.getText().toString());
        } catch (NumberFormatException e) {
            return;
        }
        number--;
        if (number < 1 || number > maxnum) {
            return;
        }
        if (number == 1) {
            decButton.setEnabled(false);
        }
        if (number < maxnum) {
            incButton.setEnabled(true);
        }
        mNumber.setText(number + "");
        mNumber.setSelection(mNumber.getText().toString().length());
        mTotal.setText(price * number + "元");
        mAmount.setText(price * number + "元");
    }

    @OnClick(R.id.button_increase)
    public void increase(final View v) {
        try {
            number = Integer.parseInt(mNumber.getText().toString());
        } catch (NumberFormatException e) {
            return;
        }
        number++;
        if (number < 1 || number > maxnum) {
            return;
        }
        if (number > 1) {
            decButton.setEnabled(true);
        }
        if (number == maxnum) {
            incButton.setEnabled(false);
        }
        mNumber.setText(number + "");
        mNumber.setSelection(mNumber.getText().toString().length());
        mTotal.setText(price * number + "元");
        mAmount.setText(price * number + "元");
    }

    @OnClick(R.id.coupon)
    public void coupon(View v) {
        Intent intent = new Intent(this, CouponActivity.class);
        startLoginActivityForResult(intent, COUPON_REQCODE);
    }
}
