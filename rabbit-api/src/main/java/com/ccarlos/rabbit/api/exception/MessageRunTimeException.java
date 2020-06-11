package com.ccarlos.rabbit.api.exception;

/**
 * @description: 消息运行时异常类
 * @author: ccarlos
 * @date: 2020/6/11 21:04
 */
public class MessageRunTimeException extends RuntimeException {

	private static final long serialVersionUID = 8651828913888663267L;

	public MessageRunTimeException() {
		super();
	}
	
	public MessageRunTimeException(String message) {
		super(message);
	}
	
	public MessageRunTimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MessageRunTimeException(Throwable cause) {
		super(cause);
	}
}
