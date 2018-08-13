package com.example.lhd.myonerowcode.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lhd.myonerowcode.R;

public class myTestServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_test_service_layout);

        Button openService = findViewById(R.id.my_test_service_open);
        Button closeService = findViewById(R.id.my_test_service_close);

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
    }
}
