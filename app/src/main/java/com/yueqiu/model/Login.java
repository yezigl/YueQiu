package com.yueqiu.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.yueqiu.utils.Constants;

/**
 * Created on 15/3/14.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class Login extends Res {

    private int uid;
    private String token;
    private String login;
    private String nickname;
    private String avatar;
    private String mobile;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void save(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constants.PREF_UID, uid);
        editor.putString(Constants.PREF_TOKEN, token);
        editor.putString(Constants.PREF_NICKNAME, nickname);
        editor.putString(Constants.PREF_AVATAR, avatar);
        editor.putString(Constants.PREF_MOBILE, mobile);
        editor.apply();
    }

    public void update(Context context, Login login) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constants.PREF_UID, login.getUid());
        editor.putString(Constants.PREF_TOKEN, login.getToken());
        editor.putString(Constants.PREF_NICKNAME, login.getNickname());
        editor.putString(Constants.PREF_AVATAR, login.getAvatar());
        editor.putString(Constants.PREF_MOBILE, login.getMobile());
        editor.apply();
    }

    public void logout(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(Constants.PREF_TOKEN, "");
    }

    public static boolean isLogin(Context context) {
        return !TextUtils.isEmpty(getToken(context));
    }

    public static Login from(Context context) {
        Login login = new Login();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        login.setUid(sp.getInt(Constants.PREF_UID, 0));
        login.setToken(sp.getString(Constants.PREF_TOKEN, ""));
        login.setNickname(sp.getString(Constants.PREF_NICKNAME, ""));
        login.setAvatar(sp.getString(Constants.PREF_AVATAR, ""));
        login.setMobile(sp.getString(Constants.PREF_MOBILE, ""));
        return login;
    }


}
