package com.example.lhd.myonerowcode.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lhd.myonerowcode.R;

public class BroadcastTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BroadcastTestActivity";
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;


    private IntentFilter intentFilterLocal;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);  //取消注册
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_test_layout);
        Log.v(TAG, "onCreate: BroadcastTestActivity页面");

        Button broadcastTestBack = (Button) findViewById(R.id.broadcast_test_button_back);
        Button broadcastTestSendBroadcast = (Button) findViewById(R.id.broadcast_test_button_send_broadcast);
        Button broadcastTestLocalBroadcast = (Button) findViewById(R.id.broadcast_test_button_local_broadcast);
        broadcastTestBack.setOnClickListener(this);
        broadcastTestSendBroadcast.setOnClickListener(this);
        broadcastTestLocalBroadcast.setOnClickListener(this);

        intentFilter = new IntentFilter();
        //当网络变化时，系统发出值为android.net.conn.CONNECTIVITY_CHANGE的广播，广播接收器想要监听什么广播，就在这里添加相应的action
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);   //注册

        localBroadcastManager = LocalBroadcastManager.getInstance(this);    //获取实例
        intentFilterLocal = new IntentFilter();
        intentFilterLocal.addAction("com.example.lhd.myonerowcode.broadcast.MY_LOCAL_RECEIVER");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilterLocal);   //注册本地广播监听器

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.broadcast_test_button_back:
                finish();
                break;
            case R.id.broadcast_test_button_send_broadcast:
                Intent intent = new Intent("com.example.lhd.myonerowcode.broadcast.MY_BROADCAST_RECEIVER");
                sendBroadcast(intent);  //发送广播
                break;
            case R.id.broadcast_test_button_local_broadcast:
                Intent intentLocal = new Intent("com.example.lhd.myonerowcode.broadcast.MY_LOCAL_RECEIVER");
                localBroadcastManager.sendBroadcast(intentLocal);   //发送本地广播
                break;
            default:
        }

    }

    /*广播接收器*/
    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //getSystemService()这是一个系统服务类，专门用于管理网络连接
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "已连接网络" + networkInfo.getExtraInfo(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "已断开网络", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*本地广播接收器*/
    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "本地广播测试", Toast.LENGTH_SHORT).show();
        }
    }
}
