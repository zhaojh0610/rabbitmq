package com.zjh.rabbit.api;

/**
 * @author zhaojh
 * @date 2021/1/17 15:55
 * @desc MessageListener：消费者监听消息
 */
public interface MessageListener {

    /**
     * 消费者监听消息处理
     * @param message
     */
    void onMessage(Message message);
}
