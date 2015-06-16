package com.yueqiu.index;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.yueqiu.BaseActivity;
import com.yueqiu.R;
import com.yueqiu.loader.GameDetailLoader;
import com.yueqiu.loader.OrderLoader;
import com.yueqiu.model.Game;
import com.yueqiu.model.Order;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.ImageViewLoader;
import com.yueqiu.utils.Logger;
import com.yueqiu.utils.StringUtils;

import java.util.List;
import java.util.Locale;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created on 15/6/9.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class GameDetailActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Game> {

    @InjectView(R.id.viewflipper)
    ViewFlipper mFlipper;
    @InjectView(R.id.icon)
    ImageView mIcon;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.address)
    TextView mAddress;
    @InjectView(R.id.date)
    TextView mDate;
    @InjectView(R.id.rule)
    TextView mRule;
    @InjectView(R.id.organizer)
    TextView mOrganizer;
    @InjectView(R.id.player)
    TextView mPlayer;
    @InjectView(R.id.signup)
    TextView mSignup;
    @InjectView(R.id.signed)
    TextView mSigned;
    @InjectView(R.id.price)
    TextView mPrice;

    String attendPattern = "限报%s人 已报%s人";
    String rulePattern = "%s人制";

    String activityId;
    boolean isOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_detail);

        activityId = getIntent().getStringExtra(Constants.INTENT_ACTIVITY_ID);

        getLoaderManager().restartLoader(hashCode(), null, this);
    }

    @Override
    public Loader<Game> onCreateLoader(int id, Bundle args) {
        return new GameDetailLoader(this).params(activityId);
    }

    @Override
    public void onLoadFinished(Loader<Game> loader, Game data) {
        if (data != null) {
            List<String> gallery = data.getStadium().getGallery();
            for (String imgUrl : gallery) {
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageViewLoader.with(this, imageView).load(imgUrl);
                mFlipper.addView(imageView);
            }
            if (gallery.size() > 1) {
                mFlipper.startFlipping();
            }
            mTitle.setText(data.getTitle());
            mAddress.setText(data.getStadium().getName());
            mDate.setText(data.getDate());
            mRule.setText(String.format(Locale.CHINA, rulePattern, data.getStadium().getSize()));
            mOrganizer.setText(data.getOrganizer().getNickname() + " " + data.getOrganizer().getMobile());
            mPlayer.setText(String.format(Locale.CHINA, attendPattern, data.getTotal(), data.getAttend()));
            mPrice.setText("￥" + data.getPrice());
            if (StringUtils.isNotBlank(data.getOrderId())) {
                isOrder = true;
                mSigned.setVisibility(View.VISIBLE);
                mSignup.setText("查看二维码");
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Game> loader) {
        loader.stopLoading();
    }

    @OnClick(R.id.gotomap)
    public void gotomap(View v) {

    }

    @OnClick(R.id.signup)
    public void signup(View v) {
        if (isOrder) {

        } else {
//            new AsyncTask<Void, Void, Order>() {
//
//                @Override
//                protected Order doInBackground(Void... params) {
//                    return null;
//                }
//
//                @Override
//                protected void onPostExecute(Order order) {
//                }
//            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            getLoaderManager().restartLoader(hashCode() + 1, null, new LoaderManager.LoaderCallbacks<Order>() {
                @Override
                public Loader<Order> onCreateLoader(int id, Bundle args) {
                    return new OrderLoader(GameDetailActivity.this).params(activityId);
                }

                @Override
                public void onLoadFinished(Loader<Order> loader, Order data) {
                    Logger.debug("Loader", data);
                    if (data != null) {
                        Intent intent = new Intent(GameDetailActivity.this, PaymentActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onLoaderReset(Loader<Order> loader) {
                    loader.stopLoading();
                }
            });
        }
    }
}
