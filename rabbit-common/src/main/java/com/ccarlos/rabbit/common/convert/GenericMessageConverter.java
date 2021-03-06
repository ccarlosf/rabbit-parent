package com.ccarlos.rabbit.common.convert;

import com.ccarlos.rabbit.common.serializer.Serializer;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.common.base.Preconditions;

/**
 * @description: 通用消息转化类
 * @author: ccarlos
 * @date: 2020/6/15 22:56
 */
public class GenericMessageConverter implements MessageConverter {

    private Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);
        this.serializer = serializer;
    }

    @Override
    public Object fromMessage(org.springframework.amqp.core.Message message)
            throws MessageConversionException {
        return this.serializer.deserialize(message.getBody());
    }

    @Override
    public org.springframework.amqp.core.Message toMessage
            (Object object, MessageProperties messageProperties)
            throws MessageConversionException {
        return new org.springframework.amqp.core.Message
				(this.serializer.serializeRaw(object), messageProperties);
    }

}
