package com.zry.base.common.callback;

/**
 * 用于RecycleView的回调
 * ZhaoRuYang
 * 2015/11/28 20:07
 */
public interface OnRecycleCallback<T> {
    void onRecycleCallback(T t, int type, int itemPosition, int viewPosition);
}
