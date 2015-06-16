package com.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yueqiu.model.Game;
import com.yueqiu.model.Representation;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.HttpUtils;
import com.yueqiu.utils.JsonUtils;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.Locale;

/**
 * Created on 15/6/16.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class GameDetailLoader extends BaseAsyncTaskLoader<Game> {

    private String urlBase = Constants.API_HOST + "/1/activity/%s";

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
        String ret = HttpUtils.get(getTokenUrl(String.format(Locale.CHINA, urlBase, activityId)));
        Representation<Game> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<Game>>() {
        }.getType());
        return getData(rep);
    }
}
