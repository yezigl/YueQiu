package com.yidongle.yueqiu.mine;

import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.MainActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.loader.UserInfoLoader;
import com.yidongle.yueqiu.model.Login;
import com.yidongle.yueqiu.model.Representation;
import com.yidongle.yueqiu.model.UploadImage;
import com.yidongle.yueqiu.model.User;
import com.yidongle.yueqiu.utils.API;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.HttpUtils;
import com.yidongle.yueqiu.utils.ImageViewLoader;
import com.yidongle.yueqiu.utils.JsonUtils;
import com.yidongle.yueqiu.utils.StringUtils;
import com.yidongle.yueqiu.utils.Utils;
import com.yidongle.yueqiu.widget.SettingView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class UserInfoActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<User> {

    @InjectView(R.id.avatar)
    ImageView mAvatar;
    @InjectView(R.id.nickname)
    SettingView mNickname;
    @InjectView(R.id.mobile)
    SettingView mMobile;

    User user;
    String avatar, mobile, nickname;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_userinfo);

        getLoaderManager().restartLoader(hashCode(), null, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == FILECHOOSER_REQCODE) {
                uri = data.getData();
            } else if (requestCode == CAMERA_REQCODE) {
                try {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, bytes);
                    String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "截图", null);
                    uri = Uri.parse(path);
                } catch (Exception e) {
                    Log.e(TAG, "Camera capture fail", e);
                }
            } else if (requestCode == PHOTOCUT_REQCODE) {
                setAvatar(data);
            } else if (requestCode == MODIFY_REQCODE) {
                String type = data.getStringExtra(Constants.INTENT_MOFIDY);
                String value = data.getStringExtra(Constants.INTENT_ORIGIN);
                if (type.equals("mobile")) {
                    mobile = value;
                } else if (type.equals("nickname")) {
                    nickname = value;
                }
                new UserModifyAsyncTask().execute();
            }
        }
        if (uri != null) {
            startPhotoZoom(uri, 240);
        }
    }

    @Override
    public Loader<User> onCreateLoader(int id, Bundle args) {
        return new UserInfoLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<User> loader, User data) {
        if (data != null) {
            user = data;
            ImageViewLoader.with(this, mAvatar)
                    .round(Utils.getDimen(this, R.dimen.avatar_size) / 2)
                    .load(data.getAvatar());
            mNickname.setDesc(data.getNickname());
            mMobile.setDesc(data.getMobile());
        }
    }

    @Override
    public void onLoaderReset(Loader<User> loader) {
        loader.stopLoading();
    }

    @OnClick(R.id.layout_user)
    public void avatar(View v) {
        //modifyTask.execute();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.widget_camera_select);
        dialog = builder.show();
        dialog.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraCapture();
            }
        });
        dialog.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
    }

    @OnClick(R.id.mobile)
    public void mobile(View v) {
        Intent intent = new Intent(this, UserModifyActivity.class);
        intent.putExtra(Constants.INTENT_MOFIDY, "mobile");
        intent.putExtra(Constants.INTENT_TITLE, "手机号");
        intent.putExtra(Constants.INTENT_ORIGIN, user.getMobile());
        startActivityForResult(intent, MODIFY_REQCODE);
    }

    @OnClick(R.id.nickname)
    public void nickname(View v) {
        Intent intent = new Intent(this, UserModifyActivity.class);
        intent.putExtra(Constants.INTENT_MOFIDY, "nickname");
        intent.putExtra(Constants.INTENT_TITLE, "昵称");
        intent.putExtra(Constants.INTENT_ORIGIN, user.getNickname());
        startActivityForResult(intent, MODIFY_REQCODE);
    }

    @OnClick(R.id.password)
    public void modifyPwd(View v) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_logout)
    public void logout(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Login.logout(UserInfoActivity.this);
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setCancelable(false);
        builder.show();
    }

    private class UserModifyAsyncTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... params) {
            Map<String, Object> reqParams = new HashMap<>();
            reqParams.put("avatar", StringUtils.defaultIfBlank(avatar, ""));
            reqParams.put("mobile", StringUtils.defaultIfBlank(mobile, ""));
            reqParams.put("nickname", StringUtils.defaultIfBlank(nickname, ""));
            String ret = HttpUtils.post(API.USER_INFO.token(UserInfoActivity.this), reqParams);
            Representation<User> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<User>>() {
            }.getType());
            return getData(rep);
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                ImageViewLoader.with(UserInfoActivity.this, mAvatar).load(user.getAvatar());
                mNickname.setDesc(user.getNickname());
                mMobile.setDesc(user.getMobile());
            }
        }
    };

    private void showCameraCapture() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, CAMERA_REQCODE);
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"), FILECHOOSER_REQCODE);
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTOCUT_REQCODE);
    }

    //将进行剪裁后的图片显示到UI界面上
    private void setAvatar(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            final Bitmap photo = bundle.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(), photo);
            mAvatar.setImageDrawable(drawable);
            new AsyncTask<Void, Void, UploadImage>() {
                @Override
                protected UploadImage doInBackground(Void... params) {
                    String ret = HttpUtils.post(API.UPLOAD_AVATAR.token(UserInfoActivity.this), photo);
                    Representation<UploadImage> rep = JsonUtils.fromJson(ret, new TypeToken<Representation<UploadImage>>() {
                    }.getType());
                    return getData(rep);
                }

                @Override
                protected void onPostExecute(UploadImage uploadImage) {
                    if (uploadImage != null) {
                        avatar = uploadImage.getUrl();
                        new UserModifyAsyncTask().execute();
                    }
                }
            }.execute();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
