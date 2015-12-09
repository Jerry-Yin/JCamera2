package com.example.jerryyin.jcamera2.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.jerryyin.jcamera2.R;
import com.example.jerryyin.jcamera2.tools.JImageHelper;


/**
 * Created by JerryYin on 12/7/15.
 */
public class JBeautyImgActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private static final int MAX_PROGRESS = 255;
    private static final int MID_PROGRESS = 127;
    private float mHue, mSaturation, mLight;
    private Bitmap mBitmap;

    private ImageView mImageView;
    private SeekBar mSeekBarHue, mSeekBarSaturation, mSeekBarLight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_beauty_img);

        mImageView = (ImageView) findViewById(R.id.image_show);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test3);
        mImageView.setImageBitmap(mBitmap);


        mSeekBarHue = (SeekBar) findViewById(R.id.seek_hue);
        mSeekBarSaturation = (SeekBar) findViewById(R.id.seek_saturation);
        mSeekBarLight = (SeekBar) findViewById(R.id.seek_light);

        mSeekBarHue.setOnSeekBarChangeListener(this);
        mSeekBarSaturation.setOnSeekBarChangeListener(this);
        mSeekBarLight.setOnSeekBarChangeListener(this);

        mSeekBarHue.setMax(MAX_PROGRESS);
        mSeekBarHue.setProgress(MID_PROGRESS);
        mSeekBarSaturation.setMax(MAX_PROGRESS);
        mSeekBarSaturation.setProgress(MID_PROGRESS);
        mSeekBarLight.setMax(MAX_PROGRESS);
        mSeekBarLight.setProgress(MID_PROGRESS);


    }


    //监听进度条状态
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_hue:
                mHue = (progress - MID_PROGRESS) * 1.0f / MID_PROGRESS * 180;
                break;

            case R.id.seek_saturation:
                mSaturation = progress * 1.0f / MID_PROGRESS;
                break;

            case R.id.seek_light:
                mLight = progress * 1.0f / MID_PROGRESS;
                break;
            default:
                break;
        }
        Bitmap bitmap = JImageHelper.handleImageEffect(mBitmap, mHue, mSaturation, mLight);
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
