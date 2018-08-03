package com.example.lhd.myonerowcode;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaUseActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private ImageView takePhotoImage;
    private Uri imageUri;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_use_layout);

        Button takePhoto = findViewById(R.id.media_use_take_photo);
        takePhotoImage = findViewById(R.id.media_use_take_photo_image);
        Button albumSelectionPhoto = findViewById(R.id.media_use_album_selection_photo);
        Button playAudio = findViewById(R.id.media_use_play_audio);
        Button pauseAudio = findViewById(R.id.media_use_pause_audio);
        Button stopAudio = findViewById(R.id.media_use_stop_audio);
        takePhoto.setOnClickListener(this);
        albumSelectionPhoto.setOnClickListener(this);
        playAudio.setOnClickListener(this);
        pauseAudio.setOnClickListener(this);
        stopAudio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.media_use_take_photo:
                takePhotoFunction();
                break;
            case R.id.media_use_album_selection_photo:
                albumSelectionPhotoFun();
                break;
            case R.id.media_use_play_audio:
                initMediaPlayer();      // 初始化MediaPlayer

                break;
            case R.id.media_use_pause_audio:
                mediaPlayer.pause();    // 暂停播放
                break;
            case R.id.media_use_stop_audio:
                mediaPlayer.stop();     // 停止播放
                break;
            default:
                break;
        }
    }

    // 准备播放音频文件
    private void initMediaPlayer() {
        // 首先检查权限
        if (ContextCompat.checkSelfPermission(MediaUseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MediaUseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            try {
                File file = new File(Environment.getExternalStorageDirectory(), "Beyond - 海阔天空_1.mp3");
                if (file != null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(file.getPath());  // 指定音频文件的路径
                    mediaPlayer.prepare();  // 让 MediaPlayer 进入到准备状态
                    mediaPlayer.start();    // 开始播放
                } else {
                    Toast.makeText(this, "找不到该文件", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 拍照
    public void takePhotoFunction() {
        // 创建File对象，用于存储拍摄的照片
        // getExternalCacheDir() 获取应用关联缓存目录，具体位置：、sdcard/Android/data/MyOneRowCode/cache
        // 因为从 Android 6.0 系统开始，读写SD卡被列为了危险权限，如果将图片存放到其他任何目录，都需要运行时权限处理才行，而使用关联目录则可以跳过这一步
        // 创建文件名 File对象
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";
        File outputImage;
//        // SD Card Mounted
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            outputImage = new File(getExternalCacheDir(), fileName);
//        } else {    // Use internal storage
//            outputImage = new File(getCacheDir(), fileName);
//        }
        outputImage = new File(getExternalCacheDir(), fileName);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 判断运行设备的系统版本是否低于 Android7.0
        if (Build.VERSION.SDK_INT >= 24) {
            // 调用 FileProvider 的 getUriForFile（）方法将 File 对象转换成一个封装过的 Uri 对象。
            // getUriForFile（）方法接收 3 个参数，第一个参数要求传人 Context 对象，第二个参数可以是任意唯一-的字符串，第三个参数则是我们刚刚创建的 File 对象。
            // 之所以要进行这样一层转换，是因为从 Android7.0 系统开始，直接使用本地真实路径的 Uri 被认为是不安全的，会抛出一个 FileUriExposedException 异常。
            // 而 FileProvider 则是一种特殊的内容提供器，它使用了和内容提供器类似的机制来对数据进行保护，可以选择性地将封装过的 Uri 共享给外部，从而提高了应用的安全性。
            imageUri = FileProvider.getUriForFile(MediaUseActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
            // 不过现在还没结束，刚才提到了内容提供器，那么我们自然要在 AndroidManifest. Xml 中对内容提供器进行注册了!!!
        } else {
            // 如果运行设备的系统版本低于 Android7.0, 就调用 Uri 的 fromFile（）方法将 File 对象转换成 Uri 对象，
            // 这个 Uni 对象标识着 outputImage.jpg 这张图片的本地真实路径。
            imageUri = Uri.fromFile(outputImage);
        }
        // 使用 fromFile() 方法将FIle对象转化成Uri对象
//        imageUri = Uri.fromFile(outputImage);
        //启动相机程序        隐式 Intent
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    //相册选取照片
    public void albumSelectionPhotoFun() {
        if (ContextCompat.checkSelfPermission(MediaUseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MediaUseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent, CHOOSE_PHOTO);    //打开相册
        }
    }

    // 解析出图片的真实路径
    public String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过 Uri 和 selection 来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //页面显示图片
    public void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            takePhotoImage.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "选取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 4.4以下系统处理图片
    public void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
    }

    // 4.4及以上系统处理图片
    public void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是 document 类型的 Uri ,则通过 document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];    //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri ，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 file 类型的 Uri 直接获取图片的路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);    //根据图片路径显示图片
    }

    /**
     * 拍照 or 选图片 结果处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //将拍摄的照片显示出来
                    try {
                        // 我们是使用 startActivityForResult（）来启动活动的，因此拍完照后会有结果返回到 onActivityResult（）方法中。
                        // 如果发现拍照成功，就可以调用 BitmapFactory 的 decodeStream（）方法将 outputImage.Jpg 这张照片解析成 Bitmap 对象.
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        // 然后把它设置到 lmageView 中显示出来。
                        takePhotoImage.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "拍照失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统的版本
                    if (Build.VERSION.SDK_INT < 19) {
                        //4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    } else {
                        //4.4及以上系统使用该方法
                        handleImageOnKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 权限申请结果处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    albumSelectionPhotoFun();
                } else {
                    Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用该功能", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
