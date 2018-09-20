package com.example.lhd.myonerowcode.util;

import com.example.lhd.myonerowcode.service.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dhc on 2018/8/7.
 * http 封装
 * 需要注意的是，不管是使用 HttpURLConnection 还是 OkHttp，最终的回调接口都还是在子线程中运行的，
 * 因此我们不可以在这里执行任何的 UI 操作，除非借助 runOnUiThread（）方法来进行线程转换。
 */

public class HttpUtil {
    /**
     * HttpURLConnection
     *
     * 我们首先给 sendHttpURLConnectionRequest（）方法添加了一个 HttpCallbackListener 参数，并在方法的内部开启了一个子线程，然后在子线程里去执行具体的网络操作。
     * 注意，子线程中是无法通过 return 语句来返回数据的，
     * 因此这里我们将服务器响应的数据传人了 HttpCallbackListener 的 onSuccess（）方法中，如果出现了异常就将异常原因传人到 onError（）方法中。
     * 现在 sendHttpRequest（）方法接收两个参数了，因此我们在调用它的时候还需要将 HttpCallbackListener 的实例传人，如下所示：
     *
     * HttpUtil.sendHttpURLConnectionRequest (url, new HttpCallbackListener () {
     *
     *      @0 verride
     *      public: void onSuccess (String response) {
     *          // 在这里根据返回内容执行具体的逻辑
     *
     *      @0 verride
     *      public void onError (Exception e) {
     *      // 在这里对异常情况进行处理
     * });
     *
     * 这样的话，当服务器成功响应的时候，我们就可以在 onFinish（）方法里对响应数据进行处理了。
     * 类似地，如果出现了异常，就可以在 onError（）方法里对异常情况进行处理。如此- -来，我们就巧妙地利用回调机制将响应数据成功返回给调用方了。
     *
     * @param url
     * @param httpCallback
     */
    public static void sendHttpURLConnectionRequest(final String url, final HttpCallbackListener httpCallback) {
        // 开启子线程 来发起网络请求 ***** 注意，一定要开启子线程，否则会报错
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader br = null;
                try {


                    URL urlRequest = new URL(url);
                    connection = (HttpURLConnection) urlRequest.openConnection();      //  请求地址
                    connection.setRequestMethod("GET");      //  请求方式
                    connection.setConnectTimeout(8000);      //  连接超时毫秒数
                    connection.setReadTimeout(8000);         //  读取超时毫秒数
                    connection.setDoInput(true);

                    connection.setDoOutput(false);  // get请求用不到conn.getOutputStream()，因为参数直接追加在地址后面，因此默认是false。设置成true会报错的。。。

                    InputStream inputStream = connection.getInputStream();  //  调用 getInputStream() 方法获取到服务器返回的输入流
                    // 下面对获取到的输入流进行读取
                    br = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    if (httpCallback != null) {
                        httpCallback.onSuccess(sb.toString());  // 回调 onSuccess() 方法
                    }
                } catch (IOException e) {
                    if (httpCallback != null) {
                        httpCallback.onError(e);    // 回调 onError() 方法
                    }
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * OkHttp
     *
     * 可以看到，sendOkHttpRequest（）方法中有一个 okhttp3. Callback 参数，这个是 OkHttp 库中自带的一个回调接口，类似于我们刚才自己编写的 HtpCallbackListener。
     * 然后在 client. NewCall（）之后没有像之前那样一直调用 execute（）方法，而是调用了一个 enqueue（）方法，并把 okhttp3.Callback 参数传人。
     * OkHttp 在 enqueue（）方法的内部已经帮我们开好子线程了，然后会在子线程中去执行 HTTP 请求，并将最终的请求结果回调到 okhttp3.Callback 当中。
     * 那么我们在调用 sendOkHttpRequest（）方法的时候就可以这样写：
     *
     * HttpUtil.Send0kHttpRequest ("http://www.baidu.com", new okhttp3.Callback() {
     *
     *      @0 verride
     *      public void onResponse (Call call, Response response) throws I0 Exception {
     *          //得到服务器返回的具体内容
     *          String responseData = response.body().String();
     *      }
     *
     *      @0 verride
     *      public void onFailure (Call call, IOException e) {
     *          // 在这里对异常情况进行处理
     *      }
     * });
     *
     * @param url
     * @param httpCallback
     */
    public static void sendOkHttpRequest(String url, okhttp3.Callback httpCallback) {
        OkHttpClient ohc = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        ohc.newCall(request).enqueue(httpCallback);
    }
}
