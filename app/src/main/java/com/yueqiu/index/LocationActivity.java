package com.yueqiu.index;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.joanzapata.android.iconify.Iconify;
import com.yueqiu.BaseActivity;
import com.yueqiu.R;
import com.yueqiu.utils.Constants;
import com.yueqiu.utils.Utils;
import com.yueqiu.widget.BaseArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created on 15/2/19.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class LocationActivity extends BaseActivity {

    @InjectView(R.id.location_gps)
    TextView mGps;
    @InjectView(R.id.list)
    ListView mList;

    LocationCityAdapter mAdapter;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location);

        Iconify.addIcons(mGps);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotation);
        anim.setInterpolator(new LinearInterpolator());
        mGps.startAnimation(anim);

        mAdapter = new LocationCityAdapter(this);
        mList.setAdapter(mAdapter);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        initServCity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManagerProxy proxy = LocationManagerProxy.getInstance(this);
        proxy.setGpsEnable(true);
        proxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 0, new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
                    mGps.clearAnimation();
                    mGps.setText(aMapLocation.getCity());
                    Log.d(TAG, aMapLocation.getCity() + " " + aMapLocation.getProvider());
                }
            }

            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initServCity() {
        List<String> list = new ArrayList<String>();
        list.add("北京");
        list.add("上海");
        mAdapter.load(list);
    }

    private class LocationCityAdapter extends BaseArrayAdapter<String> {

        public LocationCityAdapter(Activity context) {
            super(context, R.layout.activity_location_item, null);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_location_item, parent, false);
            }
            TextView city = (TextView) convertView.findViewById(R.id.city);
            TextView select = (TextView) convertView.findViewById(R.id.select);

            final String cityStr = getItem(position);
            city.setText(cityStr);
            String selectCity = mPreferences.getString(Constants.PREF_LOCATION_CITY, null);
            Iconify.addIcons(select);
            if (cityStr.equals(selectCity)) {
                select.setVisibility(View.VISIBLE);
            } else {
                select.setVisibility(View.GONE);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString(Constants.PREF_LOCATION_CITY, cityStr);
                    editor.apply();
                    Intent intent = new Intent();
                    intent.putExtra(Constants.INTENT_LOCATION_CITY, cityStr);
                    setResult(RESULT_OK, intent);
                    Utils.finish(LocationActivity.this, 300);
                }
            });

            return convertView;
        }
    }

    ;
}
