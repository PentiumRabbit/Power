/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 报警阻塞队列
 * 
 * @author chongan.wangca
 */
public class MessageQueue {

	// 队列大小
	public static final int QUEUE_MAX_SIZE = 100;

	// 阻塞队列
	private BlockingQueue<IMessage> blockingQueue = new LinkedBlockingQueue<IMessage>(
			QUEUE_MAX_SIZE);

	/**
	 * 消息入队
	 * 
	 * @return
	 */
	public boolean push(IMessage message) {
		return this.blockingQueue.offer(message);
	}

	/**
	 * 消息出队
	 * 
	 * @return
	 */
	public IMessage poll() {
		IMessage result = null;
		try {
			result = this.blockingQueue.take();
		} catch (InterruptedException e) {

		}
		return result;
	}

	/**
	 * 获取队列大小
	 * 
	 * @return
	 */
	public int size() {
		return this.blockingQueue.size();
	}


}
