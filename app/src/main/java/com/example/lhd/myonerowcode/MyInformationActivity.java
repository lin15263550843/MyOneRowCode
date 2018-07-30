package com.example.lhd.myonerowcode;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyInformationActivity extends AppCompatActivity {
    private static final String TAG = "MyInformationActivity";

//    PowerManager powerManager;
//    SensorManager sensorManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information_layout);
        Log.v(TAG, "onCreate: MyInformationActivity页面");

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
