package com.android.netconnect.http;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.android.netconnect.NetConstant;
import com.android.netconnect.database.NetCacheDao;
import com.android.netconnect.listener.HttpResultDeal;
import com.google.gson.Gson;


/**
 * @author ----zhaoruyang----
 * @data: 2014/12/25
 */
public class NetRequestRunnable implements Runnable, HttpResultDeal {

    public static final int REQUEST_SUCCESS = 0;
    public static final int REQUEST_FAIL = 1;
    public static final int LOAD_DB_CACHE = 2;
    private Handler handler;
    private NetOptions options;
    private HttpUtils httpUtils;
    private NetCacheDao cacheDao;

    public NetRequestRunnable(NetOptions options, IAsyncCallBack callback, NetCacheDao cacheDao) {
        this.options = options;
        if (callback != null) {
            handler = new NetHandler(callback, options);
        }
        httpUtils = HttpUtils.getInstance();
        this.cacheDao = cacheDao;
    }


    @Override
    public void run() {
        android.os.Process.setThreadPriority(options.getThreadPriority());
        if (options.readCache()) {
            // 读取数据库
            String cacheStr = cacheDao.getCacheStr(options.getCacheId());
            if (!TextUtils.isEmpty(cacheStr)) {
                Message message = handler.obtainMessage(LOAD_DB_CACHE, dealMsg(cacheStr, options.getCastType()));
                handler.sendMessage(message);
            }
        }

        /*get请求*/
        if (NetConstant.RequestMethod.GET == options.getMethod()) {
            httpUtils.sendGetRequest(options.getUrl(), this);
        }
        /*post请求*/
        else if (NetConstant.RequestMethod.POST == options.getMethod()) {
            httpUtils.sendPostRequest(options.getUrl(), options.getParams(), this);
        }
    }

    /**
     * 处理字符串
     *
     * @param msg
     *         字符串
     */
    private <T> T dealMsg(String msg, Class<T> tClass) {
        if (String.class.equals(tClass)) {
            return (T) msg;
        } else {
            return new Gson().fromJson(msg, tClass);
        }
    }

    @Override
    public void requestSuccess(NetConstant.RequestMethod method, String message) {
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
     * 强制关联,防止上层不能获取结果,不需要使用弱引用,不会造成内存泄露,请求有超时时间
     */
    static class NetHandler extends Handler {
        private IAsyncCallBack callBack;
        private NetOptions options;

        public NetHandler(IAsyncCallBack callBack, NetOptions options) {
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
                    callBack.callback_pre_loadcache(options.getCacheId(), msg.obj);
                    break;
                default:
                    break;
            }
        }

    }

}
