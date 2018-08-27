package com.example.lhd.myonerowcode;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.lhd.myonerowcode.adapter.CardRecyclerViewAdapter;
import com.example.lhd.myonerowcode.entity.TestTwoItemsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyInformationActivity extends AppCompatActivity {
    private static final String TAG = "MyInformationActivity";

    private SwipeRefreshLayout swipeRefresh;
    private DrawerLayout mDrawerLayout;
    //    PowerManager powerManager;
//    SensorManager sensorManager = null;
    private TestTwoItemsActivity[] ttias = {
            new TestTwoItemsActivity("0", "条目一", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目二", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目三", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目四", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目五", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目六", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目七", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目八", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目九", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目十", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目天", R.drawable.center, "内容内容内容"),
            new TestTwoItemsActivity("0", "条目目", R.drawable.center, "内容内容内容")
    };
    private List<TestTwoItemsActivity> cardItemList = new ArrayList<>();
    private CardRecyclerViewAdapter crvAdapter;

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {    // 添加导航栏按钮
//        return super.onCreatePanelMenu(featureId, menu);
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   // 处理各个按钮的点击事件
        switch (item.getItemId()) {
            case R.id.toolbar_backup:
                Toast.makeText(this, "你点击了备用", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_delete:
                Toast.makeText(this, "你点击了删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_settings:
//                Toast.makeText(this, "你点击了设置", Toast.LENGTH_SHORT).show();
                mDrawerLayout.openDrawer(GravityCompat.START);  // 打开侧边栏
                break;
            default:
                break;
        }
        return true;
//        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information_layout);
        Log.v(TAG, "onCreate: MyInformationActivity页面");

        Toolbar toolbar = (Toolbar) findViewById(R.id.title_toolbar_common);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.my_info_drawer);
        NavigationView navigationView = findViewById(R.id.nav_NavigationView);
        navigationView.setCheckedItem(R.id.nav_menu_item_1);    // 把条目一设置为默认选中状态
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_menu_item_5) {
                    mDrawerLayout.closeDrawers();   // 关闭侧边栏
                }
                return true;
            }
        });
//        ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            ab.setDisplayHomeAsUpEnabled(true);   // 显示导航栏的侧滑按钮
//            ab.setHomeAsUpIndicator(R.drawable.shezhi_on);    // 按钮图标
//        }

        Button myInformationButton1 = (Button) findViewById(R.id.my_information_button_1);
        myInformationButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("my_info_return_data", "我是从myInfo传递到main的数据，拉拉阿拉啦啦啦啦啦啦啦~");
                MyInformationActivity.this.setResult(RESULT_OK, intent);

                Snackbar.make(view, "确定返回吗", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        })
                        .show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.my_info_FloatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyInformationActivity.this, "点击了浮动按钮", Toast.LENGTH_SHORT).show();
            }
        });

        initCardList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_info_RecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);   // 显示几列
        recyclerView.setLayoutManager(gridLayoutManager);
        crvAdapter = new CardRecyclerViewAdapter(cardItemList);
        recyclerView.setAdapter(crvAdapter);

        // 下拉刷新
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.my_info_SwipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {   // 下拉刷新监听器
                refreshCardList();
            }
        });


//        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//        audioManager.setSpeakerphoneOn(false);
////        audioManager.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);STREAM_MUSIC
//
//        audioManager.setMode(AudioManager.STREAM_MUSIC);
//        audioManager.setSpeakerphoneOn(true);
//
//        powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, this.getClass().getName());
//        wakeLock.setReferenceCounted(false);
//        wakeLock.release();
//        wakeLock.acquire();
//
//        boolean isjuli;
//
//        isjuli = sensorManager.registerListener(new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                float[] dis = event.values;
//                if (0.0f == dis[0]) { // 靠近身体
////                    wakeLock.release(); // 熄灭屏幕
////                    switchToEarpiece(); // 切换到听筒
//                } else {
////                    wakeLock.acquire(); // 点亮屏幕
////                    switchToSpeaker(); // 切换到扬声器
//                }
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//            }
//        }, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);


    }

    // 下拉刷新执行的方法
    private void refreshCardList() {
        cardItemList.clear();
        crvAdapter.notifyDataSetChanged();  // 通知数据发生了变化
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // 将线程沉睡 2s ，因为本地刷新太快了，无法看到效果
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {  // 切回主线程
                    @Override
                    public void run() {
                        initCardList();
                        crvAdapter.notifyDataSetChanged();  // 通知数据发生了变化
                        swipeRefresh.setRefreshing(false);  // 传入 false 用于表示刷新事件结束，并隐藏刷新进度条。
                    }
                });

            }
        }).start();
    }

    private void initCardList() {
        cardItemList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();   // 随机数
            int index = random.nextInt(ttias.length);
            cardItemList.add(ttias[index]);
        }
    }
}
