package com.zry.power.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zry.base.common.base.BaseActivity;
import com.zry.base.common.utils.Logger;
import com.zry.net.bean.Request;
import com.zry.net.engine.ConnectMode;
import com.zry.net.engine.NetWork.RequestMethod;
import com.zry.net.http.HttpLoader;
import com.zry.net.http.OnStringNetCallback;
import com.zry.power.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initDate();
    }

    @Override
    protected void updateTheme() {

    }

    protected void initView() {

    }

    protected void initDate() {
        preLoading();
        stepIntoActivity();
    }

    /**
     * 数据预加载
     */
    private void preLoading() {
        //TODO 启动加载应用配置,网络请求,本地部署等
        Logger.i("start");
        Request options = new Request.Builder()
                .url("http://search.shouji.baofeng.com/client_settings.php?name=theme")
                .method(RequestMethod.GET)
                .connectMode(ConnectMode.connect_ok)
                .build();
        HttpLoader.getInstance().execute(options, new OnStringNetCallback() {
            @Override
            public void onNetCache(String msgId, String messageInfo) {

            }

            @Override
            public void onNetSuccess(String msgId, String messageInfo) {
                Logger.i(messageInfo + "");
            }

            @Override
            public void onNetError(String msgId, int errorCode) {

            }
        });
    }

    /**
     * 进入应用
     */
    private void stepIntoActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
