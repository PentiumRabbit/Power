package com.zry.net;

import com.zry.base.IApplication;
import com.zry.net.engine.NetConfig;
import com.zry.net.http.HttpLoader;

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
