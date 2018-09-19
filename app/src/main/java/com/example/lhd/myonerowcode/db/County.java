package com.example.lhd.myonerowcode.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dhc on 2018/9/3.
 * 县/区的属性
 */

public class County extends DataSupport {
    private int id;
    private String countyName;
    private int countyCode;
    private int cityId;
    private String weatherId;  // 天气id

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountyCode() {

        return countyCode;
    }

    public void setCountyCode(int countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {

        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getId() {

        return id;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
