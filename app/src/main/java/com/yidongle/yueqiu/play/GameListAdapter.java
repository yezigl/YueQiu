/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yidongle.yueqiu.play;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.model.Game;
import com.yidongle.yueqiu.model.User;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.ImageViewLoader;
import com.yidongle.yueqiu.utils.Utils;
import com.yidongle.yueqiu.widget.BaseArrayAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
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

        final Game ab = getItem(position);
        //ImageViewLoader.with(getContext(), holder.icon).load(ab.getStadium().getThumbnail());
        ImageViewLoader.with(getContext(), holder.image).load(ab.getStadium().getThumbnail());
        holder.title.setText(ab.getTitle());
        holder.address.setText(ab.getStadium().getName());
        holder.date.setText(ab.getDate());
        holder.player.setText(ab.getTotal() + "/" + ab.getAttend());
        holder.price.setText("￥" + ab.getPrice());
        holder.status.setText(ab.getStatusStr());
        switch (ab.getStatus()) {
            case 1:
                holder.status.setTextColor(getContext().getResources().getColor(R.color.text_disable));
                break;
            case 2:
                holder.status.setTextColor(getContext().getResources().getColor(R.color.text_theme));
                break;
            case 3:
                holder.status.setTextColor(getContext().getResources().getColor(R.color.text_orange));
                break;
            case 4:
                holder.status.setTextColor(getContext().getResources().getColor(R.color.text_disable));
                break;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GameDetailActivity.class);
                intent.putExtra(Constants.INTENT_ACTIVITY_ID, ab.getId());
                getContext().startActivity(intent);
            }
        });
        if (ab.getPlayers() != null && !ab.getPlayers().isEmpty()) {
            int iconSize = Utils.getDimen(getContext(), R.dimen.player_icon_size);
            holder.layoutPlayer.removeAllViews();
            for (User user : ab.getPlayers()) {
                ImageView view = new ImageView(getContext());
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(iconSize, iconSize);
                view.setLayoutParams(params);
                ImageViewLoader.with(getContext(), view).round(iconSize / 2).load(user.getAvatar());
                holder.layoutPlayer.addView(view);
            }
        } else {

        }

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
        @InjectView(R.id.player)
        TextView player;
        @InjectView(R.id.price)
        TextView price;
        @InjectView(R.id.layout_player)
        LinearLayout layoutPlayer;
        @InjectView(R.id.image)
        ImageView image;
        @InjectView(R.id.status)
        TextView status;
    }
}
