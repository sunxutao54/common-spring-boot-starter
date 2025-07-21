package com.axe.common.core.api;

import java.util.List;

/**
 * @Description: TODO
 * @Date: 2025/7/18
 * @Author: Sxt
 * @Version: v1.0
 */
public class IdParams {

    private Long id;

    private String idStr;

    private String ids;

    private List<Long> idList;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }
}
