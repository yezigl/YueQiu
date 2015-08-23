package com.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yueqiu.model.Representation;
import com.yueqiu.model.User;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.HttpUtils;
import com.yueqiu.utils.JsonUtils;
import com.yueqiu.utils.Logger;
import com.yueqiu.widget.BaseAsyncTaskLoader;

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
        String ret = HttpUtils.get(getTokenUrl(Constants.API_HOST + "/1/user/me"));
        Logger.debug("Loader", ret);
        Representation<User> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<User>>() {
        }.getType());
        return getData(rep);
    }
}
