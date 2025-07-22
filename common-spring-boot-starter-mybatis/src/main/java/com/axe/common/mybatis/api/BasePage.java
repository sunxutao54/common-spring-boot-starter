package com.axe.common.mybatis.api;

/**
 * @Description: TODO 分页模型
 * @Date: 2025/7/22
 * @Author: Sxt
 * @Version: v1.0
 */
public class BasePage {

    private Integer pageNum = 1;

    private Integer pageSize = 20;

    public BasePage() {

    }

    public BasePage(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public <T> PageEntity<T> ofPage(Class<T> clazz) {
        PageEntity<T> pageEntity = new PageEntity<>(this);
        pageEntity.setEntityClass(clazz);
        return pageEntity;
    }
 }
