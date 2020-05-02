package com.gy.wyy.chat.ui;

import android.content.Context;

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
    }

    /**
     *
     * @return
     */
    public static Context getAppContext(){
        return mAppContext;
    }
}
