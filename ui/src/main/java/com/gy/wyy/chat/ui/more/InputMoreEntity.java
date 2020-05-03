package com.gy.wyy.chat.ui.more;

import java.io.Serializable;

/**
 *
 */
public class InputMoreEntity implements Serializable {

    private int resourceId;
    private String title;

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "InputMoreEntity{" +
                "resourceId=" + resourceId +
                ", title='" + title + '\'' +
                '}';
    }
}
