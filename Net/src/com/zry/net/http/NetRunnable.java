package com.zry.net.http;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.zry.net.bean.Request;
import com.zry.net.database.INetCacheDao;
import com.zry.net.engine.NetWork.NetFactory;
import com.zry.net.engine.NetWork.RequestMethod;
import com.zry.net.listener.IHttpResult;

import java.lang.ref.WeakReference;


/**
 * 将一个线程的数据库操作,网络请求操作,json解析操作写在同一个线程内,避免线程资源浪费
 *
 * @author ----zhaoruyang----
 * @data: 2014/12/25
 */
public class NetRunnable implements Runnable, IHttpResult {

    public static final int REQUEST_SUCCESS = 0;
    public static final int REQUEST_FAIL = 1;
    public static final int LOAD_DB_CACHE = 2;

    private NetHandler handler;
    private Request options;
    private INetCacheDao cacheDao;

    public NetRunnable(Request options, INetCallBack callback, INetCacheDao cacheDao) {
        this.options = options;
        if (callback != null) {
            handler = new NetHandler(callback, options);
        }
        this.cacheDao = cacheDao;
    }


    @Override
    public void run() {
        if (!options.isSync()) {
            android.os.Process.setThreadPriority(options.getThreadPriority());
        }
        if (options.readCache()) {
            readDao();
        }

        NetFactory.getInstance().exeConnect(options, this);
    }

    /**
     * 读取数据库缓存
     */
    private void readDao() {
        String cacheStr = cacheDao.get(options.getKey());
        if (TextUtils.isEmpty(cacheStr)) {
            return;
        }
        sendMessage(LOAD_DB_CACHE, dealMsg(cacheStr), 0);
    }

    private void sendMessage(int from, Object msgNet, int arg) {
        Message msg = new Message();
        msg.what = from;
        msg.obj = msgNet;
        msg.arg1 = arg;
        if (options.isSync()) {
            handler.handleMessage(msg);
        } else {
            handler.sendMessage(msg);
        }
    }

    private Object dealMsg(String msg) {
        return options.parser().parse(msg);
    }


    @Override
    public void requestSuccess(RequestMethod method, String message) {
        // TODO 将流引到这里,如果需要缓存,在转化成字符串,减少GSON转化资源
        handler.removeMessages(LOAD_DB_CACHE);
        sendMessage(REQUEST_SUCCESS, dealMsg(message), 0);
        // 存入数据库
        if (options.saveCache()) {
            cacheDao.save(options.getKey(), message, options.getSaveModel());
        }
    }


    @Override
    public void requestFail(int errorCode, Exception e) {
        sendMessage(REQUEST_FAIL, e, errorCode);
    }

    /**
     * 释放资源
     */
    public void release() {
        handler.release();
    }

    /**
     * 强制关联,防止上层不能获取结果,不需要使用弱引用,不会造成内存泄露
     */
    private static class NetHandler extends Handler {
        private WeakReference<INetCallBack> reference;
        private Request options;

        NetHandler(INetCallBack callBack, Request options) {
            reference = new WeakReference<INetCallBack>(callBack);
            this.options = options;
        }

        void release() {
            reference.clear();
        }

        public void handleMessage(Message msg) {
            if (reference == null) {
                return;
            }
            INetCallBack callBack = reference.get();

            if (callBack == null) {
                return;
            }
            int i = msg.what;

            switch (i) {
                case REQUEST_SUCCESS:
                    callBack.onNetSuccess(options.getKey(), msg.obj);
                    break;
                case REQUEST_FAIL:
                    int error_code = msg.arg1;
                    callBack.onNetError(options.getKey(), error_code);
                    break;
                case LOAD_DB_CACHE:
                    callBack.onNetCache(options.getKey(), msg.obj);
                    break;
                default:
                    break;
            }
        }

    }

}
