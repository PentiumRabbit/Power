package com.android.base;

import android.app.Application;
import android.content.Context;

/**
 * @author : ZhaoRuYang
 * @date : 2018/5/23
 */
public class BaseApp extends Application {

    private ModulesApplication modulesApplication;

    public BaseApp() {
        modulesApplication=new ModulesApplication();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        modulesApplication.attachBaseContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        modulesApplication.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
