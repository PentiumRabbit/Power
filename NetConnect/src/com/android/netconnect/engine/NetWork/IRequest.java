package com.android.netconnect.engine.NetWork;

import com.android.netconnect.listener.IHttpResult;

import java.util.Map;

/**
 * 请求方式接口
 *
 * @author ----zhaoruyang----
 * @data: 2015/4/23
 */
public interface IRequest {
    void doGet(String url, IHttpResult resultDeal);

    void doPost(String url, Map<String, String> params, IHttpResult resultDeal);
}
