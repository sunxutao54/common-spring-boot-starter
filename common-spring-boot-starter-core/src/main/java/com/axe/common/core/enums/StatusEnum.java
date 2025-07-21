package com.axe.common.core.enums;

/**
 * @Description: TODO
 * @Date: 2025/7/17
 * @Author: Sxt
 * @Version: v1.0
 */
public enum StatusEnum {

    /**
     * 未启用状态
     */
    DISABLED(0, "未启用"),

    /**
     * 启用状态
     */
    ENABLED(1, "启用");

    private final int code;

    private final String desc;

    StatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取状态描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 根据状态码获取枚举
     *
     * @param code 状态码
     * @return 对应的枚举值，如果找不到则返回null
     */
    public static StatusEnum fromCode(int code) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
