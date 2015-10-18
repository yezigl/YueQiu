package com.yidongle.yueqiu.mine;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.yidongle.yueqiu.BaseFragment;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.loader.UserInfoLoader;
import com.yidongle.yueqiu.model.Login;
import com.yidongle.yueqiu.model.User;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.ImageViewLoader;
import com.yidongle.yueqiu.utils.Utils;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class MineFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<User> {

    @InjectView(R.id.avatar)
    ImageView mAvatar;
    @InjectView(R.id.nickname)
    TextView mNickname;

    User user;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_mine);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Login.isLogin(getActivity())) {
            getLoaderManager().restartLoader(hashCode(), null, this);
        } else {
            mNickname.setText("点击登录");
        }
    }

    @Override
    public Loader<User> onCreateLoader(int id, Bundle args) {
        return new UserInfoLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<User> loader, User data) {
        if (data != null) {
            user = data;
            ImageViewLoader.with(getActivity(), mAvatar)
                    .round(Utils.getDimen(getActivity(), R.dimen.avatar_size) / 2)
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
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        intent.putExtra(Constants.INTENT_USER, user);
        startLoginActivity(intent);
    }

    @OnClick(R.id.order)
    public void order(View v) {
        Intent intent = new Intent(getActivity(), MyOrderListActivity.class);
        startLoginActivity(intent);
    }

    @OnClick(R.id.coupon)
    public void coupon(View v) {
        Intent intent = new Intent(getActivity(), CouponListActivity.class);
        startLoginActivity(intent);
    }

    @OnClick(R.id.message)
    public void message(View v) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        startLoginActivity(intent);
    }

    @OnClick(R.id.feedback)
    public void feedback(View v) {
        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
        String id = new FeedbackAgent(getActivity()).getDefaultConversation().getId();
        intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID, id);
        startActivity(intent);
    }

    @OnClick(R.id.share)
    public void share(View v) {
        Intent intent = new Intent(getActivity(), ShareActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.about)
    public void about(View v) {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

}
