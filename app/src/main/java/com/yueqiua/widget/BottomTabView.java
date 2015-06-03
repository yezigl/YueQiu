package com.yueqiua.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueqiua.R;

public class BottomTabView extends FrameLayout {

    private ImageView mBubble;

    private boolean mIsRemind;

    public BottomTabView(Context context) {
        super(context);
    }

    public BottomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public BottomTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_bottomtab, this, true);

        ImageView icon = (ImageView) this.findViewById(R.id.icon);
        TextView tv = (TextView) this.findViewById(R.id.text);
        mBubble = (ImageView) this.findViewById(R.id.bubble);

        TypedArray attributesArray = context.obtainStyledAttributes(attrs, R.styleable.BottomTabView);

        Drawable src = attributesArray.getDrawable(R.styleable.BottomTabView_android_src);
        String text = attributesArray.getString(R.styleable.BottomTabView_android_text);

        attributesArray.recycle();

        icon.setImageDrawable(src);
        tv.setText(text);
    }

    public void setRemind(boolean isRemind) {
        if (mIsRemind != isRemind) {
            mIsRemind = isRemind;
            mBubble.setVisibility(isRemind ? View.VISIBLE : View.GONE);
        }
    }

}
