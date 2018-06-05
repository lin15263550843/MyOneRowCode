package com.example.lhd.myonerowcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhd.myonerowcode.broadcast.BroadcastTestActivity;
import com.example.lhd.myonerowcode.common.DialogActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override   //添加右上角菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override   //用于保存页面的缓存数据
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "我是main页面的缓存数据";
        outState.putString("main_data_key", tempData);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.main_menu_item_1:
                Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_menu_item_2:
                Toast.makeText(this, "移除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_menu_item_3:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Log.i("MainActivityIII", "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        Log.e("MainActivityEEE", "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        Log.v("MainActivityVVV", "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
        Log.d("MainActivityDDD", "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        Log.w("MainActivityWWW", "WWWWWwWWWWWwWWWWWwWWWWWwWWWWWwWWWWWwWWW");


        //隐藏掉系统自带的标题
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageButton titleImageButtonBack = (ImageButton) findViewById(R.id.title_ImageButton_back);
        ImageButton titleImageButton1 = (ImageButton) findViewById(R.id.title_ImageButton_1);
        TextView titleTextView = (TextView) findViewById(R.id.title_textview);
        titleTextView.setText("main页面");
        titleImageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "onClick: 点击了main页面的返回键");
//                MainActivity.this.finish();
            }
        });


        //获取页面缓存数据
        if (savedInstanceState != null) {
            String tempData = savedInstanceState.getString("main_data_key");
            Log.v(TAG, "onCreate: " + tempData);
        }

        Button mainButton1 = (Button) findViewById(R.id.main_button_1);
        Button mainButton2 = (Button) findViewById(R.id.main_button_2);
        Button mainButton3 = (Button) findViewById(R.id.main_button_3);
        Button mainButton4 = (Button) findViewById(R.id.main_button_4);
        Button mainButton5 = (Button) findViewById(R.id.main_button_5);
        Button mainButton6 = (Button) findViewById(R.id.main_button_6);
        Button mainButton7 = (Button) findViewById(R.id.main_button_7);
        EditText mainEditText1 = (EditText) findViewById(R.id.main_editText_1);
        Button mainButton8 = (Button) findViewById(R.id.main_button_8);
        Button mainButton9 = (Button) findViewById(R.id.main_button_9);
        Button mainButton10 = (Button) findViewById(R.id.main_button_10);
        Button mainButton11 = (Button) findViewById(R.id.main_button_11);
        Button mainButton12 = (Button) findViewById(R.id.main_button_12);
        final ProgressBar mainProgressBar1 = (ProgressBar) findViewById(R.id.main_progressbar_1);

        mainButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "我是从main传到home的数据，哈哈哈哈哈，你咬我呀~";
//                Toast.makeText(MainActivity.this, "我是弹窗，你咬我呀~", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);     //显式 intent
                intent.putExtra("extra_data_main", data);
                startActivityForResult(intent, 11111);
//                startActivity(intent);    //加上会同时同时启动俩
            }
        });
        mainButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MyInformationActivity.class);     //显式 intent
                Intent intent = new Intent("com.example.lhd.myonerowcode.MyInformationActivity");     //隐式 intent
                intent.addCategory("com.example.lhd.myonerowcode.MyInformationActivity");
                startActivity(intent);
            }
        });
        mainButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
        mainButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:17606229741"));
                startActivity(intent);
            }
        });
        mainButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mainButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = "我是从main传到Dialog的数据11111";
                String data2 = "我是从main传到Dialog的数据22222";
                DialogActivity.actionStart(MainActivity.this, data1, data2);
//                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
//                startActivity(intent);
            }
        });
        mainButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//                progressDialog.setTitle("我是ProgressDialog弹窗");
//                progressDialog.setMessage("我是ProgressDialog弹窗的message信息，略略略");
//                progressDialog.setCancelable(true);
//                progressDialog.show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("我是AlertDialog弹窗");
                dialog.setMessage("我是AlertDialog弹窗的message信息，略略略");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v(TAG, "onClick: 我是AlertDialog弹窗的确定" + i + dialogInterface);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v(TAG, "onClick: 我是AlertDialog弹窗的取消" + i + dialogInterface);
                    }
                });
                dialog.show();

            }
        });
        mainButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mainProgressBar1.getVisibility() == View.GONE) {
                    mainProgressBar1.setVisibility(View.VISIBLE);
                } else {
                    mainProgressBar1.setVisibility(View.GONE);
                }
            }
        });
        mainButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);     //显式 intent
                startActivity(intent);
            }
        });
        mainButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);     //显式 intent
                startActivity(intent);
            }
        });
        mainButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MessageConversationListActivity.class);     //显式 intent
                startActivity(intent);
            }
        });

        mainButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BroadcastTestActivity.class);     //显式 intent
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart: 我是：onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onStart: 我是：onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onStart: 我是：onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStart: 我是：onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onStart: 我是：onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "onStart: 我是：onRestart");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11111:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("home_return_data");
                    Log.v(TAG, returnedData);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("my_info_return_data");
                    Log.v(TAG, returnedData);
                }
                break;

            default:
        }
    }
}
