/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yidongle.yueqiu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.yidongle.yueqiu.model.AppError;
import com.yidongle.yueqiu.model.Login;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.utils.Constants;

import butterknife.ButterKnife;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();

    protected int FILECHOOSER_REQCODE = 0x1001;
    protected int CAMERA_REQCODE = 0x1002;
    protected int PHOTOCUT_REQCODE = 0x1003;
    protected int MODIFY_REQCODE = 0x1004;
    protected int COUPON_REQCODE = 0x1005;

    protected ProgressDialog progressDialog;

    protected SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        View back = findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back(v);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void back(View v) {
        finish();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }

    protected void showToast(int res) {
        Toast toast = Toast.makeText(this, res, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showProgressDialog(int res) {
        if (!isFinishing()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("");
            progressDialog.setMessage(getString(res));
            progressDialog.show();
        }
    }

    protected void showProgressDialog(String message) {
        if (!isFinishing()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("");
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    protected void hideProgressDialog() {
        if (!isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    protected int getDimen(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    protected <T> T getData(Representation<T> rep) {
        if (rep == null) {
            Message msg = new Message();
            msg.what = R.id.api_error;
            msg.obj = "接口请求失败";
            YueQiuApplication.hanlder.sendMessage(msg);
        } else {
            AppError error = rep.getError();
            if (error != null && error.getCode() > 0) {
                Message msg = new Message();
                msg.obj = error.getMessage();
                switch (error.getCode()) {
                    case 401:
                        msg.what = R.id.goto_login;
                        break;
                    default:
                        msg.what = R.id.api_error;
                }
                YueQiuApplication.hanlder.sendMessage(msg);
                return null;
            }
            return rep.getData();
        }
        return null;
    }

    protected String getTokenUrl(String baseUrl) {
        return baseUrl + (baseUrl.contains("?") ? "&" : "?") + "token=" + Login.getToken(this);
    }

    protected void startLoginActivity(Intent intent) {
        if (Login.isLogin(this)) {
            startActivity(intent);
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra(Constants.INTENT_TARGET_INTENT, intent);
            startActivity(loginIntent);
        }
    }

    protected void startLoginActivityForResult(Intent intent, int requestCode) {
        if (Login.isLogin(this)) {
            startActivityForResult(intent, requestCode);
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra(Constants.INTENT_TARGET_INTENT, intent);
            loginIntent.putExtra(Constants.INTENT_REQUESTCODE, requestCode);
            startActivity(loginIntent);
        }
    }
}
