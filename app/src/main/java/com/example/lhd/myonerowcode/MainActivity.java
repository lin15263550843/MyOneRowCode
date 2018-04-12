package com.example.lhd.myonerowcode;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.main_menu_item_1:
                Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_menu_item_2:
                Toast.makeText(this, "移除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_menu_item_3:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Log.i("MainActivityIII", "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        Log.e("MainActivityEEE", "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        Log.v("MainActivityVVV", "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
        Log.d("MainActivityDDD", "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        Log.w("MainActivityWWW", "WWWWWwWWWWWwWWWWWwWWWWWwWWWWWwWWWWWwWWW");

        Button mainButton1 = (Button) findViewById(R.id.main_button_1);
        Button mainButton2 = (Button) findViewById(R.id.main_button_2);
        Button mainButton3 = (Button) findViewById(R.id.main_button_3);
        Button mainButton4 = (Button) findViewById(R.id.main_button_4);
        Button mainButton5 = (Button) findViewById(R.id.main_button_5);
        mainButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "我是从main传到home的数据，哈哈哈哈哈，你咬我呀~";
//                Toast.makeText(MainActivity.this, "我是弹窗，你咬我呀~", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);     //显式 intent
                intent.putExtra("extra_data_main", data);
                startActivityForResult(intent, 11111);
//                startActivity(intent);    //不能同时启动
            }
        });
        mainButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MyInformationActivity.class);     //显式 intent
                Intent intent = new Intent("com.example.lhd.myonerowcode.MyInformationActivity");     //隐式 intent
                intent.addCategory("com.example.lhd.myonerowcode.HomeActivity");
                startActivity(intent);
            }
        });
        mainButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
        mainButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:17606229741"));
                startActivity(intent);
            }
        });
        mainButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11111:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("home_return_data");
                    Log.v(TAG, returnedData);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("my_info_return_data");
                    Log.v(TAG, returnedData);
                }
                break;

            default:
        }
    }
}
