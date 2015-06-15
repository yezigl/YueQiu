package com.yueqiu.mine;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueqiu.R;
import com.yueqiu.model.Order;
import com.yueqiu.widget.BaseArrayAdapter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class OrderListAdapter extends BaseArrayAdapter<Order> {

    public OrderListAdapter(Activity context, int resource, List<Order> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_order_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }
}
