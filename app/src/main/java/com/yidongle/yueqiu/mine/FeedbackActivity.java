package com.yidongle.yueqiu.mine;

import android.os.Bundle;

import com.umeng.fb.fragment.FeedbackFragment;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.BaseActivity;

/**
 * Created on 15/7/9.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class FeedbackActivity extends BaseActivity {

    FeedbackFragment mFeedbackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feedback);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            String conversation_id = getIntent().getStringExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID);
            mFeedbackFragment = FeedbackFragment.newInstance(conversation_id);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFeedbackFragment)
                    .commit();
        }
    }
}
