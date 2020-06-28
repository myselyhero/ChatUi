package com.gy.wyy.chat.ui;

import android.content.Context;

import com.gy.wyy.chat.ui.face.FaceManager;
import com.gy.wyy.chat.ui.tool.FileUtil;

/**
 *
 */
public class UiKit {

    private static Context mAppContext;

    /**
     *
     * @param context
     */
    public static void initKit(Context context){
        mAppContext = context;
        FileUtil.initPath();
        FaceManager.loadFaceFiles();
    }

    /**
     *
     * @return
     */
    public static Context getAppContext(){
        return mAppContext;
    }
}
