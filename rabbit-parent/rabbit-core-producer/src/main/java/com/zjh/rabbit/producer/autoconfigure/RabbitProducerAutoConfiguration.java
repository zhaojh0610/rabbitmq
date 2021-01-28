package com.zjh.rabbit.producer.autoconfigure;

import com.zjh.rabbit.task.annotation.EnableElasticJob;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaojh
 * @date 2021/1/17 21:43
 * @desc RabbitProducerAutoConfiguration自动装配
 */
@EnableElasticJob
@Configuration
@ComponentScan({"com.zjh.rabbit.producer.*"})
public class RabbitProducerAutoConfiguration {
}
