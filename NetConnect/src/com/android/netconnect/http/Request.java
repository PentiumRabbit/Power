package com.android.netconnect.http;

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
    private final Protocol.ProtocolType urlType;
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

    public Protocol.ProtocolType getUrlType() {
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

    public String getUrl() {
        return Protocol.generateUrl(urlType);
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
        public Protocol.ProtocolType urlType;
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

        public Builder setConnectMode(ConnectMode connectMode) {
            this.connectMode = connectMode;
            return this;
        }

        public Builder setCacheId(int cacheId) {
            this.cacheId = cacheId;
            return this;
        }

        public Builder setSaveModel(NetSaveModel saveModel) {
            this.saveModel = saveModel;
            return this;
        }

        public Builder setMethod(RequestMethod method) {
            this.method = method;
            return this;
        }

        public Builder setUrlType(Protocol.ProtocolType urlType) {
            this.urlType = urlType;
            return this;
        }

        public Builder setThreadPriority(int threadPriority) {
            this.threadPriority = threadPriority;
            return this;
        }

        public Builder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder setIsSync(boolean isSync) {
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