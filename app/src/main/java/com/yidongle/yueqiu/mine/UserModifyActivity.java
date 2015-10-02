package com.yidongle.yueqiu.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.utils.Constants;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/6/20.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class UserModifyActivity extends BaseActivity {

    @InjectView(R.id.nickname)
    TextView mNickname;
    @InjectView(R.id.mobile)
    TextView mMobile;
    @InjectView(R.id.title)
    TextView mTitle;

    String type, value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_usermodify);

        type = getIntent().getStringExtra(Constants.INTENT_MOFIDY);
        String title = getIntent().getStringExtra(Constants.INTENT_TITLE);
        value = getIntent().getStringExtra(Constants.INTENT_ORIGIN);

        mTitle.setText(title);
        if (type.equals("mobile")) {
            mMobile.setVisibility(View.VISIBLE);
            mMobile.setText(value);
        } else if (type.equals("nickname")) {
            mNickname.setVisibility(View.VISIBLE);
            mNickname.setText(value);
        }
    }

    @OnClick(R.id.button_modify)
    public void modify() {
        if (type.equals("mobile")) {
            value = mMobile.getText().toString();
        } else if (type.equals("nickname")) {
            value = mNickname.getText().toString();
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_MOFIDY, type);
        intent.putExtra(Constants.INTENT_ORIGIN, value);
        setResult(RESULT_OK, intent);
        finish();
    }
}
