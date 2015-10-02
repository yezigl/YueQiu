/**
 * Copyright (c) 2010-2014 meituan.com
 * All rights reserved.
 */
package com.yidongle.yueqiu.widget;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.joanzapata.android.iconify.Iconify;
import com.yidongle.yueqiu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉列表<br>
 * 提供一级和两级的列表<br>
 * 使用此控件对数据结构有一些要求:
 * <ul>
 * <li>使用一级的需要列表中的对象实现{@code toString}方法</li>
 * <li>使用两级的需要一级、二级对象分别实现{@link Parent}和{@link Child}接口</li>
 * </ul>
 * 使用方法:<br>
 * <ul>
 * <li>一级：{@link DropdownView#setSimpleDropdown(List)}</li>
 * <li>两级：{@link DropdownView#setCascadeDropdown(List)}</li>
 * </ul>
 *
 * @author lidehua
 * @version 1.0
 * @since 2014-9-1
 */
public class DropdownView extends TextView {

    static final String TAG = "dropdown";

    private Context mContext;

    private LayoutInflater mInflater;

    private OnItemClickListener mItemOnClickListener;

    private boolean mCascade;

    private boolean mExpand;

    private ListView mSimpleView;

    private ListView mParentView;

    private ListView mChildView;

    private List<?> mSimples;

//    private List<Parent> mParents;

    private PopupWindow mPopupWindow;

    private boolean mEmpty;

    private View mParentWrapper, mChildWrapper;

    private int mItemHeight = getResources().getDimensionPixelOffset(R.dimen.dropdown_height);

    private String mText;

    public interface OnItemClickListener {

        boolean onClick(View v, Object item);

    }

    public DropdownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public DropdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DropdownView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }
        if (attrs != null) {

        }

        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        mText = getText().toString();
        setText(mText + " {fa-caret-down}");
        Iconify.addIcons(this);
    }

    @Override
    public boolean performClick() {
        if (mExpand) {
            hideDropdown();
        } else {
            showDropdown();
        }
        super.performClick();
        return true;
    }

    @Override
    public boolean callOnClick() {
        if (mExpand) {
            hideDropdown();
        } else {
            showDropdown();
        }
        return true;
    }

    private void showDropdown() {
        if (mEmpty) {
            return;
        }
        if (mPopupWindow == null) {
            initDropdown();
        }
        this.mExpand = true;
        this.setSelected(mExpand);

        mPopupWindow.showAsDropDown(this, 0, 4);

        setText(mText + " {fa-caret-up}");
        Iconify.addIcons(this);
    }

    private void hideDropdown() {
        this.mExpand = false;
        this.setSelected(mExpand);
        mPopupWindow.dismiss();
        setText(mText + " {fa-caret-down}");
        Iconify.addIcons(this);
    }

    public void setSimpleDropdown(List<?> list) {
        if (list.isEmpty()) {
            mEmpty = true;
        }
        this.mSimples = list;
        this.mCascade = false;
        this.mEmpty = false;
        initDropdown();
    }

//    public void setCascadeDropdown(List<? extends Parent> parent) {
//        if (parent.isEmpty()) {
//            mEmpty = true;
//            return;
//        }
//        this.mParents = new ArrayList<Parent>();
//        this.mParents.addAll(parent);
//        this.mCascade = true;
//        this.mEmpty = false;
//        initDropdown();
//    }

    @SuppressLint("InflateParams")
    private void initDropdown() {
        View view = null;
        if (mCascade) {
//            view = mInflater.inflate(R.layout.mt_dropdown_cascade, null);
//            mParentView = (ListView) view.findViewById(R.id.parent);
//            mChildView = (ListView) view.findViewById(R.id.children);
//            mParentWrapper = view.findViewById(R.id.parent_wrapper);
//            mChildWrapper = view.findViewById(R.id.child_wrapper);
//
//            CascadeDropdownParentAdapter adapter = new CascadeDropdownParentAdapter(mContext, mParents);
//            mParentView.setAdapter(adapter);
        } else {
            view = mInflater.inflate(R.layout.mt_dropdown_simple, null);
            mSimpleView = (ListView) view.findViewById(R.id.list);

            SimpleDropdownAdapter adapter = new SimpleDropdownAdapter(mContext, mSimples);
            mSimpleView.setAdapter(adapter);
        }

        mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                mExpand = false;
                setSelected(mExpand);
            }
        });
        mPopupWindow.setTouchInterceptor(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x = event.getX();
                    float y = event.getY();
                    boolean out = false;
                    if (mCascade) {
                        int leftw = mParentWrapper.getWidth();
                        int lefth = mParentWrapper.getHeight();
                        int righth = mChildWrapper.getHeight();
                        out = x < leftw ? y > lefth : y > righth;
                    } else {
                        int h = mSimpleView.getHeight();
                        out = y > h;
                    }
                    v.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
                    if (out) {
                        hideDropdown();
                    }
                }
                return v.performClick();
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener itemOnClickListener) {
        this.mItemOnClickListener = itemOnClickListener;
    }

    /**
     * 简单的下拉列表，列表中的项必须实现toString方法
     *
     * @author lidehua
     * @version 1.0
     * @since 2014-9-1
     */
    private class SimpleDropdownAdapter extends BaseAdapter {

        private List<?> mList;

        public SimpleDropdownAdapter(Context context, List<?> list) {
            this.mList = list == null ? new ArrayList<Object>() : list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.mt_dropdown_simple_row, parent, false);
            }
            TextView content = (TextView) convertView.findViewById(R.id.content);
            content.setText(getItem(position).toString());
            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mItemOnClickListener != null) {
                        mItemOnClickListener.onClick(v, getItem(position));
                    }
                    hideDropdown();
                }
            });

            return convertView;
        }

    }

