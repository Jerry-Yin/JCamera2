package com.example.jerryyin.jcamera2.tools;

//import android.graphics.Camera;       //注意包不能导错，应该使用硬件的包，而不是3绘图的包
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by JerryYin on 12/3/15.
 * 相机方法管理
 *
 * 关于Camera类标记为废弃类： Camera 类为旧的相机类，目前新出的是Camera2，官方推荐使用后者，但普通的相机使用前者就足够了
 */
public class JCameraManager {


    /**
     * 初始化
     * 获取Camera对象
     * 一般在Activity的onResume() 中执行
     * @return
     */
    public static Camera getCamera(){
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    /**
     * 开始预览相机内容
     */
    public static void setStartPreview(Camera camera, SurfaceHolder holder){
        try {
            camera.setPreviewDisplay(holder);
            //注意，系统的默认相机预览角度是横屏的，所以需要转动90度
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束Activity时销毁相机对象，释放资源
     * 一般在Activity的onPause()方法中释放
     */
    public static void releaseCamera(Camera camera){
        if (camera != null){
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

}
