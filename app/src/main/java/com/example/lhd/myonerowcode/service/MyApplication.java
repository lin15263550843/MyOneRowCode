package com.example.lhd.myonerowcode.service;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;
import org.litepal.util.Const;

/**
 * Created by dhc on 2018/8/29.
 * 自定义 Application
 * <p>
 * Android 提供了一个 Application 类，每当应用程序启动的时候，系统就会自动将这个类进行初始化。
 * 而我们可以定制一个自己的 Application 类，以便于管理程序内一些全局的状态信息，比如说全局 Context。
 * <p>
 * 调用 MyApplication.getContext() 方法获取 Context 对象。
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePalApplication.initialize(this);    // 初始化 LitePal

    }

    // 返回全局的 Context
    public static Context getContext() {
        return context;
    }
}
