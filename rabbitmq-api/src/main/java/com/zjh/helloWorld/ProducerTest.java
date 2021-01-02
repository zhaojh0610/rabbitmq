package com.zjh.helloWorld;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
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
public class ProducerTest {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String queue = "hellWorld";
        //	参数: queue名字,是否持久化,独占的queue（仅供此连接）,不使用时是否自动删除, 其他参数
        channel.queueDeclare(queue, false, false, false, null);
        Map<String, Object> headers = new HashMap<>();
        AMQP.BasicProperties prop = new AMQP.BasicProperties().builder()
                .headers(headers)
                .contentEncoding("utf-8")
                .deliveryMode(2)
                .build();
        for (int i = 0; i < 10; i++) {
            String msg = "helloWorld" + i;
            channel.basicPublish("", queue, prop, msg.getBytes());
        }

    }
}
