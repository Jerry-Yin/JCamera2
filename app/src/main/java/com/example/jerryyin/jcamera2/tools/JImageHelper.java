package com.example.jerryyin.jcamera2.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by JerryYin on 12/7/15.
 * 图片管理工具类
 * 可以调整图片的 色相，饱和度， 亮度
 */
public class JImageHelper {


    /**
     * @param bitmap 待处理图片
     * @param hue   色相（RGB三原色 0-红 1－绿 2-蓝）
     * @param saturation 饱和度（0-2）
     * @param light   亮度
     * @return 处理后的图片
     *
     * 注意，不能直接对从文件流接收过来的bitmap进行处理，需要新建一个临时变量加以处理
     */
    public static Bitmap handleImageEffect(Bitmap bitmap, float hue, float saturation, float light){
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);   //临时变量
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //色相
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        //饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        //亮度
        ColorMatrix lightMatrix = new ColorMatrix();
        lightMatrix.setScale(light, light, light, 1);     //参数：R， G， B，A（透明度）;

        //综合三者
        ColorMatrix imgMatrix = new ColorMatrix();
        imgMatrix.postConcat(hueMatrix);
        imgMatrix.postConcat(saturationMatrix);
        imgMatrix.postConcat(lightMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imgMatrix));     //设置完成后的画笔

        /**
         * 注意此处 不是重画 bmp, 而是 bitmap
         */
        canvas.drawBitmap(bitmap, 0, 0, paint);    //用设置完的画笔重新绘制,

        return bmp;
    }


}
