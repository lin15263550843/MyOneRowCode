package com.example.lhd.myonerowcode.entity;

import java.io.Serializable;

/**
 * Created by dhc on 2018/8/30.
 * <p>
 * 实现 Serializable 接口，这样所有的 PersonSerializable 对象就都是可序列化的了，将对象序列化后传递。
 */

public class PersonSerializable implements Serializable {

    private String name;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
