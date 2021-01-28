package com.zjh.rabbit.producer.service;

import com.zjh.rabbit.common.constant.BrokerMessageStatus;
import com.zjh.rabbit.producer.entity.BrokerMessage;
import com.zjh.rabbit.producer.mapper.BrokerMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public BrokerMessage selectByMessageId(String messageId) {
        return brokerMessageMapper.selectByPrimaryKey(messageId);
    }

    public void success(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_OK.getCode(), new Date());
    }

    public void failure(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_FAIL.getCode(), new Date());
    }

    public void changeBrokerMessageStatus(String messageId, String brokerMessageStatus, Date updateTime) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, brokerMessageStatus, updateTime);
    }

    public List<BrokerMessage> queryBrokerMessageStatus(BrokerMessageStatus brokerMessageStatus) {
        return brokerMessageMapper.queryBrokerMessageStatus(brokerMessageStatus.getCode());
    }

    public List<BrokerMessage> queryBrokerMessageStatus4Timeout(BrokerMessageStatus brokerMessageStatus) {
        return brokerMessageMapper.queryBrokerMessageStatus4Timeout(brokerMessageStatus.getCode());
    }

    public int update4TryCount(String messageId) {
        return brokerMessageMapper.update4TryCount(messageId, new Date());
    }
}
