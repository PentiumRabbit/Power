package com.android.netconnect.http;

/**
 * 其他类实现需要加static,防止内存泄露
 */
public interface IAsyncCallBack {

    void callback_pre_loadcache(int msgId, Object messageInfo);

    void callback_success(int msgId, Object messageInfo);

    void callback_error(int msgId, int errorCode);

}
