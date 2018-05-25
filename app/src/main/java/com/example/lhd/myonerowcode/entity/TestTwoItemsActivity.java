package com.example.lhd.myonerowcode.entity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestTwoItemsActivity extends AppCompatActivity {

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_two_items_layout);
//    }
    private String id;
    private String name;
    private int imgId;
    private String content;

    public TestTwoItemsActivity(String id, String name, int imgId, String content) {
        this.id = id;
        this.name = name;
        this.imgId = imgId;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImgId() {

        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
