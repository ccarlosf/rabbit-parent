package com.ccarlos.rabbit.api;

/**
 * @description: 消费者监听消息
 * @author: ccarlos
 * @date: 2020/6/15 20:24
 */
public interface MessageListener {

	/**
	 * @description: 监听消息
	 * @author: ccarlos
	 * @date: 2020/6/15 20:27
	 * @param: message
	 * @return: void
	 */
	void onMessage(Message message);
	
}
