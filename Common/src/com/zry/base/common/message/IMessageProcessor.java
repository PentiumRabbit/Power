/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.base.common.message;

/**
 * 消息分发
 *
 * @author zhaoruyang
 */
interface IMessageProcessor {

    /**
     * 分发消息
     *
     * @param message
     */
    void sendMessage(IMessage message);
}
