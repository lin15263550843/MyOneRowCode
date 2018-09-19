package com.example.lhd.myonerowcode.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dhc on 2018/9/4.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient oc = new OkHttpClient();
        Request r = new Request.Builder().url(address).build();
        oc.newCall(r).enqueue(callback);
    }
}
