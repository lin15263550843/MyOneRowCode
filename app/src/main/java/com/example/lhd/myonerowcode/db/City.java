package com.example.lhd.myonerowcode.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dhc on 2018/9/3.
 * 市的属性
 */

public class City extends DataSupport {
    private int id;
    private String cityName;
    private int ctyCode;
    private int provinceId;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCtyCode() {

        return ctyCode;
    }

    public void setCtyCode(int ctyCode) {
        this.ctyCode = ctyCode;
    }

    public String getCityName() {

        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
