package com.example.lhd.myonerowcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyInformationActivity extends AppCompatActivity {
    private static final String TAG = "MyInformationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information_layout);
        Log.v(TAG, "onCreate: MyInformationActivity页面");

        Button myInformationButton1 = (Button) findViewById(R.id.my_information_button_1);
        myInformationButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("my_info_return_data", "我是从myInfo传递到main的数据，拉拉阿拉啦啦啦啦啦啦啦~");
                MyInformationActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
