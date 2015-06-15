package com.yueqiu.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yueqiu.BaseActivity;
import com.yueqiu.R;

import butterknife.OnClick;

/**
 * Created on 15/6/9.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class GameDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_detail);
    }

    @OnClick(R.id.signup)
    public void signup(View v) {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }
}
