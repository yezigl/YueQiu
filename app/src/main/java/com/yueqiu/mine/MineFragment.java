package com.yueqiu.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueqiu.BaseFragment;
import com.yueqiu.R;

import butterknife.OnClick;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_mine);
    }

    @OnClick(R.id.layout_user)
    public void userinfo(View v) {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.order)
    public void order(View v) {
        Intent intent = new Intent(getActivity(), OrderListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.coupon)
    public void coupon(View v) {
        Intent intent = new Intent(getActivity(), CouponActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.message)
    public void message(View v) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.suggest)
    public void suggest(View v) {
        Intent intent = new Intent(getActivity(), SuggestActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.about)
    public void about(View v) {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }
}
