package com.yueqiu.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yueqiu.R;
import com.yueqiu.model.Order;
import com.yueqiu.utils.Constants;
import com.yueqiu.widget.BaseArrayAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class MyOrderListAdapter extends BaseArrayAdapter<Order> {

    public MyOrderListAdapter(Activity context, List<Order> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_myorder_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Order order = getItem(position);
        holder.title.setText(order.getActivity().getTitle());
        holder.address.setText(order.getActivity().getStadium().getName());
        holder.date.setText(order.getActivity().getDate());
        holder.price.setText("总价：" + order.getStatus());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra(Constants.INTENT_ORDER_ID, order.getId());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }

        @InjectView(R.id.title)
        TextView title;
        @InjectView(R.id.address)
        TextView address;
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.price)
        TextView price;
    }
}
