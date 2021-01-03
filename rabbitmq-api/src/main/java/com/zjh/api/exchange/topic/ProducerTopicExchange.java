package com.zjh.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/3 19:03
 * @desc
 */
public class ProducerTopicExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "test_topic_exchange";
        String routingKey1 = "test.zjh";
        String routingKey2 = "test.zjh.zhao";
        String routingKey3 = "test.zjh.com";
        String msg = "I fell in love with that girl";
        channel.basicPublish(exchange, routingKey1, null, msg.getBytes());
        channel.basicPublish(exchange, routingKey2, null, msg.getBytes());
        channel.basicPublish(exchange, routingKey3, null, msg.getBytes());

    }
}
