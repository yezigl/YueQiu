/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yidongle.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.model.Game;
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
public class GameListLoader extends BaseAsyncTaskLoader<List<Game>> {

    private int dateType;

    public GameListLoader(Context context) {
        super(context);
    }

    public BaseAsyncTaskLoader<List<Game>> params(int dateType) {
        this.dateType = dateType;
        return this;
    }

    @Override
    public List<Game> loadInBackground() {
        Map<String, Object> params = getParams();
        params.put("dateType", dateType);
        String ret = HttpUtils.get(API.GAME_LIST.url(), params);
        Representation<List<Game>> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<List<Game>>>() {
        }.getType());
        return getData(rep);
    }

}
