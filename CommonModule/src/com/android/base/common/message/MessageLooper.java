/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.message;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 消息循环
 *
 * @author zhaoruyang
 */
public class MessageLooper implements Runnable {
    private final AtomicBoolean isLooping = new AtomicBoolean(true);
    private MessageQueue queue;
    private IMessageProcessor handOut;

    public MessageLooper(MessageQueue queue, IMessageProcessor handOut) {
        super();
        this.queue = queue;
        this.handOut = handOut;
    }

    @Override
    public void run() {
        if (isLooping.get()) {
            /**
             * 当消息不存在时,队列将会自我阻塞 线程wait,有消息时,notify
             */
            IMessage msg = queue.poll();
            if (msg != null)
                process(msg);
        }
    }

    private void process(IMessage msg) {
        handOut.sendMessage(msg);
    }

    public void addListener(IMessageProcessor handOut) {

    }

    /**
     * 判断该线程是否正在运行
     *
     * @return
     */
    public boolean isRunning() {
        return isLooping.get();
    }

    public void stop() {
        // 跳出死循环
        isLooping.set(false);
        queue.notify();
    }
}
