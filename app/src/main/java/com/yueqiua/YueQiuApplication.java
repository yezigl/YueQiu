/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiua;

import android.app.Application;

import com.umeng.analytics.AnalyticsConfig;
import com.yueqiua.widget.utils.ChannelReader;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class YueQiuApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AnalyticsConfig.setAppkey("556d610c67e58eef5a0014aa");
        AnalyticsConfig.setChannel(ChannelReader.getChannel(this));
    }
}
