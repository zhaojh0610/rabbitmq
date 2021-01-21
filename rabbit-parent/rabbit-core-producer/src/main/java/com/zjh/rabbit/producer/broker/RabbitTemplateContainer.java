package com.zjh.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.zjh.rabbit.api.Message;
import com.zjh.rabbit.api.MessageType;
import com.zjh.rabbit.api.exception.MessageRuntimeException;
import com.zjh.rabbit.common.convert.GenericMessageConverter;
import com.zjh.rabbit.common.convert.RabbitMessageConverter;
import com.zjh.rabbit.common.serializer.Serializer;
import com.zjh.rabbit.common.serializer.impl.JacksonSerializerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * $RabbitTemplateContainer 池化封装
 * 每一个topic 对应一个RabbitTemplate
 * 1. 提高发送效率
 * 2. 可以根据不同的需求制定不同的RabbitTemplate，比如每一个topic 都有自己的routingKey规则
 *
 * @author zhaojh
 * @date 2021/1/20 21:51
 */
@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    private Map<String, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    private JacksonSerializerFactory jack = JacksonSerializerFactory.INSTANCE;

    private Splitter splitter = Splitter.on("#");

    @Autowired
    private ConnectionFactory connectionFactory;

    public RabbitTemplate getRabbitTemplate(Message message) throws MessageRuntimeException {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        if (rabbitTemplate != null) {
            return rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getRabbitTemplate#topic :{} is not exist, create one ", topic);
        RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
        newTemplate.setExchange(topic);
        newTemplate.setRoutingKey(message.getRoutingKey());
        newTemplate.setRetryTemplate(new RetryTemplate());

        if (!MessageType.RAPID.equals(message.getMessageType())) {
            newTemplate.setConfirmCallback(this);
        }
        // 对于message的序列化方式
        Serializer serializer = jack.create();
        GenericMessageConverter gmc = new GenericMessageConverter(serializer);
        RabbitMessageConverter rmc = new RabbitMessageConverter(gmc);
        newTemplate.setMessageConverter(rmc);
        rabbitMap.putIfAbsent(topic, newTemplate);
        return rabbitMap.get(topic);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        List<String> list = splitter.splitToList(correlationData.getId());
        String messageId = "";
        Long sendTime = null;
        if (!CollectionUtils.isEmpty(list)) {
            messageId = list.get(0);
            sendTime = Long.parseLong(list.get(1));
        }
        if (ack) {
            log.info("send message is ok, confirm messageId:{}, sendTime: {}", messageId, sendTime);
        } else {
            log.error("send message is fail, confirm messageId:{}, sendTime: {}", messageId, sendTime);
        }

    }
}
