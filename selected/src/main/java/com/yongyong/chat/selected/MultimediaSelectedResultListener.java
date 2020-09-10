package com.yongyong.chat.selected;

import com.yongyong.chat.selected.entity.MultimediaSelectedEntity;

import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public interface MultimediaSelectedResultListener {

    /**
     *
     * @param list
     */
    void onSuccess(List<MultimediaSelectedEntity> list);

    /**
     *
     * @param code
     */
    void onError(int code);
}
