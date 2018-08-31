package com.example.lhd.myonerowcode.service;

import android.os.Build;
import android.util.Log;

/**
 * Created by dhc on 2018/8/30.
 * 日志工具类
 * 使用 LogUtil.v(TAG, "信息"); 打印log。
 * 我们只需要修改 level 的值就可以控制日志的打印行为了。
 * NOTHING 标识不显示所有日志。
 */

public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static int level = VERBOSE;

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Log.v(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.v(tag, msg);
        }
    }

}
