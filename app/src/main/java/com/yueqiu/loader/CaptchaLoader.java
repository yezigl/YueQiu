package com.yueqiu.loader;

import android.content.Context;

import com.yueqiu.model.Captcha;
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
public class CaptchaLoader extends BaseAsyncTaskLoader<Captcha> {

    String mobile;

    public CaptchaLoader(Context context, String mobile) {
        super(context);
        this.mobile = mobile;
    }

    @Override
    public Captcha loadInBackground() {
        Map<String, Object> params = getParams(false);
        params.put("mobile", mobile);
        String ret = HttpUtils.post(Constants.API_HOST + "/1/captcha/mobile", params);
        return JsonUtils.getGson().fromJson(ret, Captcha.class);
    }
}
