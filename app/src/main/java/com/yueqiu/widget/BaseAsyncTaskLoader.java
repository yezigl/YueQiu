/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu.widget;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public abstract class BaseAsyncTaskLoader<D> extends AsyncTaskLoader<D> {

    int offset;

    public BaseAsyncTaskLoader(Context context) {
        super(context);
    }

    public BaseAsyncTaskLoader<D> setOffset(int offset) {
        this.offset = offset;
        return this;
    }
}
