package com.ccarlos.rabbit.common.serializer;

/**
 * @description: 序列化工厂接口
 * @author: ccarlos
 * @date: 2020/6/15 22:55
 */
public interface SerializerFactory {
	
	Serializer create();
}
