package com.jhj.jshare.jshare.bean;

import java.io.Serializable;

/**
 * @author吕志豪 .
 * @date 18-2-2  上午9:43.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class ImgShareBuilder extends BaseShareBuilder  {
    //路径至少有一个
    private String imageUrl;//网路路径
    private String imagePath;//本地路径


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
