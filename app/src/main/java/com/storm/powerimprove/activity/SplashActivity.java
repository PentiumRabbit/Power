package com.storm.powerimprove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.android.base.common.fragment.BaseActivity;
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
     * 进入应用
     */
    private void stepIntoActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 数据预加载
     */
    private void preLoading() {
        //TODO 启动加载应用配置,网络请求,本地部署等


    }

}
