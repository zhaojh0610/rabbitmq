package com.zjh.rabbit.producer.broker;

import com.zjh.rabbit.api.Message;
import com.zjh.rabbit.api.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * $RabbitBrokerImpl 真正发送不同消息类型的实现类
 * @author zhaojh
 * @date 2021/1/20 20:54
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

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
            CorrelationData correlationData = new CorrelationData(String.format("%s#%s",
                    message.getMessageId(),
                    System.currentTimeMillis()));
            String routingKey = message.getRoutingKey();
            String topic = message.getTopic();
            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getRabbitTemplate(message);
            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());
        });


    }
}