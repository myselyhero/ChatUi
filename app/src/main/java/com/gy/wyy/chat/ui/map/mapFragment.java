package com.gy.wyy.chat.ui.map;

import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.gy.wyy.chat.R;

public class mapFragment extends Fragment implements BaiduMap.OnMapStatusChangeListener {

    private View contentView;

    private MapView mapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private LatLng GEO_GY = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_map, container, false);
        initView();
        return contentView;
    }

    /**
     *
     */
    private void initView(){
        mapView = contentView.findViewById(R.id.map_fragment_map_view);
        mBaiduMap = mapView.getMap();

        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //开启交通图
        mBaiduMap.setTrafficEnabled(true);//设置地图缩放
        //
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        //设置地图状态监听
        mBaiduMap.setOnMapStatusChangeListener(this);
        mLocationClient = new LocationClient(getContext());
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        mLocationClient.registerLocationListener(new LocationListener());
        //开启地图定位图层
        mLocationClient.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
     *
     * @param mapStatus 地图状态改变开始时的地图状态
     */
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    /**
     * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
     *
     * @param mapStatus 地图状态改变开始时的地图状态
     *
     * @param reason 地图状态改变的原因
     */

    //用户手势触发导致的地图状态改变,比如双击、拖拽、滑动底图
    //int REASON_GESTURE = 1;
    //SDK导致的地图状态改变, 比如点击缩放控件、指南针图标
    //int REASON_API_ANIMATION = 2;
    //开发者调用,导致的地图状态改变
    //int REASON_DEVELOPER_ANIMATION = 3;
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int reason) {

    }

    /**
     * 地图状态变化中
     *
     * @param mapStatus 当前地图状态
     */
    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    /**
     * 地图状态改变结束
     *
     * @param mapStatus 地图状态改变结束后的地图状态
     */
    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }

    /**
     * 定位回调
     */
    public class LocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mapView == null || mBaiduMap == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            /*if (bdLocation.getAddress() != null && !TextUtils.isEmpty(bdLocation.getAddress().city)){
                bdLocation.getAddress().city+bdLocation.getAddress().district+bdLocation.getAddress().street)
            }*/
            if (GEO_GY == null){
                GEO_GY = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newLatLng(GEO_GY);
                mBaiduMap.setMapStatus(statusUpdate);
            }
        }
    }
}