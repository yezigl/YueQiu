package com.yidongle.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.model.Game;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

/**
 * Created on 15/6/16.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class GameDetailLoader extends BaseAsyncTaskLoader<Game> {

    private String activityId;

    public GameDetailLoader(Context context) {
        super(context);
    }

    public GameDetailLoader params(String activityId) {
        this.activityId = activityId;
        return this;
    }

    @Override
    public Game loadInBackground() {
        String ret = HttpUtils.get(API.GAME_DETAIL.token(getContext(), activityId));
        Representation<Game> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<Game>>() {
        }.getType());
        return getData(rep);
    }
}
