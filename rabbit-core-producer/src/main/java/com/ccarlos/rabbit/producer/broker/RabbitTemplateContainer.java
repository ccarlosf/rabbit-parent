package com.ccarlos.rabbit.producer.broker;

import java.util.List;
import java.util.Map;

import com.ccarlos.rabbit.api.Message;
import com.ccarlos.rabbit.api.MessageType;
import com.ccarlos.rabbit.api.exception.MessageRunTimeException;
import com.ccarlos.rabbit.common.convert.GenericMessageConverter;
import com.ccarlos.rabbit.common.convert.RabbitMessageConverter;
import com.ccarlos.rabbit.common.serializer.Serializer;
import com.ccarlos.rabbit.common.serializer.SerializerFactory;
import com.ccarlos.rabbit.common.serializer.impl.JacksonSerializerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: $RabbitTemplateContainer池化封装
 * 每一个topic 对应一个RabbitTemplate
 * 1. 提高发送的效率
 * 2. 可以根据不同的需求制定化不同的RabbitTemplate, 比如每一个topic 都有自己的routingKey规则
 * @author: ccarlos
 * @date: 2020/6/15 21:15
 */
@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    private Map<String /* TOPIC */, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    private Splitter splitter = Splitter.on("#");

    private SerializerFactory serializerFactory = JacksonSerializerFactory.INSTANCE;

    @Autowired
    private ConnectionFactory connectionFactory;

    public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        if (rabbitTemplate != null) {
            return rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getTemplate# topic: {} is not exists, create one", topic);

        RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
        newTemplate.setExchange(topic);
        newTemplate.setRoutingKey(message.getRoutingKey());
        newTemplate.setRetryTemplate(new RetryTemplate());

        // 对于message的序列化方式
        Serializer serializer = serializerFactory.create();
        GenericMessageConverter gmc = new GenericMessageConverter(serializer);
        RabbitMessageConverter rmc = new RabbitMessageConverter(gmc);
        newTemplate.setMessageConverter(rmc);

        String messageType = message.getMessageType();
        if (!MessageType.RAPID.equals(messageType)) {
            newTemplate.setConfirmCallback(this);
            // newTemplate.setChannelTransacted(true);
        }

        rabbitMap.putIfAbsent(topic, newTemplate);

        return rabbitMap.get(topic);
    }

    /**
     * 无论是 confirm 消息 还是 reliant 消息 ，发送消息以后 broker都会去回调confirm
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // 	具体的消息应答
        List<String> strings = splitter.splitToList(correlationData.getId());
        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));
        if (ack) {
            log.info("send message is OK, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        } else {
            log.error("send message is Fail, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        }
    }
}
