/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu.index;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yueqiu.R;
import com.yueqiu.model.Game;
import com.yueqiu.utils.ImageViewLoader;
import com.yueqiu.widget.BaseArrayAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class GameListAdapter extends BaseArrayAdapter<Game> {

    public GameListAdapter(Activity context, List<Game> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_game_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Game ab = getItem(position);
        ImageViewLoader.with(getContext(), holder.icon).load(ab.getStadium().getThumbnail());
        holder.title.setText(ab.getTitle());
        holder.address.setText(ab.getStadium().getName());
        holder.date.setText(ab.getDate());
        holder.partner.setText(ab.getTotal() + "/" + ab.getAttend());
        holder.price.setText("￥" + ab.getPrice());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GameDetailActivity.class);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }

        @InjectView(R.id.icon)
        ImageView icon;
        @InjectView(R.id.title)
        TextView title;
        @InjectView(R.id.distance)
        TextView distance;
        @InjectView(R.id.address)
        TextView address;
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.partner)
        TextView partner;
        @InjectView(R.id.price)
        TextView price;
        @InjectView(R.id.layout_partner)
        LinearLayout layoutPartner;
    }
}
