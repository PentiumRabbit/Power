package com.android.netconnect.http;

/**
 * 其他类实现需要加static,防止内存泄露
 */
public interface INetCallBack<T> {

    void onNetCache(int msgId, T messageInfo);

    void onNetSuccess(int msgId, T messageInfo);

    void onNetError(int msgId, int errorCode);

}
