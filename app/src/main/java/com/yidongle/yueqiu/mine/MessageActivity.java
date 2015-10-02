package com.yidongle.yueqiu.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.BaseListFragment;
import com.yidongle.yueqiu.loader.MessageLoader;
import com.yidongle.yueqiu.model.Message;
import com.yidongle.yueqiu.utils.Utils;
import com.yidongle.yueqiu.widget.BaseArrayAdapter;
import com.yidongle.yueqiu.widget.BaseAsyncTaskLoader;

import java.util.List;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class MessageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);

        getFragmentManager().beginTransaction().add(R.id.content, new MessageFragment()).commit();
    }

    public static class MessageFragment extends BaseListFragment<Message> {

        @Override
        protected BaseArrayAdapter<Message> getAdapter() {
            return new MessageAdapter(getActivity(), null);
        }

        @Override
        protected BaseAsyncTaskLoader<List<Message>> getLoader() {
            return new MessageLoader(getActivity());
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            setDivider(null, Utils.dp2px(getActivity(), 8));
            super.onViewCreated(view, savedInstanceState);
        }
    }
}
