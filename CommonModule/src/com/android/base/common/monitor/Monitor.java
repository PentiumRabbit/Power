package com.android.base.common.monitor;

/**
 * 监控类
 *
 * @author ----zhaoruyang----
 * @data: 1/22/16
 */
public class Monitor {

    private static volatile Monitor instance = null;

    // private constructor suppresses
    private Monitor() {
    }

    public static Monitor getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (Monitor.class) {
                if (instance == null) {
                    instance = new Monitor();
                }
            }
        }

        return instance;
    }
}
