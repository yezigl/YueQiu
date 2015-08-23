package com.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yueqiu.model.Coupon;
import com.yueqiu.model.Representation;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.HttpUtils;
import com.yueqiu.utils.JsonUtils;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

/**
 * Created on 15/6/21.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class CouponLoader extends BaseAsyncTaskLoader<List<Coupon>> {

    public CouponLoader(Context context) {
        super(context);
    }

    @Override
    public List<Coupon> loadInBackground() {
        String ret = HttpUtils.get(getTokenUrl(Constants.API_HOST + "/1/user/coupons"));
        Representation<List<Coupon>> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<List<Coupon>>>() {
        }.getType());
        return getData(rep);
    }
}
