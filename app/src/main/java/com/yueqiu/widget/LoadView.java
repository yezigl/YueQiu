/**
 * Copyright (c) 2010-2014 meituan.com
 * All rights reserved.
 */
package com.yueqiu.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yueqiu.R;


/**
 * 将加载所需要的组件放到一个view里，方便处理 <br>
 * Usage:
 * 
 * <pre>
 * &lt;com.sankuai.meituan.merchant.mylib.LoadView
 *     android:id="@+id/widget_load"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     android:layout_centerInParent="true"
 *     android:onClick="reload" &gt;
 * &lt;/com.sankuai.meituan.merchant.mylib.LoadView&gt
 * </pre>
 * 
 * 默认初始化后，view的显示状态就是View.GONE<br>
 * 
 * 关于reload，有2种方式:<br>
 * <ol>
 * <li>直接在layout中指定android:onClick="reload"</li>
 * <li>setOnReloadListener，指定OnReloadListener</li>
 * </ol>
 * 
 * @author lidehua
 * @created 2014-8-6
 * 
 * @version 1.0
 */
public class LoadView extends FrameLayout {

    /**
     * 正在加载
     */
    private static final int STATUS_LOADING = 1;

    /**
     * 加载失败
     */
    private static final int STATUS_FAIL = 2;

    /**
     * 加载成功，且返回数据不为空
     */
    private static final int STATUS_SUCCESS = 3;

    /**
     * 加载成功，且返回数据为空
     */
    private static final int STATUS_SUCCESS_NONE = 4;

    private int mStatus; // 当前状态

    private ProgressBar mProgressBar;

    private TextView mStatusText;

    private int mFailTextId = R.string.load_reload; // 自定义失败的提示信息

    private int mNoneTextId = R.string.load_norecord; // 自定义成功无结果的提示信息

    private OnReloadListener mOnReloadListener;

    public interface OnReloadListener {
        void reload();
    }

    /**
     * @param context
     */
    public LoadView(Context context) {
        super(context);
        initLayout(context);
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context);
    }

    private void initLayout(Context context) {
        if (isInEditMode()) {
            return;
        }
        LayoutInflater.from(context).inflate(R.layout.widget_load, this);
        mProgressBar = (ProgressBar) this.findViewById(R.id.progress_bar);
        mStatusText = (TextView) this.findViewById(R.id.status_text);
        this.setMinimumWidth(0);
        this.setMinimumHeight(0);
        this.setVisibility(View.GONE);
    }

    /**
     * 显示加载动画
     * 
     * @param views
     *            需要隐藏的view
     */
    public LoadView loading(View... views) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        if (mStatusText != null) {
            mStatusText.setVisibility(View.GONE);
        }
        mStatus = STATUS_LOADING;
        this.setVisibility(View.VISIBLE);
        if (views != null) {
            for (View v : views) {
                v.setVisibility(View.GONE);
            }
        }
        return this;
    }

    /**
     * 加载失败
     */
    public void fail() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mStatusText != null) {
            mStatusText.setText(mFailTextId);
            mStatusText.setVisibility(View.VISIBLE);
        }
        mStatus = STATUS_FAIL;
    }

    /**
     * 加载成功
     * 
     * @param views
     *            需要显示的view，与{@link #loading(View...)}方法对应
     */
    public void success(View... views) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mStatusText != null) {
            mStatusText.setVisibility(View.GONE);
        }
        this.setVisibility(View.GONE);
        if (views != null) {
            for (View v : views) {
                v.setVisibility(View.VISIBLE);
            }
        }
        mStatus = STATUS_SUCCESS;
    }

    /**
     * 加载成功，但返回数据为空
     */
    public void none(View... views) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mStatusText != null) {
            mStatusText.setText(mNoneTextId);
            mStatusText.setVisibility(View.VISIBLE);
        }
        this.setVisibility(View.VISIBLE);
        if (views != null) {
            for (View v : views) {
                v.setVisibility(View.GONE);
            }
        }
        mStatus = STATUS_SUCCESS_NONE;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean callOnClick() {
        return mStatus == STATUS_FAIL && super.callOnClick();
    }

    @Override
    public boolean performClick() {
        return mStatus == STATUS_FAIL && super.performClick();
    }

    public void setFailText(int resId) {
        this.mFailTextId = resId;
    }

    public void setNoneText(int resId) {
        this.mNoneTextId = resId;
    }

    public void setOnReloadListener(OnReloadListener listener) {
        this.mOnReloadListener = listener;
        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnReloadListener != null) {
                    mOnReloadListener.reload();
                }
            }
        });
    }
}
