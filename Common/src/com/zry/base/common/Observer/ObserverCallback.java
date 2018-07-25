/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.base.common.Observer;

/**
 * 用于简单处理消息
 *
 * @author ----zhaoruyang----
 * @data: 2015/1/9
 */
public interface ObserverCallback {
    /*回调方法里不要做耗时,阻塞操作,否则会影响到后面小心的分发*/
    void onMessageChange(int type, int code);
}