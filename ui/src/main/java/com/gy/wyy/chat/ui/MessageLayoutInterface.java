package com.gy.wyy.chat.ui;

import com.gy.wyy.chat.ui.model.MessagePropertiesInterface;
import com.gy.wyy.chat.ui.model.OnMessageLayoutItemClickListener;

/**
 *
 */
public interface MessageLayoutInterface extends MessagePropertiesInterface {

    /**
     *
     * @param messageAdapter
     */
    void setAdapter(MessageAdapter messageAdapter);

    /**
     *
     * @param layoutItemClickListener
     */
    void setOnItemClick(OnMessageLayoutItemClickListener layoutItemClickListener);
}
