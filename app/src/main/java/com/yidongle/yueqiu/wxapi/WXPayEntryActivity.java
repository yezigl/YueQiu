package com.yidongle.yueqiu.wxapi;


import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.mine.MyOrderActivity;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.Logger;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{
	
	private static final String TAG = "WXPayEntryActivity";
	
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);

        api.handleIntent(getIntent(), this);

        showProgressDialog("正在处理...");
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

    @Override
    public void onBackPressed() {
        // do nothing
    }

	@Override
	public void onReq(BaseReq req) {
	}

	/**
	 * 回调中errCode值列表：
	 *
	 *	0	成功	展示成功页面
	 *	-1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
	 *	-2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
	 * @param resp
	 */
	@Override
	public void onResp(BaseResp resp) {
        Logger.debug("onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			int errCode = resp.errCode;
			switch (errCode) {
				case 0:
                    orderDetail();
					break;
				case -1:
                    showToast("支付失败: " + resp.errStr);
					finish();
					break;
				case -2:
                    finish();
					break;
			}
            hideProgressDialog();
		}
	}

    private void orderDetail() {
        Intent intent = new Intent(this, MyOrderActivity.class);
        intent.putExtra(Constants.INTENT_ORDER_ID, mPreferences.getString(Constants.PREF_ORDERID, ""));
        intent.putExtra(Constants.INTENT_FROMPAY, true);
        startActivity(intent);
        finish();
    }
}