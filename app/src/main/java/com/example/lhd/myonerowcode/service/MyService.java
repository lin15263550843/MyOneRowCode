package com.example.lhd.myonerowcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {  // Service 中惟一的一个抽象方法，所以必须要在子类里实现
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {    // 服务创建的时候调用
        super.onCreate();
        Log.d(TAG, "onCreate: 创建服务=============================onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {  //  每次服务启动的时候调用
        Log.d(TAG, "onStartCommand: 每次启动服务=============================onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {   // 当服务销毁时，在这里回收那些不再使用的资源
        super.onDestroy();
        Log.d(TAG, "onDestroy: 销毁服务=============================onDestroy");
    }
}

/**
 * 需要注意，每一个服务都要在 AndroidManifest.xml 文件中进行注册才能生效
 */
