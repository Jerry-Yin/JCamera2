package com.example.jerryyin.jcamera2.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.jerryyin.jcamera2.R;
import com.example.jerryyin.jcamera2.tools.JCameraManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class JCameraActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener {

    private SurfaceView mSurfaceView;
    private Button mBtnCapture;

    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         *  全屏实现
         * 1.manifest文件中添加 android:theme="@android:style/Theme.NoTitleBar.Fullscreen"  （application or Activity 均可，看自己）
         * 2.此处方式
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //应用程序的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //系统的状态栏

        setContentView(R.layout.layout_camera);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceView.setOnClickListener(this);
        mBtnCapture = (Button) findViewById(R.id.btn_take_pic);

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        mBtnCapture = (Button) findViewById(R.id.btn_take_pic);
        mBtnCapture.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = JCameraManager.getCamera();
            if (mSurfaceHolder != null) {
                JCameraManager.setStartPreview(mCamera, mSurfaceHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JCameraManager.releaseCamera(mCamera);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //预览
        JCameraManager.setStartPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //关闭 & 重启
        mCamera.stopPreview();
        JCameraManager.setStartPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //释放资源
        JCameraManager.releaseCamera(mCamera);
    }

    /**
     * 拍照
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_pic:
                /** 设置参数*/
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPictureFormat(ImageFormat.JPEG);  //照片格式
//                parameters.setPreviewSize(800, 400);    //尺寸
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO); //自动对焦

                //相机对焦回调
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        //success 表示聚焦成功
                        if (success) {
                            //拍照
                            mCamera.takePicture(null, null, mPictureCallback);
                        }
                    }
                });
                break;

            case R.id.surface_view:
                //点击画面进行自动聚焦
                if (mCamera != null) {
                    mCamera.autoFocus(null);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 拍照完毕的回调
     * 执行文件的存储，显示等后续操作
     * data 中存储着整张照片的全部数据信息
     */
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String statue = Environment.getExternalStorageState();
            if (statue.equals(Environment.MEDIA_MOUNTED)) {
                String imgPath = Environment.getExternalStorageDirectory() + "/Aj";
                File file1 = new File(imgPath);
                file1.mkdir();
                File file = new File(imgPath, "picture.jpeg");

//                File file = new File("/sdcard/temp.png");
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    outputStream.write(data);
                    Toast.makeText(JCameraActivity.this, "存储完毕！", Toast.LENGTH_SHORT).show();
                    outputStream.close();

                    Intent intent = new Intent(JCameraActivity.this, ResultActivity.class);
                    intent.putExtra("img_path", file.getAbsolutePath());
                    startActivity(intent);
                    JCameraActivity.this.finish();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    };
}
