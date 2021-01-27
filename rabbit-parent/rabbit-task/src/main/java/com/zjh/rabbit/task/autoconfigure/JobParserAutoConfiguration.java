package com.zjh.rabbit.task.autoconfigure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JobParserAutoConfiguration  定时任务自动配置类
 *
 * @author zhaojh
 * @date 2021/1/27 17:13
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "elastic.job.zk", name = {"namespace", "serverLists"}, matchIfMissing = false)
@EnableConfigurationProperties(JobZookeeperProperties.class)
public class JobParserAutoConfiguration {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(JobZookeeperProperties jobZookeeperProperties) {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(jobZookeeperProperties.getServerLists(),
                jobZookeeperProperties.getNamespace());
        zkConfig.setDigest(jobZookeeperProperties.getDigest());
        zkConfig.setMaxRetries(jobZookeeperProperties.getMaxRetries());
        zkConfig.setBaseSleepTimeMilliseconds(jobZookeeperProperties.getBaseSleepTimeMilliseconds());
        zkConfig.setConnectionTimeoutMilliseconds(jobZookeeperProperties.getConnectionTimeoutMilliseconds());
        zkConfig.setMaxSleepTimeMilliseconds(jobZookeeperProperties.getMaxSleepTimeMilliseconds());
        zkConfig.setSessionTimeoutMilliseconds(jobZookeeperProperties.getSessionTimeoutMilliseconds());
        log.info("初始化job注册中心配置成功，zkAddress:{},nameSpace:{}", jobZookeeperProperties.getServerLists(),
                jobZookeeperProperties.getNamespace());
        return new ZookeeperRegistryCenter(zkConfig);
    }
}
