package com.zjh.esjob.config;

import com.zjh.esjob.annotation.JobTraceInterceptor;
import org.springframework.context.annotation.Bean;

//@Configuration
public class TraceJobConfiguration {

	@Bean
	public JobTraceInterceptor jobTraceInterceptor() {
		System.err.println("init --------------->");
		return new JobTraceInterceptor();
	}
	
}
