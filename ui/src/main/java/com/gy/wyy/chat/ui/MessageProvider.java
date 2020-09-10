package com.gy.wyy.chat.ui;

import android.text.TextUtils;

import com.gy.wyy.chat.ui.model.MessageProviderInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/9
 */
public class MessageProvider implements MessageProviderInterface {

    private List<MessageEntity> dataSource = new ArrayList<>();
    private MessageAdapter mAdapter;

    @Override
    public List<MessageEntity> getDataSource() {
        return dataSource;
    }

    @Override
    public void setDataSource(List<MessageEntity> list) {
        if (list == null)
            return;
        dataSource.addAll(list);
    }

    @Override
    public void addItem(MessageEntity entity) {
        dataSource.add(entity);
        updateAdapter();
    }

    @Override
    public boolean deleteItem(int index) {
        if (dataSource.size() == 0)
            return false;
        dataSource.remove(index);
        updateAdapter();
        return true;
    }

    @Override
    public boolean deleteItem(String id) {
        boolean flag = false;
        for (int i = 0; i < dataSource.size(); i++) {
            if (TextUtils.equals(dataSource.get(i).getId(),id)){
                dataSource.remove(i);
                flag = true;
                break;
            }
        }
        if (flag)
            updateAdapter();
        return flag;
    }

    @Override
    public boolean updateItem(MessageEntity entity) {
        boolean flag = false;
        for (int i = 0; i < dataSource.size(); i++) {
            MessageEntity multimediaEntity = dataSource.get(i);
            if (TextUtils.equals(entity.getId(),multimediaEntity.getId())){
                dataSource.remove(i);
                dataSource.add(i,entity);
                flag = true;
                break;
            }
        }
        if (flag)
            updateAdapter();
        return flag;
    }


    @Override
    public void attachAdapter(MessageAdapter adapter) {
        mAdapter = adapter;
    }


    /**
     *
     */
    public void clear() {
        if (dataSource.size() != 0){
            dataSource.clear();
            updateAdapter();
        }
    }

    /**
     *
     */
    private void updateAdapter(){
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }
}
