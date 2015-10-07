package com.yidongle.yueqiu.utils;

import android.content.Context;

import com.yidongle.yueqiu.model.Login;

import java.util.Locale;

/**
 * Created on 15/9/24.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public enum API {

    REGISTER(Constants.API_HOST + "/v1/register", false),
    LOGIN(Constants.API_HOST + "/v1/login", false),
    CAPTCHA(Constants.API_HOST + "/v1/captcha/mobile", false),
    GAME_DETAIL(Constants.API_HOST + "/v1/activity/%s", true),
    GAME_LIST(Constants.API_HOST + "/v1/activities", false),
    USER_MESSAGES(Constants.API_HOST + "/v1/user/messages", false),
    USER_ORDERS(Constants.API_HOST + "/v1/user/orders", false),
    USER_INFO(Constants.API_HOST + "/v1/user/me", false),
    USER_COUPONS(Constants.API_HOST + "/v1/user/coupons", false),
    ORDER_DETAIL(Constants.API_HOST + "/v1/orders/%s", true),
    ORDER(Constants.API_HOST + "/v1/orders", false),
    SIGN_IN(Constants.API_HOST + "/v1/orders/%s/signin", true),
    RESETPWD(Constants.API_HOST + "/v1/resetpwd", false),
    UPLOAD_AVATAR(Constants.API_HOST + "/v1/upload/avatar", false),
    WX_UNIFIEDORDER(Constants.API_HOST + "/v1/payment/weixin/unifiedorder", false);

    private String url;
    private boolean format;

    API(String url, boolean format) {
        this.url = url;
        this.format = format;
    }

    public String token(Context context, Object... args) {
        String furl = url(args);
        return furl + (furl.contains("?") ? "&" : "?") + "token=" + Login.getToken(context);
    }

    public String url(Object... args) {
        if (format) {
            return String.format(Locale.CHINA, url, args);
        } else {
            return url;
        }
    }
}
