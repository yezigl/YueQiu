/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yueqiu.R;

/**
 * 自定义的EditText
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class FormEditText extends FrameLayout {

    private EditText mInput;

    public FormEditText(Context context) {
        super(context);
    }

    public FormEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FormEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.FormEditText);

        String labelStr = attrsArray.getString(R.styleable.FormEditText_label);
        int inputType = attrsArray.getInt(R.styleable.FormEditText_android_inputType, InputType.TYPE_NULL);
        String hintStr = attrsArray.getString(R.styleable.FormEditText_android_hint);
        int maxlength = attrsArray.getInt(R.styleable.FormEditText_android_maxLength, -1);

        attrsArray.recycle();

//        if (isInEditMode()) {
//            return;
//        }
        LayoutInflater.from(context).inflate(R.layout.widget_formedittext, this, true);

        TextView labelView = (TextView) findViewById(R.id.label);
        mInput = (EditText) findViewById(R.id.input);
        final View clearView = findViewById(R.id.clear);

        labelView.setText(labelStr);
        mInput.setInputType(inputType);
        mInput.setHint(hintStr);
        if (maxlength > 0) {
            mInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength)});
        } else {
            mInput.setFilters(new InputFilter[0]);
        }
        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    clearView.setVisibility(View.GONE);
                    mInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen
                            .mtformedittext_input_hint_textSize));
                } else {
                    clearView.setVisibility(View.VISIBLE);
                    mInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen
                            .mtformedittext_input_textSize));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                boolean clearShow = !TextUtils.isEmpty(mInput.getText()) && hasFocus;
                clearView.setVisibility(clearShow ? VISIBLE : GONE);
            }
        });
        clearView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mInput.setText("");
            }
        });
    }

    public EditText getEditText() {
        return mInput;
    }

    public String getText() {
        return mInput.getText().toString();
    }

    public void setText(CharSequence text) {
        mInput.setText(text);
    }

    public void setOnKeyListener(View.OnKeyListener listener) {
        mInput.setOnKeyListener(listener);
    }

    public void showKeyboard() {
        mInput.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mInput.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mInput, 0);
            }

        }, 666);
        mInput.requestFocus();
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) mInput.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
    }

}
