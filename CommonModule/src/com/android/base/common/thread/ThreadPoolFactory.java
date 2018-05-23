/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.thread;

/**
 * 构建不同线程池的工厂
 *
 * @author ----zhaoruyang----
 * @data: 2014/11/17
 */
public class ThreadPoolFactory {

    private static volatile ThreadPoolFactory instance = null;

    // private constructor suppresses
    private ThreadPoolFactory() {
    }

    public static ThreadPoolFactory getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (ThreadPoolFactory.class) {
                if (instance == null) {
                    instance = new ThreadPoolFactory();
                }
            }
        }

        return instance;
    }
}
