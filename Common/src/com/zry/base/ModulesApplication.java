package com.zry.base;

import android.app.Application;
import android.util.Log;


import com.zry.base.common.value.ValueTAG;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : ZhaoRuYang
 * @date : 2018/5/23
 */
public class ModulesApplication extends IApplication {

    private List<IApplication> applications=new ArrayList<>();

    public ModulesApplication() {
        loadModuleApplication();
    }

    @Override
    public void setApplication(Application application) {
        for (IApplication iApplication : applications) {
            iApplication.setApplication(application);
        }
    }

    @Override
    public void attachBaseContext() {
        for (IApplication iApplication : applications) {
            iApplication.attachBaseContext();
        }

    }

    @Override
    public void onCreate() {
        for (IApplication iApplication : applications) {
            iApplication.onCreate();
        }


    }


    private void loadModuleApplication() {
        for (int i = 0; i < ModuleConfig.names.length; i++) {
            String name = ModuleConfig.names[i];

            try {
                Class<IApplication> cls = (Class<IApplication>) Class.forName(name);

                if (cls == null) {
                    continue;
                }

                IApplication iApplication = cls.newInstance();
                if (iApplication==null) {
                    continue;
                }
                Log.i("zry", "load Module : " + cls.getSimpleName());
                applications.add(iApplication);
            } catch (Exception e) {
                Log.e(ValueTAG.EXCEPTION,"loadModuleApplication", e);
            }

        }
    }

}
