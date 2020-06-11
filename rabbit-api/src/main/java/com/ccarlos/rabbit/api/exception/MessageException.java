package com.ccarlos.rabbit.api.exception;

/**
 * @description: 消息异常类
 * @author: ccarlos
 * @date: 2020/6/11 21:04
 */
public class MessageException extends Exception {

	private static final long serialVersionUID = 6347951066190728758L;

	public MessageException() {
		super();
	}
	
	public MessageException(String message) {
		super(message);
	}
	
	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MessageException(Throwable cause) {
		super(cause);
	}
	
}
