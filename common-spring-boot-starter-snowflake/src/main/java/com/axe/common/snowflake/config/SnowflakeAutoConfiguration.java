package com.axe.common.snowflake.config;

import com.axe.common.snowflake.generator.SnowflakeIdGenerator;
import com.axe.common.snowflake.interceptor.SnowflakeIdInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;


/**
 * @Description: TODO 雪花Id拦截器配置
 * @Date: 2025/7/9
 * @Author: Sxt
 * @Version: v1.0
 */
@AutoConfiguration
public class SnowflakeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator();
    }

    @Bean
    public SnowflakeIdInterceptor snowflakeIdInterceptor(SnowflakeIdGenerator idGenerator) {
        return new SnowflakeIdInterceptor(idGenerator);
    }
}
