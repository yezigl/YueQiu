package com.yidongle.yueqiu.order;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.LoginActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.mine.CouponListActivity;
import com.yidongle.yueqiu.model.Coupon;
import com.yidongle.yueqiu.model.Game;
import com.yidongle.yueqiu.model.Login;
import com.yidongle.yueqiu.model.Order;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.pay.PaymentActivity;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.utils.StringUtils;

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

    Game activity;
    String couponId;
    Coupon coupon;

    int maxnum = 10;
    int number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

        activity = (Game) getIntent().getSerializableExtra(Constants.INTENT_ACTIVITY);

        mTitle.setText(activity.getTitle());
        mPrice.setText(activity.getPrice() + "元");
        mTotal.setText(activity.getPrice() * number + "元");
        mAmount.setText(activity.getPrice() * number + "元");
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
                reqParams.put("activityId", activity.getId());
                reqParams.put("couponId", StringUtils.defaultIfBlank(couponId, ""));
                reqParams.put("quantity", String.valueOf(number));
                String ret = HttpUtils.post(API.ORDER.token(OrderActivity.this), reqParams);
                return JsonUtils.fromJson(ret, new TypeToken<Representation<Order>>() {
                }.getType());
            }

            @Override
            protected void onPostExecute(Representation<Order> orderRepresentation) {
                hideProgressDialog();
                Order order = getData(orderRepresentation);
                if (order != null) {
                    mPreferences.edit().putString(Constants.PREF_ORDERID, order.getId()).apply();
                    Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
                    intent.putExtra(Constants.INTENT_ORDER, order);
                    startActivity(intent);
                    //finish();
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
        mTotal.setText(activity.getPrice() * number + "元");
        mAmount.setText(activity.getPrice() * number + "元");
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
        mTotal.setText(activity.getPrice() * number + "元");
        mAmount.setText(activity.getPrice() * number + "元");
    }

    @OnClick(R.id.coupon)
    public void coupon(View v) {
        Intent intent = new Intent(this, CouponListActivity.class);
        startLoginActivityForResult(intent, COUPON_REQCODE);
    }
}
