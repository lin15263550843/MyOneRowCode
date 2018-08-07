package com.example.lhd.myonerowcode.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lhd.myonerowcode.R;

public class OpenWebVIewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_web_view_layout);
        WebView webView = findViewById(R.id.open_web_view_web_view);
        // getSettings() 方法可以设置一些浏览器的属性
        // setJavaScriptEnabled() 方法让 WebView 支持 JavaScript 脚本
        webView.getSettings().setJavaScriptEnabled(true);

        // 使网页内容智适应 WebView 的宽度
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // setWebViewClient() 传入一个 WebViewClient() 的实例，作用是：当需要从一个网页跳转到另一个网页时，让目标网页仍在当前 WebView 中显示，而不是打开系统浏览器
        webView.setWebViewClient(new WebViewClient());
        // loadUrl() 方法传入想要展示的网页
//        webView.loadUrl("http://www.baidu.com");
        webView.loadUrl("https://m.baidu.com/?from=1014629y");
    }
}
