package com.example.lhd.myonerowcode;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhd.myonerowcode.broadcast.BroadcastTestActivity;
import com.example.lhd.myonerowcode.common.DialogActivity;
import com.example.lhd.myonerowcode.common.OpenWebVIewActivity;
import com.example.lhd.myonerowcode.contentResolver.ContactPeopleListActivity;
import com.example.lhd.myonerowcode.coolWeather.CoolWeatherMainActivity;
import com.example.lhd.myonerowcode.dataLocalStorage.DataLocalStorageActivity;
import com.example.lhd.myonerowcode.entity.PersonParcelable;
import com.example.lhd.myonerowcode.entity.PersonSerializable;
import com.example.lhd.myonerowcode.service.BaseActivity;
import com.example.lhd.myonerowcode.service.LongRunningService;
import com.example.lhd.myonerowcode.service.myTestServiceActivity;

public class MainActivity extends BaseActivity {

    public final static int REQUEST_READ_PHONE_STATE = 1;   //自定义的申请权限结果的返回参数  获取版本信息的权限
    public final static int REQUEST_READ_PHONE_STATE_2 = 2;   //自定义的申请权限结果的返回参数  获取版本信息的权限

    TelephonyManager telephonyManager;  //存放设备信息
    String IMEI = null;     //序列号IMEI
    String android_id = null;     //android_id
    String getLine1Number = null;     //手机号码
    String getSimSerialNumber = null;     //手机卡序列号
    String getSubscriberId = null;     //IMSI
    String getSimCountryIso = null;     //手机卡国家
    String getSimOperatorName = null;     //运营商名字
    String getNetworkOperatorName = null;     //返回移动网络运营商的名字(SPN)
    int getPhoneType;     //返回移动网络运营商的名字(SPN)
    String getMacAddress = null;     //mac地址
    String getRadioVersion = null;     //无线电固件版本号，通常是不可用的
    String RELEASE = null;     //获取系统版本字符串。如4.1.2 或2.2 或2.3等
    int SDK_INT;     //系统的API级别 一般使用下面大的SDK_INT 来查看
    private static final String TAG = "MainActivity";

    @Override   //添加右上角菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override   //用于保存页面的缓存数据
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "我是main页面的缓存数据";
        outState.putString("main_data_key", tempData);
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


        //隐藏掉系统自带的标题
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageButton titleImageButtonBack = (ImageButton) findViewById(R.id.title_ImageButton_back);
        final ImageButton titleImageButton1 = (ImageButton) findViewById(R.id.title_ImageButton_1);
        TextView titleTextView = (TextView) findViewById(R.id.title_textview);
        titleTextView.setText("main页面");
        titleImageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "onClick: 点击了main页面的返回键");
//                MainActivity.this.finish();
            }
        });


        //获取页面缓存数据
        if (savedInstanceState != null) {
            String tempData = savedInstanceState.getString("main_data_key");
            Log.v(TAG, "onCreate: " + tempData);
        }

        Button openCoolWeather = (Button) findViewById(R.id.main_button_cool_weather);
        Button mainButton1 = (Button) findViewById(R.id.main_button_1);
        Button mainButton2 = (Button) findViewById(R.id.main_button_2);
        Button mainButton3 = (Button) findViewById(R.id.main_button_3);
        Button mainButton4 = (Button) findViewById(R.id.main_button_4);
        Button mainButton5 = (Button) findViewById(R.id.main_button_5);
        Button mainButton6 = (Button) findViewById(R.id.main_button_6);
        Button mainButton7 = (Button) findViewById(R.id.main_button_7);
