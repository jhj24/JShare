package com.jhj.jshare;

import android.app.Application;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatformConfig;

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

        //------极光分享-------
        JShareInterface.setDebugMode(true);
        PlatformConfig shareConfig = new PlatformConfig();
        shareConfig.setQQ("1106544832", "1xE6T3nBMibIO5fT");
        shareConfig.setWechat("wx225929aa256e2053", "980d8583d596984fb5c162f6baf1f215");
        shareConfig.setSinaWeibo("3719457495", "06de570974a5aa3568a19dbdeaae603d", "https://www.eqidd.com");
        JShareInterface.init(this, shareConfig);

    }
}
