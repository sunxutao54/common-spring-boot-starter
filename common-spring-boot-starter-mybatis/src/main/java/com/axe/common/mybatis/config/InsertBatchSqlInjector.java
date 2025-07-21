package com.axe.common.mybatis.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * @Description: TODO 自定义SQL注入器
 * @Date: 2025/7/11
 * @Author: Sxt
 * @Version: v1.0
 */
public class InsertBatchSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass,tableInfo);
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }
}
