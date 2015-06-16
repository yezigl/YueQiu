package com.yueqiu;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yueqiu.loader.LoginLoader;
import com.yueqiu.model.Login;
import com.yueqiu.utils.Constants;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/2/19.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class LoginActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Login> {

    @InjectView(R.id.mobile)
    TextView mMobile;
    @InjectView(R.id.password)
    TextView mPassword;

//    CaptchaTimer mTimer;
    long timeRemain;
    String mobile, password;
    Class<?> targetActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        if (intent != null) {
            targetActivity = (Class<?>) intent.getSerializableExtra(Constants.INTENT_TARGET_ACTIVITY);
        }
    }

//    @OnClick(R.id.button_captcha)
//    public void captcha(View v) {
//        mobile = mMobile.getText().toString();
//        if (!Utils.isMobile(mobile)) {
//            showToast(R.string.login_mobile_error);
//            return;
//        }
//        getLoaderManager().initLoader(hashCode(), null, new LoaderManager.LoaderCallbacks<Captcha>() {
//            @Override
//            public Loader<Captcha> onCreateLoader(int id, Bundle args) {
//                mTimer = new CaptchaTimer(60000, 1000);
//                mTimer.start();
//                mButtonCaptcha.setEnabled(false);
//                return new CaptchaLoader(LoginActivity.this, mobile);
//            }
//
//            @Override
//            public void onLoadFinished(Loader<Captcha> loader, Captcha data) {
//                if (data != null && data.isSuccess()) {
//                    showToast(R.string.login_captcha_send_succ);
//                } else {
//                    showToast(R.string.login_captcha_send_fail);
//                }
//            }
//
//            @Override
//            public void onLoaderReset(Loader<Captcha> loader) {
//                loader.stopLoading();
//            }
//        });
//    }

    @OnClick(R.id.button_login)
    public void login(View v) {
        password = mPassword.getText().toString();
//        if (!Utils.isCaptcha(password)) {
//            showToast(R.string.login_captcha_error);
//            return;
//        }
        getLoaderManager().initLoader(hashCode() + 1, null, this);
    }

    @OnClick(R.id.button_register)
    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Loader<Login> onCreateLoader(int id, Bundle args) {
        return new LoginLoader(this, mobile, password);
    }

    @Override
    public void onLoadFinished(Loader<Login> loader, Login data) {
        if (data != null && data.isSuccess()) {
            data.save(this);
            finish();
        } else {
            showToast(data != null ? data.getMsg() : "登录失败");
        }
    }

    @Override
    public void onLoaderReset(Loader<Login> loader) {
        loader.stopLoading();
    }

//    class CaptchaTimer extends CountDownTimer {
//        public CaptchaTimer(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onFinish() {
//            mButtonCaptcha.setText("重新获取");
//            mButtonCaptcha.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimen(R.dimen.text_16sp));
//            mButtonCaptcha.setEnabled(true);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            mButtonCaptcha.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimen(R.dimen.text_14sp));
//            mButtonCaptcha.setEnabled(false);
//            mButtonCaptcha.setText(millisUntilFinished / 1000 + "秒后重新获取");
//            timeRemain = millisUntilFinished / 1000;
//        }
//    }
}
