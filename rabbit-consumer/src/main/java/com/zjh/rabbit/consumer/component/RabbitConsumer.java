package com.zjh.rabbit.consumer.component;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhaojh
 * @date 2021/1/8 17:06
 * @desc
 */
@Component
public class RabbitConsumer {

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue-1", durable = "true")
            , exchange = @Exchange(name = "exchange-1", type = "topic")
            , key = "springboot.*"))
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws IOException {
        // 收到消息以后进行业务端消费
        System.out.println("---------------------");
        System.out.println("消费消息:" + message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
    }

}
