package com.android.netconnect.http;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.android.netconnect.database.INetCacheDao;
import com.android.netconnect.database.NetCacheDao;
import com.android.netconnect.engine.NetWork.NetFactory;
import com.android.netconnect.engine.NetWork.RequestMethod;
import com.android.netconnect.listener.IHttpResult;
import com.google.gson.Gson;


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
    private Handler handler;
    private NetOptions options;
    private INetCacheDao cacheDao;

    public NetRunnable(NetOptions options, INetCallBack callback, INetCacheDao cacheDao) {
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
            // 读取数据库
            String cacheStr = cacheDao.getCacheStr(options.getCacheId());
            if (!TextUtils.isEmpty(cacheStr)) {
                Message message = handler.obtainMessage(LOAD_DB_CACHE, dealMsg(cacheStr, options.getCastType()));
                handler.sendMessage(message);
            }
        }

        NetFactory.getInstance().exeConnect(options, this);
    }

    /**
     * 处理字符串
     *
     * @param msg
     *         字符串
     */
    private <T> T dealMsg(String msg, Class<T> tClass) {
        //TODO 将 GSON 改成基于流的操作,更加偏于底程,效率更高,采取 TypeAdapters 和 TypeAdapterFactorys 方案来代替 JsonDeserializer
        if (String.class.equals(tClass) || msg == null) {
            return (T) msg;
        } else {
            return new Gson().fromJson(msg, tClass);
        }
    }

    @Override
    public void requestSuccess(RequestMethod method, String message) {
        // TODO 将流引到这里,如果需要缓存,在转化成字符串,减少GSON转化资源
        handler.removeMessages(LOAD_DB_CACHE);
        Message msg = handler.obtainMessage(REQUEST_SUCCESS, dealMsg(message, options.getCastType()));
        handler.sendMessage(msg);
        // 存入数据库
        if (options.saveCache()) {
            cacheDao.saveCache(options.getCacheId(), message, options.getSaveModel());
        }
    }

    @Override
    public void requestFail(int errorCode, Exception e) {
        Message msg = handler.obtainMessage(REQUEST_FAIL, errorCode, 0, e);
        handler.sendMessage(msg);
    }

    /**
     * 强制关联,防止上层不能获取结果,不需要使用弱引用,不会造成内存泄露
     */
    static class NetHandler extends Handler {
        private INetCallBack callBack;
        private NetOptions options;

        public NetHandler(INetCallBack callBack, NetOptions options) {
            this.callBack = callBack;
            this.options = options;
        }

        public void handleMessage(Message msg) {

            if (callBack == null) {
                return;
            }
            int i = msg.what;
            switch (i) {
                case REQUEST_SUCCESS:
                    callBack.callback_success(options.getCacheId(), msg.obj);
                    break;
                case REQUEST_FAIL:
                    int error_code = msg.arg1;
                    callBack.callback_error(options.getCacheId(), error_code);
                    break;
                case LOAD_DB_CACHE:
                    callBack.callback_loadcache(options.getCacheId(), msg.obj);
                    break;
                default:
                    break;
            }
        }

    }

}
