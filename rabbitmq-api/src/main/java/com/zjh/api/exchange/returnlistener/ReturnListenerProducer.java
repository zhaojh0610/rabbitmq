package com.zjh.api.exchange.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/3 19:03
 * @desc
 */
public class ReturnListenerProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "test_return_listener_exchange";
        String routingKey = "test.zjh.com";
        String msg = "I fell in love with that girl";

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode,
                                     String replyText,
                                     String exchange,
                                     String routingKey,
                                     AMQP.BasicProperties properties,
                                     byte[] body) throws IOException {
                System.out.println("replyCode: " + replyCode);
                System.out.println("replyText: " + replyText);
                System.out.println("exchange: " + exchange);
                System.out.println("routingKey: " + routingKey);
                System.out.println("properties: " + properties);
                System.out.println("body: " + new String(body));
            }
        });
        //  开启return消息机制
        boolean mandatory = true;
        channel.basicPublish(exchange, routingKey, mandatory, null, msg.getBytes());
    }
}
