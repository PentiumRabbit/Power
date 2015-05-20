package com.android.netconnect.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池的工具类
 *
 * @author ----zhaoruyang----
 * @data: 2015/5/20
 */
public class ExecutorUtil {
    private static final String TAG = "ExecutorServiceUtil";

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "ExecutorUtil #" + mCount.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<>(128);


    public static ExecutorService getDefultPool() {
        //        corePoolSize - 池中所保存的线程数，包括空闲线程。
        //        maximumPoolSize - 池中允许的最大线程数。
        //        keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
        //        unit - keepAliveTime 参数的时间单位。
        //        workQueue - 执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的 Runnable 任务。
        //        抛出：
        //        IllegalArgumentException - 如果 corePoolSize 或 keepAliveTime 小于零，
        //        或者 maximumPoolSize 小于或等于零，或者 corePoolSize 大于 maximumPoolSize。
        //        NullPointerException - 如果 workQueue 为 null
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                sPoolWorkQueue,
                sThreadFactory,
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

}
