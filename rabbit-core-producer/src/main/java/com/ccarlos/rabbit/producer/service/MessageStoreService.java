package com.ccarlos.rabbit.producer.service;

import com.ccarlos.rabbit.producer.constant.BrokerMessageStatus;
import com.ccarlos.rabbit.producer.entity.BrokerMessage;
import com.ccarlos.rabbit.producer.mapper.BrokerMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description: 消息存储服务
 * @author: ccarlos
 * @date: 2020/6/17 22:20
 */
@Service
public class MessageStoreService {

    @Autowired
    private BrokerMessageMapper brokerMessageMapper;

    public int insert(BrokerMessage brokerMessage) {
        return this.brokerMessageMapper.insert(brokerMessage);
    }

    public void succuess(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId,
                BrokerMessageStatus.SEND_OK.getCode(),
                new Date());
    }

    public void failure(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId,
                BrokerMessageStatus.SEND_FAIL.getCode(),
                new Date());
    }
}
