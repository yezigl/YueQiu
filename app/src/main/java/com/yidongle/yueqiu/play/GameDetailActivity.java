package com.yidongle.yueqiu.play;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.loader.GameDetailLoader;
import com.yidongle.yueqiu.loader.LoaderRequest;
import com.yidongle.yueqiu.loader.OrderDetailLoader;
import com.yidongle.yueqiu.model.Game;
import com.yidongle.yueqiu.model.Order;
import com.yidongle.yueqiu.model.User;
import com.yidongle.yueqiu.order.OrderActivity;
import com.yidongle.yueqiu.pay.PaymentActivity;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.ImageViewLoader;
import com.yidongle.yueqiu.utils.Utils;

import java.util.List;

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
    @InjectView(R.id.price)
    TextView mPrice;
    @InjectView(R.id.layout_player)
    LinearLayout mLayoutPlayer;

    Game game;
    String activityId;

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
            mDate.setText(data.getDate());
            mRule.setText(getString(R.string.game_rule, data.getStadium().getSize()));
            mOrganizer.setText(getString(R.string.game_organizer, data.getOrganizer().getNickname(), data.getOrganizer().getMobile()));
            mPlayer.setText(getString(R.string.game_player, data.getTotal(), data.getAttend()));
            mPrice.setText(getString(R.string.price_rmb, data.getPrice()));
            List<User> players = data.getPlayers();
            if (players != null) {
                for (User user : players) {
                    int iconSize = Utils.getDimen(this, R.dimen.player_icon_size);
                    View view = LayoutInflater.from(this).inflate(R.layout.activity_game_detail_player, mLayoutPlayer, false);
                    ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
                    TextView nickname = (TextView) view.findViewById(R.id.nickname);
                    TextView quantity = (TextView) view.findViewById(R.id.quantity);
                    ImageViewLoader.with(this, avatar).round(iconSize / 2).load(user.getAvatar());
                    nickname.setText(user.getNickname());
                    mLayoutPlayer.addView(view);
                }
            }
            mSignup.setEnabled(game.getOrderInfo().isCanBuy());
            if (game.getOrderInfo().isPayed()) {
                mSignup.setText("已购买");
            } else if (game.getOrderInfo().isHasBuy()) {
                mSignup.setText("去支付");
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
        if (game.getOrderInfo().isHasBuy() && !game.getOrderInfo().isPayed()) {
            new LoaderRequest<Order>(this) {
                @Override
                public Loader<Order> onCreateLoader(int id, Bundle args) {
                    return new OrderDetailLoader(getContext()).params(game.getOrderInfo().getOrderId());
                }

                @Override
                public void onLoadFinished(Loader<Order> loader, Order data) {
                    if (data != null) {
                        Intent intent = new Intent(GameDetailActivity.this, PaymentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra(Constants.INTENT_ORDER, data);
                        intent.putExtra(Constants.INTENT_ACTIVITY, game);
                        startActivity(intent);
                    }
                }
            }.request();
        } else if (!game.getOrderInfo().isHasBuy()) {
            Intent intent = new Intent(this, OrderActivity.class);
            intent.putExtra(Constants.INTENT_ACTIVITY, game);
            startActivity(intent);
        }
    }
}
