package com.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yueqiu.model.Captcha;
import com.yueqiu.model.Representation;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.HttpUtils;
import com.yueqiu.utils.JsonUtils;
import com.yueqiu.utils.Logger;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.Map;

/**
 * Created on 15/3/14.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class CaptchaLoader extends BaseAsyncTaskLoader<Captcha> {

    String mobile;

    public CaptchaLoader(Context context, String mobile) {
        super(context);
        this.mobile = mobile;
    }

    @Override
    public Captcha loadInBackground() {
        Map<String, Object> params = getParams();
        params.put("mobile", mobile);
        String ret = HttpUtils.post(Constants.API_HOST + "/1/captcha/mobile", params);
        Logger.debug("Loader", ret);
        Representation<Captcha> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<Captcha>>() {
        }.getType());
        return getData(rep);
    }
}
