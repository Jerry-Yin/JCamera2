package com.example.jerryyin.jcamera2.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.jerryyin.jcamera2.R;
import com.example.jerryyin.jcamera2.tools.JImageHelper;

/**
 * Created by JerryYin on 12/9/15.
 */
public class PixelEffectActivity extends Activity {

    private ImageView mImgOrigin, mImgOld, mImgBottom, mImgRelief;

    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pixels);
        initView();
    }

    private void initView() {
        mImgOrigin = (ImageView) findViewById(R.id.img_origin);
        mImgOld = (ImageView) findViewById(R.id.img_old);
        mImgBottom = (ImageView) findViewById(R.id.img_bottom);
        mImgRelief = (ImageView) findViewById(R.id.img_relief);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test3);

        mImgOrigin.setImageBitmap(mBitmap);
        mImgBottom.setImageBitmap(JImageHelper.handleBottomImgEffect(mBitmap));
        mImgOld.setImageBitmap(JImageHelper.handleOldImgEffect(mBitmap));
        mImgRelief.setImageBitmap(JImageHelper.handleReliefImgEffect(mBitmap));
    }
}
