package com.gy.wyy.chat.ui.home;

import java.io.Serializable;

/**
 *
 */
public class HomeEntity implements Serializable {

    public static final int HOME_HOLDER_IMAGE = 101;
    public static final int HOME_HOLDER_VIDEO = 102;

    private String url;
    private int viewType = HOME_HOLDER_IMAGE;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public HomeEntity() {
    }

    public HomeEntity(String url, int viewType) {
        this.url = url;
        this.viewType = viewType;
    }

    @Override
    public String toString() {
        return "HomeEntity{" +
                "url='" + url + '\'' +
                ", viewType=" + viewType +
                '}';
    }
}
