package com.android.netconnect.engine;

import android.content.Context;

import java.util.Map;

/**
 * 初始化网络加载全局配置
 *
 * @author ----zhaoruyang----
 * @data: 2015/4/23
 */
public class NetConfig {
    private final Context context;
    private final Map<String, String> netParams;

    private NetConfig(Builder builder) {
        context = builder.context;
        netParams = builder.netParams;
    }

    public Context getContext() {
        return context;
    }

    public Map<String, String> getNetParams() {
        return netParams;
    }

    public static class Builder {


        private Context context;
        public Map<String, String> netParams;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        public Builder setNetParams(Map<String, String> netParams) {
            this.netParams = netParams;
            return this;
        }

        /**
         * Sets all options equal to incoming options
         */
        public Builder cloneFrom(NetConfig options) {

            return this;
        }


        public NetConfig build() {
            return new NetConfig(this);
        }

    }
}
