package com.axe.common.snowflake.generator;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: TODO 雪花算法ID生成器
 * @Date: 2025/7/8
 * @Author: Sxt
 * @Version: v1.0
 */
@Component
public class SnowflakeIdGenerator {
    // 配置常量
    private static final String CONFIG_FILE = "snowflake.properties";
    private static final long EPOCH = 1672531200000L;
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;
    // 计算最大值和位移
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    @Value("${axe.snowflake.worker-id:1}")
    @Getter
    private long workerId;

    @Value("${axe.snowflake.datacenter-id:1}")
    @Getter
    private long datacenterId;

    private final AtomicLong lastTimestamp = new AtomicLong(-1);
    private final AtomicLong sequence = new AtomicLong(0);

    // 时钟状态管理
    private final AtomicReference<ClockState> clockState = new AtomicReference<>(new ClockState(0, 0));

    /**
     * 内部类：封装时钟状态
     */
    private static class ClockState {
        final long offset;     // 时钟偏移量
        final long version;    // 版本号（用于CAS循环）

        ClockState(long offset, long version) {
            this.offset = offset;
            this.version = version;
        }
    }

//    public SnowflakeIdGenerator() {
//        Properties prop = new Properties();
//        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
//            if (input == null) {
//                this.workerId = 1L;
//                this.datacenterId = 1L;
//            }else {
//                prop.load(input);
//                this.workerId = Long.parseLong(prop.getProperty("worker.id"));
//                this.datacenterId = Long.parseLong(prop.getProperty("datacenter.id"));
//            }
//            // 验证ID范围
//            if (workerId > MAX_WORKER_ID || workerId < 0) {
//                throw new IllegalArgumentException("workerId 必须是 0 ~" + MAX_WORKER_ID);
//            }
//            if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
//                throw new IllegalArgumentException("datacenterId 必须是 0 ~" + MAX_DATACENTER_ID);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("无法加载雪花算法配置", e);
//        }
//    }

    /**
     * 生成下一个ID
     */
    public long nextId() {
        while (true) {
            ClockState currentState = clockState.get();
            long baseTimestamp = System.currentTimeMillis();
            long adjustedTimestamp = baseTimestamp + currentState.offset;
            long lastTs = lastTimestamp.get();
            // 处理时钟回拨
            if (adjustedTimestamp < lastTs) {
                if (!handleClockBackward(baseTimestamp, lastTs, currentState)) {
                    continue; // 重试
                }
            }
            // 同一毫秒内的序列号处理
            if (lastTs == adjustedTimestamp) {
                long seq = sequence.incrementAndGet() & SEQUENCE_MASK;
                if (seq != 0) {
                    return combineParts(adjustedTimestamp, seq);
                }
                // 序列号溢出，等待下一毫秒
                adjustedTimestamp = waitNextMillis(lastTs);
            }
            // 尝试更新时间戳
            if (lastTimestamp.compareAndSet(lastTs, adjustedTimestamp)) {
                sequence.set(0);
                return combineParts(adjustedTimestamp, 0);
            }
        }
    }

    /**
     * 处理时钟回拨
     * @return true 如果时钟状态已更新可以继续，false 需要重试
     */
    private boolean handleClockBackward(long currentTime, long lastTimestamp, ClockState currentState) {
        long backwardOffset = lastTimestamp - currentTime;

        // 仅容忍5ms内的回拨
        if (backwardOffset > 5) {
            throw new RuntimeException("重大的时间倒流事件：" + backwardOffset + "ms");
        }

        // 原子更新时钟偏移量
        long newOffset = currentState.offset + backwardOffset;
        ClockState newState = new ClockState(newOffset, currentState.version + 1);

        return clockState.compareAndSet(currentState, newState);
    }

    /**
     * 组合ID各部分
     */
    private long combineParts(long timestamp, long sequence) {
        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 优化的等待策略
     */
    private long waitNextMillis(long lastTimestamp) {
        long startTime = System.nanoTime();
        long currentTimestamp;

        while (true) {
            currentTimestamp = System.currentTimeMillis() + clockState.get().offset;
            // 已超过上一毫秒，可以返回
            if (currentTimestamp > lastTimestamp) {
                return currentTimestamp;
            }
            // 根据等待时间选择不同策略
            long elapsedNanos = System.nanoTime() - startTime;
            if (elapsedNanos < 100_000) {
                // 极短时间：轻量级自旋
                spinWait();
            } else if (elapsedNanos < 1_000_000) {
                // 中等时间：线程让步
                Thread.yield();
            } else {
                // 长时间：休眠1ms
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 轻量级自旋等待
     */
    private void spinWait() {
        for (int i = 0; i < 50; i++) {
            synchronized (this) {}
        }
    }


    public Map<String, Long> parseId(long id) {
        Map<String, Long> result = new HashMap<>();
        result.put("timestamp", (id >> TIMESTAMP_SHIFT) + EPOCH);
        result.put("datacenterId", (id >> DATACENTER_ID_SHIFT) & MAX_DATACENTER_ID);
        result.put("workerId", (id >> WORKER_ID_SHIFT) & MAX_WORKER_ID);
        result.put("sequence", id & SEQUENCE_MASK);
        return result;
    }
}
