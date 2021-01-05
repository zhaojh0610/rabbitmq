package com.zjh.api.exchange.rqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/4 23:10
 * @desc
 */
public class RequeueConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "test_reenter_exchange";
        String queueName = "test_reenter_queue";
        String type = "topic";
        String routingKey = "test.#";
        channel.exchangeDeclare(exchange, type, true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchange, routingKey);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            Integer falg = (Integer) delivery.getProperties().getHeaders().get("flag");
            System.out.println("收到的消息：" + msg + falg);
            if (falg == 0) {
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(),false,false);
            } else {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        }

    }
}
