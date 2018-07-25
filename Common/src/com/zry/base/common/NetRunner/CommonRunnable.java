package com.zry.base.common.NetRunner;

import android.content.Context;

import com.zry.base.common.bean.BaseNet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author ----zhaoruyang----
 * @data: 3/7/16
 */
public class CommonRunnable<T> implements Runnable {
    private static final String TAG = "CommonRunnable";

    private INetCallback<T> callback;
    private Context context;
    private String url;
    private HashMap<String, String> map;

    public CommonRunnable(INetCallback<T> callback, Context context, HashMap<String, String> map, String url) {
        this.callback = callback;
        this.context = context;
        this.url = url;
        this.map = map;
    }

    public CommonRunnable(INetCallback<T> callback, Context context, String url) {
        this.callback = callback;
        this.context = context;
        this.url = url;
    }

    @Override
    public void run() {
        BaseNet<T> result = requestNet();
        if (callback == null) {
            return;
        }
        if (result == null) {
            callback.onNetFail(NetValue.NO_DATA, "");
            return;
        }

        if (result.getStatus() != 1) {
            callback.onNetFail(result.getStatus(), result.getMsg());
        }

        callback.onNetSuccess(result.getResult());
    }

    /**
     * 请求网络，数据转换
     *
     * @return
     */
    private BaseNet<T> requestNet() {
        String json = "";
        Gson gson = new Gson();
        Type objectType = new TypeToken<BaseNet<T>>() {
        }.getType();
        return gson.fromJson(json, objectType);
    }
}
