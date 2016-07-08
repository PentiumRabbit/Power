package com.storm.powerimprove.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.base.common.fragment.BaseActivity;
import com.android.base.utils.Logger;
import com.android.netconnect.bean.Request;
import com.android.netconnect.engine.ConnectMode;
import com.android.netconnect.engine.NetWork.RequestMethod;
import com.android.netconnect.http.HttpLoader;
import com.android.netconnect.http.INetCallBack;
import com.storm.powerimprove.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void updateTheme() {

    }

    @Override
    protected void initView() {

    }

    @Override
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
                .url("")
                .method(RequestMethod.GET)
                .connectMode(ConnectMode.connect_ok)
                .build();
        HttpLoader.getInstance().exeRequest(options, new INetCallBack() {
            @Override
            public void callback_cache(int msgId, Object messageInfo) {

            }

            @Override
            public void callback_success(int msgId, Object messageInfo) {
                Logger.i(messageInfo + "");
            }

            @Override
            public void callback_error(int msgId, int errorCode) {

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
