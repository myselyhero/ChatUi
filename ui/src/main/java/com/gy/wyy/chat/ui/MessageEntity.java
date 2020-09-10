package com.gy.wyy.chat.ui;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
public class MessageEntity implements Serializable, Comparable<MessageEntity> {

    /**
     * 消息類型
     */

    /**
     * 文本
     */
    public static final int MESSAGE_ENTITY_TEXT = 101;

    /**
     * 图片
     */
    public static final int MESSAGE_ENTITY_IMAGE = 102;

    /**
     * 视频
     */
    public static final int MESSAGE_ENTITY_VIDEO = 103;

    /**
     * 语音
     */
    public static final int MESSAGE_ENTITY_AUDIO = 104;

    /**
     * 位置
     */
    public static final int MESSAGE_ENTITY_LOCATION= 105;

    /**
     * 红包
     */
    public static final int MESSAGE_ENTITY_PACKAGE = 106;

    /**
     * 转账
     */
    public static final int MESSAGE_ENTITY_TRANSFER = 107;

    /**
     * 文件
     */
    public static final int MESSAGE_ENTITY_FILE = 108;

    /**
     * 收藏
     */
    public static final int MESSAGE_ENTITY_COLLECT = 109;

    /**
     * 语音通话
     */
    public static final int MESSAGE_ENTITY_AUDIO_CALL = 110;

    /**
     * 视频通话
     */
    public static final int MESSAGE_ENTITY_VIDEO_CALL = 111;

    /**
     * 提示
     */
    public static final int MESSAGE_ENTITY_VIDEO_TIPS = 112;

    private String id = UUID.randomUUID().toString();

    /* 消息发送者 */
    private String formUser;

    /* 消息类型 */
    private int msgType = MESSAGE_ENTITY_TEXT;
    private MessageStatus status = MessageStatus.NORMAL;
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

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
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

    @Override
    public int compareTo(MessageEntity o) {
        return 0;
    }
}
