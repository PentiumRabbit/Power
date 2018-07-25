package com.zry.dev;

import android.os.Build;
import android.os.StrictMode;

import com.zry.base.IApplication;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author : ZhaoRuYang
 * @date : 2018/5/23
 */
public class DevApplication extends IApplication{
    @Override
    public void onCreate() {
        super.onCreate();

        initDebugModel();
        initStetho();

        // 初始化OOM检测
        LeakCanary.install(application);
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

    /*开启严苛模式*/
    public void initDebugModel() {
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
}
