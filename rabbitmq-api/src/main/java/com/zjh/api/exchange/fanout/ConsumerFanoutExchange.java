package com.zjh.api.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/3 19:03
 * @desc
 */
public class ConsumerFanoutExchange {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "test_fanout_exchange";
        String exchangeType = "fanout";
        String queueName = "test_fanout_queue";
        String routingKey = "";
        //  exchange声明
        channel.exchangeDeclare(exchange, exchangeType, true, false, null);
        //  queue声明
        channel.queueDeclare(queueName, false, false, false, null);
        //  队列绑定
        channel.queueBind(queueName, exchange, routingKey);
        //  durable 是否持久化消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //  参数：队列名称、是否自动ack、consumer
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            byte[] body = delivery.getBody();
            System.out.println(new String(body));
        }


    }

}
