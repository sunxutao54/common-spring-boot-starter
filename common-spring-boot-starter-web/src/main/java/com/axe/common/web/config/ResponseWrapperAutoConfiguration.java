package com.axe.common.web.config;

import com.axe.common.web.advice.GlobalExceptionHandler;
import com.axe.common.web.advice.ResponseWrapperAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO
 * @Date: 2025/7/21
 * @Author: Sxt
 * @Version: v1.0
 */
@Configuration
@ConditionalOnWebApplication
public class ResponseWrapperAutoConfiguration {

    /**
     * 注册全局返回值封装逻辑
     */
    @Bean
    public ResponseWrapperAdvice responseWrapperAdvice() {
        return new ResponseWrapperAdvice();
    }

    /**
     * 注册全局异常处理器
     */
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
