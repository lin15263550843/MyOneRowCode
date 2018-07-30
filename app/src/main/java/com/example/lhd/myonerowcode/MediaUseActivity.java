package com.example.lhd.myonerowcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MediaUseActivity extends AppCompatActivity implements View.OnClickListener {

    Button takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_use_layout);
        takePhoto = findViewById(R.id.media_use_take_photo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.media_use_take_photo:
                
                break;
            default:
                break;
        }
    }
}
