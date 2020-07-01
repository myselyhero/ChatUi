package com.gy.wyy.chat.player;

import android.content.Context;

/**
 * @author 王永勇
 * @// TODO: 2020/6/28
 */
public class PlayerKit {

    private static Context appContext;

    /**
     *
     * @param context
     */
    public static void init(Context context){
        appContext = context;
    }

    /**
     *
     * @return
     */
    public static Context getAppContext() {
        return appContext;
    }
}
