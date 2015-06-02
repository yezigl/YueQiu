/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }
}
