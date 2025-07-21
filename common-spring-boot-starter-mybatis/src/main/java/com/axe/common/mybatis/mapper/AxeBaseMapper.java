package com.axe.common.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * @Description: TODO
 * @Date: 2025/7/11
 * @Author: Sxt
 * @Version: v1.0
 */
public interface AxeBaseMapper<T> extends BaseMapper<T> {


    /**
     * TODO 插入批处理某些列
     * @param entityList 实体名单
     * @return boolean
     * @author: sxt
     * @date: 2025/07/11 13:41:33
     */
    boolean insertBatchSomeColumn(Collection<T> entityList);
}
