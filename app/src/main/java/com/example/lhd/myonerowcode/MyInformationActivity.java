package com.example.lhd.myonerowcode;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.PowerManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MyInformationActivity extends AppCompatActivity {
    private static final String TAG = "MyInformationActivity";

    private DrawerLayout mDrawerLayout;
//    PowerManager powerManager;
//    SensorManager sensorManager = null;

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {    // 添加导航栏按钮
//        return super.onCreatePanelMenu(featureId, menu);
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   // 处理各个按钮的点击事件
        switch (item.getItemId()) {
            case R.id.toolbar_backup:
                Toast.makeText(this, "你点击了备用", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_delete:
                Toast.makeText(this, "你点击了删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_settings:
//                Toast.makeText(this, "你点击了设置", Toast.LENGTH_SHORT).show();
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
//        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information_layout);
        Log.v(TAG, "onCreate: MyInformationActivity页面");

        Toolbar toolbar = (Toolbar) findViewById(R.id.title_toolbar_common);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.my_info_drawer);
//        ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            ab.setDisplayHomeAsUpEnabled(true);   // 显示导航栏的侧滑按钮
//            ab.setHomeAsUpIndicator(R.drawable.shezhi_on);    // 按钮图标
//        }

        Button myInformationButton1 = (Button) findViewById(R.id.my_information_button_1);
        myInformationButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("my_info_return_data", "我是从myInfo传递到main的数据，拉拉阿拉啦啦啦啦啦啦啦~");
                MyInformationActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        });

//        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//        audioManager.setSpeakerphoneOn(false);
////        audioManager.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);STREAM_MUSIC
//
//        audioManager.setMode(AudioManager.STREAM_MUSIC);
//        audioManager.setSpeakerphoneOn(true);
//
//        powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, this.getClass().getName());
//        wakeLock.setReferenceCounted(false);
//        wakeLock.release();
//        wakeLock.acquire();
//
//        boolean isjuli;
//
//        isjuli = sensorManager.registerListener(new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                float[] dis = event.values;
//                if (0.0f == dis[0]) { // 靠近身体
////                    wakeLock.release(); // 熄灭屏幕
////                    switchToEarpiece(); // 切换到听筒
//                } else {
////                    wakeLock.acquire(); // 点亮屏幕
////                    switchToSpeaker(); // 切换到扬声器
//                }
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//            }
//        }, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);


    }
}
