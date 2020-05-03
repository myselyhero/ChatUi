package com.gy.wyy.chat.ui.face;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import com.gy.wyy.chat.ui.UiKit;

public class FaceUtil {

    public static String UI_PARAMS = "ilive_ui_params";
    public static String SOFT_KEY_BOARD_HEIGHT = "soft_key_board_height";

    private static int softKeyBoardHeight;
    private static SharedPreferences preferences = UiKit.getAppContext().getSharedPreferences(UI_PARAMS, Context.MODE_PRIVATE);

    /**
     *
     * @return
     */
    public static int getSoftKeyBoardHeight() {
        if (softKeyBoardHeight != 0)
            return softKeyBoardHeight;
        softKeyBoardHeight = preferences.getInt(SOFT_KEY_BOARD_HEIGHT, 0);
        if (softKeyBoardHeight == 0) {
            int height = getScreenSize()[1];
            return height * 2 / 5;
        }
        return softKeyBoardHeight;
    }

    /**
     *
     * @return
     */
    public static int[] getScreenSize() {
        int size[] = new int[2];
        DisplayMetrics dm = UiKit.getAppContext().getResources().getDisplayMetrics();
        size[0] = dm.widthPixels;
        size[1] = dm.heightPixels;
        return size;
    }
}
