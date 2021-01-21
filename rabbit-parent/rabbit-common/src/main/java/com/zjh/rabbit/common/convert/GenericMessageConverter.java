package com.zjh.rabbit.common.convert;

import com.google.common.base.Preconditions;
import com.zjh.rabbit.common.serializer.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author zhaojh
 * @date 2021/1/21 14:47
 */
public class GenericMessageConverter implements MessageConverter {

    private Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);
        this.serializer = serializer;
    }


    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return this.serializer.deserialize(message.getBody());
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(serializer.serializeRaw(object), messageProperties);
    }

}
