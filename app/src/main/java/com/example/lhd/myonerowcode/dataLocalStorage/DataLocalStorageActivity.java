package com.example.lhd.myonerowcode.dataLocalStorage;
/**
 * 数据存储
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhd.myonerowcode.R;
import com.example.lhd.myonerowcode.service.MyDatabaseHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataLocalStorageActivity extends AppCompatActivity {

    private static final String TAG = "DataLocalStorageActivit";
    private EditText enterTestData;

    private Button saveTestData;
    private Button getTestData;
    private TextView fileTestData;

    private Button setsharedPreferencesTestData;
    private Button getsharedPreferencesTestData;
    private TextView sharedPreferencesTestData;

    private Button sqlLiteSetTestData;
    private Button sqlLiteAddTestData;
    private Button sqlLiteGetTestData;
    private TextView sqlLiteTestData;

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_local_storage_layout);

        enterTestData = (EditText) findViewById(R.id.data_local_storage_editText);
//        enterTestData.setSelection(localTestData.length()); //光标移到文本尾部继续输入

        fileTestData = (TextView) findViewById(R.id.data_local_storage_text);
        saveTestData = (Button) findViewById(R.id.data_local_storage_button);
        getTestData = (Button) findViewById(R.id.data_local_storage_button_get);

        setsharedPreferencesTestData = (Button) findViewById(R.id.data_local_storage_button2);
        getsharedPreferencesTestData = (Button) findViewById(R.id.data_local_storage_button3);
        sharedPreferencesTestData = (TextView) findViewById(R.id.data_local_storage_text3);

        sqlLiteSetTestData = (Button) findViewById(R.id.data_local_storage_sql);
        sqlLiteAddTestData = (Button) findViewById(R.id.data_local_storage_sql_add_data);
        sqlLiteGetTestData = (Button) findViewById(R.id.data_local_storage_sql_get);
        sqlLiteTestData = (TextView) findViewById(R.id.data_local_storage__sql_text);
        dbHelper = new MyDatabaseHelper(this, "BookTest.db", null, 1);  //四个参数 1：Context，2：数据库名，3：允许我们在查询数据的时候返回一个自定义的Cursor，4：当前数据库的版本号。

        // 存储到文件
        saveTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testData = enterTestData.getText().toString();
                saveTestDataFunction(testData);
            }
        });
        // 读取保存的数据
        getTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String localTestData = getTestDataFunction();
                if (!TextUtils.isEmpty(localTestData)) {   //TextUtils.isEmpty 可以进行两种判断 null 或者 空字符串
                    fileTestData.setText(localTestData);
                }
            }
        });
        // 将数据存储到 SharedPreferences 中
        setsharedPreferencesTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testData = enterTestData.getText().toString();
                setDataSharedPreferencesFunction(testData);
            }
        });
        // 从 SharedPreferences 中读取数据
        getsharedPreferencesTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataSharedPreferencesFunction();
            }
        });
        // 创建 sqlLite 数据库
        sqlLiteSetTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase(); //创建数据库，如果已存在直接打开当数据库不可写入的时候（如磁盘空间已满），getWritableDatabase()方法会出现异常。
                //dbHelper.getReadableDatabase();  //创建数据库，如果已存在直接打开当数据库不可写入的时候（如磁盘空间已满），getReadableDatabase()方法返回的对象将以只读的方法去打开数据库。
            }
        });
        // 添加数据到 sqlLite 中
        sqlLiteAddTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlLiteDatabaseAddData();
            }
        });
        // 取出 sqlLite 中的数据
        sqlLiteGetTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getsqlLiteDatabaseData();
            }
        });
    }

    // 保存数据到本地文件
    public void saveTestDataFunction(String testData) {
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = openFileOutput("MyOneRowCodeTestData", Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(testData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
//                    this.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //获取数据到本地文件
    public String getTestDataFunction() {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileInputStream = openFileInput("MyOneRowCodeTestData");
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    //将数据存储到 SharedPreferences 中
    public void setDataSharedPreferencesFunction(String testData) {
        // 调用 SharedPreferences 对象的 editor() 方法来获取一个  SharedPreferences.Editor 对象
        // getSharedPreferences()方法 指定文件名为 “testDataSharedPreferences” 并得到 SharedPreferences.Editor 对象
        // 向 SharedPreferences.Editor 对象中添加数据 ，并调用 apply() 方法将添加的数据提交
        SharedPreferences.Editor editor = getSharedPreferences("testDataSharedPreferences", MODE_PRIVATE).edit();
        editor.putString("name", testData);
        editor.putInt("age", 25);
        editor.putBoolean("married", false);
        editor.apply();
    }

    //从 SharedPreferences 中读取数据
    public void getDataSharedPreferencesFunction() {
        SharedPreferences sharedPreferences = getSharedPreferences("testDataSharedPreferences", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        int age = sharedPreferences.getInt("age", 0);
        Boolean married = sharedPreferences.getBoolean("married", false);
        Log.d(TAG, "getDataSharedPreferencesFunction: " + name + age + married);
        sharedPreferencesTestData.setText(name);
    }

    // 添加数据到 sqlLite 中
    public void sqlLiteDatabaseAddData() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("name", "第一行代码");
        values.put("author", "大卫 P 查理斯");
        values.put("pages", 789);
        values.put("price", 85.43);
        database.insert("BookTest", null, values);  //插入第一条数据
        values.clear();
        //开始组装第二条数据
        values.put("name", "Android群英传");
        values.put("author", "小卫 d 艾斯兰");
        values.put("pages", 8934);
        values.put("price", 143.86);
        database.insert("BookTest", null, values);  //插入第二条数据
        values.clear();
        Toast.makeText(DataLocalStorageActivity.this, "BookTest表插入数据成功成功", Toast.LENGTH_SHORT).show();
    }

    // 取出 sqlLite 中的数据
    public void getsqlLiteDatabaseData() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //查询BookTest表中的所有数据
        Cursor cursor = database.query("BookTest", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //遍历cursor对象，取出所有数据
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.d(TAG, "getsqlLiteDatabaseData: ======================: " + name);
                Log.d(TAG, "getsqlLiteDatabaseData: ======================: " + author);
                Log.d(TAG, "getsqlLiteDatabaseData: ======================: " + pages);
                Log.d(TAG, "getsqlLiteDatabaseData: ======================: " + price);
                sqlLiteTestData.setText(name);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