//        EditText mainEditText1 = (EditText) findViewById(R.id.main_editText_1);
        Button mainButton8 = (Button) findViewById(R.id.main_button_8);
        Button mainButton9 = (Button) findViewById(R.id.main_button_9);
        Button mainButton10 = (Button) findViewById(R.id.main_button_10);
        Button mainButton11 = (Button) findViewById(R.id.main_button_11);
        Button mainButton12 = (Button) findViewById(R.id.main_button_12);
        Button mainButton13 = (Button) findViewById(R.id.main_button_13);
        Button mainButton14 = (Button) findViewById(R.id.main_button_14);
        Button storageButton15 = (Button) findViewById(R.id.main_button_storage_15);
        Button contentResolverButton16 = (Button) findViewById(R.id.content_resolver_16);
        Button notificationBar17 = (Button) findViewById(R.id.main_notification_bar_17);
        Button mainMedia18 = (Button) findViewById(R.id.main_media_18);
        Button webView19 = (Button) findViewById(R.id.main_web_view_19);
        Button httpURLConnection20 = (Button) findViewById(R.id.main_http_url_connection_20);
        Button myTestService21 = (Button) findViewById(R.id.main_my_test_service_21);
        Button baiduLocate22 = (Button) findViewById(R.id.main_baidu_locate_22);
        Button collapsingToolbar23 = (Button) findViewById(R.id.main_collapsing_toolbar_23);
        Button serializableAndParcelable24 = (Button) findViewById(R.id.main_serializable_and_parcelable_24);
        Button alarm25 = (Button) findViewById(R.id.main_alarm_25);

        final ProgressBar mainProgressBar1 = (ProgressBar) findViewById(R.id.main_progressbar_1);

        // 打开酷欧天气
        openCoolWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoolWeatherMainActivity.class);
                startActivity(intent);
            }
        });
        mainButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "我是从main传到home的数据，哈哈哈哈哈，你咬我呀~";
//                Toast.makeText(MainActivity.this, "我是弹窗，你咬我呀~", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);     //显式 intent
                intent.putExtra("extra_data_main", data);
                startActivityForResult(intent, 11111);
//                startActivity(intent);    //加上会同时同时启动俩
            }
        });
        mainButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MyInformationActivity.class);     //显式 intent
                Intent intent = new Intent("com.example.lhd.myonerowcode.MyInformationActivity");     //隐式 intent
                intent.addCategory("com.example.lhd.myonerowcode.MyInformationActivity");
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
//                Intent intent = new Intent(Intent.ACTION_DIAL);   //ACTION_DIAL为打开拨号界面
                //运行时权限检查
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_READ_PHONE_STATE_2);
                } else {
                    goCall();
                }
            }
        });
        mainButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mainButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = "我是从main传到Dialog的数据11111";
                String data2 = "我是从main传到Dialog的数据22222";
                DialogActivity.actionStart(MainActivity.this, data1, data2);
//                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
//                startActivity(intent);
            }
        });
        mainButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//                progressDialog.setTitle("我是ProgressDialog弹窗");
//                progressDialog.setMessage("我是ProgressDialog弹窗的message信息，略略略");
//                progressDialog.setCancelable(true);
//                progressDialog.show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("我是AlertDialog弹窗");
                dialog.setMessage("我是AlertDialog弹窗的message信息，略略略");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v(TAG, "onClick: 我是AlertDialog弹窗的确定" + i + dialogInterface);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v(TAG, "onClick: 我是AlertDialog弹窗的取消" + i + dialogInterface);
                    }
                });
                dialog.show();

            }
        });
        mainButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mainProgressBar1.getVisibility() == View.GONE) {
                    mainProgressBar1.setVisibility(View.VISIBLE);
                } else {
                    mainProgressBar1.setVisibility(View.GONE);
                }
            }
        });
        mainButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);     //显式 intent
                startActivity(intent);
            }
        });
        mainButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);     //显式 intent
                startActivity(intent);
            }
        });
        mainButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MessageConversationListActivity.class);     //显式 intent
                startActivity(intent);
            }
        });
        mainButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BroadcastTestActivity.class);     //显式 intent
                startActivity(intent);
            }
        });
        mainButton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.lhd.myonerowcode.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
        mainButton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telephonyManager = (TelephonyManager) MainActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
                int permissionCheck = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) { //如果应用之前请求过此权限但用户拒绝了请求，将返回 true。
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    // return;

                    //申请权限，字符串数组内是一个或多个要申请的权限，REQUEST_READ_PHONE_STATE（1）是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
//                        //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
//                    } else {
//                        //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
//                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
//                    }
                } else {
                    getDeviceInformation();
                }
            }
        });
        storageButton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataLocalStorageActivity.class);
                startActivity(intent);
            }
        });
        contentResolverButton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactPeopleListActivity.class);
                startActivity(intent);
            }
        });
        notificationBar17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.lhd.myonerowcode.MyInformationActivity");     //隐式 intent
                intent.addCategory("com.example.lhd.myonerowcode.MyInformationActivity");

                // 可以把 PendingIntent 简单的理解为延迟执行的 Intent ； 提供了 getActivity()  getService()  getBroadcast() 等方法，接收参数相同
                // 四个参数，第一个：Context ；第二个：一般用不到，通常传入0；第三个：Intent 对象；第四个：用于确定PendingIntent行为，有 FLAG_ONE_SHOT 、FLAG_NO_CREATE 、FLAG_CANCEL_CURRENT 、FLAG_UPDATE_CURRENT 这4种值可选，通常传0就可以了
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                // 通知栏通知
                // NotificationManager用来对通知进行管理   ;   getSystemService()方法接收一个字符串参数，用于确定获取系统的哪个服务
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // 接下来需要使用一个 Builder 构造器来创建 Notification 对象，但问题在于，几乎 Android 系统的每一个版本都会对通知这部分功能进行或多或少的修改，API 不稳定性问题在通知上面突显得尤其严重。
                // 那么该如何解决这个问题呢？其实解决方案我们之前已经见过好几回了，就是使用 support 库中提供的兼容 API。
                // support-v4 库中提供了一个 NotificationCompat 类，使用这个类的构造器来创建 Notification 对象，就可以保证我们的程序在所有 Android 系统版本上都能正常工作了，代码如下所示：
                // Notification notification = new NotificationCompat.Builder(context).build();
                // 当然，上述代码只是创建了一个空的 Notification 对象，并没有什么实际作用，我们可以在最终的 build（）方法之前连缀任意多的设置方法来创建一个丰富的 Notification 对象，先来看一些最基本的设置：
                // 一共调用了 5 个设置方法，下拉系统状态栏就可以看到这部分内容
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("第一行代码通知栏标题")              // setContentTitle() 方法用于指定通知的标题内容
                        .setContentText("我是第一行代码通知栏的内容，略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~")  // setContentText() 方法用于指定通知的正文内容
                        .setWhen(System.currentTimeMillis())               // setWhen() 方法用于指定通知被创建的时间，以毫秒为单位，当下拉系统状态栏时，这里指定的时间会显示在相应的通知上
                        .setSmallIcon(R.mipmap.ic_launcher)                // setSmallIcon() 方法用于设置通知的小图标，注意只能使用纯 alpha 图层的图片进行设置，小图标会显示在系统状态栏上
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)) // setLargeIcon（）方法用于设置通知的大图标，当下拉系统状态栏时，就可以看到设置的大图标了
//                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Bell.ogg"))) //发出通知的时候播放音频
//                        .setVibrate(new long[]{0, 1000, 1000, 1000, 1000, 1000})    //手机振动，隔一秒振动一下
                        .setLights(Color.GREEN, 1000, 1000)   // 手机LED灯效果；三个参数：颜色   亮起时长   暗去的时长
