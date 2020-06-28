package com.gy.wyy.chat.ui.message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gy.wyy.chat.ui.MessageEntity;

import java.util.ArrayList;
import java.util.List;

public class MessageViewModel extends ViewModel {

    private MutableLiveData<List<MessageEntity>> liveData = new MutableLiveData<>();
    private List<MessageEntity> dataSource = new ArrayList<>();

    public MessageViewModel() {
        for (int i = 0; i < 10; i++) {
            MessageEntity entity = new MessageEntity();
            if (i % 2 != 0){//奇数
                entity.setSelf(true);
                entity.setText("哈哈"+i);
            }else {
                entity.setSelf(false);
                entity.setMsgType(MessageEntity.MESSAGE_ENTITY_IMAGE);
                entity.setDataPath("http://pic2.zhimg.com/50/v2-67f162fdc821545edf25ba0dce14bb0d_hd.jpg");
            }
            dataSource.add(entity);
        }
        liveData.setValue(dataSource);
    }

    /**
     *
     * @return
     */
    public MutableLiveData<List<MessageEntity>> getLiveData() {
        return liveData;
    }
}