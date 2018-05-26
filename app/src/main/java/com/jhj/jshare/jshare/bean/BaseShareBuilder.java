package com.jhj.jshare.jshare.bean;

import java.io.Serializable;

/**
 * @author吕志豪 .
 * @date 18-2-2  上午10:35.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public abstract class BaseShareBuilder implements Serializable {
    private String source; //来源
    private String sourceOwner; //来源所有者

    public String getSourceOwner() {
        return sourceOwner;
    }

    public BaseShareBuilder setSourceOwner(String sourceOwner) {
        this.sourceOwner = sourceOwner;
        return this;
    }

    public String getSource() {
        return source;
    }

    public BaseShareBuilder setSource(String source) {
        this.source = source;
        return this;
    }

}
