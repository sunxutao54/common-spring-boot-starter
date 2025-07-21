package com.axe.common.thread.policy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Description: TODO 重试提交失败后退回调用者执行
 * @Date: 2025/7/21
 * @Author: Sxt
 * @Version: v1.0
 */
@Slf4j
public class CallerFallbackPolicy implements RejectedExecutionHandler, DisposableBean {

    private final BlockingQueue<Runnable> retryQueue;
    private final Thread retryThread;
    private final AtomicBoolean isRunning = new AtomicBoolean(true);
    private final ThreadPoolExecutor executor;
    private final int maxRetries;
    private final AtomicInteger retryCounter = new AtomicInteger(0);
    private final AtomicInteger fallbackCounter = new AtomicInteger(0);

    public CallerFallbackPolicy(ThreadPoolExecutor executor, int queueCapacity, int maxRetries) {
        this.executor = executor;
        this.retryQueue = new ArrayBlockingQueue<>(queueCapacity);
        this.maxRetries = maxRetries;
        this.retryThread = new Thread(this::retryLoop, "Axe-RetryPolicy-Thread");
        this.retryThread.setDaemon(true);
        this.retryThread.start();
    }

    @Override
    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
        if (executor.isShutdown()) {
            throw new RejectedExecutionException("线程池已关闭。");
        }
        // 包装任务，记录当前调用者线程
        Runnable retryTask = () -> {
            try {
                task.run();
            } catch (Exception e) {
                // 处理异常
                log.error("任务执行失败",e);
            }
        };
        if (!retryQueue.offer(retryTask)) {
            // 队列满时立即由调用者执行
            executeImmediately(retryTask);
        }
    }

    private void retryLoop() {
        while (isRunning.get() || !retryQueue.isEmpty()) {
            try {
                Runnable task = retryQueue.poll(100, TimeUnit.MILLISECONDS);
                if (task == null) {
                    continue;
                }

                int attempt = 0;
                boolean succeeded = false;

                while (attempt < maxRetries && isRunning.get()) {
                    try {
                        executor.execute(task);
                        retryCounter.incrementAndGet();
                        succeeded = true;
                        break;
                    } catch (RejectedExecutionException ex) {
                        attempt++;
                        TimeUnit.MILLISECONDS.sleep(calculateBackoff(attempt));
                    }
                }

                if (!succeeded) {
                    // 重试多次失败，由当前线程执行
                    executeImmediately(task);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.error("重试循环错误",e);
            }
        }
    }

    private void executeImmediately(Runnable task) {
        try {
            fallbackCounter.incrementAndGet();
            task.run();
        } catch (Exception e) {
            log.error("立即执行失败",e);
        }
    }

    private long calculateBackoff(int attempt) {
        return (long) Math.min(500 * Math.pow(2, attempt), 10000);
    }

    @Override
    public void destroy() {
        shutdown();
    }

    public void shutdown() {
        if (isRunning.compareAndSet(true, false)) {
            retryThread.interrupt();
            // 优雅处理剩余任务
            while (!retryQueue.isEmpty()) {
                Runnable task = retryQueue.poll();
                if (task != null) {
                    executeImmediately(task);
                }
            }
        }
    }

    // 监控指标
    public int getPendingRetryCount() {
        return retryQueue.size();
    }

    public int getRetriedCount() {
        return retryCounter.get();
    }

    public int getFallbackCount() {
        return fallbackCounter.get();
    }
}
