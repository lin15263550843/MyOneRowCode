package com.example.lhd.myonerowcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpURLConnectionActivity extends AppCompatActivity {

    private static final String TAG = "HttpURLConnectionActivi";
    TextView connectionContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_url_connection_layout);

        Button initiateRequest = findViewById(R.id.http_url_connection_Initiate_request);
        Button okHttpInitiateRequest = findViewById(R.id.okhttp_Initiate_request);
        connectionContent = findViewById(R.id.http_url_connection_content);

        initiateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithHttpURLConnection();
            }
        });
        okHttpInitiateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
            }
        });
    }

    // 使用 okHttp 发起请求
    private void sendRequestWithOkHttp() {
        // 开启子线程 来发起网络请求 ***** 注意，一定要开启子线程，否则会报错
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();   // 创建 OkHttpClient 实例
                    RequestBody rb = new FormBody.Builder()     // 创建 RequestBody 对象来存放待提交的参数
                            .add("userid", "01416271")
                            .add("POSTSOURCEID", "")
                            .add("POSTSOURCESYS", "")
                            .add("arrow", "all")
                            .add("cacheMinId", "")
                            .add("isConcern", "all")
                            .add("isNew", "")
                            .add("lastMinId", "")
                            .add("pageSize", "10")
                            .add("searchDay", "")
                            .add("searchKey", "")
                            .add("searchMonth", "")
                            .add("searchWeek", "")
                            .add("searchYear", "")
                            .add("showType", "show")
                            .add("taskStatus", "")
                            .add("taskType", "")
                            .add("userPk", "8700319417")
                            .build();
                    Request request = new Request.Builder()    // 创建 Request 对象
                            .url("http://testhaier.jushanghui.com/bspfront/blog/listDisplay")  //设置请求地址
                            .post(rb)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, "sendRequestWithOkHttp: " + responseData);
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 发起http请求
    private void sendRequestWithHttpURLConnection() {
        // 开启子线程 来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://m.baidu.com/?from=1014629y");
                    connection = (HttpURLConnection) url.openConnection();      //  请求地址
                    connection.setRequestMethod("GET");      //  请求方式
                    connection.setConnectTimeout(8000);      //  连接超时毫秒数
                    connection.setReadTimeout(8000);         //  读取超时毫秒数
                    InputStream inputStream = connection.getInputStream();  //  调用 getInputStream() 方法获取到服务器返回的输入流
                    // 下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // 关闭流
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect(); // 关掉这个http连接
                    }
                }
            }
        }).start();
    }

    /**
     * 可以看到，我们在 initiateRequest 按钮的点击事件里调用了 sendRequestWithHttpURLConnection（）方法，在这个方法中先是开启了一个子线程，
     * 然后在子线程里使用 HttpURLConnection 发出一条 HTTP 请求，请求的目标地址就是百度的首页。
     * 接着利用 BufferedReader 对服务器返回的流进行读取，并将结果传人到了 showResponse（）方法中。
     * 而在 showResponse（）方法里则是调用了一个 runOnUiThread（）方法，然后在这个方法的匿名类参数中进行操作，将返回的数据显示到界面上。
     * <p>
     * 那么这里为什么要用这个 runOnUiThread（）方法呢？这是因为 Android 是不允许在子线程中进行 UI 操作的，我们需要通过这个方法将线程切换到主线程，然后再更新 U1 元素。
     * 关于这部分内容，我们将会在下一章中进行详细讲解，现在你只需要记得必须这么写就可以了。
     */
    private void showResponse(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                connectionContent.setText(s);
            }
        });
    }
}
