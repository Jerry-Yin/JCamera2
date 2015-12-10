package com.example.jerryyin.jcamera2.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.jerryyin.jcamera2.activity.JCameraActivity;
import com.example.jerryyin.jcamera2.activity.ResultActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by JerryYin on 12/10/15.
 * 存储照片或者图片的工具类
 */
public class JImageSaveTool {

    /**
     * 存储到SD卡指定目录，普通方法
     * @param data 照片数据
     */
    public static void saveImageToSd( Context context, byte[] data){
        String statue = Environment.getExternalStorageState();
        if (statue.equals(Environment.MEDIA_MOUNTED)) {
            //如果存在SdCard
            String imgPath = Environment.getExternalStorageDirectory() + "/Aj";
            File file1 = new File(imgPath);
            file1.mkdir();
            File file = new File(imgPath, "picture.jpeg");

            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                outputStream.write(data);
                Toast.makeText(context, "存储完毕！", Toast.LENGTH_SHORT).show();
                outputStream.close();

//                goToShowImg(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 存储到指定目录，并且插入到系统的图库中
     * 资料来源：http://www.stormzhang.com/android/2014/07/24/android-save-image-to-gallery/
     * @param context
     * @param bitmap
     */
    public static void saveImageToGallery(Context context, Bitmap bitmap){
        //首先保存图片
        File imgPath = new File(Environment.getExternalStorageDirectory(), "AJ");
        if (! imgPath.exists()){
            imgPath.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(imgPath, fileName);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //其次，再插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));

    }


}
