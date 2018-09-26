package com.jhj.jshare.jshare.bean;

/**
 * 分享文本
 * Created by jhj on 18-5-25.
 */

public class TextShareBuilder extends BaseShareBuilder {

    private String title;

    /**
     * sina(是)、QZone(否)
     */
    private String text;


    public String getTitle() {
        return title;
    }

    public TextShareBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public TextShareBuilder setText(String text) {
        this.text = text;
        return this;
    }
    /* text
     * title
     *
     */

}
