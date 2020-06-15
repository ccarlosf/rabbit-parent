package com.ccarlos.rabbit.producer.broker;


import com.ccarlos.rabbit.api.Message;

/**
 * @description: 具体发送不同种类型消息的接口
 * @author: ccarlos
 * @date: 2020/6/15 20:52
 */
public interface RabbitBroker {
	
	void rapidSend(Message message);
	
	void confirmSend(Message message);
	
	void reliantSend(Message message);
	
	void sendMessages();
	
}
