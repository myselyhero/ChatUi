package com.gy.wyy.chat.ui;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
public class MessageEntity implements Serializable {

    /**
     * 消息類型
     */
    public static final int MESSAGE_ENTITY_TEXT = 101;
    public static final int MESSAGE_ENTITY_IMAGE = 102;//图片

    /**
     * 正常状态
     */
    public static final int MESSAGE_STATUS_NORMAL = 0;
    /**
     * 发送中状态
     */
    public static final int MESSAGE_STATUS_SENDING = 1;
    /**
     * 发送成功状态
     */
    public static final int MESSAGE_STATUS_SEND_SUCCESS = 2;
    /**
     * 发送失败状态
     */
    public static final int MESSAGE_STATUS_SEND_FAIL = 3;

    private String id = UUID.randomUUID().toString();
    private String formUser;
    private int msgType = MESSAGE_ENTITY_TEXT;
    private int status = MESSAGE_STATUS_NORMAL;
    private boolean self;
    private long msgTime = System.currentTimeMillis();
    private String text;
    private String dataPath;

    public String getId() {
        return id;
    }

    public String getFormUser() {
        return formUser;
    }

    public void setFormUser(String formUser) {
        this.formUser = formUser;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSelf() {
        return self;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id='" + id + '\'' +
                ", formUser='" + formUser + '\'' +
                ", msgType=" + msgType +
                ", status=" + status +
                ", self=" + self +
                ", msgTime=" + msgTime +
                ", text='" + text + '\'' +
                ", dataPath='" + dataPath + '\'' +
                '}';
    }
}
