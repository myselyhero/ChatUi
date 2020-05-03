package com.gy.wyy.chat.ui.input;

import android.widget.EditText;

/**
 *
 */
public interface InputLayoutInterface {

    /**
     * disable 语音输入后，会隐藏按钮
     *
     * @param disable
     */
    void disableAudioInput(boolean disable);

    /**
     * disable 表情输入后，会隐藏按钮
     *
     * @param disable
     */
    void disableFaceInput(boolean disable);

    /**
     * disable 更多功能后，会隐藏按钮
     *
     * @param disable
     */
    void disableMoreInput(boolean disable);

    /**
     * 获取输入框View
     *
     * @return 输入框EditText
     */
    EditText getInputText();
}
