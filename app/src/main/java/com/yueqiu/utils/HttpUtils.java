package com.yueqiu.utils;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 15/6/12.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class HttpUtils {

    static OkHttpClient client = new OkHttpClient();

    public static String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(String url, Map<String, Object> params) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            list.add(entry.getKey() + "=" + StringUtils.encodeURL(String.valueOf(entry.getValue())));
        }
        return get(url + (url.contains("?") ? "&" : "?") + StringUtils.join(list.toArray(), "&"));
    }

    public static String post(String url, Map<String, Object> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
