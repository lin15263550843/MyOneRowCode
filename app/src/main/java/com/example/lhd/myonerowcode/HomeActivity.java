package com.example.lhd.myonerowcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent1 = new Intent();
        intent1.putExtra("home_return_data", "我是home返回到main的数据，哈哈哈哈哈，略略略略略略略略~");
        setResult(RESULT_OK, intent1);
//        finish();
        HomeActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Log.v(TAG, "onCreate: HomeActivity页面");
        final Intent intent = getIntent();
        String mainData = intent.getStringExtra("extra_data_main");
        Log.v(TAG, mainData);

        Button homeButton1 = (Button) findViewById(R.id.home_button_1);
        homeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "点击了返回main的按钮");
                Intent intent1 = new Intent();
                intent1.putExtra("home_return_data", "我是home返回到main的数据，哈哈哈哈哈，略略略略略略略略~");
                setResult(RESULT_OK, intent1);
//                finish();
                HomeActivity.this.finish();

            }
        });
    }
}
