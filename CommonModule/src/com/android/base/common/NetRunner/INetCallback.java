package com.android.base.common.NetRunner;

/**
 * 用于网络回调
 * <p/>
 * ZhaoRuYang
 * 2/1/16 2:31 PM
 */
public interface INetCallback<T> {

    void onNetSuccess(T t);

    void onNetFail(int errorCode, String msg);
}
