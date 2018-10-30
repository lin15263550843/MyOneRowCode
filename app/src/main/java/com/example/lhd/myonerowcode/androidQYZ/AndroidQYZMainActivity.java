package com.example.lhd.myonerowcode.androidQYZ;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lhd.myonerowcode.R;

public class AndroidQYZMainActivity extends AppCompatActivity {

    CustomTopBar customTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_qyz__main_layout);

        customTopBar = findViewById(R.id.qyz_main_customTopBar);
//        customTopBar.setOnClickListener(new CustomTopBar.setOnTopBarClickListener);
        customTopBar.setOnTopBarClickListener(new TopBarClickListener() {
            @Override
            public void leftClick(View v) {
                // left 按钮点击事件
                finish();
            }

            @Override
            public void rightClick(View v) {
                // right 按钮点击事件
                Toast.makeText(AndroidQYZMainActivity.this, "你点击了菜单按钮", Toast.LENGTH_SHORT).show();
            }
        });

        customTopBar.setButtonVisible(0, true); // 显示左侧按钮
        customTopBar.setButtonVisible(1, true); // 显示右侧按钮
    }


}
