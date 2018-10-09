package com.example.lhd.myonerowcode.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.lhd.myonerowcode.gson.Weather;
import com.example.lhd.myonerowcode.util.HttpUtil;
import com.example.lhd.myonerowcode.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CoolWeatherService extends Service {
    private static final String TAG = "CoolWeatherService";

    public CoolWeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingImg();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 1000;  //8个小时
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, CoolWeatherService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pi);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新必应每日一图
     */
    private void updateBingImg() {
        String requestBingImgUrl = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingImgUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic = response.body().string();
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(CoolWeatherService.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
            }
        });
    }

    /**
     * 更新天气信息
     */
    private void updateWeather() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sp.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;
            String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=b6271cbdc8db48d0b86cfdc9bdba72e2";
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Weather weather = Utility.handleWeatherResponse(responseText);
                    if (weather != null && "ok".equals(weather.status)) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(CoolWeatherService.this).edit();
                        editor.putString("weather", responseText);
                        editor.apply();
                        Log.d(TAG, "onResponse: 更新了一次天气============================");
                    }
                }
            });
        }
    }

}
