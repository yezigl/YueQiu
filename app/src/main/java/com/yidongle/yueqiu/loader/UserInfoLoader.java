package com.yidongle.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.model.User;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

/**
 * Created on 15/6/19.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class UserInfoLoader extends BaseAsyncTaskLoader<User> {

    public UserInfoLoader(Context context) {
        super(context);
    }

    @Override
    public User loadInBackground() {
        String ret = HttpUtils.get(API.USER_INFO.token(getContext()));
        Representation<User> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<User>>() {
        }.getType());
        return rep.getData();
    }
}
