package com.yidongle.yueqiu.play;

import android.content.Intent;
import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.yidongle.yueqiu.BaseActivity;
import com.yidongle.yueqiu.R;
import com.yidongle.yueqiu.utils.Constants;
import com.yidongle.yueqiu.utils.Logger;

/**
 * Created on 15/6/16.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class MapActivity extends BaseActivity {

    private MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        float latitude = intent.getFloatExtra(Constants.INTENT_LATITUDE, 0);
        float longitude = intent.getFloatExtra(Constants.INTENT_LONGITUDE, 0);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        init();

        LatLng ll = new LatLng(latitude, longitude);
        Logger.debug(ll);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 17));
        aMap.addMarker(new MarkerOptions().position(ll));
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
