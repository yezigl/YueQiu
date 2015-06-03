package com.yueqiu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yueqiu.index.LocationActivity;
import com.yueqiu.widget.BottomTabView;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.tab1)
    BottomTabView mTab1;
    @InjectView(R.id.tab2)
    BottomTabView mTab2;
    @InjectView(R.id.tab3)
    BottomTabView mTab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    @OnClick({R.id.tab1, R.id.tab2, R.id.tab3})
    public void tabClick(View v) {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivityForResult(intent, 0);
    }
}
