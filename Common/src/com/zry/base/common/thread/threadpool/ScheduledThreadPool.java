/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.base.common.thread.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 全局的周期性线程池(装饰类)
 *
 * @author ----zhaoruyang----
 * @data: 2014/11/14
 */
public class ScheduledThreadPool {


    private ScheduledExecutorService scheduledThreadPool;

    // private constructor suppresses
    private ScheduledThreadPool() {

        scheduledThreadPool = Executors.newScheduledThreadPool(5);

    }

    public static ScheduledThreadPool getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 定时执行任务
     *
     * @param runnable
     *         任务
     * @param initialDelay
     *         延迟的时间
     * @param period
     *         间隔时间(单位:毫秒)
     *
     * @return ScheduledFuture
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, int initialDelay, int period) {
        return scheduledThreadPool.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 延时执行任务
     *
     * @param runnable
     *         任务
     * @param initialDelay
     *         延迟的时间
     *
     * @return ScheduledFuture
     */
    public ScheduledFuture<?> schedule(Runnable runnable, int initialDelay) {
        return scheduledThreadPool.schedule(runnable, initialDelay, TimeUnit.MILLISECONDS);
    }

    private static class SingletonHolder {
        private static volatile ScheduledThreadPool instance = new ScheduledThreadPool();
    }

}
