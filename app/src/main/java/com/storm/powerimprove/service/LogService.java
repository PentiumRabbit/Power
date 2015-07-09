package com.storm.powerimprove.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 获取日志的服务,设置成前台服务,和音乐一样,绑定服务获取日志信息
 */
public class LogService extends Service {
    public LogService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
