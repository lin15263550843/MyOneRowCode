package com.example.lhd.myonerowcode.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.Timer;
import java.util.TimerTask;

public class LongRunningService extends Service {
    private static final String TAG = "LongRunningService";

    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    /**
     * 先是在 onStartCommand() 方法中开启了一个子线程，这样就可以在这里执行具体的逻辑操作了。
     * 之所以要在于线程里执行逻辑操作，是因为逻辑操作也是需要耗时的，如果放在主线程里执行可能会对定时任务的准确性造成轻微的影响。
     * 创建线程之后的代码就是我们刚刚讲解的 Alarm 机制的用法了，
     * 先是获取到了 AlarmManager 的实例，
     * 然后定义任务的触发时间为一一小时后，
     * 再使用 PendingIntent 指定处理定时任务的服务为 LongRunningService，
     * 最后调用 set() 方法完成设定。
     * 这样我们就将一个长时间在后台定时运行的服务成功实现了。
     * <p>
     * 因为一旦启动了 LongRunningService，就会在 onStartCommand() 方法里设定一个定时任务，
     * 这样一小时后将会再次启动 LongRunningService，从而也就形成了一个永久的循环，保证 LongRunningService 的 onStartCommand() 方法可以每隔一小时就执行一次。
     * 最后，只需要在你想要启动定时服务的时候调用如下代码即可：
     * Intent intent = new Intent (context, LongRunningService. Class);
     * context.StartService (intent);
     * <p>
     * 另外需要注意的是，从 Android4.4 系统开始，Alarm 任务的触发时间将会变得不准确，有可能会延迟一段时间后任务才能得到执行。
     * 这并不是个 bug，而是系统在耗电性方面进行的优化。
     * 系统会自动检测目前有多少 Alarm 任务存在，然后将触发时间相近的几个任务放在一起执行，这就可以大幅度地减少 CPU 被唤醒的次数，从而有效延长电池的使用时间。
     * <p>
     * 当然，如果你要求 Alarm 任务的执行时间必须准确无误，Android 仍然提供了解决方案。
     * 使用 AlarmManager 的 setExact() 方法来替代 set() 方法，就基本上可以保证任务能够准时执行了。
     */


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 在这里执行具体的操作
//                LogUtil.d(TAG, "在这里执行具体的操作");
//                LogUtil.d(TAG, "一个小时执行一次，永久循环!!!");
//            }
//        }).start();
        new Thread(() -> {
            // 在这里执行具体的操作
            LogUtil.d(TAG, "在这里执行具体的操作");
            LogUtil.d(TAG, "一个小时执行一次，永久循环!!!");
        }).start();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int anHour = 60 * 60 * 1000;  // 一小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent intent1 = new Intent(this, LongRunningService.class);    // 一个小时执行一次，永久循环
        PendingIntent pi = PendingIntent.getService(this, 0, intent1, 0);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
//            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);   // 可以在精准的时间执行
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
