package com.axe.common.snowflake.api.entity;

import com.axe.common.snowflake.annotation.SnowflakeId;

import java.util.Date;

/**
 * @Description: TODO 实体基类
 * @Date: 2025/7/8
 * @Author: Sxt
 * @Version: v1.1
 */
public class BaseEntity {

    /**
     * 主键
     */
    @SnowflakeId
    private Long id;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改人
     */
    private Long lastUpdatedBy;

    /**
     * 最后修改时间
     */
    private Date lastUpdatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createdBy=" + createdBy +
                ", createDate=" + createDate +
                ", lastUpdatedBy=" + lastUpdatedBy +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}
