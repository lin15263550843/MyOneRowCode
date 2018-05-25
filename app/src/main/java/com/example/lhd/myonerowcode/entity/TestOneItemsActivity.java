package com.example.lhd.myonerowcode.entity;

/**
 * Created by lhd on 2018/5/7.
 */

public class TestOneItemsActivity {
    private String id;
    private String name;
    private int imgId;
    private String content;

    public TestOneItemsActivity(String id, String name, int imgId, String content) {
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
