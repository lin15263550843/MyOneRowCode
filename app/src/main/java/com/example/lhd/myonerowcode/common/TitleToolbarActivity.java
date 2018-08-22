package com.example.lhd.myonerowcode.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lhd.myonerowcode.R;

public class TitleToolbarActivity extends AppCompatActivity {
    private static final String TAG = "TitleToolbarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_toolbar_layout);
        Log.d(TAG, "onCreate: my name is TitleToolbarActivity");

    }
}
