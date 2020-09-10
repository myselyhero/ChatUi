package com.gy.wyy.chat.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.gy.wyy.chat.ui.model.OnMessageCallBack;
import com.gy.wyy.chat.ui.tool.ChatUiSharedPreferenceUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/9
 */
public class MessageManagerKit {

    private String TAG = MessageManagerKit.class.getSimpleName();

    private static MessageManagerKit managerKit;

    /**
     *
     */
    private MessageProvider mProvider;

    /**
     * 缓存
     */
    private SharedPreferences mPreferences;

    /**
     *
     */
    private void init(){
        if (mProvider == null)
            mProvider = new MessageProvider();
        if (mPreferences == null)
            mPreferences = UiKit.getAppContext().getSharedPreferences(ChatUiParameter.MESSAGE_CACHE_FILE, Context.MODE_PRIVATE);
    }

    /**
     *
     * @return
     */
    public static MessageManagerKit getInstance(){
        if (managerKit == null)
            managerKit = new MessageManagerKit();
        return managerKit;
    }

    /**
     *
     * @param callBack
     */
    public void get(OnMessageCallBack callBack) {
        if (callBack == null)
            return;
        init();
        List<MessageEntity> list = ChatUiSharedPreferenceUtils.getListData(mPreferences, ChatUiParameter.MESSAGE_CACHE_FILE, MessageEntity.class);
        mProvider.setDataSource(list);
        callBack.onSuccess(mProvider);
    }

    /**
     *
     * @param entity
     */
    private void put(MessageEntity entity){
        if (entity == null)
            return;
        mProvider.addItem(entity);
        LinkedList<MessageEntity> linkedList = new LinkedList<>(mProvider.getDataSource());
        ChatUiSharedPreferenceUtils.putListData(mPreferences, ChatUiParameter.MESSAGE_CACHE_FILE, linkedList);
    }
}
