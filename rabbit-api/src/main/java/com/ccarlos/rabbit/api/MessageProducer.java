package com.ccarlos.rabbit.api;

import com.ccarlos.rabbit.api.exception.MessageRunTimeException;

import java.util.List;

/**
 * @description: 消息生产者
 * @author: ccarlos
 * @date: 2020/6/15 20:23
 */
public interface MessageProducer {

    /**
     * @description: send消息的发送 附带SendCallback回调执行响应的业务逻辑处理
     * @author: ccarlos
     * @date: 2020/6/15 20:25
     * @param: message
     * @param: sendCallback
     * @return: void
     */
    void send(Message message, SendCallback sendCallback) throws MessageRunTimeException;

    /**
     * @description: message消息的发送
     * @author: ccarlos
     * @date: 2020/6/15 20:26
     * @param: message
     * @return: void
     */
    void send(Message message) throws MessageRunTimeException;


    /**
     * @description: send 消息的批量发送
     * @author: ccarlos
     * @date: 2020/6/15 20:26
     * @param: messages
     * @return: void
     */
    void send(List<Message> messages) throws MessageRunTimeException;

}
