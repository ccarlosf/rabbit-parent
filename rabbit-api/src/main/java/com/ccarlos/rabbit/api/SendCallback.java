package com.ccarlos.rabbit.api;

/**
 * @description: 回调函数处理
 * @author: ccarlos
 * @date: 2020/6/15 20:25
 */
public interface SendCallback {

	void onSuccess();
	
	void onFailure();
	
}
