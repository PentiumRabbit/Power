package com.zry.base.common.callback;

/**
 * ZhaoRuYang
 * 用于adapter回调
 */
public interface OnItemCallback<T> {
    void onAdapterCallback(T t, int type);
}
