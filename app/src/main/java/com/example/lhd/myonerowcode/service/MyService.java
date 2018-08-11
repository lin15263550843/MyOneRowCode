package com.example.lhd.myonerowcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {    // 服务创建的时候调用
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {  //  每次服务启动的时候调用
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {   // 当服务销毁时，在这里回收那些不再使用的资源
        super.onDestroy();
    }
}

/**
 * 需要注意，每一个服务都要在 AndroidManifest.xml 文件中进行注册才能生效
 */
