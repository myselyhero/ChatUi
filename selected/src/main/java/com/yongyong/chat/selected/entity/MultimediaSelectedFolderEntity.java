package com.yongyong.chat.selected.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王永勇
 * @// TODO: 2020/7/7
 */
public class MultimediaSelectedFolderEntity implements Serializable, Comparable<MultimediaSelectedFolderEntity> {

    private String folder;
    private String path;
    private long bucketId = -1;
    private int num;
    private boolean checked;
    private List<MultimediaSelectedEntity> data;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<MultimediaSelectedEntity> getData() {
        if (data == null)
            data = new ArrayList<>();
        return data;
    }

    public void setData(List<MultimediaSelectedEntity> data) {
        this.data = data;
    }

    public void addData(MultimediaSelectedEntity entity){
        if (data == null)
            data = new ArrayList<>();

        data.add(entity);
    }

    public void addData(int index,MultimediaSelectedEntity entity){
        if (data == null)
            data = new ArrayList<>();

        data.add(index,entity);
    }

    @Override
    public String toString() {
        return "MultimediaSelectedFolderEntity{" +
                "folder='" + folder + '\'' +
                ", path='" + path + '\'' +
                ", bucketId=" + bucketId +
                ", num=" + num +
                ", checked=" + checked +
                ", data=" + data +
                '}';
    }

    @Override
    public int compareTo(MultimediaSelectedFolderEntity o) {
        return num > o.getNum() ? -1 : 1;
    }
}
