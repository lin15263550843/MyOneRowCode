package com.example.lhd.myonerowcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


/**
 * 需要注意，每一个服务都要在 AndroidManifest.xml 文件中进行注册才能生效。
 * <p>
 * 另外需要注意，任何一个服务在整个应用程序范围内都是通用的，即 MyService 不仅可以和 MainActivity 绑定，还可以和任何一个其他的活动进行绑定，
 * 而且在绑定完成后它们都可以获取到相同的 DownloadBinder 实例。
 * <p>
 * 服务的生命周期
 * <p>
 * 之前我们学习过了活动以及碎片的生命周期。类似地，服务也有自己的生命周期，
 * 前面我们使用到的 onCreate (). OnStartCommand (). OnBind（）和 onDestroy（）等方法都是在服务的生命周期内可能回调的方法。
 * 一旦在项目的任何位置调用了 Context 的 startService（）方法，相应的服务就会启动起来，并回调 onStartCommand（）方法。
 * 如果这个服务之前还没有创建过，onCreate（）方法会先于 onStartCommand（）方法执行。
 * 服务启动了之后会一直保持运行状态，直到 stopService（）或 stopSelf（）方法被调用。
 * 注意，虽然每调用一次 startService（）方法，onStartCommand（）就会执行一次，但实际上每个服务都只会存在一个实例。
 * 所以不管你调用了多少次 startService（）方法，只需调用一次 stopService（）或 stopSelf（）方法，服务就会停止下来了。
 * 另外，还可以调用 Context 的 bindService（）来获取一个服务的持久连接，这时就会回调服务中的 onBind（）方法。
 * 类似地，如果这个服务之前还没有创建过，onCreate（）方法会先于 onBind（）方法执行。
 * 之后，调用方可以获取到 onBind（）方法里返回的 IBinder 对象的实例，这样就能自由地和服务进行通信了。
 * 只要调用方和服务之间的连接没有断开，服务就会一直保持运行状态。
 * <p>
 * 当调用了 startService（）方法后，又去调用 stopService（）方法，这时服务中的 onDestroy（）方法就会执行，表示服务已经销毁了。
 * 类似地，当调用了 bindService（）方法后，又去调用 unbindService（）方法，onDestroy（）方法也会执行，这两种情况都很好理解。
 * 但是需要注意，我们是完全有可能对一个服务既调用了 startService（）方法，又调用了 bindService（）方法的，这种情况下该如何才能让服务销毁掉呢？
 * 根据 Android 系统的机制，一个服务只要被启动或者被绑定了之后，就会一直处于运行状态，必须要让以上两种条件同时不满足，服务才能被销毁。
 * 所以，这种情况下要同时调用 stopService（）和 unbindService（）方法，onDestroy（）方法才会执行。
 * 这样你就已经把服务的生命周期完整地走了一遍。
 */

public class MyService extends Service {
    private static final String TAG = "MyService";
    private DownloadBinder mBinder = new DownloadBinder();

    public MyService() {
    }

    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d(TAG, "startDownload: 开始模拟下载");
        }

        public int getPorgress() {
            Log.d(TAG, "getPorgress: 模拟下载进度");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {  // Service 中惟一的一个抽象方法，所以必须要在子类里实现
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//        Log.d(TAG, "onBind: " + intent.toString());
        return mBinder;

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
