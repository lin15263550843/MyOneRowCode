package com.example.lhd.myonerowcode.entity;

import java.util.Date;

/**
 * Created by dhc on 2018/8/7.
 * Gson 解析 json 数据
 */

public class GSONEntity {

    private String code;
    private String name;
    private String startTime;
    private String endTime;
    private int price;
    private String count;

    public GSONEntity(String code, String name, String startTime, String endTime, int price, String count) {
        this.code = code;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {

        this.count = count;
    }

    public int getPrice() {

        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEndTime() {

        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {

        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
