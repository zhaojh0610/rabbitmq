package com.zjh.api.exchange.ttl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/2 22:15
 * @desc
 */
public class DlxConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "test_dlx_exchange";
        String queue = "test_dlx_queue";
        String type = "topic";
        String routingKey = "test.#";
        //  声明exchange
        channel.exchangeDeclare(exchange, type, true, false, false, null);
        Map<String, Object> argument = new HashMap<>();
        //  配置死信队列
        argument.put("x-dead-letter-exchange", "dlx.exchange");
        //	durable 是否持久化消息
        channel.queueDeclare(queue, false, false, false, argument);
        //  绑定队列到exchange上
        channel.queueBind(queue, exchange, routingKey);

        //  死信交换机声明
        channel.exchangeDeclare("dlx.exchange", type, true, false, false, null);
        //  死信队列声明
        channel.queueDeclare("dlx.queue", true, false, false, null);
        //  死信交换机和死信队列绑定
        channel.queueBind("dlx.queue", "dlx.exchange", "#");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        //	参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queue, false, consumer);
        Map<String, Object> headers = new HashMap<>();
        while (true) {
            //	获取消息，如果没有消息，这一步将会一直阻塞
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            byte[] body = delivery.getBody();
            System.out.println(new String(body));
        }

    }
}

