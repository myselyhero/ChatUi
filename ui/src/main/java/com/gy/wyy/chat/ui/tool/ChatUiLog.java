package com.gy.wyy.chat.ui.tool;

import android.util.Log;

/**
 *
 */
public class ChatUiLog {

    private static final String TAG = "ChatUi-";

    private static String mixTag(String tag) {
        return TAG + tag;
    }

    /**
     * 打印INFO级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void v(String strTag, Object strInfo) {
        Log.v(mixTag(strTag), strInfo.toString());
    }

    /**
     * 打印DEBUG级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void d(String strTag, Object strInfo) {
        Log.d(mixTag(strTag), strInfo.toString());
    }

    /**
     * 打印INFO级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void i(String strTag, Object strInfo) {
        Log.i(mixTag(strTag), strInfo.toString());
    }

    /**
     * 打印WARN级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void w(String strTag, Object strInfo) {
        Log.w(mixTag(strTag), strInfo.toString());
    }

    /**
     * 打印WARN级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void w(String strTag, Object strInfo, Throwable e) {
        Log.w(mixTag(strTag), strInfo + e.getMessage());
    }

    /**
     * 打印ERROR级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void e(String strTag, Object strInfo) {
        Log.e(mixTag(strTag), strInfo.toString());
    }
}
