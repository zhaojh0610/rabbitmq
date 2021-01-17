package com.zjh.rabbit.api;

import com.zjh.rabbit.api.exception.MessageRuntimeException;

import java.util.List;

/**
 * @author zhaojh
 * @date 2021/1/17 15:52
 * @desc 生产者消息发送接口
 */
public interface MessageProducer {

    /**
     * 生产者message发送
     *
     * @param message
     * @throws MessageRuntimeException
     */
    void send(Message message) throws MessageRuntimeException;

    /**
     * 生产者message发送，附带SendCallBack回调执行响应的业务逻辑处理
     *
     * @param message
     * @param sendCallBack
     * @throws MessageRuntimeException
     */
    void send(Message message, SendCallBack sendCallBack) throws MessageRuntimeException;

    /**
     * 生产者批量发送消息
     *
     * @param list
     */
    void send(List<Message> list);
}
