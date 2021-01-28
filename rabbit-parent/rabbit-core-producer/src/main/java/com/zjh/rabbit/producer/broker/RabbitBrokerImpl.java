package com.zjh.rabbit.producer.broker;

import com.zjh.rabbit.api.Message;
import com.zjh.rabbit.api.MessageType;
import com.zjh.rabbit.common.constant.BrokerMessageConstant;
import com.zjh.rabbit.common.constant.BrokerMessageStatus;
import com.zjh.rabbit.producer.entity.BrokerMessage;
import com.zjh.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * $RabbitBrokerImpl 真正发送不同消息类型的实现类
 *
 * @author zhaojh
 * @date 2021/1/20 20:54
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    private MessageStoreService messageStoreService;

    /**
     * 迅速发消息
     *
     * @param message
     */
    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    @Override
    public void reliantSend(Message message) {
        BrokerMessage brokerMessage = messageStoreService.selectByMessageId(message.getMessageId());
        if (brokerMessage == null) {
            Date now = new Date();
            brokerMessage = new BrokerMessage();
            message.setMessageType(MessageType.RELIANT);
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setStatus(BrokerMessageStatus.SEND_ING.getCode());
            // tryCount在最开始发送的时候不需要进行设置
            brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConstant.TIMEOUT.getCode()));
            brokerMessage.setCreateTime(now);
            brokerMessage.setUpdateTime(now);
            brokerMessage.setMessage(message);
            messageStoreService.insert(brokerMessage);
        }
        sendKernel(message);
    }

    @Override
    public void sendMessages() {

    }

    /**
     * $sendKernel 发送消息的核心方法，使用异步线程池发送消息
     *
     * @param message
     */
    private void sendKernel(Message message) {
        AsyncBaseQueue.submit((Runnable) () -> {
            CorrelationData correlationData = new CorrelationData(String.format("%s#%s#%s",
                    message.getMessageId(),
                    System.currentTimeMillis(),
                    message.getMessageType())
            );
            String routingKey = message.getRoutingKey();
            String topic = message.getTopic();
            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getRabbitTemplate(message);
            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());
        });


    }
}