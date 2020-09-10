package com.gy.wyy.chat.ui.model;

import com.gy.wyy.chat.ui.MessageAdapter;
import com.gy.wyy.chat.ui.MessageEntity;

import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/9
 */
public interface MessageProviderInterface {

    /**
     * 获取具体的数据集合
     *
     * @return
     */
    List<MessageEntity> getDataSource();

    /**
     *  设置数据源
     * @param list
     */
    void setDataSource(List<MessageEntity> list);

    /**
     *
     * @param entity
     */
    void addItem(MessageEntity entity);

    /**
     *  删除单个数据
     * @param index
     * @return
     */
    boolean deleteItem(int index);

    /**
     * 通过ID删除
     * @param id
     * @return
     */
    boolean deleteItem(String id);

    /**
     * 更新单个条目
     * @param entity
     * @return
     */
    boolean updateItem(MessageEntity entity);


    /**
     * 绑定适配器时触发的调用
     *
     * @param adapter 适配器
     * @return
     */

    void attachAdapter(MessageAdapter adapter);
}
