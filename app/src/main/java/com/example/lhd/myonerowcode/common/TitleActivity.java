package com.example.lhd.myonerowcode.common;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lhd.myonerowcode.R;

/**
 * Created by lhd on 2018/5/4.
 * title导航栏
 */

public class TitleActivity extends LinearLayout {
    private static final String TAG = "TitleActivity";

    public TitleActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_layout, this);

        ImageButton titleButtonBack = (ImageButton) findViewById(R.id.title_ImageButton_back);
        ImageButton titleButton1 = (ImageButton) findViewById(R.id.title_ImageButton_1);

        titleButtonBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((Activity) getContext()).finish();
                Log.v(TAG, "onClick: 你点击了返回按钮");
                Toast.makeText(getContext(), "你点击了返回按钮", Toast.LENGTH_SHORT).show();
            }
        });
        titleButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((Activity) getContext()).finish();
                Toast.makeText(getContext(), "你点击了我，想干嘛", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
