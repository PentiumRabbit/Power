/*
 * @Title:  HttpLoader.java
 * @package_name:  com.stormtv.market.network
 * @Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 * @author:  ----zhaoruyang----
 * @data:  2014-7-24 下午2:35:02
 * @version:  V1.0
 *
 */

package com.android.netconnect.http;

import com.android.netconnect.bean.Request;
import com.android.netconnect.engine.NetConfig;

import java.util.Map;

/**
 * HttpLoader
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014-7-24 下午2:35:02
 */

public class HttpLoader {
    private static volatile HttpLoader instance = null;
    private NetConfig config;


    // private constructor suppresses
    private HttpLoader() {
        //TODO 在全局配置中,可以自定义配置线程池
    }

    public static HttpLoader getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (HttpLoader.class) {
                if (instance == null) {
                    instance = new HttpLoader();
                }
            }
        }

        return instance;
    }

    /**
     * 在Application中初始化
     */
    public void init(NetConfig config) {
        this.config = config;
    }


    /**
     * 执行请求
     *
     * @param options
     *         选项
     * @param callBack
     *         回调
     */
    public void exeRequest(Request options, INetCallBack callBack) {
        if (config == null) {
            throw new NullPointerException("HttpLoader need ApplicationContext init");
        }
        Map<String, String> params    = options.getParams();
        Map<String, String> netParams = config.getNetParams();
        if (params != null && netParams != null) {
            params.putAll(netParams);
        }
        if (!options.isSync()) {
            config.getThreadPool().execute(new NetRunnable(options, callBack, config.getNetCacheDao()));
        } else {
            /*在调用该方法的线程中执行*/
            new NetRunnable(options, callBack, config.getNetCacheDao()).run();
        }

    }


    /**
     * 关闭所有任务
     */
    public void shutdownPool() {
        config.getThreadPool().shutdown();
    }

}
