/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu.widget;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.yueqiu.model.Login;
import com.yueqiu.model.Representation;

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

    protected Map<String, Object> getParams(boolean withToken) {
        Map<String, Object> params = new HashMap<>();
        if (withToken) {
            params.put("token", Login.getToken(getContext()));
        }

        return params;
    }

    protected <T> T getData(Representation<T> rep) {
        if (rep == null) {
            // show toast
        } else {
            return rep.getData();
        }
        return null;
    }
}
