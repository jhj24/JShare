package com.jhj.jshare;

import android.app.Application;

import cn.jiguang.share.android.api.JShareInterface;

/**
 * @author吕志豪 .
 * @date 18-4-14  上午10:05.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class MyApplication extends Application {

    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JShareInterface.init(this);
    }
}
