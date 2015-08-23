/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueqiu.model.Login;
import com.yueqiu.utils.Constants;

import butterknife.ButterKnife;

/**
 * Fragment基类
 *
 * @author lidehua
 * @version 1.0
 * @since 3.7
 */
public class BaseFragment extends Fragment {

    protected ProgressDialog mProgressDialog;

    protected View createView(LayoutInflater inflater, ViewGroup container, int layoutResId) {
        View view = inflater.inflate(layoutResId, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    protected void showProgressDialog(String msg) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            mProgressDialog = ProgressDialog.show(getActivity(), "", msg);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing() && isAdded()) {
            // fragment销毁重建后，再dismiss dialog会出错；如果销毁时dismiss重建后不方便重新showDialog，直接try-catch吧
            try {
                mProgressDialog.dismiss();
            } catch (IllegalArgumentException e) {
                Log.e("ProgressDialog", e.getMessage());
            }
        }
    }

    protected void startLoginActivity(Intent intent) {
        if (Login.isLogin(getActivity())) {
            startActivity(intent);
        } else {
            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
            loginIntent.putExtra(Constants.INTENT_TARGET_INTENT, intent);
            startActivity(loginIntent);
        }
    }

    protected void startLoginActivityForResult(Intent intent, int requestCode) {
        if (Login.isLogin(getActivity())) {
            startActivityForResult(intent, requestCode);
        } else {
            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
            loginIntent.putExtra(Constants.INTENT_TARGET_INTENT, intent);
            loginIntent.putExtra(Constants.INTENT_REQUESTCODE, requestCode);
            startActivity(loginIntent);
        }
    }
}