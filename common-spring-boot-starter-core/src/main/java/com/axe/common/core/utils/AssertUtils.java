package com.axe.common.core.utils;

import com.axe.common.core.exception.AxeAssertException;

import java.util.Collection;

/**
 * @Description: TODO 断言工具类
 * @Date: 2025/7/10
 * @Author: Sxt
 * @Version: v1.0
 */
public class AssertUtils {

    private AssertUtils(){}

    /**
     * TODO 断言不为空
     * @param obj     对象
     * @param message 消息
     * @author: sxt
     * @date: 2025/07/11 09:21:48
     */
    public static <T> void assertNotNull(T obj, String message){
        if (obj == null){
            throw new AxeAssertException(message);
        }
    }

    /**
     * TODO 断言不为空
     * @param collection 集合
     * @param message    消息
     * @author: sxt
     * @date: 2025/07/11 09:21:52
     */
    public static void assertNotEmpty(Collection<?> collection, String message){
        if (CollectionUtils.isEmpty(collection)){
            throw new AxeAssertException(message);
        }
    }

    /**
     * TODO 断言不为空
     * @param str     字符串
     * @param message 消息
     * @author: sxt
     * @date: 2025/07/11 09:21:53
     */
    public static void assertNotEmpty(String str, String message){
        if (StringUtils.isEmpty(str)){
            throw new AxeAssertException(message);
        }
    }
}
