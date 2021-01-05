package com.zjh.api.exchange.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/2 22:15
 * @desc
 */
public class DlxProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        String exchange = "test_dlx_exchange";
        String routingKey = "test.dlx";
        Channel channel = connection.createChannel();
        Map<String, Object> headers = new HashMap<>();
        AMQP.BasicProperties prop = new AMQP.BasicProperties().builder()
                .headers(headers)
                //  设置过期时间
                .expiration("6000")
                .contentEncoding("utf-8")
                .deliveryMode(2)
                .build();
        for (int i = 0; i < 10; i++) {
            String msg = "helloWorld" + i;
            channel.basicPublish(exchange, routingKey, prop, msg.getBytes());
        }

    }
}
