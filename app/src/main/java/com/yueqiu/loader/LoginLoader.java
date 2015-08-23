package com.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yueqiu.model.Login;
import com.yueqiu.model.Representation;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.HttpUtils;
import com.yueqiu.utils.JsonUtils;
import com.yueqiu.utils.Logger;
import com.yueqiu.utils.Utils;
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
        String ret = HttpUtils.post(Constants.API_HOST + "/1/login", params);
        Logger.debug("Loader", ret);
        Representation<Login> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<Login>>() {
        }.getType());
        return getData(rep);
    }
}
