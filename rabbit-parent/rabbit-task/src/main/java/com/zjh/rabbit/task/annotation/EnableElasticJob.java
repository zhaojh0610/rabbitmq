package com.zjh.rabbit.task.annotation;

import com.zjh.rabbit.task.autoconfigure.JobParserAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableElasticJob  定时任务启动注解
 *
 * @author zhaojh
 * @date 2021/1/27 20:16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(JobParserAutoConfiguration.class)
public @interface EnableElasticJob {

}
