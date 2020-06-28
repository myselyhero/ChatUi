package com.gy.wyy.chat.ui.model;

import android.view.View;

import com.gy.wyy.chat.ui.MessageEntity;

/**
 *
 */
public interface OnMessageLayoutItemClickListener {

    /**
     *
     * @param view
     * @param position
     * @param entity
     */
    void onMessageClick(View view, int position, MessageEntity entity);

    /**
     *
     * @param view
     * @param position
     * @param entity
     */
    void onMessageLongClick(View view, int position, MessageEntity entity);
}
