package com.axe.common.sensitive.enums;

/**
 * @Description: TODO
 * @Date: 2025/7/17
 * @Author: Sxt
 * @Version: v1.0
 */
public enum SensitiveType {

    /** 中文名 */
    CHINESE_NAME,

    /** 身份证号 */
    ID_CARD,

    /** 手机号 */
    PHONE,

    /** 邮箱 */
    EMAIL,

    /** 银行卡 */
    BANK_CARD,

    // 自定义（配合prefixLen/suffixLen）
    CUSTOM
}
