package com.example.lhd.myonerowcode.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dhc on 2018/9/19.
 * <p>
 * cool_suggestion 中的具体内容如下所示：
 * "cool_suggestion": {
 *      "comf"：{
 *          "txt": "白天天气较热，虽然有雨，但仍煞无法削弱较高气温给人们带来的暑意，这种天气会让您感到不很舒适."
 *      }
 *      "cw": {
 *          "txt": "不宜洗车，未来 24 小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄胜您的爱车。"
 *      }
 *      "sport": {
 *          "txt": "有降水，且风力较强，推荐您在室内进行低强度运动；若坚持户外运动，请选择避雨防风的地点。"
 *      }
 * }
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;

    public class Comfort {

        @SerializedName("txt")
        public String info;
    }

    private class CarWash {

        @SerializedName("txt")
        public String info;
    }

    private class Sport {

        @SerializedName("txt")
        public String info;
    }
}
