package com.storm.powerimprove;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;

import com.android.base.ConstantValue;
import com.android.base.utils.LogUtil;
import com.android.netconnect.engine.NetConfig;
import com.android.netconnect.http.HttpLoader;
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
public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        initConfig();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    // 初始化
    private void initConfig() {
        // 率先设置Debug开关
        LogUtil.setDebug(AppConstant.IS_DEBUG);
        initImageLoader();
        initDebugModel();
        initHttpLoader();
    }

    /*初始化HttpLoader*/
    private void initHttpLoader() {
        NetConfig netConfig = new NetConfig.Builder(getApplicationContext()).setNetParams(null).build();
        HttpLoader.getInstance().init(netConfig);
    }

    /*初始化ImageLoader*/
    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .threadPoolSize(3)
                .build();

        ImageLoader.getInstance().init(config);
        L.writeLogs(AppConstant.IS_DEBUG);
        L.writeDebugLogs(AppConstant.IS_DEBUG);
    }

    /*开启严苛模式*/
    public void initDebugModel() {
        if (!AppConstant.IS_DEBUG) {
            return;
        }
        // check if android:debuggable is set to true
        StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.ThreadPolicy.Builder threadBuilder = new StrictMode.ThreadPolicy.Builder();
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            threadBuilder.detectAll();
            StrictMode.setThreadPolicy(threadBuilder.build());
            vmBuilder.detectAll();
            StrictMode.setVmPolicy(vmBuilder.build());

        }
    }

}
