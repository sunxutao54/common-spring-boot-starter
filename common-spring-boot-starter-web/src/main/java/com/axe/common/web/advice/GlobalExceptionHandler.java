package com.axe.common.web.advice;

import com.axe.common.core.api.R;
import com.axe.common.core.constant.CommonConstant;
import com.axe.common.core.exception.AxeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: TODO
 * @Date: 2025/7/21
 * @Author: Sxt
 * @Version: v1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AxeException.class)
    public R<String> handleBusinessException(AxeException ex) {
        log.error("处理自定义业务异常", ex);
        return R.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("处理参数校验异常", ex);
        return R.fail(CommonConstant.HTTP_REQUEST_ERROR_CODE, message);
    }

    @ExceptionHandler(BindException.class)
    public R<String> handleBindException(BindException ex) {
        String message = ex.getAllErrors().get(0).getDefaultMessage();
        log.error("处理BindException", ex);
        return R.fail(CommonConstant.HTTP_REQUEST_ERROR_CODE, message);
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleAllExceptions(Exception ex) {
        log.error("全局异常处理", ex);
        return R.fail();
    }
}
