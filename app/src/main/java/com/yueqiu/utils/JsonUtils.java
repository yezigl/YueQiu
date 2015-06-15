package com.yueqiu.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created on 15/3/14.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class JsonUtils {

    private static Gson gson;

    public synchronized static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return getGson().fromJson(json, clazz);
        } catch (Exception e) {

        }
        return null;
    }

    public static <T> T fromJson(String json, Type type) {
        try {
            return getGson().fromJson(json, type);
        } catch (Exception e) {

        }
        return null;
    }

}
