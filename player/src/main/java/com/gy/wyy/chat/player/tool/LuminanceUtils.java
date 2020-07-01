package com.gy.wyy.chat.player.tool;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

/**
 * @author 王永勇
 *
 * 调节屏幕亮度的类
 *
 * @// TODO: 2020/7/1
 */
public class LuminanceUtils {

    private static final String TAG = LuminanceUtils.class.getSimpleName();

    /**
     * 判断是否开启了自动亮度调节
     * @param activity
     * @return
     */
    public static boolean isAutoBrightness(Activity activity) {
        boolean autoBrightness = false;
        ContentResolver aContentResolver = activity.getContentResolver();
        try {
            autoBrightness = Settings.System.getInt(aContentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Exception e) {
            Log.e(TAG, "判断是否开启了自动亮度调节失败:" + e.toString());
        }
        return autoBrightness;
    }

    /**
     * 改变亮度
     * @param activity
     * @param value
     */
    public static void setLightness(Activity activity, int value) {
        try {
            if (isAutoBrightness(activity)) {
                stopAutoBrightness(activity);
            }
            Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.screenBrightness = (value <= 0 ? 1 : value) / 255f;
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
            Log.e(TAG, "无法改变屏幕亮度:" + e.toString());
        }
    }

    /**
     * 获取亮度0~255
     * @param activity
     * @return
     */
    public static int getLightness(Activity activity) {
        return Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);
    }

    /**
     * 停止自动亮度调节
     * @param activity
     */
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 开启亮度自动调节
     * @param activity
     */
    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }
}
