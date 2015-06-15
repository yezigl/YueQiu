/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu.loader;

import android.content.Context;

import com.yueqiu.model.Login;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.HttpUtils;
import com.yueqiu.utils.JsonUtils;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.Map;

/**
 * register
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class RegisterLoader extends BaseAsyncTaskLoader<Login> {

    String login, password, mobile, captcha;

    public RegisterLoader(Context context) {
        super(context);
    }

    public RegisterLoader params(String login, String password, String mobile, String captcha) {
        this.login = login;
        this.password = password;
        this.mobile = mobile;
        this.captcha = captcha;
        return this;
    }

    @Override
    public Login loadInBackground() {
        Map<String, Object> params = getParams(false);
        params.put("login", login);
        params.put("password", password);
        params.put("phone", mobile);
        params.put("captchamobile", captcha);
        String ret = HttpUtils.post(Constants.API_HOST + "/1/register", params);
        return JsonUtils.getGson().fromJson(ret, Login.class);
    }
}
