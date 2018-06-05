package com.example.lhd.myonerowcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lhd.myonerowcode.adapter.TestOneAdapter;
import com.example.lhd.myonerowcode.entity.TestOneItemsActivity;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    //定义list的数组
//    protected String[] dataList = {"11111", "22222", "33333", "44444", "55555", "66666", "77777", "88888", "99999", "00000", "1111111", "1222222", "1333333"};
    private List<TestOneItemsActivity> testOneList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListViewActivity.this, android.R.layout.simple_list_item_1, dataList);
//        ListView listView = (ListView) findViewById(R.id.list_view_list_view);
//        listView.setAdapter(adapter);

        initTestOneList();  //初始化TestOneList数组
        TestOneAdapter testOneAdapter = new TestOneAdapter(ListViewActivity.this, R.layout.test_one_items_layout, testOneList);
        ListView listViewListView = findViewById(R.id.list_view_list_view);
        listViewListView.setAdapter(testOneAdapter);
        listViewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TestOneItemsActivity testOneItemsActivity = testOneList.get(i);
                Toast.makeText(ListViewActivity.this, testOneItemsActivity.getContent(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initTestOneList() {
        for (int i = 0; i < 5; i++) {
            TestOneItemsActivity testOneItem1 = new TestOneItemsActivity("1111111", "1111111", R.drawable.jiaohu_on, "我是1111111略略略");
            testOneList.add(testOneItem1);
            TestOneItemsActivity testOneItem2 = new TestOneItemsActivity("2222222", "2222222", R.drawable.jiaohu_on, "我是2222222略略略");
            testOneList.add(testOneItem2);
            TestOneItemsActivity testOneItem3 = new TestOneItemsActivity("3333333", "3333333", R.drawable.jiaohu_on, "我是3333333略略略");
            testOneList.add(testOneItem3);
            TestOneItemsActivity testOneItem4 = new TestOneItemsActivity("4444444", "4444444", R.drawable.jiaohu_on, "我是4444444略略略");
            testOneList.add(testOneItem4);
            TestOneItemsActivity testOneItem5 = new TestOneItemsActivity("5555555", "5555555", R.drawable.jiaohu_on, "我是5555555略略略");
            testOneList.add(testOneItem5);
        }
    }
}
