package com.android.base.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.base.common.handler.CommonHandler;
import com.android.base.common.handler.IHandlerMessage;

/**
 * @author ----zhaoruyang----
 * @data: 2015/2/6
 */
public class BaseActivity extends AppCompatActivity implements IHandlerMessage {
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected FragmentManager supportFragmentManager;
    protected Handler         handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportFragmentManager = getSupportFragmentManager();
        handler = new CommonHandler<BaseActivity>(this);
        // 优化的DelayLoad
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                handler.post(loadDate);
            }
        });

    }

    private Runnable loadDate = new Runnable() {
        @Override
        public void run() {
            initView();
            initDate();
        }
    };

    /**
     * 空实现，避免子类有些不需要的实现
     */
    @Override
    public void handlerCallback(Message msg) {

    }



    /**
     * 更新主题
     */
    protected void updateTheme() {

    }

    /**
     * 初始化View
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initDate() {
    }


    @Override
    public void finish() {
        super.finish();
        //TODO 添加动画
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //TODO 添加动画
    }

    @Override
    protected void onDestroy() {
        setContentView(new View(this));
        super.onDestroy();
    }



    /**
     * 替换Fragment
     *
     * @param layout_id
     *         布局id
     * @param fragment
     *         替换的fragment
     * @param needBack
     *         是否添加到回退栈
     */
    protected void replaceFragment(int layout_id, Fragment fragment, boolean needBack) {
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();

        beginTransaction.replace(layout_id, fragment, fragment.getClass().getName());
        if (needBack) {
            beginTransaction.addToBackStack(fragment.getClass().getName());
        }
        beginTransaction.commitAllowingStateLoss();

    }
}
