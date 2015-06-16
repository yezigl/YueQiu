package com.yueqiu.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

import com.yueqiu.LoginActivity;
import com.yueqiu.R;

import java.lang.ref.WeakReference;

/**
 * Created on 15/6/17.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class AppHanlder extends Handler {

    private static AppHanlder appHanlder;

    private WeakReference<Context> contextRef;

    public AppHanlder(Context context) {
        contextRef = new WeakReference<Context>(context);
    }

    public static AppHanlder getInstance(Context context) {
//        if (appHanlder == null) {
        appHanlder = new AppHanlder(context);
//        }
        return appHanlder;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.api_error:
                if (contextRef.get() != null) {
                    Toast toast = Toast.makeText(contextRef.get(), (String) msg.obj, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            case R.id.goto_login:
                if (contextRef.get() != null) {
                    Intent intent = new Intent(contextRef.get(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    contextRef.get().startActivity(intent);
                }
                break;
        }
    }
}
