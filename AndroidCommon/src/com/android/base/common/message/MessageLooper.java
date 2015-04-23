/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.message;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 消息循环
 * 
 * @author Administrator
 *
 */
public class MessageLooper implements Runnable {
	private MessageQueue queue;
	private IMessageProcessor handOut;
	private final AtomicBoolean isLooping = new AtomicBoolean(true);

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

	public void stop() {
		// 跳出死循环
		isLooping.set(false);
		queue.notify();
	}
}
