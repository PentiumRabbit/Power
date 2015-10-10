/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.BitmapLoader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.android.base.common.value.ValueTAG;
import com.android.base.utils.Logger;
import com.android.base.utils.SysInfoUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ----zhaoruyang----
 * @data: 2014/12/12
 */
public class BitmapLoader {

    private static volatile BitmapLoader instance = null;
    private final BitmapLruCache imageMap;
    private final Executor pool;
    private Handler handler;
    private final AtomicBoolean atomicBoolean = new AtomicBoolean();

    // private constructor suppresses
    private BitmapLoader(int memory) {
        handler = defineHandler();
        pool = Executors.newFixedThreadPool(SysInfoUtil.getDefaultThreadPoolSize());
        imageMap = new BitmapLruCache(memory);
    }

    public static BitmapLoader getInstance(int memory) {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (BitmapLoader.class) {
                if (instance == null) {
                    instance = new BitmapLoader(memory);
                }
            }
        }

        return instance;
    }

    public void loadBitmap(final String imageUrl, final ImageView imageView, Callable<Bitmap> mWorker) {
        imageView.setTag(imageUrl);
        final Bitmap bitmapFromMemCache = imageMap.getBitmapFromMemCache(imageUrl);
        if (bitmapFromMemCache != null) {
            imageView.setImageBitmap(bitmapFromMemCache);
            return;
        }

        FutureTask<Bitmap> futureTask = new FutureTask<Bitmap>(mWorker) {
            @Override
            protected void done() {
                try {
                    final Bitmap result = get();
                    if (result == null) {
                        return;
                    }
                    imageMap.addBitmapToMemoryCache(imageUrl, result);
                    if (!imageUrl.equals(imageView.getTag())) {
                        return;
                    }
                    handler = defineHandler();
                    if (handler != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(result);
                            }
                        });
                    }

                } catch (InterruptedException | ExecutionException e) {
                    Logger.e(ValueTAG.EXCEPTION, "*****EXCEPTION*****\n", e);
                }
            }
        };
        pool.execute(futureTask);


    }

    private Handler defineHandler() {
        if (handler == null && Looper.myLooper() == Looper.getMainLooper()) {
            handler = new Handler();
        }
        return handler;
    }


}
