package com.gy.wyy.chat.ui;

import android.content.Context;

import com.gy.wyy.chat.ui.face.FaceManager;
import com.gy.wyy.chat.ui.tool.FileUtil;

import java.lang.ref.WeakReference;

/**
 *
 */
public class UiKit {

    /**
     * 全局上下文
     */
    private static WeakReference<Context> mAppContext;

    /**
     *
     * @param context
     */
    public static void initKit(Context context){
        mAppContext = new WeakReference<>(context);
        FileUtil.initPath();
        FaceManager.loadFaceFiles();
    }

    /**
     *
     * @return
     */
    public static Context getAppContext(){
        return mAppContext.get();
    }
}
