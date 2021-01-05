package com.zjh.api.exchange.rqueue;

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
 * @date 2021/1/4 23:10
 * @desc
 */
public class RequeueProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "test_reenter_exchange";
        String queueName = "test_reenter_queue";
        String routingKey = "test.com";
        channel.queueDeclare(queueName, true, false, false, null);
        for (int i = 0; i < 5000000; i++) {
            Map<String, Object> headers = new HashMap();
            headers.put("flag", i);
            AMQP.BasicProperties prop = new AMQP.BasicProperties().builder().headers(headers).build();
            String msg = "相信美好的事情即将发生";
            channel.basicPublish(exchange, routingKey, prop, msg.getBytes());
        }

    }
}
