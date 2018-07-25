package com.zry.base.common.BitmapLoader;

import java.util.concurrent.Callable;

/**
 * 用于线程的回调设置
 *
 * @Description: WorkCallback
 * @Author: ZhaoRuYang
 * @Update: ZhaoRuYang(2015-11-27 10:18)
 */
public abstract class WorkCallback<T> implements Callable<T> {
    private static final String TAG = WorkCallback.class.getSimpleName();

    @Override
    public T call() throws Exception {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        return run();
    }

    public abstract T run();
}
