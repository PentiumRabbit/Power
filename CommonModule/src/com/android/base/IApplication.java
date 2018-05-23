package com.android.base;

import android.app.Application;

/**
 * 抽象的application接口
 * @author : ZhaoRuYang
 * @date : 2018/5/23
 */
public abstract class IApplication {
    protected Application application;


    public void setApplication(Application application) {
        this.application = application;

    }

    public void attachBaseContext() {
    }

    public void onCreate() {

    }
}
