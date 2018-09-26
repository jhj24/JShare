package com.jhj.jshare.jshare.bean;

import java.io.Serializable;

/**
 * 分享图标
 * Created by jhj on 18-5-25.
 */

public class GridBean implements Serializable {
    private int icon;
    private String title;
    private int position;

    public GridBean(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public GridBean(String title, int icon, int position) {
        this.title = title;
        this.icon = icon;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
