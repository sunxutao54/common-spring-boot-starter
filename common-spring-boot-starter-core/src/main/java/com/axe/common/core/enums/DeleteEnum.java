package com.axe.common.core.enums;

/**
 * @Description: TODO 逻辑删除
 * @Date: 2025/7/17
 * @Author: Sxt
 * @Version: v1.0
 */
public enum DeleteEnum {

    /**
     * 正常状态（未删除）
     */
    NORMAL(0, "正常"),

    /**
     * 已删除状态
     */
    DELETED(1, "已删除");

    private final int code;
    private final String desc;

    DeleteEnum(int code, String desc) {
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
    public static DeleteEnum fromCode(int code) {
        for (DeleteEnum status : DeleteEnum.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
