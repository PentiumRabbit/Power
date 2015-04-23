/*
 * @Title:  AsyncHttpWraper.java
 * @package_name:  com.stormtv.market.network
 * @Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 * @Description:  TODO<请描述此文件是做什么的>
 * @author:  ----zhaoruyang----
 * @data:  2014-7-24 下午2:35:02
 * @version:  V1.0
 *
 */

package com.android.netconnect.http;

import android.content.Context;

import com.android.netconnect.database.NetCacheDao;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AsyncHttpWraper
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014-7-24 下午2:35:02
 */

public class HttpLoader {
    private static volatile HttpLoader instance = null;
    private ExecutorService cachedThreadPool;
    private NetCacheDao netCacheDao;
    private Context context;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncHttpWraper #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<>(128);
    private Map<String, String> initParams;


    // private constructor suppresses
    private HttpLoader() {
        //        corePoolSize - 池中所保存的线程数，包括空闲线程。
        //        maximumPoolSize - 池中允许的最大线程数。
        //        keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
        //        unit - keepAliveTime 参数的时间单位。
        //        workQueue - 执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的 Runnable 任务。
        //        抛出：
        //        IllegalArgumentException - 如果 corePoolSize 或 keepAliveTime 小于零，
        //        或者 maximumPoolSize 小于或等于零，或者 corePoolSize 大于 maximumPoolSize。
        //        NullPointerException - 如果 workQueue 为 null
        cachedThreadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                sPoolWorkQueue,
                sThreadFactory,
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

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
     *
     * @param context
     *         全局的Context
     */
    public void initAsyncHttp(Context context) {
        this.context = context;
        netCacheDao = NetCacheDao.getInstance(context);
    }

    /**
     * 初始化共享参数
     */
    public void initNetParams(Map<String, String> params) {
        this.initParams = params;
    }

    /**
     * 执行请求
     *
     * @param options
     *         选项
     * @param callBack
     *         回调
     */
    public void exeRequest(NetOptions options, IAsyncCallBack callBack) {
        if (netCacheDao == null || context == null) {
            throw new NullPointerException("HttpLoader need ApplicationContext init");
        }
        Map<String, String> params = options.getParams();
        if (initParams != null && params != null) {
            params.putAll(initParams);
        }
//        params.put(NetConstant.PARAM_SCREEN, "l");
//        params.put(NetConstant.PARAM_VER, "1.6.4");
//        params.put(NetConstant.PARAM_PLATF, "android");
//        params.put(NetConstant.PARAM_IMEI, SysInfoUtil.getIMEI(context));
        if (!options.isSync()) {
            cachedThreadPool.execute(new NetRunnable(options, callBack, netCacheDao));
        } else {
            new NetRunnable(options, callBack, netCacheDao).run();
        }

    }


    /**
     * 关闭所有任务
     */
    public void shutdownPool() {
        cachedThreadPool.shutdown();
    }

}
