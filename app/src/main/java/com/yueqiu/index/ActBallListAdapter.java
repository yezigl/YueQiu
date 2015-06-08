/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu.index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yueqiu.R;
import com.yueqiu.model.ActBall;
import com.yueqiu.widget.BaseArrayAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class ActBallListAdapter extends BaseArrayAdapter<ActBall> {

    public ActBallListAdapter(Activity context, List<ActBall> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_actball_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ActBall ab = getItem(position);
        holder.name.setText(ab.getName());

        return convertView;
    }

    class ViewHolder {
        ViewHolder(View v) {
            ButterKnife.inject(v);
        }

        @InjectView(R.id.name)
        TextView name;
    }
}
