/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.power;

import com.zry.power.BuildConfig;

/**
 * ConstantValue
 * <p/>
 * 用于全局构建的信息参数
 *
 * @author ----zhaoruyang----
 */

public interface AppArg {

    /*debug开关*/
    boolean IS_DEBUG = BuildConfig.DEBUG_MODEL;
    /*包名：所有涉及报名的，都要引用这个*/
    String PACKAGE_ID = BuildConfig.APPLICATION_ID;
    /*本应用的版本号*/
    int VERSION_CODE = BuildConfig.VERSION_CODE;
    /*本应用的版本名*/
    String VERSION_NAME = BuildConfig.VERSION_NAME;
    /*渠道信息*/   // 由于批量打包，导致该字段不可用
//    String FLAVOR = BuildConfig.FLAVOR;

}
