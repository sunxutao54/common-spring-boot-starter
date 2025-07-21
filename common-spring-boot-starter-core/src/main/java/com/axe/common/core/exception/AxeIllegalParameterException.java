package com.axe.common.core.exception;

import com.axe.common.core.constant.CommonConstant;

/**
 * @Description: TODO 非法参数异常
 * @Date: 2025/7/9
 * @Author: Sxt
 * @Version: v1.0
 */
public class AxeIllegalParameterException extends AxeException {
    public AxeIllegalParameterException(String message) {
        super(CommonConstant.HTTP_REQUEST_ERROR_CODE,message);
    }
}
