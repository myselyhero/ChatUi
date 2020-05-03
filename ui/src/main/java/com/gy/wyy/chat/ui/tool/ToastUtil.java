package com.gy.wyy.chat.ui.tool;

import android.widget.Toast;

import com.gy.wyy.chat.ui.UiKit;

/**
 *
 */
public class ToastUtil {

    /**
     *
     * @param key
     */
    public static void toastShort(String key){
        Toast.makeText(UiKit.getAppContext(),key,Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param key
     */
    public static void toastLong(String key){
        Toast.makeText(UiKit.getAppContext(),key,Toast.LENGTH_LONG).show();
    }
}
