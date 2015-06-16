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

import java.util.Map;

/**
 * Created on 15/6/17.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class OrderLoader extends BaseAsyncTaskLoader<Order> {

    String activityId;

    public OrderLoader(Context context) {
        super(context);
    }

    public OrderLoader params(String activityId) {
        this.activityId = activityId;
        return this;
    }

    @Override
    public Order loadInBackground() {
        Map<String, Object> params = getParams();
        params.put("activityId", activityId);
        String ret = HttpUtils.post(getTokenUrl(Constants.API_HOST + "/1/orders"), params);
        Logger.debug("Loader", ret);
        Representation<Order> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<Order>>() {
        }.getType());
        return getData(rep);
    }
}
