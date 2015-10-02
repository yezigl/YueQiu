package com.yidongle.yueqiu.mine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.utils.StringUtils;
import com.yidongle.yueqiu.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/6/19.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class ResetPasswordActivity extends BaseActivity {

    @InjectView(R.id.password_origin)
    TextView mOrigin;
    @InjectView(R.id.password_new)
    TextView mNew;
    @InjectView(R.id.password_confirm)
    TextView mConfirm;

    String originpwd, newpwd, confirmpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resetpassword);
    }

    @OnClick(R.id.button_submit)
    public void submit() {
        originpwd = mOrigin.getText().toString();
        newpwd = mNew.getText().toString();
        confirmpwd = mConfirm.getText().toString();

        if (StringUtils.isBlank(originpwd)) {
            showToast("原密码不能为空");
            mOrigin.requestFocus();
            return;
        }
        if (StringUtils.isBlank(newpwd)) {
            showToast("原密码不能为空");
            mNew.requestFocus();
            return;
        }
        if (StringUtils.isBlank(confirmpwd)) {
            showToast("原密码不能为空");
            mConfirm.requestFocus();
            return;
        }

        showProgressDialog("正在提交...");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Map<String, Object> reqParams = new HashMap<>();
                reqParams.put("originPassword", Utils.sha1Hex(originpwd));
                reqParams.put("newPassword", Utils.sha1Hex(newpwd));
                reqParams.put("confirmPassword", Utils.sha1Hex(confirmpwd));
                String ret = HttpUtils.post(API.RESETPWD.token(ResetPasswordActivity.this), reqParams);
                Representation<Void> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<Void>>() {
                }.getType());
                return getData(rep);
            }

            @Override
            protected void onPostExecute(Void rep) {
                hideProgressDialog();
                if (rep != null) {
                    showToast("修改成功");
                    finish();
                }
            }
        }.execute();
    }
}
