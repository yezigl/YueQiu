package com.yueqiu.loader;

import android.content.Context;

import com.yueqiu.model.Login;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.HttpUtils;
import com.yueqiu.utils.JsonUtils;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.Map;

/**
 * Created on 15/3/14.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class LoginLoader extends BaseAsyncTaskLoader<Login> {

    String mobile;
    String captcha;

    public LoginLoader(Context context, String mobile, String captcha) {
        super(context);
        this.mobile = mobile;
        this.captcha = captcha;
    }

    @Override
    public Login loadInBackground() {
        Map<String, Object> params = getParams(false);
        params.put("mobile", mobile);
        params.put("captcha", captcha);
        String ret = HttpUtils.post(Constants.API_HOST + "/1/login/mobile", params);
        return JsonUtils.fromJson(ret, Login.class);
    }
}
