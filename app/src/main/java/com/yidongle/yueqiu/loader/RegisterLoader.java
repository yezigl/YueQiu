/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yidongle.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.model.Login;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.utils.Utils;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.Map;

/**
 * register
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class RegisterLoader extends BaseAsyncTaskLoader<Login> {

    String password, mobile, captcha;

    public RegisterLoader(Context context) {
        super(context);
    }

    public RegisterLoader params(String mobile, String password, String captcha) {
        this.password = password;
        this.mobile = mobile;
        this.captcha = captcha;
        return this;
    }

    @Override
    public Login loadInBackground() {
        Map<String, Object> params = getParams();
        params.put("mobile", mobile);
        params.put("password", Utils.sha1Hex(password));
        params.put("captcha", captcha);
        String ret = HttpUtils.post(API.REGISTER.url(), params);
        Representation<Login> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<Login>>() {
        }.getType());
        return getData(rep);
    }
}
