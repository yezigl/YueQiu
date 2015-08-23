package com.yueqiu.loader;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yueqiu.model.Message;
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
public class MessageLoader extends BaseAsyncTaskLoader<List<Message>> {

    public MessageLoader(Context context) {
        super(context);
    }

    @Override
    public List<Message> loadInBackground() {
        String ret = HttpUtils.get(getTokenUrl(Constants.API_HOST + "/1/user/messages"));
        Representation<List<Message>> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<List<Message>>>() {
        }.getType());
        return getData(rep);
    }
}
