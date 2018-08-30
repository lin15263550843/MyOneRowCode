package com.example.lhd.myonerowcode.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by dhc on 2018/8/30.
 * <p>
 * 实现 Parcelable 接口，Parcelable 方式的实现原理是将一个完整的对象进行分解，而分解后的每一部分都是 Intent 所支持的数据类型，这样也就实现传递对象的功能了。
 */

public class PersonParcelable implements Parcelable {

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

//    protected PersonParcelable(Parcel in) {
//        name = in.readString();
//        age = in.readInt();
//    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name); // 写出 name
        dest.writeInt(age); // 写出 age
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonParcelable> CREATOR = new Creator<PersonParcelable>() {
        @Override
        public PersonParcelable createFromParcel(Parcel in) {
//            return new PersonParcelable(in);
            PersonParcelable pp = new PersonParcelable();
            pp.name = in.readString(); // 读取 name
            pp.age = in.readInt(); // 读取 age
            return pp;
        }

        @Override
        public PersonParcelable[] newArray(int size) {
            return new PersonParcelable[size];
        }
    };
}
