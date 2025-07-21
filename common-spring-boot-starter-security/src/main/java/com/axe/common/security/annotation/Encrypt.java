package com.axe.common.security.annotation;

import com.axe.common.security.enums.EncryptType;

import java.lang.annotation.*;

/**
 * @Description: TODO 加密注解
 * @Date: 2025/7/21
 * @Author: Sxt
 * @Version: v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {
    EncryptType type();
}
