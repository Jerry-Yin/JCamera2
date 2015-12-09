package com.example.jerryyin.jcamera2.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.jerryyin.jcamera2.R;

/**
 * Created by JerryYin on 12/7/15.
 */
public class SurfaceViewTestActivity extends Activity {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;  //负责维护surfaceView上面的绘制内容
    private Paint mPaint;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_camera);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);

        mPaint = new Paint();
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //锁定整个区域
                Canvas canvas = mHolder.lockCanvas();
                //绘制背景
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
                canvas.drawBitmap(bitmap, 0, 0, null);
                //绘制完成，释放画布，提交修改
                mHolder.unlockCanvasAndPost(canvas);

                //重新锁一次，持久化上次的内容
                mHolder.lockCanvas(new Rect(0, 0, 0, 0));
                mHolder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //处理按下事件
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    Canvas canvas = mHolder.lockCanvas(new Rect(x-50, y-50, x+50, y+50));
                    canvas.save();
                    canvas.rotate(30, x, y);    //旋转30度
                    mPaint.setColor(Color.RED);
                    canvas.drawRect(x - 40, y - 40, x, y, mPaint);  //绘制红色方块

                    //恢复canvas之前保存的状态
                    canvas.restore();

                    mPaint.setColor(Color.BLUE);
                    canvas.drawRect(x, y, x + 40, y + 40, mPaint);

                    mHolder.unlockCanvasAndPost(canvas);

                }
                return false;
            }
        });
    }


}
