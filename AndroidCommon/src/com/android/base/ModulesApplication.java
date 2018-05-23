package com.android.base;

import android.app.Application;

import com.android.base.common.utils.Logger;
import com.android.base.common.value.ValueTAG;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : ZhaoRuYang
 * @date : 2018/5/23
 */
public class ModulesApplication implements IApplication {

    private List<IApplication> applications=new ArrayList<>();

    public ModulesApplication() {
        loaderModuleApplication();
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


    private void loaderModuleApplication() {
        for (int i = 0; i < ModuleConfig.names.length; i++) {
            String name = ModuleConfig.names[i];

            try {
                Class<IApplication> cls = (Class<IApplication>) Class.forName(name);
                IApplication iApplication = cls.newInstance();
                if (iApplication==null) {
                    continue;
                }

                applications.add(iApplication);
            } catch (Exception e) {
                Logger.e(ValueTAG.EXCEPTION, e);
            }

        }
    }

}
