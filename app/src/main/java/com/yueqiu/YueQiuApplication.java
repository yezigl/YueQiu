/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu;

import android.app.Application;

import com.umeng.analytics.AnalyticsConfig;
import com.yueqiu.utils.AppHanlder;
import com.yueqiu.utils.ChannelReader;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class YueQiuApplication extends Application {

    public static AppHanlder hanlder;

    @Override
    public void onCreate() {
        super.onCreate();

        hanlder = new AppHanlder(this);

        AnalyticsConfig.setAppkey("556d610c67e58eef5a0014aa");
        AnalyticsConfig.setChannel(ChannelReader.getChannel(this));
    }
}
