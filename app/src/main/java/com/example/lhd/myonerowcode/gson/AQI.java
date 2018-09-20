package com.example.lhd.myonerowcode.gson;

/**
 * Created by dhc on 2018/9/19.
 * 由于 JSON 中的一些字段可能不太适合直接作为 Java 字段来命名，因此这里使用了 @SerializedName 注解的方式来让 JSON 字段和 Java 字段之间建立映射关系。
 * <p>
 * aqi 中具体内容如下所示：
 * "aqi": {
 *      "ity": {
 *          "aqi": "44",
 *          "pm25*: "13"
 *      }
 * }
 */

public class AQI {

    public AQICity city;

    public class AQICity {

        public String aqi;

        public String pm25;
    }
}
