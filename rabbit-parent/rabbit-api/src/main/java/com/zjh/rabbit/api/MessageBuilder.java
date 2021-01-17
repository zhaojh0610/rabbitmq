package com.zjh.rabbit.api;

import com.zjh.rabbit.api.exception.MessageRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhaojh
 * @date 2021/1/17 15:08
 * @desc MessageBuilder：建造者模型
 */
@Builder
@AllArgsConstructor
public class MessageBuilder {

    private String messageId;

    private String topic;

    private String routingKey = "";

    private Map<String, Object> attributes = new HashMap<>();

    private Integer delayMills;

    private String messageType = "";

    private MessageBuilder() {
    }

    public MessageBuilder create() {
        return new MessageBuilder();
    }

    public MessageBuilder withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder withTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder withRoutingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public MessageBuilder withAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public MessageBuilder withAttribute(String key, Object value) {
        this.attributes.put(key, value);
        return this;
    }

    public MessageBuilder withDelayMills(Integer delayMills) {
        this.delayMills = delayMills;
        return this;
    }

    public Message build() {
        if (StringUtils.isBlank(messageId)) {
            messageId = UUID.randomUUID().toString();
        }

        if (StringUtils.isBlank(topic)) {
            throw new MessageRuntimeException("this topic is null");
        }
        Message message = new Message(messageId, topic, routingKey, attributes, delayMills, messageType);
        return message;
    }

}
