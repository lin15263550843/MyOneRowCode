package com.example.lhd.myonerowcode.androidQYZ;

import android.view.View;

/**
 * Created by dhc on 2018/10/26.
 * <p>
 * 接口对象，实现回调机制
 * 通过银蛇的接口对象调用接口中的方法
 * 而不用去考虑如何实现，具体的实现由调用者去创建
 */

public interface TopBarClickListener {
    // 左按钮点击事件
    void leftClick(View v);

    // 右按钮点击事件
    void rightClick(View v);
}
