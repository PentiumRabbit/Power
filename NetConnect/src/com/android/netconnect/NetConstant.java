package com.android.netconnect;

/**
 * @author ----zhaoruyang----
 * @data: 2014/12/25
 */
public interface NetConstant {

    String TAG_CONNECT_NET = "storm.market.connect_net";

    /*网络请求参数*/
    String PARAM_SCREEN = "screen";

    String PARAM_VER = "ver";

    String PARAM_PLATF = "platf";

    String PARAM_IMEI = "imei";

    /*网络错误码*/

    /*请求成功,但没有数据*/
    int ERROR_NO_MSG = -1;

    /*请求异常*/
    int ERROR_EXCEPTION = -2;

    int MODE_CONNECT_URL = 1;

    int MODE_CONNECT_CLIENT = 2;
}
