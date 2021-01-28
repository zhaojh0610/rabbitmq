package com.zjh.rabbit.producer.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.zjh.rabbit.producer.entity.BrokerMessage;
import com.zjh.rabbit.task.annotation.ElasticJobConfig;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RetryMessageDataFlowJob
 *
 * @author zhaojh
 * @date 2021/1/28 11:26
 */
@Component
@ElasticJobConfig(name = "com.zjh.rabbit.producer.task.RetryMessageDataflowJob",
        cron = "0/10 0 0 0 0 ?",
        description = "可靠性投递消息补偿任务",
        overwrite = true,
        shardingTotalCount = 1)
public class RetryMessageDataflowJob implements DataflowJob<BrokerMessage> {

    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> data) {

    }
}
