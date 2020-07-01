package com.gy.wyy.chat.player.tool;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 *
 */
public class ScreenUtil {

    /**
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    /**
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