//    private class CascadeDropdownParentAdapter extends ArrayAdapter<Parent> {
//
//        private int mLastPos = -1;
//
//        private View mLastView;
//
//        public CascadeDropdownParentAdapter(Context context, List<Parent> list) {
//            super(context, R.layout.mt_dropdown_cascade_parent_row, list);
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            final Parent p = getItem(position);
//            if (convertView == null) {
//                convertView = mInflater.inflate(R.layout.mt_dropdown_cascade_parent_row, parent, false);
//            }
//            TextView content = (TextView) convertView.findViewById(R.id.content);
//            content.setText(p.getString());
//            convertView.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    initChild(p.getChildren());
//                    itemSelect(v, position);
//                }
//            });
//            if (position == 0 && mLastPos == -1) {
//                initChild(p.getChildren());
//                itemSelect(convertView, position);
//            }
//
//            return convertView;
//        }
//
//        public void itemSelect(View v, int pos) {
//            if (mLastPos == pos) {
//                return;
//            }
//            if (mLastView != null) {
//                mLastView.setBackgroundResource(R.color.bg_light);
//                mLastView.findViewById(R.id.more).setVisibility(View.VISIBLE);
//            }
//            if (v != null) {
//                v.setBackgroundResource(R.color.bg_dropdown_normal);
//                v.findViewById(R.id.more).setVisibility(View.GONE);
//                mLastView = v;
//                mLastPos = pos;
//            }
//            if (mCascade) {
//                int h = Math.max(mItemHeight * mParentView.getCount(), (mItemHeight + 1) * mChildView.getCount());
//                LayoutParams params1 = mParentWrapper.getLayoutParams();
//                params1.height = h;
//                mParentWrapper.setLayoutParams(params1);
//                LayoutParams params2 = mChildWrapper.getLayoutParams();
//                params2.height = h;
//                mChildWrapper.setLayoutParams(params2);
//            }
//        }
//
//        private void initChild(List<Child> list) {
//            CascadeDropdownChildAdapter adapter = (CascadeDropdownChildAdapter) mChildView.getAdapter();
//            if (adapter == null) {
//                adapter = new CascadeDropdownChildAdapter(mContext, list);
//                mChildView.setAdapter(adapter);
//            } else {
//                adapter.updateList(list);
//            }
//        }
//
//    }
//
//    private class CascadeDropdownChildAdapter extends ArrayAdapter<Child> {
//
//        private List<Child> list;
//        private LayoutInflater layoutInflater;
//
//        public CascadeDropdownChildAdapter(Context context, List<Child> list) {
//            super(context, R.layout.mt_dropdown_cascade_child_row, list);
//            this.list = list;
//            this.layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
//        }
//
//        public void updateList(List<Child> list) {
//            this.list.clear();
//            this.list.addAll(list);
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            if (convertView == null) {
//                convertView = layoutInflater.inflate(R.layout.mt_dropdown_cascade_child_row, parent, false);
//            }
//            TextView content = (TextView) convertView.findViewById(R.id.content);
//            content.setText(getItem(position).getString());
//            convertView.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    if (mItemOnClickListener != null) {
//                        mItemOnClickListener.onClick(v, getItem(position));
//                    }
//                    hideDropdown();
//                }
//            });
//
//            return convertView;
//        }
//
//    }
}
