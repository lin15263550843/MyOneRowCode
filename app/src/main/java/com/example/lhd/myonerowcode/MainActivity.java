package com.example.lhd.myonerowcode;

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
            case R.id.main_menu_item_1: Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();break;
            case R.id.main_menu_item_2: Toast.makeText(this, "移除", Toast.LENGTH_SHORT).show();break;
            case R.id.main_menu_item_3: Toast.makeText(this, "Item3", Toast.LENGTH_SHORT).show();break;
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

        Button mainButton1 = findViewById(R.id.main_button_1);
        mainButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "我是弹窗，你咬我呀~", Toast.LENGTH_LONG).show();
            }
        });


    }

}
