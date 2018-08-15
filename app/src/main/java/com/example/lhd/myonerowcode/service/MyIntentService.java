package com.example.lhd.myonerowcode.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by dhc on 2018/8/15.
 */

public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
//    public MyIntentService(String name) {
//        super(name);    // 调用父类的有参构造函数
//    }
    public MyIntentService() {
        super("MyIntentService");    // 调用父类的有参构造函数
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 打印当前线程的id
        Log.d(TAG, "onHandleIntent: 当前线程的id:" + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 关闭当前服务");
    }
}
