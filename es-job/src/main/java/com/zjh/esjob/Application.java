package com.zjh.esjob;

import com.zjh.rabbit.task.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author zhaojh
 */
@EnableElasticJob
@SpringBootApplication
@ComponentScan(basePackages = {"com.zjh.esjob.*","com.zjh.esjob.service.*", "com.zjh.esjob.annotation.*","com.zjh.esjob.task.*"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
