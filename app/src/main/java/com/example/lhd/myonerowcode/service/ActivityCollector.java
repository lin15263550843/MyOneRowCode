package com.example.lhd.myonerowcode.service;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhd on 2018/6/5.
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    /*添加一个活动*/
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /*删除一个活动*/
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /*删除所有活动，一般用于退出程序时调用*/
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
