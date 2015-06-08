/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu.loader;

import android.content.Context;

import com.yueqiu.model.ActBall;
import com.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class ActBallListLoader extends BaseAsyncTaskLoader<List<ActBall>> {

    public ActBallListLoader(Context context) {
        super(context);
    }

    @Override
    public List<ActBall> loadInBackground() {
        List<ActBall> list = new ArrayList<>();
        ActBall ab1 = new ActBall();
        ab1.setName("name1");
        list.add(ab1);
        ActBall ab2 = new ActBall();
        ab2.setName("name2");
        list.add(ab2);
        return list;
    }
}
