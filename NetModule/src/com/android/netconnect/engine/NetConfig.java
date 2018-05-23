package com.android.netconnect.engine;

import android.content.Context;

import com.android.netconnect.database.INetCacheDao;
import com.android.netconnect.database.NetCacheDao;
import com.android.netconnect.util.ExecutorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 初始化网络加载全局配置
 *
 * @author ----zhaoruyang----
 * @data: 2015/4/23
 */
public final class NetConfig {
    private final Context context;
    private final Map<String, String> netParams;
    private final ExecutorService threadPool;
    private final INetCacheDao netCacheDao;

    private NetConfig(Builder builder) {
        context = builder.context;
        netParams = builder.netParams;
        threadPool = builder.threadPool;
        netCacheDao = builder.netCacheDao;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public INetCacheDao getNetCacheDao() {
        return netCacheDao;
    }

    public Context getContext() {
        return context;
    }

    public Map<String, String> getNetParams() {
        return netParams;
    }

    public static class Builder {


        private Context context;
        public Map<String, String> netParams = new HashMap<>();
        public ExecutorService threadPool = ExecutorUtil.getDefultPool();
        public INetCacheDao netCacheDao;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
            netCacheDao =new NetCacheDao(this.context);
        }


        public Builder setNetDao(INetCacheDao netCacheDao) {
            this.netCacheDao = netCacheDao;
            return this;
        }

        public Builder setNetParams(Map<String, String> netParams) {
            this.netParams = netParams;
            return this;
        }

        public Builder setThreadPool(ExecutorService threadPool) {
            this.threadPool = threadPool;
            return this;
        }


        public NetConfig build() {
            return new NetConfig(this);
        }

    }
}
