package com.yueqiu.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Created on 15/2/19.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class Utils {

    private static DisplayMetrics metrics;

    private static Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    private static Pattern CAPTCHA_PATTERN = Pattern.compile("\\d{6}");

    public static int dp2px(Context context, int dp) {
        if (metrics == null) {
            metrics = context.getResources().getDisplayMetrics();
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static void finish(final Activity activity, int delayMills) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.finish();
            }
        }, delayMills);
    }

    public static boolean isMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(mobile).find();
    }

    public static boolean isCaptcha(String captcha) {
        if (TextUtils.isEmpty(captcha)) {
            return false;
        }
        return CAPTCHA_PATTERN.matcher(captcha).find();
    }

    public static String sha1Hex(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] bytes = md.digest(s.getBytes());
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append(Integer.toString((b & 0xFF) + 0x100, 16).substring(1));
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDimen(Context context, int resId) {
        return context.getResources().getDimensionPixelSize(resId);
    }

}
