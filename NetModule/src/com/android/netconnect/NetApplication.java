package com.android.netconnect;

import android.app.Application;

import com.android.base.IApplication;
import com.android.netconnect.engine.NetConfig;
import com.android.netconnect.http.HttpLoader;

/**
 * @author : ZhaoRuYang
 * @date : 2018/5/23
 */
public class NetApplication extends IApplication {

    @Override
    public void onCreate() {
        initHttpLoader();
    }

    /*初始化HttpLoader*/
    private void initHttpLoader() {
        NetConfig netConfig = new NetConfig.Builder(application).setNetParams(null).build();
        HttpLoader.getInstance().init(netConfig);
    }
}
