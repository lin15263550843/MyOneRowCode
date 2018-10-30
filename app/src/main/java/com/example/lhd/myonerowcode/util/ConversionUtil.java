package com.example.lhd.myonerowcode.util;

import android.util.DisplayMetrics;

import com.example.lhd.myonerowcode.service.BaseActivity;

/**
 * DisplayMetrics是安卓提供的封装像素密度以及大小信息的类。前两行代码是对他的初始化。当获取了当前手机的手机密度信息后根据下面的公式。
 * px = dp * (dpi / 160)
 * <p>
 * Created by dhc on 2018/10/29.
 */

public class ConversionUtil extends BaseActivity {

    private int getPixelsFromDp(int size) {

        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

    }
}
