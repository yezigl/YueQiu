package com.yidongle.yueqiu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import java.lang.ref.WeakReference;

public class ImageViewLoader implements Callback, View.OnTouchListener {

    WeakReference<ImageView> imageViewRef;
    Picasso picasso;
    String url;
    int placeholder;
    int roundRadius;

    public ImageViewLoader(ImageView imageView, Picasso picasso) {
        this.picasso = picasso;
        this.imageViewRef = new WeakReference<ImageView>(imageView);
    }

    public static ImageViewLoader with(Context context, ImageView imageView) {
        return new ImageViewLoader(imageView, Picasso.with(context));
    }

    public void load(int resourceId) {
        ImageView imageView = imageViewRef.get();
        if (imageView == null) {
            return;
        }
        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        RequestCreator requestCreator = picasso.load(resourceId);
        // 圆角
        if (roundRadius > 0) {
            requestCreator.transform(new RoundImage());
        }
        // 如果view设置了layout_width和layout_height，则使用width和height缩放
        if (params != null && params.width > 0 && params.height > 0) {
            requestCreator.resize(params.width, params.height);
        }

        requestCreator.into(imageView, this);
    }

    public void load(String url) {
        load(url, 0);
    }

    public void load(String url, int placeholder) {
        this.url = url;
        this.placeholder = placeholder;
        ImageView imageView = imageViewRef.get();
        if (imageView == null) {
            return;
        }
        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        RequestCreator requestCreator = picasso.load(TextUtils.isEmpty(url) ? null : url);
        // 默认图片
        if (placeholder != 0) {
            requestCreator.placeholder(placeholder);
        }
        // 圆角
        if (roundRadius > 0) {
            requestCreator.transform(new RoundImage());
        }
        // 如果view设置了layout_width和layout_height，则使用width和height缩放
        if (params != null && params.width > 0 && params.height > 0) {
            requestCreator.resize(params.width, params.height);
        }

        requestCreator.into(imageView, this);
    }

    public ImageViewLoader round(int radius) {
        this.roundRadius = radius;
        return this;
    }

    @Override
    public void onSuccess() {
        ImageView imageView = imageViewRef.get();
        if (imageView != null) {
            imageView.setOnTouchListener(null);
        }
    }

    @Override
    public void onError() {
        ImageView imageView = imageViewRef.get();
        if (imageView != null) {
            imageView.setOnTouchListener(this);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            load(url, placeholder);
            return true;
        }
        return false;
    }

    private class RoundImage implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap output;
            try {
                output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            } catch (Throwable e) {
                Log.e(key(), "round error", e);
                return source;
            }

            Canvas canvas = new Canvas(output);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
            RectF rectF = new RectF(rect);
            float roundPx = roundRadius;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, rect, rect, paint);
            source.recycle();

            return output;
        }

        @Override
        public String key() {
            return "round";
        }
    }
}