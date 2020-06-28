package com.ccarlos.rabbit.producer.broker;

import java.util.Date;

import com.ccarlos.rabbit.api.Message;
import com.ccarlos.rabbit.api.MessageType;
import com.ccarlos.rabbit.producer.constant.BrokerMessageConst;
import com.ccarlos.rabbit.producer.constant.BrokerMessageStatus;
import com.ccarlos.rabbit.producer.entity.BrokerMessage;
import com.ccarlos.rabbit.producer.service.MessageStoreService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 真正的发送不同类型的消息实现类
 * @author: ccarlos
 * @date: 2020/6/15 20:54
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    private MessageStoreService messageStoreService;

    /**
     * @description: 迅速发消息
     * @author: ccarlos
     * @date: 2020/6/15 20:56
     * @param: message
     * @return: void
     */
    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        //2. 执行真正的发送消息逻辑
        sendKernel(message);
    }

    /**
     * @description: 发送消息的核心方法 使用异步线程池进行发送消息
     * @author: ccarlos
     * @date: 2020/6/15 20:57
     * @param: message
     * @return: void
     */
    private void sendKernel(Message message) {
        AsyncBaseQueue.submit((Runnable) () -> {
            CorrelationData correlationData =
                    new CorrelationData(String.format("%s#%s#%s",
                            message.getMessageId(),
                            System.currentTimeMillis(),
                            message.getMessageType()));
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();

            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());
        });
    }

    /**
     * @description: 发送确认消息
     * @author: ccarlos
     * @date: 2020/6/17 20:33
     * @param: message
     * @return: void
     */
    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    /**
     * @description: 消息可靠性投递
     * @author: ccarlos
     * @date: 2020/6/17 22:19
     * @param: message
     * @return: void
     */
    @Override
    public void reliantSend(Message message) {
        message.setMessageType(MessageType.RELIANT);
        BrokerMessage bm = messageStoreService.selectByMessageId(message.getMessageId());
        if (bm == null) {
            //1. 把数据库的消息发送日志先记录好
            Date now = new Date();
            BrokerMessage brokerMessage = new BrokerMessage();
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
            //tryCount 在最开始发送的时候不需要进行设置
            brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
            brokerMessage.setCreateTime(now);
            brokerMessage.setUpdateTime(now);
            brokerMessage.setMessage(message);
            messageStoreService.insert(brokerMessage);
        }
        //2. 执行真正的发送消息逻辑
        sendKernel(message);
    }

    @Override
    public void sendMessages() {

    }
}
