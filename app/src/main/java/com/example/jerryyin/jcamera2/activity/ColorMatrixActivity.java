package com.example.jerryyin.jcamera2.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.jerryyin.jcamera2.R;

/**
 * Created by JerryYin on 12/7/15.
 */
public class ColorMatrixActivity extends Activity implements View.OnClickListener {

    private ImageView mImageView;
    private GridLayout mGridLayout;
    private Button mbtnChange, mbtnReset;
    private Bitmap mBitmap;

    private int mEtWidth, mEtHeigh;
    private EditText[] meTexts = new EditText[20];  //20个输入框
    private float[] mColorMatrix = new float[20];   //20个数值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_color_matrix);

        initViews();
        setUpGridText();
    }

    private void initViews() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
        mImageView = (ImageView) findViewById(R.id.img_matrix);
        mGridLayout = (GridLayout) findViewById(R.id.grid_layout);
        mbtnChange = (Button) findViewById(R.id.btn_change);
        mbtnReset = (Button) findViewById(R.id.btn_reset);
        mbtnReset.setOnClickListener(this);
        mbtnChange.setOnClickListener(this);

        mImageView.setImageBitmap(mBitmap);
    }

    private void setUpGridText() {
        /** 注意，此处在必须用post一步获取宽高，否则直接获取为0 */
        mGridLayout.post(new Runnable() {
            @Override
            public void run() {
                mEtWidth = mGridLayout.getWidth() / 5;
                mEtHeigh = mGridLayout.getHeight() / 4;

                addEditText();
                initMatrix();
//                initEditText();
            }
        });
    }

    private void addEditText() {
        for (int i = 0; i <20; i++){
            EditText editText = new EditText(ColorMatrixActivity.this);
            meTexts[i] = editText;
            mGridLayout.addView(editText, mEtWidth, mEtHeigh);
        }
    }

    private void initMatrix() {
        for (int i =0; i<20; i++) {
            if (i % 6 ==0){
                meTexts[i].setText(String.valueOf(1));  //斜对角线为1
            }else {
                meTexts[i].setText(String.valueOf(0));
            }
        }
    }

    private void getMatrix(){
        for (int i=0; i<20; i++){
            mColorMatrix[i] = Float.valueOf(meTexts[i].getText().toString());
        }
    }

    private void setImgMatrix(){
        //临时变量 操作
        Bitmap bmp = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        ColorMatrix colorMatrix  = new ColorMatrix();
        colorMatrix.set(mColorMatrix);

        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        mImageView.setImageBitmap(bmp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change:
                getMatrix();
                setImgMatrix();

                break;

            case R.id.btn_reset:
                initMatrix();
                getMatrix();
                setImgMatrix();

                break;
            default:
                break;
        }
    }
}
