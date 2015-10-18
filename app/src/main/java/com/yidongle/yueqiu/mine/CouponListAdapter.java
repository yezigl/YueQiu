package com.yidongle.yueqiu.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.model.Coupon;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.widget.BaseArrayAdapter;

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
public class CouponListAdapter extends BaseArrayAdapter<Coupon> {

    public CouponListAdapter(Activity context, List<Coupon> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_coupon_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Coupon coupon = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constants.INTENT_COUPON, coupon);
                mContext.setResult(Activity.RESULT_OK);
                mContext.finish();
            }
        });
        holder.price.setText(String.valueOf(coupon.getPrice()));
        holder.name.setText(coupon.getName());
        holder.desc.setText(coupon.getDesc());
        return convertView;
    }

    class ViewHolder {
        ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }

        @InjectView(R.id.price)
        TextView price;
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.desc)
        TextView desc;
    }
}
