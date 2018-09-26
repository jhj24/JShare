package com.jhj.jshare.jshare.bean;

/**
 * 分享图片
 * Image路径至少选择一个
 * Created by jhj on 18-5-25.
 */

public class ImgShareBuilder extends BaseShareBuilder {

    private String text;
    /**
     * 网路路径
     */
    private String imageUrl;
    /**
     * 本地路径
     */
    private String imagePath;


    public String getText() {
        return text;
    }

    public ImgShareBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public ImgShareBuilder imgUrl(String imgUrl) {
        this.imageUrl = imgUrl;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ImgShareBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImgShareBuilder setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }


}
