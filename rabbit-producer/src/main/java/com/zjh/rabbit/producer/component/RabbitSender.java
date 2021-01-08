package com.zjh.rabbit.producer.component;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author zhaojh
 * @date 2021/1/7 22:45
 * @desc
 */
@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 这里就是确认消息的回调监听接口，用于确认消息是否被broker所收到
     */
    final ConfirmCallback confirmCallback = new ConfirmCallback() {
        /**
         *
         * @param correlationData 作为一个唯一标识
         * @param ack 是否落盘成功
         * @param cause 失败的异常信息
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("消息ACK结果:" + ack + ",correlationData:" + correlationData.getId());
        }
    };

    /**
     * 对外发送消息的方法
     *
     * @param message    具体的消息内容
     * @param properties 额外的附加属性
     * @throws Exception
     */
    public void sender(Object message, Map<String, Object> properties) throws Exception {

        MessageHeaders messageHeaders = new MessageHeaders(properties);
        Message<?> msg = MessageBuilder.createMessage(message, messageHeaders);

        rabbitTemplate.setConfirmCallback(confirmCallback);

        // 指定业务唯一的ID
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        MessagePostProcessor mpp = new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                System.out.println("---> ost to do: " + message);
                return message;
            }
        };
        rabbitTemplate.convertAndSend("exchange-1", "springboot.rabbit", msg, mpp, correlationData);
    }
}
