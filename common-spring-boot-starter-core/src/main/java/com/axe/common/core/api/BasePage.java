package com.axe.common.core.api;

/**
 * @Description: TODO 通用分页参数类
 * @Date: 2025/7/9
 * @Author: Sxt
 * @Version: v1.0
 */
public class BasePage {

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 20;


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
}
