package com.axe.common.thread.config;

import com.axe.common.thread.policy.CallerFallbackPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: TODO 线程池配置
 * @Date: 2025/7/9
 * @Author: Sxt
 * @Version: v1.0
 */
@AutoConfiguration
public class ThreadPoolConfig {

    @Value("${axe.thread-pool.core-size:5}")
    private int corePoolSize;

    @Value("${axe.thread-pool.max-size:10}")
    private int maxPoolSize;

    @Value("${axe.thread-pool.queue-capacity:25}")
    private int queueCapacity;

    @Value("${axe.thread-pool.keep-alive-seconds:60}")
    private int keepAliveSeconds;

    @Value("${axe.thread-pool.thread-name-prefix:axe-task-}")
    private String threadNamePrefix;

    @Value("${axe.thread-pool.await-termination-seconds:30}")
    private int awaitTerminationSeconds;

    @Value("${axe.thread-pool.wait-for-tasks:true}")
    private boolean waitForTasksToComplete;

    @Value("${axe.thread-pool.retry-queue-capacity:3}")
    private int retryQueueCapacity;

    @Value("${axe.thread-pool.max-retries:3}")
    private int maxRetries;

    @Bean(name = "axeTaskExecutor")
    public ThreadPoolTaskExecutor axeTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToComplete);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        executor.initialize();

        ThreadPoolExecutor underlyingExecutor = executor.getThreadPoolExecutor();
        CallerFallbackPolicy rejectionPolicy = new CallerFallbackPolicy(
                underlyingExecutor,
                retryQueueCapacity,
                maxRetries
        );
        executor.setRejectedExecutionHandler(rejectionPolicy);
        return executor;
    }
}
