package com.storm.powerimprove;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;

import com.android.base.IApplication;
import com.android.base.common.utils.Logger;
import com.android.netconnect.engine.NetConfig;
import com.android.netconnect.http.HttpLoader;
import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.squareup.leakcanary.LeakCanary;

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

        Logger.i("zry", TAG + " --- initConfig()");

        // 初始化OOM检测
        LeakCanary.install(application);

        initImageLoader();
        initDebugModel();
    }

    /**
     * 设置debug开关
     */
    private void initDebug() {
        Logger.setDebug(AppArg.IS_DEBUG);
//        initStetho();
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

    /*开启严苛模式*/
    public void initDebugModel() {
        if (!AppArg.IS_DEBUG) {
            return;
        }
        // check if android:debuggable is set to true
        StrictMode.VmPolicy.Builder     vmBuilder     = new StrictMode.VmPolicy.Builder();
        StrictMode.ThreadPolicy.Builder threadBuilder = new StrictMode.ThreadPolicy.Builder();
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            threadBuilder.detectAll();
            StrictMode.setThreadPolicy(threadBuilder.build());
            vmBuilder.detectAll();
            StrictMode.setVmPolicy(vmBuilder.build());

        }
    }

    /**
     * 初始化
     */
    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(application)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(application))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application))
                        .build());
    }


}
