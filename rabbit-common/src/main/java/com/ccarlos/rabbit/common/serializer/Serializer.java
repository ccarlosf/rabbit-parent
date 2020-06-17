package com.ccarlos.rabbit.common.serializer;

/**
 * @description: 序列化和反序列化的接口
 * @author: ccarlos
 * @date: 2020/6/15 22:46
 */
public interface Serializer {

	byte[] serializeRaw(Object data);
	
	String serialize(Object data);
	
	<T> T deserialize(String content);
	
	<T> T deserialize(byte[] content);
	
}
