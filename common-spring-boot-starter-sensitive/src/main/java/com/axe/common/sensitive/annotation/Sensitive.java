package com.axe.common.sensitive.annotation;

import com.axe.common.sensitive.enums.SensitiveType;
import com.axe.common.sensitive.serializer.DesensitizeSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 脱敏注解
 * @Date: 2024/7/11 15:40
 * @Author: Sxt
 * @Version: v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizeSerializer.class)
public @interface Sensitive {

    /**
     * 脱敏字段类型
     */
    SensitiveType type() default SensitiveType.CUSTOM;

    /**
     * 自定义规则：左侧保留位数（可选）
     */
    int prefixLen() default 0;

    /**
     * 自定义规则：右侧保留位数（可选）
     */
    int suffixLen() default 0;

    /**
     * 替换字符（默认*）
     */
    char maskChar() default '*';
}
