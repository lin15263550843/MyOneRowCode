package com.example.lhd.myonerowcode.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dhc on 2018/9/20.
 * <p>
 * 把 basic、aqi、now、suggestion 和 daily_forecast 对应的实体类全部都创建好了，
 * 接下来还需要再创建一个总的实例类来引用刚刚创建的各个实体类。在 gson 包下新建一个 Weather 类。
 * <p>
 * 在 Weather 类中，我们对 Basic. AQI、Now. Suggestion 和 Forecast 类进行了引用。
 * 其中，由于 daily_forecast 中包含的是一个数组，因此这里使用了 List 集合来引用 Forecast 类。
 * 另外，返回的天气数据中还会包含一项 status 数据，成功返回 ok，失败则会返回具体的原因，那么这里也需要添加一个对应的 status 字段。
 */

public class Weather {

    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
