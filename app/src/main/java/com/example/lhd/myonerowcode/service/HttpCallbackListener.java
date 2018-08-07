package com.example.lhd.myonerowcode.service;

/**
 * Created by dhc on 2018/8/7.
 * http 请求的回调方法接口
 */

public interface HttpCallbackListener {
    // 成功的回调
    void onSuccess(String response);

    // 失败的回调
    void onError(Exception error);
}
