package com.zjh.rabbit.producer.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.zjh.rabbit.common.constant.BrokerMessageStatus;
import com.zjh.rabbit.producer.broker.RabbitBroker;
import com.zjh.rabbit.producer.entity.BrokerMessage;
import com.zjh.rabbit.producer.service.MessageStoreService;
import com.zjh.rabbit.task.annotation.ElasticJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RetryMessageDataFlowJob
 *
 * @author zhaojh
 * @date 2021/1/28 11:26
 */
@Slf4j
@Component
@ElasticJobConfig(name = "com.zjh.rabbit.producer.task.RetryMessageDataflowJob",
        cron = "0/10 0 0 0 0 ?",
        description = "可靠性投递消息补偿任务",
        overwrite = true,
        shardingTotalCount = 1)
public class RetryMessageDataflowJob implements DataflowJob<BrokerMessage> {

    @Autowired
    private MessageStoreService messageStoreService;

    @Autowired
    private RabbitBroker rabbitBroker;

    private final int MAX_RETRY_COUNT = 3;

    /**
     * 拉取sending状态的任务
     *
     * @param shardingContext
     * @return
     */
    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        List<BrokerMessage> list = messageStoreService.queryBrokerMessageStatus(BrokerMessageStatus.SEND_ING);
        log.info("The sending status data size is {}", list.size());
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> list) {
        list.stream().forEach(brokerMessage -> {
            String messageId = brokerMessage.getMessageId();
            if (brokerMessage.getTryCount() >= MAX_RETRY_COUNT) {
                messageStoreService.failure(messageId);
                log.warn("------消息设置为最终失败，消息id：{}", messageId);
            } else {
                // 每次重发前，需要更新消息重试次数+1
                messageStoreService.update4TryCount(messageId);
                rabbitBroker.reliantSend(brokerMessage.getMessage());
            }
        });
    }
}
