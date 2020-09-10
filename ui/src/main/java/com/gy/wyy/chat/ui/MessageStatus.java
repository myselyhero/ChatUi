package com.gy.wyy.chat.ui;

import java.io.Serializable;

/**
 * @author 王永勇
 * @// TODO: 2020/7/9
 */
public enum MessageStatus implements Serializable {

    NORMAL,
    SENDING,
    SEND_SUCCESS,
    SEND_FAIL,
    DELETE,
    REVOKE,
    UN_DOWNLOAD,
    DOWNLOADING,
    DOWNLOADED
}
