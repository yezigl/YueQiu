package com.yueqiu.mine;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.yueqiu.BaseActivity;
import com.yueqiu.R;
import com.yueqiu.loader.UserInfoLoader;
import com.yueqiu.model.Login;
import com.yueqiu.model.User;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.ImageViewLoader;
import com.yueqiu.utils.Utils;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class MineActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<User> {

    @InjectView(R.id.avatar)
    ImageView mAvatar;
    @InjectView(R.id.nickname)
    TextView mNickname;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mine);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Login.isLogin(this)) {
            getLoaderManager().restartLoader(hashCode(), null, this);
        } else {
            mNickname.setText("点击登录");
        }
    }

    @Override
    public Loader<User> onCreateLoader(int id, Bundle args) {
        return new UserInfoLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<User> loader, User data) {
        if (data != null) {
            user = data;
            ImageViewLoader.with(this, mAvatar)
                    .round(Utils.getDimen(this, R.dimen.avatar_size) / 2)
                    .load(data.getAvatar());
            mNickname.setText(data.getNickname());
        }
    }

    @Override
    public void onLoaderReset(Loader<User> loader) {
        loader.stopLoading();
    }

    @OnClick(R.id.layout_user)
    public void userinfo(View v) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.putExtra(Constants.INTENT_USER, user);
        startLoginActivity(intent);
    }

    @OnClick(R.id.order)
    public void order(View v) {
        Intent intent = new Intent(this, MyOrderListActivity.class);
        startLoginActivity(intent);
    }

    @OnClick(R.id.coupon)
    public void coupon(View v) {
        Intent intent = new Intent(this, CouponActivity.class);
        startLoginActivity(intent);
    }

    @OnClick(R.id.message)
    public void message(View v) {
        Intent intent = new Intent(this, MessageActivity.class);
        startLoginActivity(intent);
    }

    @OnClick(R.id.feedback)
    public void feedback(View v) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        String id = new FeedbackAgent(this).getDefaultConversation().getId();
        intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID, id);
        startActivity(intent);
    }

    @OnClick(R.id.share)
    public void share(View v) {
        Intent intent = new Intent(this, ShareActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.about)
    public void about(View v) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}
