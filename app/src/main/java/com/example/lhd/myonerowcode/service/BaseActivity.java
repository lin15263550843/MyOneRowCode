package com.example.lhd.myonerowcode.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.lhd.myonerowcode.LoginActivity;

/**
 * Created by lhd on 2018/6/5.
 */

public class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceiver recreate;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this); //删除活动
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActivityCollector.addActivity(this);    //添加活动
    }

    /**
     * 为是么不写在onCreate()和onDestroy()方法里来注册和取消广播？
     * 因为我们需要保证只有处于栈顶的活动才能接收到这条强制下线的广播，非栈顶的活动不应该也没有必要去接收这条广播，所以写在onResume()和onPause()里面能很好地解决这个问题，当一个活动失去栈顶位置时就会自动取消广播接收器注册。
     */
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.lhd.myonerowcode.FORCE_OFFLINE");
        recreate = new ForceOfflineReceiver();
        registerReceiver(recreate, intentFilter);   //注册广播
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (recreate != null) {
            unregisterReceiver(recreate);   //取消注册的广播
        }
        recreate = null;
    }

    /**
     * 广播接收器
     */
    private class ForceOfflineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("您只能退出登录");
            builder.setCancelable(false);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 这里面执行退出登录的所有操作
                    ActivityCollector.finishAll();     //销毁所有活动
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    context.startActivity(intent1);    //重新启动LoginActivity登录页面
                }
            });
            builder.show();

        }
    }
}
