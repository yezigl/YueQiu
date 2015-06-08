package com.yueqiu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yueqiu.index.ActBallListFragment;
import com.yueqiu.widget.BottomTabView;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.tab1)
    BottomTabView mTab1;
    @InjectView(R.id.tab2)
    BottomTabView mTab2;
    @InjectView(R.id.tab3)
    BottomTabView mTab3;

    private View mCurrentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (savedInstanceState != null) {
            showFragment(savedInstanceState.getString(CURRENT_TAB_TAG), true);
            currentTag = savedInstanceState.getString(CURRENT_TAB_TAG);
        } else {
            showFragment(TAG_01, false);
            currentTag = TAG_01;
        }
        mCurrentTab = currentTag.equals(TAG_01) ? mTab1 : (currentTag.equals(TAG_02) ? mTab2 : mTab3);
        mCurrentTab.setSelected(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_TAB_TAG, currentTag);
    }

    @OnClick({R.id.tab1, R.id.tab2, R.id.tab3})
    public void tabClick(View v) {
        if (v == mCurrentTab) {
            return;
        }
        if (mCurrentTab != null) {
            mCurrentTab.setSelected(false);
        }
        mCurrentTab = v;
        mCurrentTab.setSelected(true);
        switch (v.getId()) {
            case R.id.tab1:
                showFragment(TAG_01, false);
                break;
            case R.id.tab2:
                showFragment(TAG_02, false);
                break;
            case R.id.tab3:
                showFragment(TAG_03, false);
                break;
        }
    }

    private String currentTag;
    private Fragment currentFragment;
    private static final String TAG_01 = "tag_01";
    private static final String TAG_02 = "tag_02";
    private static final String TAG_03 = "tag_03";
    private static final String[] TAGS = {TAG_01, TAG_02, TAG_03};
    private static final String CURRENT_TAB_TAG = "fragment_tag";

    public void showFragment(String tag, boolean fromSaved) {
        if (tag == null) {
            tag = TAG_01;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment = fm.findFragmentByTag(tag);

        if (fragment == null) {
            if (tag.equals(TAG_01)) {
                fragment = ActBallListFragment.newInstance();
            } else if (tag.equals(TAG_02)) {
                fragment = ActBallListFragment.newInstance();
            } else if (tag.equals(TAG_03)) {
                fragment = ActBallListFragment.newInstance();
            }

            ft.add(R.id.content, fragment, tag);
        } else {
            if (fragment.isHidden()) {
                ft.show(fragment);
            }
            if (fragment.isDetached()) {
                ft.attach(fragment);
            }
        }
        if (!fromSaved) {
            if (currentFragment != null && fragment != currentFragment) {
                ft.hide(currentFragment);
            }
        }

        currentFragment = fragment;
        currentTag = tag;

        if (fromSaved) {
            // 处理多个fragment重叠
            for (String t : TAGS) {
                Fragment f = fm.findFragmentByTag(t);
                if (f != null && f != currentFragment) {
                    ft.hide(f);
                }
            }
        }

        ft.commitAllowingStateLoss();
    }
}
