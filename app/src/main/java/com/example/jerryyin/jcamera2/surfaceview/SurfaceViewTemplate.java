package com.example.jerryyin.jcamera2.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by JerryYin on 12/7/15.
 * SurfaceView 的模版用法
 * 1.通过线程绘制图形
 * 2.通过callback回调控制canvas的生命周期
 */
public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Canvas mCanvas;
    private SurfaceHolder mHolder;
    private Thread mThread;

    private boolean isRunning;  //控制线程的启动与停止


    public SurfaceViewTemplate(Context context) {
        super(context, null);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * 一些常用设置
         * 1.可获得焦点
         * 2.设置常量
         */
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        mHolder = getHolder();
        mHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //开启绘制的线程
        isRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //通过标志位关闭线程
        isRunning = false;
    }


    //绘制图形的线程

    @Override
    public void run() {
        while (isRunning){
            //绘制
            drawSomething();
        }
    }


    private void drawSomething() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null){
                //todo draw something

            }
        } catch (Exception e) {
        } finally {
            //绘制完成，释放画布，提交修改
            mHolder.unlockCanvasAndPost(mCanvas);
        }


    }
}
