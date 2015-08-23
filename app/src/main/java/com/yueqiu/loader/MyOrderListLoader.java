/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yueqiu.model.Order;
import com.yueqiu.model.Representation;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.HttpUtils;
import com.yueqiu.utils.JsonUtils;
import com.yueqiu.utils.Logger;
import com.yueqiu.widget.BaseAsyncTaskLoader;

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
        String ret = HttpUtils.get(getTokenUrl(Constants.API_HOST + "/1/user/orders"), params);
        Logger.debug("Loader", ret);
        Representation<List<Order>> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<List<Order>>>() {
        }.getType());
        return getData(rep);
    }

}
