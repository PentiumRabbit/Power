package com.android.netconnect.engine.NetWork.Impl;

import com.android.base.utils.Logger;
import com.android.netconnect.NetConstant;
import com.android.netconnect.engine.NetWork.IRequest;
import com.android.netconnect.engine.NetWork.RequestMethod;
import com.android.netconnect.listener.IHttpResult;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Facebook 很早就开始使用Square公司开发的 OkHttp（一个开源的网络协议栈）了，现在Google 官方也从Android 4.4开始使用 OkHttp作为HttpURLConnection的默认实现了。  OkHttp 支持在糟糕的网络环境下面更快的重试，并且还能利用 SPDY 协议进行快速的并发网络请求。
 * 利用OkHttp调整图片的预先抓取算法，确保app中下载队列前面的图片被优先处理，防止队列阻塞时间过长。
 *
 * @author ----zhaoruyang----
 * @data: 2015/5/21
 */
public class OKHttpClientImpl implements IRequest {
    private static final String TAG = "OKHttpConnectImpl";

    private OkHttpClient client;

    public OKHttpClientImpl() {
        client = new OkHttpClient();
        client.setConnectTimeout(8, TimeUnit.SECONDS);
    }

    @Override
    public void doGet(String url, IHttpResult resultDeal) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            dealResult(resultDeal, response, RequestMethod.GET);
        } catch (IOException e) {
            if (resultDeal != null) {
                resultDeal.requestFail(NetConstant.ERROR_EXCEPTION, e);
            }
            Logger.e(NetConstant.TAG_NET_CONNECT, "*****EXCEPTION*****\n", e);
        }

    }


    @Override
    public void doPost(String url, Map<String, String> params, IHttpResult resultDeal) {
        Request request = new Request.Builder()
                .url(url)
                        //添加http头
//                .headers(Headers.of(params))
                .post(createPostParams(params))
                .build();

        try {
            Response response = client.newCall(request).execute();
            dealResult(resultDeal, response, RequestMethod.POST);
        } catch (IOException e) {
            if (resultDeal != null) {
                resultDeal.requestFail(NetConstant.ERROR_EXCEPTION, e);
            }
            Logger.e(NetConstant.TAG_NET_CONNECT, "*****EXCEPTION*****\n", e);
        }
    }

    private RequestBody createPostParams(Map<String, String> params) {
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            formEncodingBuilder.add(entry.getKey(), entry.getValue());
        }

        return formEncodingBuilder.build();

    }


    /**
     * 处理结果
     *
     * @param resultDeal
     * @param response
     * @param method
     *
     * @throws IOException
     */
    private void dealResult(IHttpResult resultDeal, Response response, RequestMethod method) throws IOException {
        if (resultDeal == null) {
            return;
        }
        if (!response.isSuccessful()) {
            resultDeal.requestFail(response.code(), null);
            return;
        }
        resultDeal.requestSuccess(method, response.body().charStream());
    }

}
