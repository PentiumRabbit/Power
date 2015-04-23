package com.android.netconnect.http;

import com.android.netconnect.NetConstant;
import com.android.netconnect.engine.NetWork.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ----zhaoruyang----
 * @data: 2014/12/25
 */
public final class NetOptions {
    private final int cacheId;
    private final int saveModel;
    private final RequestMethod method;
    private final Protocol.ProtocolType urlType;
    private final int threadPriority;
    private final Map<String, String> params;
    private final boolean isSync;
    private final Class castType;


    /* 不缓存 */
    public static final int CACHE_TAG_NO_CACHE = 0x00000001;
    /* 退出清理 */
    public static final int CACHE_TAG_CLEAR = 0x00000002;
    /* 一直保留 */
    public static final int CACHE_TAG_SAVE = 0x00000003;

    private NetOptions(Builder builder) {
        cacheId = builder.cacheId;
        saveModel = builder.saveModel;
        method = builder.method;
        urlType = builder.urlType;
        threadPriority = builder.threadPriority;
        params = builder.params;
        isSync = builder.isSync;
        castType = builder.castType;
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
        return saveModel != CACHE_TAG_NO_CACHE;
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
        return saveModel != CACHE_TAG_NO_CACHE;
    }

    public int getSaveModel() {
        return saveModel;
    }

    public static class Builder {
        private int cacheId = 0;
        private int saveModel = 0;
        private RequestMethod method = RequestMethod.POST;
        public Protocol.ProtocolType urlType;
        private int threadPriority = android.os.Process.THREAD_PRIORITY_BACKGROUND;
        public Map<String, String> params = new HashMap<>();
        public boolean isSync = false;
        /*默认强转的类型为String*/
        public Class castType = String.class;

        public Builder() {

        }

        public Builder setCacheId(int cacheId) {
            this.cacheId = cacheId;
            return this;
        }

        public Builder setSaveModel(int saveModel) {
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
        public Builder cloneFrom(NetOptions options) {
            cacheId = options.cacheId;
            saveModel = options.saveModel;
            method = options.method;
            return this;
        }


        public NetOptions build() {
            return new NetOptions(this);
        }


    }


    public static NetOptions createSimple() {
        return new Builder().build();
    }
}
