package com.yidongle.yueqiu.utils;

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

    public static void debug(Object... objects) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
        String clazz = ste.getClassName();
        String method = ste.getMethodName();
        StringBuilder builder = new StringBuilder();
        builder.append(clazz).append(".").append(method).append(" ");
        for (int i = 0; i < objects.length; i++) {
            if (i != 0) {
                builder.append("#");
            }
            builder.append(objects[i]);
        }
        Log.d(DEFAULT_TAG, builder.toString());
    }
}