//                        .setDefaults(NotificationCompat.DEFAULT_ALL)     // 通知的默认效果，它会根据当前手机的环境来决定播放什么铃声，以及如何振动
                        .setContentIntent(pendingIntent)                   // 通知栏的点击动作
                        .setAutoCancel(true)                               // 点击通知栏的消息后关闭消息，隐式调用
                        //显式过长的内容，不会省略号
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("我是第一行代码通知栏的内容，略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~略略略~"))
                        //显示一张图片
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.message_lefts)))
                        .setPriority(NotificationCompat.PRIORITY_MAX)   //消息的重要程度: PRIORITY_MIN < PRIORITY_LOW < PRIORITY_DEFAULT < PRIORITY_HIGH < PRIORITY_MAX
                        .build();
                // 以上工作都完成之后，只需要调用 NotificationManager 的 notify（）方法就可以让通知显示出来了。
                // notify（）方法接收两个参数，第一个参数是 id，要保证为每个通知所指定的 id 都是不同的。第二个参数则是 Notification 对象，这里直接将我们刚刚创建好的 Notification 对象传人即可。因此，显示一个通知就可以写成：
                assert notificationManager != null;
                notificationManager.notify(1, notification);

//                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                manager.cancel(1);  //显式调用cancel()关闭通知栏消息，参数为消息id
            }
        });
        mainMedia18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaUseActivity.class);
                startActivity(intent);
            }
        });
        webView19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OpenWebVIewActivity.class);
                startActivity(intent);
            }
        });
        httpURLConnection20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HttpURLConnectionActivity.class);
                startActivity(intent);
            }
        });
        myTestService21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, myTestServiceActivity.class);
                startActivity(intent);
            }
        });
        baiduLocate22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BaiduLocateTestActivity.class);
                startActivity(intent);
            }
        });
        collapsingToolbar23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollapsingToolbarActivity.class);
                startActivity(intent);
            }
        });
        serializableAndParcelable24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersonSerializable ps = new PersonSerializable();
                ps.setName("小明");
                ps.setAge(18);

                PersonParcelable pp = new PersonParcelable();
                pp.setName("小刚");
                pp.setAge(8);

                Intent intent = new Intent(MainActivity.this, SerializableAndParcelableActivity.class);
                intent.putExtra("PersonSerializable_data", ps);
                intent.putExtra("PersonParcelable_data", pp);
                startActivity(intent);
            }
        });
