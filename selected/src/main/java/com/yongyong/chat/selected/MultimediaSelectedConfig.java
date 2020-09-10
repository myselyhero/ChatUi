package com.yongyong.chat.selected;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaSelectedConfig implements Parcelable {

    /**
     *
     */
    private static MultimediaSelectedConfig parameter = new MultimediaSelectedConfig();

    public MultimediaSelectedConfig(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(maxNum);
        dest.writeByte((byte) (only ? 1 : 0));
        dest.writeByte((byte) (crop ? 1 : 0));
        dest.writeByte((byte) (compress ? 1 : 0));
        dest.writeByte((byte) (camera ? 1 : 0));
        dest.writeByte((byte) (gif ? 1 : 0));
        dest.writeLong(minDuration);
        dest.writeLong(maxDuration);
    }

    protected MultimediaSelectedConfig(Parcel in) {
        maxNum = in.readInt();
        only = in.readByte() != 0;
        crop = in.readByte() != 0;
        compress = in.readByte() != 0;
        camera = in.readByte() != 0;
        gif = in.readByte() != 0;
        minDuration = in.readLong();
        maxDuration = in.readLong();
    }

    public static final Creator<MultimediaSelectedConfig> CREATOR = new Creator<MultimediaSelectedConfig>() {
        @Override
        public MultimediaSelectedConfig createFromParcel(Parcel in) {
            return new MultimediaSelectedConfig(in);
        }

        @Override
        public MultimediaSelectedConfig[] newArray(int size) {
            return new MultimediaSelectedConfig[size];
        }
    };

    /**
     *
     * @return
     */
    public static MultimediaSelectedConfig getInstance() {
        if (parameter == null)
            parameter = new MultimediaSelectedConfig();
        return parameter;
    }

    /**
     * 选择类型
     */
    private MultimediaSelectedType multimediaType = MultimediaSelectedType.All;

    /**
     * 最大选择数量
     */
    private int maxNum = 9;

    /**
     * 单选（设置此参数时最大数量不可用）
     */
    private boolean only;

    /**
     * 剪切
     */
    private boolean crop;

    /**
     * 压缩
     */
    private boolean compress;

    /**
     * 是否可拍照
     */
    private boolean camera;

    /**
     * 是否显示gif
     */
    private boolean gif;

    /**
     * 最小时长
     */
    private long minDuration = 3;

    /**
     * 最大时长
     */
    private long maxDuration = 15;

    /**
     *
     */
    public static MultimediaSelectedResultListener resultListener;


    public MultimediaSelectedType getMultimediaType() {
        return multimediaType;
    }

    public void setMultimediaType(MultimediaSelectedType multimediaType) {
        this.multimediaType = multimediaType;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public boolean isOnly() {
        return only;
    }

    public void setOnly(boolean only) {
        this.only = only;
    }

    public boolean isCrop() {
        return crop;
    }

    public void setCrop(boolean crop) {
        this.crop = crop;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public boolean isCamera() {
        return camera;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }

    public boolean isGif() {
        return gif;
    }

    public void setGif(boolean gif) {
        this.gif = gif;
    }

    public long getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(long minDuration) {
        this.minDuration = minDuration;
    }

    public long getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(long maxDuration) {
        this.maxDuration = maxDuration;
    }

    @Override
    public String toString() {
        return "MultimediaSelectedConfig{" +
                "multimediaType=" + multimediaType +
                ", maxNum=" + maxNum +
                ", only=" + only +
                ", crop=" + crop +
                ", compress=" + compress +
                ", camera=" + camera +
                ", gif=" + gif +
                ", minDuration=" + minDuration +
                ", maxDuration=" + maxDuration +
                '}';
    }
}
