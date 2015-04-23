package com.android.netconnect.engine.NetWork;

/**
 * @author ----zhaoruyang----
 * @data: 2015/4/23
 */
public class NetFactory {

    private static volatile NetFactory instance = null;

    // private constructor suppresses
    private NetFactory() {
    }

    public static NetFactory getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (NetFactory.class) {
                if (instance == null) {
                    instance = new NetFactory();
                }
            }
        }

        return instance;
    }
}
