package com.zjh.rabbit.producer.broker;

import com.zjh.rabbit.api.Message;

/**
 * @author zhaojh
 * @date 2021/1/20 16:19
 * @desc RabbitBroker 具体发送不同类型消息的接口
 */
public interface RabbitBroker {

    /**
     * 发送迅速消息接口
     *
     * @param message
     */
    void rapidSend(Message message);

    /**
     * 发送确认消息
     *
     * @param message
     */
    void confirmSend(Message message);

    /**
     * 发送可靠性消息
     *
     * @param message
     */
    void reliantSend(Message message);

    /**
     * 发送消息接口
     */
    void sendMessages();
}
