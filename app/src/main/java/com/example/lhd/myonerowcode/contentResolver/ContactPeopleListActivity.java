package com.example.lhd.myonerowcode.contentResolver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lhd.myonerowcode.R;
import com.example.lhd.myonerowcode.adapter.TestOneAdapter;
import com.example.lhd.myonerowcode.entity.TestOneItemsActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactPeopleListActivity extends AppCompatActivity {
    TestOneAdapter arrayAdapter;
    private List<TestOneItemsActivity> contactPeopleList = new ArrayList<>();
    ListView contactPeopleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_people_list_layout);

        arrayAdapter = new TestOneAdapter(ContactPeopleListActivity.this, R.layout.test_one_items_layout, contactPeopleList);
        contactPeopleListView = findViewById(R.id.contact_people_list);
        contactPeopleListView.setAdapter(arrayAdapter);
        readContactPeoples();   //获取联系人数据
    }

    //获取联系人数据
    private void readContactPeoples() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {   //检查通讯录权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 3);
        } else {
            Cursor cursor = null;
            //查询联系人数据
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {   //Cursor必须调用cursor1.moveToNext()方法才能开始取数据，需要使用while循环(取完之后为false)
                    //获取联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取联系人手机号
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //组装一条数据
                    TestOneItemsActivity testOneItem = new TestOneItemsActivity("", displayName, R.drawable.jiaohu_on, number);
                    contactPeopleList.add(testOneItem);
                }
                arrayAdapter.notifyDataSetChanged();    //添加数据后，用来刷新数据
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContactPeoples();
                } else {
                    Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                break;
            default:
                break;
        }
    }
}
