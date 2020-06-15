package com.ccarlos.rabbit.producer.broker;


import com.ccarlos.rabbit.api.Message;
import com.ccarlos.rabbit.api.MessageProducer;
import com.ccarlos.rabbit.api.SendCallback;
import com.ccarlos.rabbit.api.exception.MessageRunTimeException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 发送消息的实际实现类
 * @author: ccarlos
 * @date: 2020/6/15 20:41
 */
@Component
public class ProducerClient implements MessageProducer {


	@Override
	public void send(Message message, SendCallback sendCallback) throws MessageRunTimeException {

	}

	@Override
	public void send(Message message) throws MessageRunTimeException {

	}

	@Override
	public void send(List<Message> messages) throws MessageRunTimeException {

	}
}
