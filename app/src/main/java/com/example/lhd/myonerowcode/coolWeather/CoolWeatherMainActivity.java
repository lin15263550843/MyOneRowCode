package com.example.lhd.myonerowcode.coolWeather;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhd.myonerowcode.R;
import com.example.lhd.myonerowcode.gson.Forecast;
import com.example.lhd.myonerowcode.gson.Weather;
import com.example.lhd.myonerowcode.util.HttpUtil;
import com.example.lhd.myonerowcode.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CoolWeatherMainActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;

    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cool_weather_main_layout);
        // 初始化各个控件
        weatherLayout = (ScrollView) findViewById(R.id.cool_weather_main_layout);
        titleCity = findViewById(R.id.cool_title_city);
        titleUpdateTime = findViewById(R.id.cool_title_update_time);
        degreeText = findViewById(R.id.cool_now_degree_text);
        weatherInfoText = findViewById(R.id.cool_now_info_text);
        forecastLayout = findViewById(R.id.cool_forecast_layout);
        aqiText = findViewById(R.id.cool_aqi_text);
        pm25Text = findViewById(R.id.cool_aqi_pm25_text);
        comfortText = findViewById(R.id.cool_suggestion_comfort_text);
        carWashText = findViewById(R.id.cool_suggestion_car_wash_text);
        sportText = findViewById(R.id.cool_suggestion_sport_text);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sp.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
//            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);    // 请求数据的时候隐藏 ScrollView 不然空数据的界面看上去会很奇怪
            requestWeather("CN101120202");
        }
    }

    /**
     * 根据天气id请求城市天气信息
     *
     * @param weatherId
     */
    private void requestWeather(String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=b6271cbdc8db48d0b86cfdc9bdba72e2";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(CoolWeatherMainActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(() -> {   // 切换到主线程
                    if (weather != null && "ok".equals(weather.status)) {
                        // 将请求返回并解析好的数据缓存到 SharedPreferences 当中
                        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(CoolWeatherMainActivity.this).edit();
                        editor.putString("weather", responseText);
                        editor.apply();
                        // 显示数据
                        showWeatherInfo(weather);
                    } else {
                        Toast.makeText(CoolWeatherMainActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 处理并展示 Weather 实体类中的数据
     *
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];  // 只显示时分
        String degree = weather.now.temperature + "°C";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.cool_forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.cool_forecast_item_date_text);
            TextView infoText = view.findViewById(R.id.cool_forecast_item_info_text);
            TextView maxText = view.findViewById(R.id.cool_forecast_item_max_text);
            TextView minText = view.findViewById(R.id.cool_forecast_item_min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            maxText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运动建议：" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);  // 显示 ScrollView
    }
}
