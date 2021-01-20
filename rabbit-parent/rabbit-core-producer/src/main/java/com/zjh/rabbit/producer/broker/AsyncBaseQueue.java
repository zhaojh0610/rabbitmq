package com.zjh.rabbit.producer.broker;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author zhaojh
 * @date 2021/1/20 21:12
 * @desc AsyncBaseQueue 异步线程池
 */
@Slf4j
public class AsyncBaseQueue {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int QUEUE_SIZE = 10000;

    public static ExecutorService senderAsync = new ThreadPoolExecutor(CORE_POOL_SIZE,
            CORE_POOL_SIZE,
            60L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_SIZE),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("rabbit_client_async_sender");
                    return thread;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    log.error("async sender is error rejected, runnable: {}, executor: {}", r, executor);
                }
            });

    public static void submit(Runnable runnable) {
        senderAsync.submit(runnable);
    }
}
