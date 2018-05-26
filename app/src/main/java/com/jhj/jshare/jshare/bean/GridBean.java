package com.jhj.jshare.jshare.bean;

import java.io.Serializable;

/**
 * @author吕志豪 .
 * @date 17-12-20  下午4:08.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class GridBean implements Serializable{
    private String title;
    private int resId;
    private int pos;

    public GridBean(String title, int resId) {
        this.title = title;
        this.resId = resId;
    }

    public GridBean(String title, int resId, int pos) {
        this.title = title;
        this.resId = resId;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
