package com.example.lhd.myonerowcode;

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

    }
}
