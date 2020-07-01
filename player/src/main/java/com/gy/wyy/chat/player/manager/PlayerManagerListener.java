package com.gy.wyy.chat.player.manager;

import android.media.MediaPlayer;

/**
 * @author 王永勇
 * @// TODO: 2020/6/28
 */
public interface PlayerManagerListener {

    /**
     *
     * @param mediaPlayer
     */
    void onPrepared(MediaPlayer mediaPlayer);

    /**
     *
     */
    void onCompletion();

    /**
     *
     * @param percent
     */
    void onBuffering(int percent);

    /**
     *
     * @param what
     */
    void onError(int what);

    /**
     *
     * @param what
     */
    void onInfo(int what);

    /**
     *
     * @param width
     * @param height
     */
    void onSizeChanged(int width, int height);
}
