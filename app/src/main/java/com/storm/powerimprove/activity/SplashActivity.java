package com.storm.powerimprove.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.base.common.fragment.BaseActivity;
import com.android.base.utils.Logger;
import com.android.netconnect.engine.ConnectMode;
import com.android.netconnect.engine.NetWork.RequestMethod;
import com.android.netconnect.http.HttpLoader;
import com.android.netconnect.http.INetCallBack;
import com.android.netconnect.http.NetOptions;
import com.android.netconnect.http.Protocol;
import com.storm.powerimprove.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preLoading();
        stepIntoActivity();
    }

    /**
     * 数据预加载
     */
    private void preLoading() {
        //TODO 启动加载应用配置,网络请求,本地部署等
        Logger.i("start");
        NetOptions options=new NetOptions.Builder()
                .setUrlType(Protocol.ProtocolType.GET_CLIENT_SETTINGS)
                .setMethod(RequestMethod.GET)
                .setConnectMode(ConnectMode.connect_ok)
                .build();
        HttpLoader.getInstance().exeRequest(options, new INetCallBack() {
            @Override
            public void callback_cache(int msgId, Object messageInfo) {

            }

            @Override
            public void callback_success(int msgId, Object messageInfo) {
                Logger.i(messageInfo+"");
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
