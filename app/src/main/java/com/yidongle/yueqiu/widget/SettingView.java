/**
 * Copyright (c) 2010-2014 meituan.com
 * All rights reserved.
 */
package com.yidongle.yueqiu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.yidongle.yueqiu.R;

import java.lang.reflect.Method;

/**
 * 集多个功能于一体的设置组件
 *
 * @author lidehua
 * @version 1.0
 * @since 2014年12月8日
 */
public class SettingView extends FrameLayout {

    private static final String TAG = "SettingView";

    private TextView mTitle;

    private TextView mDesc;

    private ToggleButton mToggleButton;

    public SettingView(Context context) {
        super(context);
    }

    public SettingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray attributesArray = context.obtainStyledAttributes(attrs, R.styleable.SettingView);

        boolean showHeaderDivider = attributesArray.getBoolean(R.styleable.SettingView_showHeaderDivider, false);

        boolean showFooterDivider = attributesArray.getBoolean(R.styleable.SettingView_showFooterDivider, true);

        String title = attributesArray.getString(R.styleable.SettingView_text);

        String desc = attributesArray.getString(R.styleable.SettingView_desc);

        boolean showMore = attributesArray.getBoolean(R.styleable.SettingView_showMore, true);

        boolean isToggle = attributesArray.getBoolean(R.styleable.SettingView_isToggle, false);

        final String hanlderName = attributesArray.getString(R.styleable.SettingView_onClick);

        final String toggleHanlderName = attributesArray.getString(R.styleable.SettingView_onToggleClick);

        Drawable icon = attributesArray.getDrawable(R.styleable.SettingView_icon);

        attributesArray.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.widget_setting, this, true);

        View headerDivider = view.findViewById(R.id.dividerHeader);
        View footerDivider = view.findViewById(R.id.dividerFooter);
        mTitle = (TextView) view.findViewById(R.id.title);
        mDesc = (TextView) view.findViewById(R.id.desc);
        View moreView = view.findViewById(R.id.more);
        mToggleButton = (ToggleButton) view.findViewById(R.id.toggle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        headerDivider.setVisibility(showHeaderDivider ? View.VISIBLE : View.GONE);
        footerDivider.setVisibility(showFooterDivider ? View.VISIBLE : View.GONE);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
        if (icon != null) {
            iconView.setImageDrawable(icon);
        } else {
            iconView.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(desc)) {
            mDesc.setText(desc);
            mDesc.setVisibility(View.VISIBLE);
        }
        moreView.setVisibility(showMore ? View.VISIBLE : View.GONE);
        mToggleButton.setVisibility(isToggle ? View.VISIBLE : View.GONE);

        if (hanlderName != null) {
            this.setOnClickListener(new OnClickListener() {

                private Method mHandler;

                @Override
                public void onClick(View view) {
                    if (mHandler == null) {
                        try {
                            mHandler = getContext().getClass().getMethod(hanlderName, View.class);
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                    Log.d(TAG, mHandler + " s");
                    try {
                        mHandler.invoke(getContext(), SettingView.this);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            });
        }

        if (toggleHanlderName != null) {
            mToggleButton.setOnClickListener(new OnClickListener() {

                private Method mHandler;

                @Override
                public void onClick(View v) {
                    if (mHandler == null) {
                        try {
                            mHandler = getContext().getClass().getMethod(toggleHanlderName, View.class);
                        } catch (Exception e) {
                        }
                    }
                    try {
                        mHandler.invoke(getContext(), mToggleButton, !mToggleButton.isChecked());
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            this.performClick();
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setDesc(String text) {
        mDesc.setText(text);
        mDesc.setVisibility(View.VISIBLE);
    }

    public void setText(String login) {
        mTitle.setText(login);
    }

    public void setChecked(boolean checked) {
        mToggleButton.setChecked(checked);
    }

    public boolean isChecked() {
        return mToggleButton.isChecked();
    }

    public void setOnToggleClick(OnCheckedChangeListener onCheckedChangeListener) {
        mToggleButton.setOnCheckedChangeListener(onCheckedChangeListener);
    }
}
