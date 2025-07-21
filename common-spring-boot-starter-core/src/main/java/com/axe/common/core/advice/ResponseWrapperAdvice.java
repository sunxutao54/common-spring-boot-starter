package com.axe.common.core.advice;

import com.axe.common.core.annotation.IgnoreResponseWrapper;
import com.axe.common.core.api.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Description: TODO 统一返回结果
 * @Date: 2025/7/21
 * @Author: Sxt
 * @Version: v1.0
 */
@RestControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return (!returnType.getParameterType().equals(R.class))
                && (!returnType.hasMethodAnnotation(IgnoreResponseWrapper.class));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof String) {
            return R.ok(body).toString();
        }
        return R.ok(body);
    }
}
