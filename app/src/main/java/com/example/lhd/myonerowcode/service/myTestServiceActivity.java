package com.example.lhd.myonerowcode.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lhd.myonerowcode.R;

public class myTestServiceActivity extends AppCompatActivity {

    private static final String TAG = "myTestServiceActivity";
    private MyService.DownloadBinder downloadBinder;

    /**
     * 这里我们首先创建了一个 ServiceConnection 的匿名类，在里面重写了 onServiceConnected（）方法和 onServiceDisconnected（）方法，
     * 这两个方法分别会在活动与服务成功绑定以及解除绑定的时候调用。
     * 在 onServiceConnected（）方法中，我们又通过向下转型得到了 DownloadBinder 的实例，有了这个实例，活动和服务之间的关系就变得非常紧密了。
     * 现在我们可以在活动中根据具体的场景来调用 DownloadBinder 中的任何 public（）方法，即实现了指挥服务千什么服务就去千什么的功能。
     * 这里仍然只是做了个简单的测试，在 onServiceConnected（）方法中调用了 DownloadBinder 的 startDownload（）和 getProgress（）方法。
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getPorgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_test_service_layout);

        Button openService = findViewById(R.id.my_test_service_open);
        Button closeService = findViewById(R.id.my_test_service_close);
        final Button bindService = findViewById(R.id.my_test_service_bind);
        final Button unbindService = findViewById(R.id.my_test_service_unbind);
        Button startIntentService = findViewById(R.id.my_test_intent_service_start);

        openService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIntent = new Intent(myTestServiceActivity.this, MyService.class);
                startService(openIntent);   // 启动服务
            }
        });
        closeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent closeIntent = new Intent(myTestServiceActivity.this, MyService.class);
                stopService(closeIntent);   // 停止服务
            }
        });
        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里我们仍然是构建出了一个 Intent 对象，然后调用 bindService（）方法将 MainActivity 和 MyService 进行绑定。
                // bindService（）方法接收 3 个参数，
                // 第一个参数就是刚刚构建出的 Intent 对象，
                // 第二个参数是前面创建出的 ServiceConnection 的实例，
                // 第三个参数则是一个标志位，这里传人 BIND AUTO_ CREATE 表示在活动和服务进行绑定后自动创建服务。
                // 这会使得 MyService 中的 onCreate（）方法得到执行，但 onStartCommand（）方法不会执行。
                Intent bindIntent = new Intent(myTestServiceActivity.this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);  // 绑定服务
            }
        });
        unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection); // 解绑服务
            }
        });
        startIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 主线程的id：" + Thread.currentThread().getId());
                Intent intentService = new Intent(myTestServiceActivity.this, MyIntentService.class);
                startService(intentService);
            }
        });
    }
}
