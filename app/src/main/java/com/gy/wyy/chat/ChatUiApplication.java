package com.gy.wyy.chat;

import android.app.Application;

import com.gy.wyy.chat.player.PlayerKit;
import com.gy.wyy.chat.ui.UiKit;

/**
 *
 */
public class ChatUiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UiKit.initKit(this);
        PlayerKit.init(this);
    }
}
