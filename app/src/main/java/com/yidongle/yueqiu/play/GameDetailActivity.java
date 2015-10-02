package com.yidongle.yueqiu.play;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.loader.GameDetailLoader;
import com.yidongle.yueqiu.model.Game;
import com.yidongle.yueqiu.model.Order;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.ImageViewLoader;

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

    Game game;
    Order order;
    String activityId;
    double latitude;
    double longitude;

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
            game = data;
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
            latitude = data.getStadium().getLatitude();
            longitude = data.getStadium().getLongitude();
            mDate.setText(data.getDate());
            mRule.setText(String.format(Locale.CHINA, rulePattern, data.getStadium().getSize()));
            mOrganizer.setText(data.getOrganizer().getNickname() + " " + data.getOrganizer().getMobile());
            mPlayer.setText(String.format(Locale.CHINA, attendPattern, data.getTotal(), data.getAttend()));
            mPrice.setText("￥" + data.getPrice());
            order = data.getOrder();
            if (order != null) {
                if (order.isCreated()) {
                    mSignup.setText("去支付");
                } else if (order.isPayed()) {
                    mSigned.setVisibility(View.VISIBLE);
                    mSignup.setVisibility(View.GONE);
                } else if (order.isSignin()) {
                    mSigned.setVisibility(View.VISIBLE);
                    mSigned.setText("已签到");
                    mSignup.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Game> loader) {
        loader.stopLoading();
    }

    @OnClick(R.id.gotomap)
    public void gotomap(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(Constants.INTENT_LATITUDE, game.getStadium().getLatitude());
        intent.putExtra(Constants.INTENT_LONGITUDE, game.getStadium().getLongitude());
        startActivity(intent);
    }

    @OnClick(R.id.signup)
    public void signup(View v) {
        if (order == null) {
            order = new Order();
        }
        if (order.isCreated()) {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(Constants.INTENT_ORDER, order);
            intent.putExtra(Constants.INTENT_ACTIVITY, game);
            startActivity(intent);
        } else if (order.isPayed()) {

        } else if (order.isNew()) {
            Intent intent = new Intent(GameDetailActivity.this, OrderActivity.class);
            intent.putExtra(Constants.INTENT_ACTIVITY, game);
            startActivity(intent);
        }
    }
}
