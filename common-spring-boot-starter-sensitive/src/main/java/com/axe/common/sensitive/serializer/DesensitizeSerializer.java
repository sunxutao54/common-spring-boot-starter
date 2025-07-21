package com.axe.common.sensitive.serializer;

import com.axe.common.core.constant.CommonConstant;
import com.axe.common.core.utils.StringUtils;
import com.axe.common.sensitive.annotation.Sensitive;
import com.axe.common.sensitive.enums.SensitiveType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;


/**
 * @Description: TODO
 * @Date: 2025/7/17
 * @Author: Sxt
 * @Version: v1.0
 */
public class DesensitizeSerializer extends StdSerializer<String> implements ContextualSerializer {

    private SensitiveType type;
    private int prefixLen;
    private int suffixLen;
    private char maskChar;

    public DesensitizeSerializer() {
        super(String.class);
    }

    public DesensitizeSerializer(SensitiveType type, int prefixLen, int suffixLen, char maskChar) {
        super(String.class);
        this.type = type;
        this.prefixLen = prefixLen;
        this.suffixLen = suffixLen;
        this.maskChar = maskChar;
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Sensitive ann = property.getAnnotation(Sensitive.class);
        if (ann != null) {
            return new DesensitizeSerializer(
                    ann.type(),
                    ann.prefixLen(),
                    ann.suffixLen(),
                    ann.maskChar()
            );
        }
        return this;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(process(value));
    }

    private String process(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        switch (type) {
            case PHONE:
                return customMask(value, 3, 4);
            case ID_CARD:
            case BANK_CARD:
                return customMask(value, 6, 4);
            case CHINESE_NAME:
                return customMask(value, 1, 0);
            case EMAIL:
                return maskEmail(value);
            case CUSTOM:
            default:
                return customMask(value, prefixLen, suffixLen);
        }
    }

    /**
     * 通用脱敏模板
     * @param value   原始字符串
     * @param keepPre 保留前缀长度
     * @param keepSuf 保留后缀长度
     */
    private String customMask(String value, int keepPre, int keepSuf) {
        int length = value.length();
        // 边界检查：避免非法索引
        keepPre = Math.max(0, Math.min(keepPre, length));
        keepSuf = Math.max(0, Math.min(keepSuf, length - keepPre));

        String prefix = value.substring(0, keepPre);
        String suffix = keepSuf > 0 ? value.substring(length - keepSuf) : "";
        int maskLength = length - keepPre - keepSuf;

        // 全掩码场景（如姓名长度=1）
        if (maskLength <= 0) {
            return StringUtils.repeat(maskChar, length);
        }
        return prefix + StringUtils.repeat(maskChar, maskLength) + suffix;
    }

    /**
     * 邮箱脱敏特殊处理
     */
    private String maskEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return CommonConstant.EMPTY;
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 0) {
            return email;
        }
        String prefix = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        // 邮箱前缀脱敏：长度<3时全掩码
        int maskLen = prefix.length() < 3 ? prefix.length() : prefix.length() - 1;
        return (prefix.length() < 3 ? "" : prefix.charAt(0))
                + StringUtils.repeat(maskChar, maskLen)
                + domain;
    }
}