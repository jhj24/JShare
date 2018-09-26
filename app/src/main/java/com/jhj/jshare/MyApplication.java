package com.jhj.jshare;

import android.app.Application;

import cn.jiguang.share.android.api.JShareInterface;

/**
 * Application
 * Created by jhj on 18-5-25.
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
