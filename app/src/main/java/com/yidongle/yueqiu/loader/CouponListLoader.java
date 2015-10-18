package com.yidongle.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.model.Coupon;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

/**
 * Created on 15/6/21.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class CouponListLoader extends BaseAsyncTaskLoader<List<Coupon>> {

    public CouponListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Coupon> loadInBackground() {
        String ret = HttpUtils.get(API.USER_COUPONS.token(getContext()));
        Representation<List<Coupon>> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<List<Coupon>>>() {
        }.getType());
        return getData(rep);
    }
}
