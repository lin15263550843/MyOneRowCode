package com.example.lhd.myonerowcode.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dhc on 2018/9/20.
 * <p>
 * daily_forecast 中包含的是一个数组，数组中的每一项都代表着未来一天的天气信息。
 * 针对于这种情况，我们只需要定义出单日天气的实体类就可以了，然后在声明实体类引用的时候使用集合类型来进行声明。
 * <p>
 * daily_forecast 中的具体内容如下所示：
 * "daily. Forecast": [
 *      {
 *          "date": "2016-08-08",
 *          "cond": {
 *              "txt_d": "阵雨"
 *          }
 *          "tmp": {
 *              "max"; "35",
 *              "min"; "29"
 *          }
 *      },
 *      {
 *          "date": "2016-08-08",
 *          "cond": {
 *              "txt_d": "阵雨"
 *          }
 *          "tmp": {
 *              "max"; "35",
 *              "min"; "29"
 *          }
 *      },
 *      ...
 * ]
 */

public class Forecast {

    public String date;

    @SerializedName("cond")
    public More more;

    @SerializedName("tmp")
    public Temperature temperature;

    private class More {

        @SerializedName("txt_d")
        public String info;
    }

    private class Temperature {

        public String max;
        public String min;
    }
}
