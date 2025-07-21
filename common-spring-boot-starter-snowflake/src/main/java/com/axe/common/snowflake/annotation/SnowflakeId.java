package com.axe.common.snowflake.annotation;

import java.lang.annotation.*;

/**
 * @Description: TODO 雪花ID
 * @Date: 2025/7/8
 * @Author: Sxt
 * @Version: v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SnowflakeId {

}
