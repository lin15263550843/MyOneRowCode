package com.example.lhd.myonerowcode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lhd.myonerowcode.entity.PersonParcelable;
import com.example.lhd.myonerowcode.entity.PersonSerializable;
import com.example.lhd.myonerowcode.service.LogUtil;

public class SerializableAndParcelableActivity extends AppCompatActivity {
    private static final String TAG = "SAPActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serializable_and_parcelable_layout);

        // 获取 main 传递过来的数据
        PersonSerializable ps = (PersonSerializable) getIntent().getSerializableExtra("PersonSerializable_data");
        PersonParcelable pp = (PersonParcelable) getIntent().getParcelableExtra("PersonParcelable_data");
        LogUtil.d(TAG, "onCreate: 姓名：" + ps.getName());
        LogUtil.d(TAG, "onCreate: 年龄：" + ps.getAge());
        LogUtil.d(TAG, "onCreate: 姓名：" + pp.getName());
        LogUtil.d(TAG, "onCreate: 年龄：" + pp.getAge());

        /*
        * 首先我们来看一下 Alarm 机制的用法吧，其实并不复杂，主要就是借助了 AlarmManager 类来实现的。
        * 这个类和 NotificationManager 有点类似，都是通过调用 Context 的 getSystem- Service（）方法来获取实例的，只是这里需要传人的参数是 Context.ALARM_SERVICE。
        *
        * 因此，获取一个 AlarmManager 的实例就可以写成：AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        *
        * 接下来调用 AlarmManager 的 set() 方法就可以设置一个定时任务了，比如说想要设定一个任务在 10 秒钟后执行，就可以写成：
        * long triggerAtTime = SystemClock.elapsedRealtime() + 10 * 1000;
        * alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        *
        *  使用 SystemClock.elapsedRealtime() 方法可以获取到系统开机至今所经历时间的毫秒数，使用 System.CurrentTimcMillis() 方法可以获取到 1970 年 1 月 1 日 0 点至今所经历时间的毫秒数。
        *
        * 上面的两行代码你不一定能看得明白，因为 set() 方法中需要传人的 3 个参数稍微有点复杂，下面我们就来仔细地分析一下。
        *
        * 第一个参数是一个整型参数，用于指定 AlarmManager 的工作类型，有 4 种值可选，分别是 ELAPSED_REALTIME、ELAPSED_REALTIME._WAKEUP、RTC 和 RTC_WAKEUP。
        * 其中 ELAPSED_REALTIME 表示让定时任务的触发时间从系统开机开始算起，但不会唤醒 CPU。
        * ELAPSED_REALTIME_WAKEUP 同样表示让定时任务的触发时间从系统开机开始算起，但会唤醒 CPU。
        * RTC 表示让定时任务的触发时间从 1970 年 1 月 1 日 0 点开始算起，但不会唤醒 CPU。
        * RTC_WAKEUP 同样表示让定时任务的触发时间从 1970 年 1 月 1 日 0 点开始算起，但会唤醒 CPU。
        *
        * 第二个参数，这个参数就好理解多了，就是定时任务触发的时间，以毫秒为单位。
        * 如果第一个参数使用的是 ELAPSED_REALTIME 或 ELAPSED_REALTIME_WAKEUP，则这里传人开机至今的时间再加上延迟执行的时间。
        * 如果第一个参数使用的是 RTC 或 RTC_WAKEUP，则这里传人 1970 年 1 月 1 日 0 点至今的时间再加上延迟执行的时间。
        *
        * 第三个参数是一个 PendingIntent，对于它你应该已经不会陌生了吧。这里我们一~般会调用 getService() 方法或者 getBroadcast() 方法来获取一个能够执行服务或广播的 Pending- Intent。
        * 这样当定时任务被触发的时候，服务的 onStartCommand() 方法或广播接收器的 onReceive() 方法就可以得到执行。
        *
        * 了解了 set() 方法的每个参数之后，你应该能想到，设定-一个任务在 10 秒钟后执行也可以写成：
        * long triggerAtTime = System.currentTimeMillis() + 10 + 1000;
        * alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pendingIntent);
        * */
        Intent intent = new Intent(this, MainActivity.class);   // 10s 之后跳到 main 页面
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // 创建定时任务：Alarm 机制
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + 10 * 1000;
//        long triggerAtTime = System.currentTimeMillis() + 10 + 1000;
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pendingIntent);
        }
    }
}
