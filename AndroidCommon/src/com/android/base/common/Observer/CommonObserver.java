/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.Observer;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.android.base.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用的观察者,用于简单的消息分发
 *
 * @author ----zhaoruyang----
 * @data: 2015/1/9
 */
public class CommonObserver {

    private static final String TAG = "CommonObserver";
    private static volatile CommonObserver instance = null;
    private final SparseArray<List<ObserverCallback>> observers;

    // private constructor suppresses
    private CommonObserver() {
        observers = new SparseArray<>();
    }

    public static CommonObserver getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (CommonObserver.class) {
                if (instance == null) {
                    instance = new CommonObserver();
                }
            }
        }

        return instance;
    }


    /**
     * 注册观察者
     *
     * @param type     事件类型
     * @param callback 事件回调
     */
    public void register(int type, ObserverCallback callback) {
        // TODO: 2015/12/14 callback 存入弱引用,防止泄露,需要吗?
        // 有移除逻辑,应该不需要弱引用
        if (callback == null)
            throw new NullPointerException();
        Logger.i(TAG, "callback register" + type);
        synchronized (observers) {
            List<ObserverCallback> observerCallbacks = observers.get(type);
            if (observerCallbacks == null) {
                List<ObserverCallback> callbacks = new ArrayList<>();
                callbacks.add(callback);
                observers.put(type, callbacks);
            } else {
                if (observerCallbacks.contains(callback)) {
                    return;
                }
                observerCallbacks.add(callback);
            }
        }


    }

    /**
     * 移除观察者
     *
     * @param type     事件类型
     * @param callback 事件回调
     */
    public void unRegister(int type, ObserverCallback callback) {
        synchronized (observers) {
            List<ObserverCallback> callbacks = observers.get(type);
            if (callbacks == null) {
                return;
            }
            callbacks.remove(callback);
        }
    }

    /**
     * 清空观察者
     *
     * @param type 类型
     */
    public void clearObservers(int type) {
        observers.get(type).clear();
        observers.remove(type);
    }

    /**
     * 消息分发
     *
     * @param type 消息类型
     * @param code 消息标识
     */
    public void notifyListener(int type, int code) {
        Logger.i(TAG, "callback notifyListener" + type);
        List<ObserverCallback> callbacks = observers.get(type);
        if (callbacks == null) {
            return;
        }
        for (ObserverCallback listener : callbacks) {
            // TODO: 2015/12/14 统计回调时长,超过某些时长使用log.e
            listener.onMessageChange(type, code);
        }
    }

}
