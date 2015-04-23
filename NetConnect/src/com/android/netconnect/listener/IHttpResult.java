/*
 * @Title:  ResultDeal.java
 * @package_name:  com.stormtv.market.network
 * @Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 * @Description:  TODO<请描述此文件是做什么的>
 * @author:  ----zhaoruyang----
 * @data:  2014-7-24 下午5:48:53
 * @version:  V1.0
 *
 */

package com.android.netconnect.listener;

import com.android.netconnect.NetConstant;

/**
 * ResultDeal
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014-7-24 下午5:48:53
 */

public interface IHttpResult {

    /*网络请求成功*/
    void requestSuccess(NetConstant.RequestMethod method,String message);

    /*网络请求失败*/
    void requestFail(int errorCode, Exception e);
}
