package com.zjh.api.exchange.helloWorld;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/2 22:15
 * @desc
 */
public class ConsumerTest {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String queue = "hellWorld";
        //	durable 是否持久化消息
        channel.queueDeclare(queue, false, false, false, null);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //	参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queue, true, consumer);
        Map<String, Object> headers = new HashMap<>();
        while (true) {
            //	获取消息，如果没有消息，这一步将会一直阻塞
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            byte[] body = delivery.getBody();
            System.out.println(new String(body));
        }

    }
}

