package com.yueqiu.utils;

import android.graphics.Bitmap;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
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
        return post(url, params, null);
    }

    public static String post(String url, Map<String, Object> params, Map<String, String> headers) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        Request.Builder rb = new Request.Builder()
                .url(url)
                .post(builder.build());

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                rb.addHeader(entry.getKey(), entry.getValue());
            }
        }

        try {
            Response response = client.newCall(rb.build()).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, String entity) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), entity);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody).build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        Logger.debug("Loader", bitmap + " xxx");
//        RequestBody requestBody = new MultipartBuilder()
//                .type(MultipartBuilder.FORM)
//                .addFormDataPart("bucket", "avatar")
//                .addFormDataPart("file", String.valueOf(System.currentTimeMillis()), RequestBody.create(MediaType.parse("image/png"), bitmapdata))
//                .build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), bitmapdata);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
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
