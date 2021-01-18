package com.zjh.rabbit.producer.broker;

import com.zjh.rabbit.api.Message;
import com.zjh.rabbit.api.MessageProducer;
import com.zjh.rabbit.api.SendCallBack;
import com.zjh.rabbit.api.exception.MessageRuntimeException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhaojh
 * @date 2021/1/17 21:47
 * @desc ProducerClient生产者发送消息接口实现类
 */
@Component
public class ProducerClient implements MessageProducer {


    @Override
    public void send(Message message) throws MessageRuntimeException {
    }

    @Override
    public void send(List<Message> list) {

    }

    @Override
    public void send(Message message, SendCallBack sendCallBack) throws MessageRuntimeException {

    }
}
