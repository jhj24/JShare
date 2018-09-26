package com.jhj.jshare.jshare.bean;

import java.io.Serializable;

/**
 * 分享的基类
 * Created by jhj on 18-5-25.
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
