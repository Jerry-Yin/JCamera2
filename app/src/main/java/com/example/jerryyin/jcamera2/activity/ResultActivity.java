package com.example.jerryyin.jcamera2.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.jerryyin.jcamera2.R;

/**
 * Created by JerryYin on 12/7/15.
 * 用于显示拍照效果的界面
 */
public class ResultActivity extends Activity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);

        mImageView = (ImageView) findViewById(R.id.img_result);

        Intent intent = getIntent();
        String path = intent.getStringExtra("img_path");
        /**
         * 注意，此处得到的bitmap是横屏的图片，需要再次旋转90度以显示
         */
        Bitmap bitmap = BitmapFactory.decodeFile(path); //原图
        Matrix matrix = new Matrix();   //用于旋转的矩阵
        matrix.setRotate(90);   //旋转90度
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        mImageView.setImageBitmap(bitmap);
    }
}
