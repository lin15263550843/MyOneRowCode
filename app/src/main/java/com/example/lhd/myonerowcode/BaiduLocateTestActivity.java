package com.example.lhd.myonerowcode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class BaiduLocateTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BaiduLocateTestActivity";
    public LocationClient mLocationClient;
    private TextView longitude;                          // 当前经度
    private TextView dimension;                          // 当前维度
    private TextView positioningWay;                     // 定位方式
    private TextView geographicLocation;                 // 地理位置

    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirsLocate = true;
    private BDLocation mBdLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // LocationClient 的构建函数接受一个 Context 参数，调用 getApplicationContext() 方法来获取一个全局的 Context 参数并传入。
        mLocationClient = new LocationClient(getApplicationContext());
        // 调用 LocationClient 的 registerLocationListener() 方法来注册一个定位监听器，当获取到位置信息的时候，就会回调这个定位监听器。
        mLocationClient.registerLocationListener((BDAbstractLocationListener) new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext()); // 在 setContentView() 之前调用 SDKInitializer 的 initialize() 方法来进行初始化操作
        setContentView(R.layout.baidu_locate_test_layout);

        longitude = findViewById(R.id.baidu_locate_longitude);
        dimension = findViewById(R.id.baidu_locate_dimension);
        positioningWay = findViewById(R.id.baidu_locate_positioning_way);
        geographicLocation = findViewById(R.id.baidu_locate_geographic_location);
        Button getLongitudeAndDimension = findViewById(R.id.get_longitude_and_dimension);
        getLongitudeAndDimension.setOnClickListener(this);
        mapView = findViewById(R.id.bdmap_view);
        baiduMap = mapView.getMap();  // 百度LBS SDK的API中提供了一个 BaiduMap 类，它是地图的总控制器，调用 MapView 的 getMap() 方法就能获取到 BaiduMap 的实例
        baiduMap.setMyLocationEnabled(true);    // 启用在地图上显示"我"的位置
    }

    // 获取位置
    private void requestLocation() {
        // 我们先定义一个空的权限 List 集合，再分别判断每个权限，如果没有被授权就添加到权限集合里，用来一次性申请多个权限。
        List<String> permissionList = new ArrayList<>();    // 创建 List 集合
        if (ContextCompat.checkSelfPermission(BaiduLocateTestActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(BaiduLocateTestActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(BaiduLocateTestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);   // 将 LIst 集合转化成数组
            ActivityCompat.requestPermissions(BaiduLocateTestActivity.this, permissions, 1);
        } else {
            initLocation();
            mLocationClient.start();    // 开始定位
        }
    }

    // 移动定位
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
//        option.setScanSpan(5000);   // 五秒钟定位一次
        // 地位模式，Device_Sensors ：传感器模式，只会使用GPS定位，Hight_Accuracy ：高精度模式，也是默认的模式，会自动切换模式，Battery_Saving ：节电模式，只会使用网络进行定位
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
//        option.setAddrType("all");
        option.setIsNeedAddress(true);  // 不添加无法获取 国家 省 市 区 街道 等信息
        mLocationClient.setLocOption(option);
    }

    // 地图定位当前位置
    private void navigateTo(BDLocation location) {
        if (isFirsLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate msp = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(msp);
            msp = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(msp);
            isFirsLocate = false;
        }
        // MyLocationData.Builder 类用来封装设备当前所在位置的，我们只需要将经纬度信息传入到这个类的相应方法中就可以了
        MyLocationData.Builder mld = new MyLocationData.Builder();
        mld.latitude(location.getLatitude());
        mld.longitude(location.getLongitude());
        // MyLocationData.Builder 类还提供了一个 build() 方法，当我们吧要封装的信息都设置完成之后，只需要调用它 build() 方法，就会生成一个 MyLocationData 的实例，
        // 然后再将这个实例传入到 BaiduMap 的 setMyLocationData() 方法中，就可以再地图上显示当前位置了。
        MyLocationData myLocationData = mld.build();
        baiduMap.setMyLocationData(myLocationData);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);    // 关闭在地图上显示"我"的位置
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_longitude_and_dimension:
                isFirsLocate = true;
                requestLocation();
                if (mBdLocation != null) {
                    if (mBdLocation.getLocType() == BDLocation.TypeNetWorkLocation || mBdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                        navigateTo(mBdLocation);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int item : grantResults) {
                        if (item != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意该权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    initLocation();
                    mLocationClient.start();    // 开始定位
                } else {
                    Toast.makeText(this, "申请权限时发生错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private class MyLocationListener extends BDAbstractLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mBdLocation = bdLocation;
            StringBuilder sb = new StringBuilder();
            sb.append("【").append(bdLocation.getLatitude()).append("】");    // 获取维度
            sb.append("【").append(bdLocation.getLongitude()).append("】");   // 获取经度
            sb.append("【").append(bdLocation.getCountry()).append("】");     // 获取国家
            sb.append("【").append(bdLocation.getProvince()).append("】");    // 获取省
            sb.append("【").append(bdLocation.getCity()).append("】");        // 获取市
            sb.append("【").append(bdLocation.getDistrict()).append("】");    // 获取区
            sb.append("【").append(bdLocation.getStreet()).append("】");      // 获取街道
            sb.append("【").append(bdLocation.getStreet()).append("】");      // 获取街道
            sb.append("【").append(bdLocation.getAddrStr()).append("】");     // 获取详细地址信息
            longitude.setText(String.valueOf(bdLocation.getLatitude()));     // 获取维度
            dimension.setText(String.valueOf(bdLocation.getLongitude()));    // 获取经度
//            geographicLocation.setText(String.valueOf(bdLocation.getCountry() + bdLocation.getProvince() + bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet() + "--" + bdLocation.getAddrStr()));   //位置
            geographicLocation.setText(String.valueOf(bdLocation.getAddrStr()));   //位置
            sb.append("定位方式：");
            if (bdLocation.getLocType() == BDLocation.TypeCacheLocation) {
                sb.append("GPS");
                positioningWay.setText("GPS");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("网络");
                positioningWay.setText("网络");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("请连接网络");
                positioningWay.setText("请连接网络");
            }
            Log.d(TAG, "onReceiveLocation: " + sb);
            if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation || bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                navigateTo(bdLocation);
            }
        }
    }
}
