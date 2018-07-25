package com.zry.power;

import android.graphics.Bitmap;

import com.zry.base.IApplication;
import com.zry.base.common.utils.Logger;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;

/**
 * App的全局Application
 *
 * @author ----zhaoruyang----
 * @data: 2015/6/11
 */
public class App  extends IApplication {
    private static final String TAG = App.class.getSimpleName();

    @Override
    public void attachBaseContext() {
    }

    @Override
    public void onCreate() {
        initConfig();
    }


    // 初始化
    private void initConfig() {
        // 率先设置Debug开关
        initDebug();
        initImageLoader();
    }

    /**
     * 设置debug开关
     */
    private void initDebug() {
        Logger.setDebug(AppArg.IS_DEBUG);
    }



    /*初始化ImageLoader*/
    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(application)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .threadPoolSize(3)
                .build();

        ImageLoader.getInstance().init(config);
        L.writeLogs(AppArg.IS_DEBUG);
        L.writeDebugLogs(AppArg.IS_DEBUG);
    }






}
