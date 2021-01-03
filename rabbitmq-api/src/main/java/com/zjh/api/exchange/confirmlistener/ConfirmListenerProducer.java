package com.zjh.api.exchange.confirmlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojh
 * @date 2021/1/3 19:03
 * @desc
 */
public class ConfirmListenerProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.120.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "test_confirm_listener_exchange";
        String routingKey = "test.zjh";
        String msg = "I fell in love with that girl";
        //  开启消息确认
        channel.confirmSelect();
        //  添加消息确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("--------------ok------------");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("--------------error------------");
            }
        });
        channel.basicPublish(exchange, routingKey, null, msg.getBytes());
    }
}
