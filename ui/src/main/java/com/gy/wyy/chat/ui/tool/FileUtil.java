package com.gy.wyy.chat.ui.tool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.gy.wyy.chat.ui.UiKit;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */
public class FileUtil  {

    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();//获取SD卡路径
    public static String MEDIA_PACKET_PATH = SD_CARD_PATH +"/"+ UiKit.getAppContext().getPackageName();
    public static String MEDIA_CACHE = MEDIA_PACKET_PATH + "/cache/";

    /**
     *  初始化路径(需具有读写权限)
     */
    public static void initPath() {

        File file = new File(MEDIA_CACHE);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     *
     * @param bitmap
     * @return
     */
    public static String saveBitmap(Bitmap bitmap) {

        String jpegName = MEDIA_CACHE + System.currentTimeMillis() + ".jpg";
        File file = new File(jpegName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            UiKit.getAppContext().sendBroadcast(intent);
            return jpegName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
