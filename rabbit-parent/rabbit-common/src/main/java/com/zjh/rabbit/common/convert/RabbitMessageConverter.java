package com.zjh.rabbit.common.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author zhaojh
 * @date 2021/1/21 15:21
 */
public class RabbitMessageConverter implements MessageConverter {

    private GenericMessageConverter delegate;

    private final String defaultExpire = String.valueOf(1000 * 60 * 60 * 24);

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        this.delegate = genericMessageConverter;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        com.zjh.rabbit.api.Message msg = (com.zjh.rabbit.api.Message) this.delegate.fromMessage(message);
        return msg;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        messageProperties.setExpiration(defaultExpire);
        messageProperties.setContentEncoding("UTF-8");
        return this.delegate.toMessage(object, messageProperties);
    }
}
