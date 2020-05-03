package com.gy.wyy.chat.ui.face;

import android.graphics.Bitmap;

import com.gy.wyy.chat.ui.tool.ScreenUtil;

import java.io.Serializable;

public class Emoji implements Serializable {

    private static final int defaultSize = ScreenUtil.getPxByDp(32);
    private String desc;
    private String filter;
    private Bitmap icon;
    private int width = defaultSize;
    private int height = defaultSize;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
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

    @Override
    public String toString() {
        return "Emoji{" +
                "desc='" + desc + '\'' +
                ", filter='" + filter + '\'' +
                ", icon=" + icon +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
