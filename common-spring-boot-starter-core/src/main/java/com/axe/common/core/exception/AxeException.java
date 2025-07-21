package com.axe.common.core.exception;

import com.axe.common.core.constant.CommonConstant;

/**
 * @Description: TODO 异常基类
 * @Date: 2025/7/9
 * @Author: Sxt
 * @Version: v1.0
 */
public class AxeException extends RuntimeException{

    private int code = CommonConstant.HTTP_ERROR_CODE;

    public AxeException(int code, String message){
        super(message);
        this.code = code;
    }

    public AxeException(String message){
        super(message);
    }

    public AxeException(Throwable cause) {
        super(cause);
    }

    public AxeException(String message, Throwable cause) {
        super(message,cause);
    }

    public int getCode() {
        return code;
    }
}
