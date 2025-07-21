package com.axe.common.core.exception;

import com.axe.common.core.constant.CommonConstant;

/**
 * @Description: TODO 断言异常
 * @Date: 2025/7/9
 * @Author: Sxt
 * @Version: v1.0
 */
public class AxeAssertException extends AxeException {
    public AxeAssertException(String message) {
        super(CommonConstant.HTTP_REQUEST_ERROR_CODE,message);
    }
}
