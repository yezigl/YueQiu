package com.yueqiu.utils;

import android.util.Log;

/**
 * Created on 15/6/8.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class Logger {

    private static final String DEFAULT_TAG = "YueQiuTag";

    public static void debug(String tag, Object... objects) {
        StringBuilder builder = new StringBuilder();
        for (Object object : objects) {
            builder.append(object).append("#");
        }
        Log.d(tag, builder.toString());
    }
}
