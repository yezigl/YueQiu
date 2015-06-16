package com.yueqiu;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yueqiu.loader.CaptchaLoader;
import com.yueqiu.loader.RegisterLoader;
import com.yueqiu.model.Captcha;
import com.yueqiu.model.Login;
import com.yueqiu.utils.StringUtils;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/2/19.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class RegisterActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Login> {

    @InjectView(R.id.password)
    EditText mPassword;
    @InjectView(R.id.mobile)
    EditText mMobile;
    @InjectView(R.id.captcha)
    EditText mCaptcha;
    @InjectView(R.id.button_captcha)
    Button mCaptchaButton;

    private static final long RETRY_TIME = 60000;
    private static final String PREF_KEY = "mobile_captcha_time";

    TimeCount tc;
    long timeStart;

    String login, password, mobile, captcha;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_register);

        // 判断时候仍在重新发送的周期内
        timeStart = mPreferences.getLong(PREF_KEY, 0);
        long timeUsed = System.currentTimeMillis() - timeStart;
        if (timeStart > 0 && timeUsed < RETRY_TIME) {
            tc = new TimeCount(RETRY_TIME - timeUsed, 1000);
            tc.start();
            mCaptchaButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen
                    .text_14sp));
            mCaptchaButton.setEnabled(false);
            mCaptchaButton.setText((RETRY_TIME - timeUsed) / 1000 + "秒后重新获取");
        }

        mCaptcha.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    register(v);
                    return false;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreferences.edit().putLong(PREF_KEY, timeStart).apply();
    }

    @OnClick(R.id.button_captcha)
    public void getCaptcha(View v) {
        mobile = mMobile.getText().toString();
        if (StringUtils.isBlank(mobile)) {
            showToast("手机号不能为空");
            //mMobile.showKeyboard();
            return;
        } else if (mobile.length() != 11) {
            showToast("手机号不正确");
            //mMobile.showKeyboard();
            return;
        }
        tc = new TimeCount(RETRY_TIME, 1000);
        tc.start();
        timeStart = System.currentTimeMillis();
        getLoaderManager().restartLoader(hashCode() + 1, null, new LoaderManager.LoaderCallbacks<Captcha>() {
            @Override
            public Loader<Captcha> onCreateLoader(int id, Bundle args) {
                showProgressDialog("正在获取验证码...");
                return new CaptchaLoader(RegisterActivity.this, mobile);
            }

            @Override
            public void onLoadFinished(Loader<Captcha> loader, Captcha data) {
                hideProgressDialog();
                if (data != null && data.isResult()) {
                    showToast("发送成功");
                }
            }

            @Override
            public void onLoaderReset(Loader<Captcha> loader) {
                loader.stopLoading();
            }
        });
    }

    @OnClick(R.id.button_register)
    public void register(View v) {
        password = mPassword.getText().toString();
        mobile = mMobile.getText().toString();
        captcha = mCaptcha.getText().toString();

        if (StringUtils.isBlank(mobile)) {
            showToast("手机号不能为空");
            //mMobile.showKeyboard();
            return;
        } else if (mobile.length() != 11) {
            showToast("手机号不正确");
            //mMobile.showKeyboard();
            return;
        }

        if (StringUtils.isBlank(captcha)) {
            showToast("验证码不能为空");
            //mCaptcha.showKeyboard();
            return;
        }

        if (StringUtils.isBlank(password)) {
            showToast("密码不能为空");
            //mPassword.showKeyboard();
            return;
        }

        getLoaderManager().restartLoader(hashCode(), null, this);
    }

    @Override
    public Loader<Login> onCreateLoader(int id, Bundle args) {
        showProgressDialog("正在提交请求...");
        return new RegisterLoader(this).params(mobile, password, captcha);
    }

    @Override
    public void onLoadFinished(Loader loader, Login data) {
        hideProgressDialog();
        if (data != null) {
            data.save(this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        loader.stopLoading();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mCaptchaButton.setText("重新获取");
            mCaptchaButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimen(R.dimen.text_16sp));
            mCaptchaButton.setEnabled(true);
            timeStart = 0;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mCaptchaButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimen(R.dimen.text_14sp));
            mCaptchaButton.setEnabled(false);
            mCaptchaButton.setText(millisUntilFinished / 1000 + "秒后重新获取");
        }
    }
}