//        alarm25.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LongRunningService.class);
//                startService(intent);
//            }
//        });
        alarm25.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LongRunningService.class);
            startService(intent);
        });
    }

    //有权限时，获取设备信息
    public void getDeviceInformation() {

        int permissionCheck = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return;
            //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);

        } else {
            IMEI = telephonyManager.getDeviceId();
            getLine1Number = telephonyManager.getLine1Number();     //手机号码
            getSimSerialNumber = telephonyManager.getSimSerialNumber();     //手机卡序列号
            getSubscriberId = telephonyManager.getSubscriberId();     //IMSI
            getSimCountryIso = telephonyManager.getSimCountryIso();     //手机卡国家
            getSimOperatorName = telephonyManager.getSimOperatorName();     //运营商名字
            getNetworkOperatorName = telephonyManager.getNetworkOperatorName();     //返回移动网络运营商的名字(SPN)
            getPhoneType = telephonyManager.getPhoneType();     //返回移动网络运营商的名字(SPN)

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("提示");
            builder.setMessage("IMEI: " + IMEI);
            builder.setCancelable(false);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //  android_id = Settings.Secure.getString(MainActivity.this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                    android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);   //android_id
                    WifiManager wifi = (WifiManager) MainActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo info = wifi.getConnectionInfo();
                    getMacAddress = info.getMacAddress();    //mac地址
                    RELEASE = Build.VERSION.RELEASE;    //获取系统版本字符串。如4.1.2 或2.2 或2.3等
                    SDK_INT = Build.VERSION.SDK_INT;    //SDK	系统的API级别 一般使用下面大的SDK_INT 来查看
                    getRadioVersion = Build.getRadioVersion();//	无线电固件版本号，通常是不可用的

                    Log.v(TAG, "IMEI: " + IMEI);
                    Log.v(TAG, "getLine1Number: " + getLine1Number);
                    Log.v(TAG, "getSimSerialNumber: " + getSimSerialNumber);
                    Log.v(TAG, "getSubscriberId: " + getSubscriberId);
                    Log.v(TAG, "getSimCountryIso: " + getSimCountryIso);
                    Log.v(TAG, "getSimOperatorName: " + getSimOperatorName);
                    Log.v(TAG, "getNetworkOperatorName: " + getNetworkOperatorName);
                    Log.v(TAG, "getPhoneType: " + getPhoneType);
                    Log.v(TAG, "RELEASE: " + RELEASE);
                    Log.v(TAG, "getMacAddress: " + getMacAddress);
                    Log.v(TAG, "android_id: " + android_id);
                    Log.v(TAG, "Build.VERSION.SDK_INT: " + SDK_INT);
                    Log.v(TAG, "Build.VERSION.RELEASE: " + RELEASE);
                    Log.v(TAG, "Build.getRadioVersion: " + getRadioVersion);
                }
            });
            builder.show();
        }
    }

    public void goCall() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_READ_PHONE_STATE_2);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);    //ACTION_CALL为打电话动作
            intent.setData(Uri.parse("tel:17606229741"));
            startActivity(intent);
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     * <p>
     * 在onRequestPermissionsResult可以得知申请结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_READ_PHONE_STATE) {
//            for (int i = 0; i < permissions.length; i++) {
//                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                    Log.v(TAG, "onRequestPermissionsResult: 权限" + permissions[i] + "申请成功");
////                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
//                    getDeviceInformation();
//                } else {
////                    Log.v(TAG, "onRequestPermissionsResult: 权限" + permissions[i] + "申请失败");
//                    Toast.makeText(this, "" + "权限申请失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.v(TAG, "onRequestPermissionsResult: 权限" + permissions[i] + "申请成功");
//                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                        getDeviceInformation();
                    } else {
//                    Log.v(TAG, "onRequestPermissionsResult: 权限" + permissions[i] + "申请失败");
                        Toast.makeText(this, "" + "权限申请失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_READ_PHONE_STATE_2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "onRequestPermissionsResult: 权限" + permissions[0] + "申请成功");
                    goCall();
                } else {
                    Toast.makeText(this, "" + "权限申请失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart: 我是：onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onStart: 我是：onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onStart: 我是：onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStart: 我是：onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onStart: 我是：onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "onStart: 我是：onRestart");
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
