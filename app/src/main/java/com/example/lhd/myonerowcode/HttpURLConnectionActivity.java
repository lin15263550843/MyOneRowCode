package com.example.lhd.myonerowcode;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lhd.myonerowcode.entity.GSONEntity;
import com.example.lhd.myonerowcode.service.HttpCallbackListener;
import com.example.lhd.myonerowcode.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpURLConnectionActivity extends AppCompatActivity {

    private static final String TAG = "HttpURLConnectionActivi";
    private static final int UPDATE_TEXT = 1;
    private TextView connectionContent;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_TEXT:
                    // 在这里可以进行UI更新；
                    connectionContent.setText("我是在子线程中更新的内容，略略略~~~~");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_url_connection_layout);

        Button initiateRequest = findViewById(R.id.http_url_connection_Initiate_request);
        Button okHttpInitiateRequest = findViewById(R.id.okhttp_Initiate_request);
        Button analysisJsonDataButton = findViewById(R.id.Analysis_json_data);
        Button encapsulationHttpRequest = findViewById(R.id.encapsulation_http_request);
        Button subThreadUpdateUi = findViewById(R.id.sub_thread_update_ui);
        Button asyncTaskUpdateUi = findViewById(R.id.asynctask_update_ui);
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
        analysisJsonDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analysisJsonData();
            }
        });
        encapsulationHttpRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtilHttpRequest();
            }
        });
        subThreadUpdateUi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subThreadUpdateUiFun();
            }
        });
        asyncTaskUpdateUi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new myTestTask().execute();
            }
        });
    }

    // 子线程中更新UI
    private void subThreadUpdateUiFun() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // connectionContent.setText("我是在子线程中更新的内容，略略略~~~~");  // 这样直接在子线程中更新UI程序会崩溃的
                // 利用解析异步消息处理机制，子线程中更新UI
                Message message = new Message();
                message.what = UPDATE_TEXT;
                handler.sendMessage(message);   // 将 Message 对象发送出去
            }
        }).start();
    }

    // 请求本地的 json 格式数据
    private void analysisJsonData() {
        // 开启子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.168.3.61:8888/getActivityList.json")  // 指定请求地址
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                    parseJSONWIthGSON(responseData);
                    parseJSONWIthJSONObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 使用封装的 http 请求
    private void HttpUtilHttpRequest() {
        String url = "http://10.168.3.61:8888/getActivityList.json";
        HttpUtil.sendOkHttpRequest(url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 在这里对异常情况进行处理
                Log.d(TAG, "onFailure: 请求失败");
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                showResponse(responseData);
                parseJSONWIthGSON(responseData);
                parseJSONWIthJSONObject(responseData);
            }
        });
        HttpUtil.sendHttpURLConnectionRequest(url, new HttpCallbackListener() {
            @Override
            public void onSuccess(String response) {
                //得到服务器返回的具体内容
//                showResponse(response);  // 请求回来的数据不好看
                parseJSONWIthGSON(response);
                parseJSONWIthJSONObject(response);
            }

            @Override
            public void onError(Exception error) {
                // 在这里对异常情况进行处理
                Log.d(TAG, "onError: 请求失败");
                Log.d(TAG, error.getMessage());
            }
        });
    }

    // 使用 JSONObject 和 JSONArray 解析 json 格式数据
    private void parseJSONWIthJSONObject(String responseData) {
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
            for (int i = 0; jsonArray.length() > i; i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                String name = jo.getString("name");
                Log.d(TAG, "parseJSONWIthJSONObject: ============ " + name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 使用 GSON 解析 json 格式数据
    private void parseJSONWIthGSON(String responseData) {
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            Gson gson = new Gson();
//        GSONEntity gsonEntity = gson.fromJson(responseData, GSONEntity.class);
            List<GSONEntity> gsonList = gson.fromJson(jsonObject.getString("data"), new TypeToken<List<GSONEntity>>() {
            }.getType());
            for (GSONEntity ge : gsonList) {
//                Log.d(TAG, "parseJSONWIthGSON: ======" + ge.getCode());
                Log.d(TAG, "parseJSONWIthGSON: ======" + ge.getName());
//                Log.d(TAG, "parseJSONWIthGSON: ======" + ge.getPrice());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                            .add("userId", "01416271")
                            .add("roleCode", "hejshywzc_new")
                            .add("shopId", "8800125026")
                            .add("token", "MDEyODkxMzNAMTUzMzk2ODQ4MDI4Ng==")
                            .build();
                    Request request = new Request.Builder()    // 创建 Request 对象
                            .url("https://m.baidu.com/?from=1014629y")  //设置请求地址
                            .url("http://testhaier.jushanghui.com/bspfront/myShop/marketFixReport")  //设置请求地址
                            .post(rb)
                            .build();
                    Response response = client.newCall(request).execute();  // execute() 方法来发送请求并获取服务器返回的数据
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
                    Log.d(TAG, "sendRequestWithOkHttp: " + response.toString());
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

    /**
     * 首先来看一下 Asynctask 的基本用法，由于 Asynctask 是一个抽象类，所以如果我们想使用它，就必须要创建一个子类去继承它。
     * 在继承时我们可以为 Asynctask 类指定 3 个泛型参数，这 3 个参数的用途如下。
     * ロ Params。在执行 Async Task 时需要传人的参数，可用于在后台任务中使用。
     * 口 Progress。后台任务执行时，如果需要在界面上显示当前的进度，则使用这里指定的泛型作为进度单位。
     * ロ Result。当任务执行完毕后，如果需要对结果进行返回，则使用这里指定的泛型作为返回值类型。
     * <p>
     * 这里我们把 Asynctask 的第一个泛型参数指定为 void，表示在执行 Async Task 的时候不需要传入参数给后台任务。
     * 第二个泛型参数指定为 Integer，表示使用整型数据来作为进度显示单位。
     * 第三个泛型参数指定为 Boolean，则表示使用布尔型数据来反馈执行结果。
     */
    class myTestTask extends AsyncTask<Void, Integer, Boolean> {
        int timer = 10;

        @Override
        protected void onPreExecute() {
//            super.onPreExecute();
            // 这个方法会在后台任务开始执行之前调用，用于进行一些界面上的初始化操作，比如显示个进度条对话框等。
            Log.d(TAG, "onPreExecute: 开始任务~~~");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            // 这个方法中的所有代码都会在子线程中运行，我们应该在这里去处理所有的耗时任务。
            // 任务旦完成就可以通过 return 语句来将任务的执行结果返回，如果 AsyncTask 的第三个泛型参数指定的是 Void，就可以不返回任务执行结果。
            // 注意，在这个方法中是不可以进行 UI 操作的，如果需要更新 UI 元素，比如说反馈当前任务的执行进度，可以调用 publishProgress(Progress...）方法来完成。
            while (timer > 0) {
                try {
                    timer--;
                    publishProgress(timer);
                    Log.d(TAG, "doInBackground: 任务执行中=============" + timer);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
            // 当在后台任务中调用了 publishProgress(Progress...）方法后，onProgressUpdate(Progress...）方法就会很快被调用，该方法中携带的参数就是在后台任务中传递过来的。
            // 在这个方法中可以对 UI 进行操作，利用参数中的数值就可以对界面元素进行相应的更新。
            Log.d(TAG, "onProgressUpdate: ============" + Arrays.toString(values));
            connectionContent.setText(String.valueOf(values[0]));   // 在这里更新进度
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
            // 当后台任务执行完毕并通过 return 语句进行返回时，这个方法就很快会被调用。
            // 返回的数据会作为参数传递到此方法中，可以利用返回的数据来进行一些 UI 操作，比如说提醒任务执行的结果，以及关闭掉进度条对话框等。
            if (aBoolean) {
                Log.d(TAG, "onPreExecute: 任务成功执行完了~~~" + true);
            } else {
                Log.d(TAG, "onPreExecute: 任务执行失败~~~" + aBoolean);
            }
        }
    }
}
