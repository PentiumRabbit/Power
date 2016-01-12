package com.android.netconnect.http;

/**
 * 其他类实现需要加static,防止内存泄露
 */
public interface INetCallBack {

    void callback_cache(int msgId, Object messageInfo);

    void callback_success(int msgId, Object messageInfo);

    void callback_error(int msgId, int errorCode);

}
