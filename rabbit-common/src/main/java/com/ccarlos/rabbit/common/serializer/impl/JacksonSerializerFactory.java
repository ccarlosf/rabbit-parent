package com.ccarlos.rabbit.common.serializer.impl;


import com.ccarlos.rabbit.api.Message;
import com.ccarlos.rabbit.common.serializer.Serializer;
import com.ccarlos.rabbit.common.serializer.SerializerFactory;

/**
 * @description: Json序列化工厂接口
 * @author: ccarlos
 * @date: 2020/6/15 22:55
 */
public class JacksonSerializerFactory implements SerializerFactory {

	public static final SerializerFactory INSTANCE = new JacksonSerializerFactory();
	
	@Override
	public Serializer create() {
		return JacksonSerializer.createParametricType(Message.class);
	}

}
