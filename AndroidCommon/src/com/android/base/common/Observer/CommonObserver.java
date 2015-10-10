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


    public synchronized void register(@NonNull ObserverType type, ObserverCallback callback) {
        if (callback == null)
            throw new NullPointerException();

        int ordinal = type.ordinal();
        Logger.i(TAG, "callback register" + ordinal);
        List<ObserverCallback> observerCallbacks = observers.get(ordinal);
        if (observerCallbacks == null) {
            List<ObserverCallback> callbacks = new ArrayList<>();
            callbacks.add(callback);
            observers.put(ordinal, callbacks);
        } else {
            if (observerCallbacks.contains(callback))
                return;
            observerCallbacks.add(callback);
        }


    }

    public synchronized void unRegister(@NonNull ObserverType type, ObserverCallback callback) {

        int ordinal = type.ordinal();
        List<ObserverCallback> callbacks = observers.get(ordinal);
        if (callbacks != null) {
            callbacks.remove(callback);
        }


    }

    public void clearOnservers(@NonNull ObserverType type) {
        int ordinal = type.ordinal();
        observers.get(ordinal).clear();
        observers.remove(ordinal);
    }

    public void notifyListener(@NonNull ObserverType type, int code) {

        int ordinal = type.ordinal();
        Logger.i(TAG, "callback notifyListener" + ordinal);
        List<ObserverCallback> callbacks = observers.get(ordinal);
        if (callbacks == null) {
            return;
        }
        for (ObserverCallback listener : callbacks) {
            listener.onMessageChange(type, code);
        }
    }

}
