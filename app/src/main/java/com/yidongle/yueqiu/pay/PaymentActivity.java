package com.yidongle.yueqiu.pay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.PayResult;
import com.alipay.sdk.pay.SignUtils;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.android.iconify.Iconify;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.mine.MyOrderActivity;
import com.yidongle.yueqiu.model.Order;
import com.yidongle.yueqiu.model.PayType;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.model.WeixinPrePay;
import com.yidongle.yueqiu.time.NTP;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.DateUtils;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.utils.Logger;
import com.yidongle.yueqiu.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    @InjectView(R.id.time_remain)
    TextView mTimeRemain;
    @InjectView(R.id.button_payment)
    Button mPayment;

    Order order;
    PayType payType = PayType.WEIXIN;
    long remainTime;

    final IWXAPI wxapi = WXAPIFactory.createWXAPI(this, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);

        wxapi.registerApp(Constants.WX_APP_ID);

        order = (Order) getIntent().getSerializableExtra(Constants.INTENT_ORDER);

        mTitle.setText(getString(R.string.payment_order_title, order.getActivity().getTitle()));
        mAmount.setText(getString(R.string.payment_order_price, order.getAmount()));
        mTimeRemain.setText(getString(R.string.payment_time_remain2, "00", "00"));

        remainTime = Constants.PAY_EXIRE_TIME - NTP.currentTimeMillis()
                + DateUtils.parse(order.getCreateTime(), DateUtils.F_DATE_TIME).getTime();
        if (remainTime > 0) {
            TimeCount tc = new TimeCount(remainTime, 1000);
            tc.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Iconify.addIcons(mAlipayChecked, mWeixinChecked);
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mPayment.setEnabled(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minute = millisUntilFinished / 1000 / 60;
            long second = (millisUntilFinished - minute * 60 * 1000) / 1000;
            mTimeRemain.setText(getString(R.string.payment_time_remain2, (minute < 10 ? "0" + minute : minute), (second < 10 ? "0" + second : second)));
        }
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
        if (payType == PayType.ALIPAY) {
            alipayPay();
        } else if (payType == PayType.WEIXIN) {
            weixinPay();
        }
    }

    private void weixinPay() {
        showProgressDialog("正在请求服务器...");
        new AsyncTask<Void, Void, Representation<WeixinPrePay>>() {

            @Override
            protected Representation<WeixinPrePay> doInBackground(Void... params) {
                Map<String, Object> map = new HashMap<>();
                map.put("orderId", order.getId());
                String ret = HttpUtils.post(API.WX_UNIFIEDORDER.url(), map);
                return JsonUtils.fromJson(ret, new TypeToken<Representation<WeixinPrePay>>() {
                }.getType());
            }

            @Override
            protected void onPostExecute(Representation<WeixinPrePay> data) {
                hideProgressDialog();
                WeixinPrePay prePay = getData(data);
                if (prePay != null) {
                    Logger.debug(prePay);
                    PayReq request = new PayReq();
                    request.appId = prePay.getAppId();
                    request.partnerId = prePay.getPartnerId();
                    request.prepayId = prePay.getPrepayId();
                    request.packageValue = prePay.getPackageValue();
                    request.nonceStr = prePay.getNonceStr();
                    request.timeStamp = prePay.getTimestamp();
                    request.sign = prePay.getSign();

                    wxapi.sendReq(request);

                    //finish();
                }
            }
        }.execute();
    }

    /**
     * 6001 用户去取消
     */
    private void alipayPay() {
        showProgressDialog("正在请求服务器...");
        new AsyncTask<Void, Void, PayResult>() {

            @Override
            protected PayResult doInBackground(Void... params) {
                PayTask payTask = new PayTask(PaymentActivity.this);
                Map<String, Object> values = new TreeMap<>();
                values.put("service", Constants.ALIPAY_PAY_SERVICE);
                values.put("partner", Constants.ALIPAY_PARNTER_ID);
                values.put("_input_charset", Constants.ALIPAY_CHARSET);
                //values.put("sign_type", Constants.ALIPAY_SIGN_TYPE);
                values.put("app_id", Constants.ALIPAY_APP_ID);
                values.put("notify_url", Constants.ALIPAY_NOTIFY_URL);
                values.put("out_trade_no", order.getId());
                values.put("subject", order.getActivity().getTitle());
                values.put("payment_type", Constants.ALIPAY_PAYMENT_TYPE);
                values.put("seller_id", Constants.ALIPAY_SELLER_ID);
                values.put("total_fee", order.getAmount() - order.getDiscount());
                values.put("body", order.getActivity().getTitle());
                values.put("it_b_pay", Constants.ALIPAY_EXPIRE_TIME);
                List<String> list = new ArrayList<>();
                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    list.add(entry.getKey() + "=" + entry.getValue());
                }
                String orderInfo = StringUtils.join(list, "&");

                // 对订单做RSA 签名
                String sign = StringUtils.encodeURL(SignUtils.sign(orderInfo, Constants.ALIPAY_RSA_PRIVATE));
                final String payInfo = orderInfo + "&sign=" + sign + "&sign_type=RSA";
                String ret = payTask.pay(payInfo);
                return new PayResult(ret);
            }

            @Override
            protected void onPostExecute(PayResult payResult) {
                hideProgressDialog();
                String resultStatus = payResult.getResultStatus();

                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    Intent intent = new Intent(PaymentActivity.this, MyOrderActivity.class);
                    intent.putExtra(Constants.INTENT_FROMPAY, true);
                    intent.putExtra(Constants.INTENT_ORDER_ID, mPreferences.getString(Constants.PREF_ORDERID, ""));
                    startActivity(intent);
                } else {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        showToast("支付结果确认中");
                        Intent intent = new Intent(PaymentActivity.this, MyOrderActivity.class);
                        intent.putExtra(Constants.INTENT_FROMPAY, true);
                        intent.putExtra(Constants.INTENT_ORDER_ID, mPreferences.getString(Constants.PREF_ORDERID, ""));
                        startActivity(intent);
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        showToast("支付失败");

                    }
                }
            }
        }.execute();

    }
}

