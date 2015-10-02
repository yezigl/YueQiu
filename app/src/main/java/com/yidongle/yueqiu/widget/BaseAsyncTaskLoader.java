/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yidongle.yueqiu.widget;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Message;

import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.YueQiuApplication;
import com.yidongle.yueqiu.model.AppError;
import com.yidongle.yueqiu.model.Login;
import com.yidongle.yueqiu.model.Representation;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public abstract class BaseAsyncTaskLoader<D> extends AsyncTaskLoader<D> {

    int offset;

    public BaseAsyncTaskLoader(Context context) {
        super(context);
    }

    public BaseAsyncTaskLoader<D> setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    protected Map<String, Object> getParams() {
        return new HashMap<>();
    }

    protected String getTokenUrl(String baseUrl) {
        return baseUrl + (baseUrl.contains("?") ? "&" : "?") + "token=" + Login.getToken(getContext());
    }

    protected <T> T getData(Representation<T> rep) {
        if (rep == null) {
            Message msg = new Message();
            msg.what = R.id.api_error;
            msg.obj = "接口请求失败";
            YueQiuApplication.hanlder.sendMessage(msg);
        } else {
            AppError error = rep.getError();
            if (error != null && error.getCode() > 0) {
                Message msg = new Message();
                msg.obj = error.getMessage();
                switch (error.getCode()) {
                    case 401:
                        Login.clear(getContext());
                        msg.what = R.id.goto_login;
                        break;
                    default:
                        msg.what = R.id.api_error;
                }
                YueQiuApplication.hanlder.sendMessage(msg);
                return null;
            }
            return rep.getData();
        }
        return null;
    }
}
