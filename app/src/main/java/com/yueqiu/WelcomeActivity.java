package com.yueqiu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created on 15/6/13.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
