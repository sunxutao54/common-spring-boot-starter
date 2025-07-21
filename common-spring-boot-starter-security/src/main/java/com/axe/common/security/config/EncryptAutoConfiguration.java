package com.axe.common.security.config;

import com.axe.common.security.interceptor.EncryptInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;


/**
 * @Description: TODO 加密拦截器配置
 * @Date: 2025/7/9
 * @Author: Sxt
 * @Version: v1.0
 */
@AutoConfiguration
public class EncryptAutoConfiguration {

    @Bean
    public EncryptInterceptor encryptInterceptor() {
        return new EncryptInterceptor();
    }
}
