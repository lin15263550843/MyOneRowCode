package com.example.lhd.myonerowcode.common;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lhd.myonerowcode.R;

/**
 * Created by lhd on 2018/5/4.
 * dialog提示框
 */

public class DialogActivity extends AppCompatActivity {

    private static final String TAG = "DialogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);

        final Intent intent = getIntent();
        String mainData1 = intent.getStringExtra("param1");
        String mainData2 = intent.getStringExtra("param2");
        Log.v(TAG, mainData1);
        Log.v(TAG, mainData2);

    }

    //自定义启动活动的方法
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, DialogActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }
}
