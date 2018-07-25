package com.zry.net.http;

/**
 * 其他类实现需要加static,防止内存泄露
 */
public interface INetCallBack<T> {

    void onNetCache(String key, T msg);

    void onNetSuccess(String key, T msg);

    void onNetError(String key, int errorCode);

}
