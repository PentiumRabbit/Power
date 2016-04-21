/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base;

/**
 * AppParams
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月10日 下午7:48:15
 */

public class AppParams {

    /**
     * 当前网络状态
     * 0:没有网络
     * 1:无线网络
     * 2:手机网络
     */
    public static int currentNetStatus = -1;

    /**
     * 判断应用是否再前台
     */
    public static boolean isVisibleApp = false;
}
