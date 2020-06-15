package com.ccarlos.rabbit.producer.broker;

import java.util.Date;

import com.ccarlos.rabbit.api.Message;
import com.ccarlos.rabbit.api.MessageType;
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

    /**
     * @description: 迅速发消息
     * @author: ccarlos
     * @date: 2020/6/15 20:56
     * @param: message
     * @return: void
     */
    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RELIANT);
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
                    new CorrelationData(String.format("%s#%s",
                            message.getMessageId(),
                            System.currentTimeMillis()));
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();

            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());

        });
    }

    @Override
    public void confirmSend(Message message) {

    }

    @Override
    public void reliantSend(Message message) {

    }

    @Override
    public void sendMessages() {

    }
}
