package com.example.lhd.myonerowcode;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class CollapsingToolbarActivity extends AppCompatActivity {

    private static final String TAG = "CollapsingToolbarActivity";
    public static final String TITLE_NAME = "测试标题名称哈哈哈";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsing_toolbar_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.collapsing_toolbar_toolbar);
        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_collapsingToolbar);
        ImageView titleImage = (ImageView) findViewById(R.id.collapsing_toolbar_image);
        TextView cardText = (TextView) findViewById(R.id.collapsing_toolbar_content_text);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);     // 启用 HomeAsUp 按钮，默认图标就是一个返回按钮，所以不用单独设置图标了。
        }
        ctl.setTitle(TITLE_NAME);   // 设置标题名称
        titleImage.setImageResource(R.drawable.my_test_bg);     // 设置标题背景图片
        String cardContent = generateTestContent(TITLE_NAME);   // 卡片内容
        cardText.setText(cardContent);
    }

    // 把名字循环拼接500次
    private String generateTestContent(String titleName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append(titleName);
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
