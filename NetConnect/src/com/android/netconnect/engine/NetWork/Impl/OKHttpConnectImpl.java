package com.android.netconnect.engine.NetWork.Impl;

import com.android.netconnect.engine.NetWork.IRequest;
import com.android.netconnect.listener.IHttpResult;

import java.util.Map;

/**
 * Facebook 很早就开始使用Square公司开发的 OkHttp（一个开源的网络协议栈）了，现在Google 官方也从Android 4.4开始使用 OkHttp作为HttpURLConnection的默认实现了。  OkHttp 支持在糟糕的网络环境下面更快的重试，并且还能利用 SPDY 协议进行快速的并发网络请求。
 * 利用OkHttp调整图片的预先抓取算法，确保app中下载队列前面的图片被优先处理，防止队列阻塞时间过长。
 *
 * @author ----zhaoruyang----
 * @data: 2015/5/21
 */
public class OKHttpConnectImpl implements IRequest {
    private static final String TAG = "OKHttpConnectImpl";

    @Override
    public void doGet(String url, IHttpResult resultDeal) {

    }

    @Override
    public void doPost(String url, Map<String, String> params, IHttpResult resultDeal) {

    }
}
