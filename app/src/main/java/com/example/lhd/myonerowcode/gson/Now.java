package com.example.lhd.myonerowcode.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dhc on 2018/9/19.
 * <p>
 * now 中的具体内容如下所示：
 * "now": {
 *      "tmp": "29",
 *      "cond: {
 *          "txt":“阵雨“
 *      }
 * }
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;
    }
}
