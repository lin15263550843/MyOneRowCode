package com.example.lhd.myonerowcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.lhd.myonerowcode.adapter.TestTwoAdapter;
import com.example.lhd.myonerowcode.entity.TestTwoItemsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewActivity extends AppCompatActivity {
    private List<TestTwoItemsActivity> testTwoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        initTestOneList();  //初始化数组
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_recycler_view);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.INVALID_OFFSET);     //设置布局的排列方向，默认是纵向排列的，LinearLayoutManager.HORIZONTAL表示调整为横向滚动
//        recyclerView.setLayoutManager(linearLayoutManager);     //指定RecyclerView的布局方式为LinearLayoutManager

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
//        recyclerView.setLayoutManager(gridLayoutManager);     //指定RecyclerView的布局方式为GridLayoutManager

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);     //指定RecyclerView的布局方式为StaggeredGridLayoutManager

        TestTwoAdapter testTwoAdapter = new TestTwoAdapter(testTwoList);
        recyclerView.setAdapter(testTwoAdapter);
    }

    private void initTestOneList() {
        for (int i = 0; i < 5; i++) {
            TestTwoItemsActivity testOneItem1 = new TestTwoItemsActivity("1111111", "1111111", R.drawable.center, getRandomLengthName("我是111略略略"));
            testTwoList.add(testOneItem1);
            TestTwoItemsActivity testOneItem2 = new TestTwoItemsActivity("2222222", "2222222", R.drawable.center, getRandomLengthName("我是222略略略"));
            testTwoList.add(testOneItem2);
            TestTwoItemsActivity testOneItem3 = new TestTwoItemsActivity("3333333", "3333333", R.drawable.center, getRandomLengthName("我是333略略略"));
            testTwoList.add(testOneItem3);
            TestTwoItemsActivity testOneItem4 = new TestTwoItemsActivity("4444444", "4444444", R.drawable.center, getRandomLengthName("我是444略略略"));
            testTwoList.add(testOneItem4);
            TestTwoItemsActivity testOneItem5 = new TestTwoItemsActivity("5555555", "5555555", R.drawable.center, getRandomLengthName("我是555略略略"));
            testTwoList.add(testOneItem5);
        }
    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(35) + 1;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(name);
        }
        return stringBuilder.toString();
    }
}
