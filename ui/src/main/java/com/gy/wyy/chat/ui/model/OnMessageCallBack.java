package com.gy.wyy.chat.ui.model;

/**
 * @author 王永勇
 * @// TODO: 2020/7/9
 */
public interface OnMessageCallBack {

    /**
     *
     * @param object
     */
    void onSuccess(Object object);

    /**
     *
     * @param code
     */
    void onError(int code);
}
