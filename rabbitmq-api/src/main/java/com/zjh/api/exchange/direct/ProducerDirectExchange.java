package com.zjh.api.exchange.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/3 19:03
 * @desc
 */
public class ProducerDirectExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "test_direct_exchange";
        String routingKey = "test_direct_routingkey";
        String msg = "I fell in love with that girl";
        channel.basicPublish(exchange, routingKey, null, msg.getBytes());

    }
}
