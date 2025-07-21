package com.axe.common.snowflake.interceptor;

import com.axe.common.snowflake.annotation.SnowflakeId;
import com.axe.common.snowflake.generator.SnowflakeIdGenerator;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;

/**
 * @Description: TODO 自定义拦截器，自动插入ID
 * @Date: 2025/7/9
 * @Author: Sxt
 * @Version: v1.1
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class SnowflakeIdInterceptor implements Interceptor {

    private final SnowflakeIdGenerator idGenerator;

    public SnowflakeIdInterceptor(SnowflakeIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        if (SqlCommandType.INSERT != ms.getSqlCommandType() || parameter == null) {
            return invocation.proceed();
        }
        Class<?> parameterClass = parameter.getClass();
        for (Field field : parameterClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(SnowflakeId.class)) {
                field.setAccessible(true);
                if (field.get(parameter) == null) {
                    field.set(parameter, idGenerator.nextId());
                }
            }
        }
        return invocation.proceed();
    }
}
