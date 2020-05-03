package com.gy.wyy.chat.ui.model;

import android.graphics.drawable.Drawable;

/**
 *
 */
public interface MessagePropertiesInterface {

    /**
     * 设置右边聊天气泡的背景
     *
     * @param drawable
     */
    void setRightBubble(Drawable drawable);

    /**
     *
     * @return
     */
    Drawable getRightBubble();

    /**
     * 设置左边聊天气泡的背景
     *
     * @param drawable
     */
    void setLeftBubble(Drawable drawable);

    /**
     *
     * @return
     */
    Drawable getLeftBubble();

    /**
     * 设置右边聊天内容字体颜色
     *
     * @param color
     */
    void setRightContentFontColor(int color);

    /**
     *
     * @return
     */
    int getRightContentFontColor();

    /**
     * 设置右边聊天内容字体大小
     * @param size
     */
    void setRightContentFontSize(int size);

    /**
     *
     * @return
     */
    int getRightContentFontSize();

    /**
     * 设置左边聊天内容字体颜色
     *
     * @param color
     */
    void setLeftContentFontColor(int color);

    /**
     *
     * @return
     */
    int getLeftContentFontColor();

    /**
     * 设置左边聊天内容字体大小
     * @param size
     */
    void setLeftContentFontSize(int size);

    /**
     *
     * @return
     */
    int getLeftContentFontSize();

    /**
     * 设置聊天时间的背景
     *
     * @param drawable
     */
    void setTimeBubble(Drawable drawable);

    /**
     *
     * @return
     */
    Drawable getTimeBubble();

    /**
     * 设置聊天时间的字体大小
     *
     * @param size
     */
    void setTimeFontSize(int size);

    /**
     *
     * @return
     */
    int getTimeFontSize();

    /**
     * 设置聊天时间的字体颜色
     *
     * @param color
     */
    void setTimeFontColor(int color);

    /**
     *
     * @return
     */
    int getTimeFontColor();
}
