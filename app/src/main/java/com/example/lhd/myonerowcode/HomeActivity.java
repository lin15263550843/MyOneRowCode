package com.example.lhd.myonerowcode;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lhd.myonerowcode.fragment.HomeFragment;
import com.example.lhd.myonerowcode.fragment.MyListFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HomeActivity";
    Button buttonHomeFragment;
    Button buttonMyListFragment;
    Drawable shouyeOff;
    Drawable shouyeOn;
    Drawable shezhiOff;
    Drawable shezhiOn;

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

//        Button homeButton1 = (Button) findViewById(R.id.home_button_1);
//        homeButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.v(TAG, "点击了返回main的按钮");
//                Intent intent1 = new Intent();
//                intent1.putExtra("home_return_data", "我是home返回到main的数据，哈哈哈哈哈，略略略略略略略略~");
//                setResult(RESULT_OK, intent1);
////                finish();
//                HomeActivity.this.finish();
//
//            }
//        });
        shouyeOff = getResources().getDrawable(R.drawable.shouye_off);
        shouyeOn = getResources().getDrawable(R.drawable.shouye_on);
        shezhiOff = getResources().getDrawable(R.drawable.shezhi_off);
        shezhiOn = getResources().getDrawable(R.drawable.shezhi_on);

        shouyeOff.setBounds(0, 0, 50, 50);  //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        shouyeOn.setBounds(0, 0, 50, 50);   //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        shezhiOff.setBounds(0, 0, 50, 50);  //第一0是距左边距离，第二0是距上边距离，40分别是长宽
        shezhiOn.setBounds(0, 0, 50, 50);   //第一0是距左边距离，第二0是距上边距离，40分别是长宽

        buttonHomeFragment = (Button) findViewById(R.id.home_button_home_fragment);
        buttonMyListFragment = (Button) findViewById(R.id.home_button_mylist_fragment);
        buttonHomeFragment.setOnClickListener(this);
        buttonMyListFragment.setOnClickListener(this);
        buttonHomeFragment.setCompoundDrawables(null, shouyeOn, null, null);
        buttonMyListFragment.setCompoundDrawables(null, shezhiOff, null, null);
        replaceFragment(new HomeFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_button_home_fragment:
                buttonHomeFragment.setCompoundDrawables(null, shouyeOn, null, null);
                buttonMyListFragment.setCompoundDrawables(null, shezhiOff, null, null);
//                Toast.makeText(this, "首页", Toast.LENGTH_SHORT).show();
                replaceFragment(new HomeFragment());
                break;
            case R.id.home_button_mylist_fragment:
                buttonHomeFragment.setCompoundDrawables(null, shouyeOff, null, null);
                buttonMyListFragment.setCompoundDrawables(null, shezhiOn, null, null);
//                Toast.makeText(this, "列表", Toast.LENGTH_SHORT).show();
                replaceFragment(new MyListFragment());
                break;
            default:
                Toast.makeText(this, "default", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_dynamic_fragment, fragment);
        fragmentTransaction.commit();
    }
}
