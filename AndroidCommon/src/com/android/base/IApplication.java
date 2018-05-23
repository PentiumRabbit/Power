package com.android.base;

import android.app.Application;
import android.support.annotation.CallSuper;

/**
 * 抽象的application接口
 * @author : ZhaoRuYang
 * @date : 2018/5/23
 */
public interface IApplication {

    void setApplication(Application application);

    void attachBaseContext();

    void onCreate() ;
}
