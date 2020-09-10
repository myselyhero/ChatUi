package com.yongyong.chat.selected.entity;

import java.io.Serializable;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaSelectedEntity implements Serializable {

    private String path;
    private String cutPath;
    private String compressPath;
    private long duration;
    private boolean isChecked;
    private String mimeType;
    private int width;
    private int height;
    private long size;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCutPath() {
        return cutPath;
    }

    public void setCutPath(String cutPath) {
        this.cutPath = cutPath;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "MultimediaSelectedEntity{" +
                "path='" + path + '\'' +
                ", cutPath='" + cutPath + '\'' +
                ", compressPath='" + compressPath + '\'' +
                ", duration=" + duration +
                ", isChecked=" + isChecked +
                ", mimeType='" + mimeType + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", size=" + size +
                '}';
    }
}
