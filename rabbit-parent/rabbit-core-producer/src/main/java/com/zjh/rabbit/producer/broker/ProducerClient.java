package com.zjh.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.zjh.rabbit.api.Message;
import com.zjh.rabbit.api.MessageProducer;
import com.zjh.rabbit.api.MessageType;
import com.zjh.rabbit.api.SendCallBack;
import com.zjh.rabbit.api.exception.MessageRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhaojh
 * @date 2021/1/17 21:47
 * @desc ProducerClient生产者发送消息接口实现类
 */
@Component
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) throws MessageRuntimeException {
        Preconditions.checkNotNull(message.getTopic());
        String messageType = message.getMessageType();
        switch (messageType) {
            case MessageType.RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIANT:
                rabbitBroker.reliantSend(message);
                break;
            default:
                break;
        }
    }

    @Override
    public void send(List<Message> list) {
    }

    @Override
    public void send(Message message, SendCallBack sendCallBack) throws MessageRuntimeException {
    }


}
