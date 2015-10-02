/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yidongle.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.model.Order;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;
import java.util.Map;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class MyOrderListLoader extends BaseAsyncTaskLoader<List<Order>> {

    private int status;

    public MyOrderListLoader(Context context) {
        super(context);
    }

    public BaseAsyncTaskLoader<List<Order>> params(int status) {
        this.status = status;
        return this;
    }

    @Override
    public List<Order> loadInBackground() {
        Map<String, Object> params = getParams();
        params.put("status", status);
        String ret = HttpUtils.get(API.USER_ORDERS.token(getContext()), params);
        Representation<List<Order>> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<List<Order>>>() {
        }.getType());
        return getData(rep);
    }

}
