package com.axe.common.mybatis.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;

/**
 * @Description: TODO
 * @Date: 2025/7/22
 * @Author: Sxt
 * @Version: v1.0
 */
public class PageEntity<T> extends PageDTO<T> {

    public void setEntityClass(Class<T> entityClass) {

    }

    public PageEntity(BasePage  page) {
        super(page.getPageNum(), page.getPageSize(), 0);
    }

    public PageEntity(BasePage  page, long total) {
        super(page.getPageNum(), page.getPageSize(), total);
    }
}
