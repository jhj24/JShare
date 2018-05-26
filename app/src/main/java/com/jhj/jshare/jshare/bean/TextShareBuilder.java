package com.jhj.jshare.jshare.bean;

import java.io.Serializable;

/**
 * @author吕志豪 .
 * @date 18-2-2  上午9:43.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class TextShareBuilder extends BaseShareBuilder  {

   private String title;
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

}
