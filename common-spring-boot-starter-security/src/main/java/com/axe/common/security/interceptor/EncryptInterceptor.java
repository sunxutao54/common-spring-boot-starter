package com.axe.common.security.interceptor;

import com.axe.common.core.utils.StringUtils;
import com.axe.common.security.encrypt.annotation.Encrypt;
import com.axe.common.security.encrypt.enums.EncryptType;
import com.axe.common.security.encrypt.utils.Argon2Utils;
import com.axe.common.security.encrypt.utils.BCryptUtils;
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
public class EncryptInterceptor implements Interceptor {

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
            if (field.isAnnotationPresent(Encrypt.class)) {
                field.setAccessible(true);
                EncryptType type = field.getAnnotation(Encrypt.class).type();
                String value = field.get(parameter).toString();
                if (StringUtils.isNotEmpty(value)){
                    switch (type){
                        case BCRYPT:
                            field.set(parameter, BCryptUtils.encode(value));
                            break;
                        case ARGON2_ID:
                            field.set(parameter, Argon2Utils.hashPassword(value.toCharArray()));
                            break;
                        default:
                    }
                }
            }
        }
        return invocation.proceed();
    }
}
