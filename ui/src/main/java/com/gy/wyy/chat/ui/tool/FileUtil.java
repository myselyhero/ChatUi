package com.gy.wyy.chat.ui.tool;

import android.os.Environment;

import com.gy.wyy.chat.ui.UiKit;

import java.io.File;

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
}
