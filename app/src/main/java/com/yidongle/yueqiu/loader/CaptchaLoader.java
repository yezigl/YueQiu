package com.yidongle.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.model.Captcha;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

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
        String ret = HttpUtils.post(API.CAPTCHA.url(), params);
        Representation<Captcha> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<Captcha>>() {
        }.getType());
        return getData(rep);
    }
}
