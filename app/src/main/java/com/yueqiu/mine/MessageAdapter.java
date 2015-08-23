package com.yueqiu.mine;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yueqiu.R;
import com.yueqiu.model.Message;
import com.yueqiu.widget.BaseArrayAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created on 15/6/21.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class MessageAdapter extends BaseArrayAdapter<Message> {

    public MessageAdapter(Activity context, List<Message> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_message_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Message message = getItem(position);
        holder.title.setText(message.getTitle());
        holder.content.setText(message.getContent());
        holder.ctime.setText(message.getCreateTime());
        return convertView;
    }

    class ViewHolder {
        ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }

        @InjectView(R.id.price)
        TextView title;
        @InjectView(R.id.name)
        TextView content;
        @InjectView(R.id.desc)
        TextView ctime;
    }
}
