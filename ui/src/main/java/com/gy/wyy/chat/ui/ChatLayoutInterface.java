package com.gy.wyy.chat.ui;

import com.gy.wyy.chat.ui.input.InputLayout;

/**
 *
 */
public interface ChatLayoutInterface {

    /**
     * 设置默认参数
     */
    void initDefault();

    /**
     * 获取消息布局
     * @return
     */
    MessageLayout getMessageLayout();

    /**
     *
     * @return
     */
    MessageAdapter getMessageAdapter();

    /**
     * 获取输入布局
     * @return
     */
    InputLayout getInputLayout();
}
