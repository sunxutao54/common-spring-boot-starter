package com.axe.common.mybatis.service.impl;

import com.axe.common.core.utils.CollectionUtils;
import com.axe.common.mybatis.mapper.AxeBaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @Description: TODO
 * @Date: 2025/7/18
 * @Author: Sxt
 * @Version: v1.0
 */
public class AxeServiceImpl<M extends AxeBaseMapper<T>, T> extends ServiceImpl<M,T> implements IService<T> {

    @Resource
    private M axeBaseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> entityList) {
        return this.axeBaseMapper.insertBatchSomeColumn(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        int size = entityList.size();
        if (size > batchSize){
            entityList = CollectionUtils.cut(entityList, 0, batchSize);
        }
        return this.axeBaseMapper.insertBatchSomeColumn(entityList);
    }
}
