package com.gy.wyy.chat.ui;

import android.graphics.drawable.Drawable;

import com.gy.wyy.chat.ui.model.MessagePropertiesInterface;

/**
 *
 */
public class MessageProperties implements MessagePropertiesInterface {

    /* 左边 */
    private int mLeftContextFontSize;
    private int mLeftContextFontColor;

    /* 右边的字体属性 */
    private int mRightContextFontSize;
    private int mRightContentFontColor;

    /* 聊天气泡 */
    private Drawable mRightBubble;
    private Drawable mLeftBubble;

    /* 聊天提示（预留） */
    private int mTipsMessageFontSize;
    private int mTipsMessageFontColor;
    private Drawable mTipsMessageBubble;

    /* 聊天时间 */
    private int mTimeFontSize;
    private int mTimeFontColor;
    private Drawable mTimeBubble;

    private static MessageProperties messageProperties = new MessageProperties();

    public static MessageProperties getInstance() {
        if (messageProperties == null) {
            messageProperties = new MessageProperties();
        }
        return messageProperties;
    }

    private MessageProperties() {

    }

    @Override
    public void setRightBubble(Drawable drawable) {
        mRightBubble = drawable;
    }

    @Override
    public Drawable getRightBubble() {
        return mRightBubble;
    }

    @Override
    public void setLeftBubble(Drawable drawable) {
        mLeftBubble = drawable;
    }

    @Override
    public Drawable getLeftBubble() {
        return mLeftBubble;
    }

    @Override
    public void setRightContentFontColor(int color) {
        mRightContentFontColor = color;
    }

    @Override
    public int getRightContentFontColor() {
        return mRightContentFontColor;
    }

    @Override
    public void setRightContentFontSize(int size) {
        mRightContextFontSize = size;
    }

    @Override
    public int getRightContentFontSize() {
        return mRightContextFontSize;
    }

    @Override
    public void setLeftContentFontColor(int color) {
        mLeftContextFontColor = color;
    }

    @Override
    public int getLeftContentFontColor() {
        return mLeftContextFontColor;
    }

    @Override
    public void setLeftContentFontSize(int size) {
        mLeftContextFontSize = size;
    }

    @Override
    public int getLeftContentFontSize() {
        return mLeftContextFontSize;
    }

    @Override
    public void setTimeBubble(Drawable drawable) {
        mTimeBubble = drawable;
    }

    @Override
    public Drawable getTimeBubble() {
        return mTimeBubble;
    }

    @Override
    public void setTimeFontSize(int size) {
        mTimeFontSize = size;
    }

    @Override
    public int getTimeFontSize() {
        return mTimeFontSize;
    }

    @Override
    public void setTimeFontColor(int color) {
        mTimeFontColor = color;
    }

    @Override
    public int getTimeFontColor() {
        return mTimeFontColor;
    }
}
