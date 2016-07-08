package com.android.netconnect.bean;

import com.android.netconnect.database.NetSaveModel;
import com.android.netconnect.engine.ConnectMode;
import com.android.netconnect.engine.NetWork.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ----zhaoruyang----
 * @data: 2014/12/25
 */
public final class Request {
    private final int cacheId;
    private final NetSaveModel saveModel;
    private final RequestMethod method;
    private final String urlType;
    private final int threadPriority;
    private final Map<String, String> params;
    private final boolean isSync;
    private final Class castType;
    private final ConnectMode connectMode;


    private Request(Builder builder) {
        cacheId = builder.cacheId;
        saveModel = builder.saveModel;
        method = builder.method;
        urlType = builder.urlType;
        threadPriority = builder.threadPriority;
        params = builder.params;
        isSync = builder.isSync;
        castType = builder.castType;
        connectMode = builder.connectMode;
    }

    public static Request createSimple() {
        return new Builder().build();
    }

    public ConnectMode getConnectMode() {
        return connectMode;
    }

    public Class getCastType() {
        return castType;
    }

    public boolean isSync() {
        return isSync;
    }

    public int getCacheId() {
        return cacheId;
    }

    public String url() {
        return urlType;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public boolean saveCache() {
        return saveModel != NetSaveModel.no_cache;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public boolean readCache() {
        return saveModel != NetSaveModel.no_cache;
    }

    public NetSaveModel getSaveModel() {
        return saveModel;
    }

    public static class Builder {
        public String urlType;
        public Map<String, String> params = new HashMap<>();
        public boolean isSync = false;
        /*默认强转的类型为String*/
        public Class castType = String.class;
        /*默认HttpClient*/
        public ConnectMode connectMode = ConnectMode.connect_ok;
        private int cacheId = 0;
        private NetSaveModel saveModel = NetSaveModel.no_cache;
        private RequestMethod method = RequestMethod.POST;
        private int threadPriority = android.os.Process.THREAD_PRIORITY_BACKGROUND;

        public Builder() {

        }

        public Builder connectMode(ConnectMode connectMode) {
            this.connectMode = connectMode;
            return this;
        }

        public Builder setCacheId(int cacheId) {
            this.cacheId = cacheId;
            return this;
        }

        public Builder saveModel(NetSaveModel saveModel) {
            this.saveModel = saveModel;
            return this;
        }

        public Builder method(RequestMethod method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.urlType = url;
            return this;
        }

        public Builder threadPriority(int threadPriority) {
            this.threadPriority = threadPriority;
            return this;
        }

        public Builder addParams(Map<String, String> params) {
            this.params.putAll(params);
            return this;
        }

        public Builder addParams(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        public Builder sync(boolean isSync) {
            this.isSync = isSync;
            return this;
        }

        public Builder setCastType(Class castType) {
            this.castType = castType;
            return this;
        }

        /**
         * Sets all options equal to incoming options
         */
        public Builder cloneFrom(Request options) {
            cacheId = options.cacheId;
            saveModel = options.saveModel;
            method = options.method;
            urlType = options.urlType;
            threadPriority = options.threadPriority;
            params = options.params;
            isSync = options.isSync;
            castType = options.castType;
            connectMode = options.connectMode;

            return this;
        }


        public Request build() {
            return new Request(this);
        }


    }
}
