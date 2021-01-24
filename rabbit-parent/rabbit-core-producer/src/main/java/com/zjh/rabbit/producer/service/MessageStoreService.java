package com.zjh.rabbit.producer.service;

import com.zjh.rabbit.producer.entity.BrokerMessage;
import com.zjh.rabbit.producer.mapper.BrokerMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhaojh
 * @date 2021/1/24 9:31
 */
@Service
public class MessageStoreService {

    @Autowired
    private BrokerMessageMapper brokerMessageMapper;

    public int insert(BrokerMessage brokerMessage) {
        return brokerMessageMapper.insert(brokerMessage);
    }

    public void changeBrokerMessageStatus(String messageId, String brokerMessageStatus, Date updateTime) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, brokerMessageStatus, updateTime);
    }

}
