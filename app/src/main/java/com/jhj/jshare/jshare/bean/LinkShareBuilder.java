package com.jhj.jshare.jshare.bean;


/**
 * 分享链接
 * Created by jhj on 18-5-25.
 */
public class LinkShareBuilder extends BaseShareBuilder {

    private String id;
    private String text;
    private String title;//Qzone(是)
    private String url;
    private String imageUrl;
    private String imagePath;


    public String getId() {
        return id;
    }

    public LinkShareBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public LinkShareBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public LinkShareBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public LinkShareBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LinkShareBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public LinkShareBuilder setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }
}
