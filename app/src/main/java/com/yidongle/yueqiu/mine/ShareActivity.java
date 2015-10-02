package com.yidongle.yueqiu.mine;

import android.os.Bundle;
import android.view.View;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.utils.Constants;

import butterknife.OnClick;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class ShareActivity extends BaseActivity {

    String desc = "你还在为没有球友发愁吗？来，约个球！！！";
    String title = "我正在使用约个球";
    String url = "http://www.yueqiua.com/share/mobile/android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share);
    }

    @OnClick(R.id.weixin)
    public void weixin(View v) {
        final IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
        api.registerApp(Constants.WX_APP_ID);

        WXWebpageObject textObj = new WXWebpageObject();
        textObj.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = desc;
        msg.title = title;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        api.sendReq(req);
    }

    @OnClick(R.id.weixinfriends)
    public void weixinfriends(View v) {
        final IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
        api.registerApp(Constants.WX_APP_ID);

        WXWebpageObject textObj = new WXWebpageObject();
        textObj.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = desc;
        msg.title = title;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        api.sendReq(req);
    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
