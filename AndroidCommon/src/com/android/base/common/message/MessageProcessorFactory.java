/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.SparseArray;

/**
 * 创建多线程异步共享队列消息发送工厂
 * 
 * @author Administrator
 *
 */
public class MessageProcessorFactory {

	private MessageProcessorFactory factory = new MessageProcessorFactory();
	ExecutorService pool = Executors.newCachedThreadPool();
	private SparseArray<MessageQueue> queueMap;
	private SparseArray<MessageLooper> runnableMap;

	private MessageProcessorFactory() {
		queueMap = new SparseArray<MessageQueue>();
		runnableMap = new SparseArray<MessageLooper>();
	}

	public MessageProcessorFactory getInstance() {
		return factory;
	}

	public boolean registerProcessor(ProcessorType type,
			IMessageProcessor handout) {
		int ordinal = type.ordinal();

		if (queueMap.get(ordinal) != null) {
			return false;
		}

		MessageQueue queue = new MessageQueue();
		MessageLooper loop = new MessageLooper(queue, handout);
		pool.execute(loop);
		runnableMap.put(ordinal, loop);
		queueMap.put(ordinal, queue);
		return true;
	}

	public boolean addMessage(ProcessorType type, IMessage message) {
		int ordinal = type.ordinal();
		MessageQueue queue = queueMap.get(ordinal);
		queue.push(message);

		return true;
	}

	public void unRegistProcessor(ProcessorType type) {
		int ordinal = type.ordinal();
		queueMap.remove(ordinal);
		MessageLooper loop = runnableMap.get(ordinal);
		loop.stop();
		runnableMap.remove(ordinal);

	}

}
