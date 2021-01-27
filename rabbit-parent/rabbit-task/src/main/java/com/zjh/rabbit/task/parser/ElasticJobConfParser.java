package com.zjh.rabbit.task.parser;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.zjh.rabbit.task.annotation.ElasticJobConfig;
import com.zjh.rabbit.task.autoconfigure.JobZookeeperProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.Iterator;
import java.util.Map;

/**
 * ElasticJobConfParser  定时任务注解解析类
 *
 * @author zhaojh
 * @date 2021/1/27 20:43
 */
public class ElasticJobConfParser implements ApplicationListener<ApplicationReadyEvent> {

    private JobZookeeperProperties jobZookeeperProperties;
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    public ElasticJobConfParser(JobZookeeperProperties jobZookeeperProperties
            , ZookeeperRegistryCenter zookeeperRegistryCenter) {
        this.jobZookeeperProperties = jobZookeeperProperties;
        this.zookeeperRegistryCenter = zookeeperRegistryCenter;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            ApplicationContext context = event.getApplicationContext();
            Map<String, Object> beanMap = context.getBeansWithAnnotation(ElasticJobConfig.class);
            for (Iterator<Object> it = beanMap.values().iterator(); it.hasNext(); ) {
                Object confBean = it.next();
                Class<?> clazz = confBean.getClass();
                if (clazz.getName().indexOf("$") > 0) {
                    String className = clazz.getName();
                    clazz = Class.forName(className.substring(0, className.indexOf("$")));
                }
                // 	获取接口类型 用于判断是什么类型的任务
                String jobTypeName = clazz.getInterfaces()[0].getSimpleName();
                //	获取配置项 ElasticJobConfig
                ElasticJobConfig conf = clazz.getAnnotation(ElasticJobConfig.class);

                String jobClass = clazz.getName();
                String jobName = this.jobZookeeperProperties.getNamespace() + "." + conf.name();
                String cron = conf.cron();
                String shardingItemParameters = conf.shardingItemParameters();
                String description = conf.description();
                String jobParameter = conf.jobParameter();
                String jobExceptionHandler = conf.jobExceptionHandler();
                String executorServiceHandler = conf.executorServiceHandler();

                String jobShardingStrategyClass = conf.jobShardingStrategyClass();
                String eventTraceRdbDataSource = conf.eventTraceRdbDataSource();
                String scriptCommandLine = conf.scriptCommandLine();

                boolean failover = conf.failover();
                boolean misfire = conf.misfire();
                boolean overwrite = conf.overwrite();
                boolean disabled = conf.disabled();
                boolean monitorExecution = conf.monitorExecution();
                boolean streamingProcess = conf.streamingProcess();

                int shardingTotalCount = conf.shardingTotalCount();
                int monitorPort = conf.monitorPort();
                int maxTimeDiffSeconds = conf.maxTimeDiffSeconds();
                int reconcileIntervalMinutes = conf.reconcileIntervalMinutes();


            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
