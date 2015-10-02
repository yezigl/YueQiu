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
 * Created on 15/3/14.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class LoginLoader extends BaseAsyncTaskLoader<Login> {

    String mobile;
    String password;

    public LoginLoader(Context context, String mobile, String password) {
        super(context);
        this.mobile = mobile;
        this.password = password;
    }

    @Override
    public Login loadInBackground() {
        Map<String, Object> params = getParams();
        params.put("mobile", mobile);
        params.put("password", Utils.sha1Hex(password));
        String ret = HttpUtils.post(API.LOGIN.url(), params);
        Representation<Login> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<Login>>() {
        }.getType());
        return getData(rep);
    }
}
