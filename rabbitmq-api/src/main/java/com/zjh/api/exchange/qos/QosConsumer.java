package com.zjh.api.exchange.qos;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/4 23:10
 * @desc
 */
public class QosConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String queueName = "test_qos_queue";
        channel.queueDeclare(queueName, true, false, false, null);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicQos(0, 1, false);
        channel.basicConsume(queueName, false, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            Integer falg = (Integer) delivery.getProperties().getHeaders().get("flag");
//            if (falg % 3 == 0) {
//                System.out.println("Nack：" + msg + falg);
//                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
//            } else {
//                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//                System.out.println("Ack：" + msg + falg);
//            }
        }

    }
}
