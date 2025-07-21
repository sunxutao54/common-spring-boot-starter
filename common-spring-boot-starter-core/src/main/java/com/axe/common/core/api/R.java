
package com.axe.common.core.api;

import com.axe.common.core.constant.CommonConstant;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description: Restful API 接口规范返回类
 * @Date: 2025/5/21
 * @Author: sxt
 * @Version: v1.0
 */
@Getter
@ToString
public class R<T> {

    /**
     * HTTP请求响应码
     */
    private final int code;

    /**
     * 响应信息
     */
    private final String message;

    /**
     * 响应数据
     */
    private final T data;


    private R(int code,String message,T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> R<T> ok(){
        return new R<>(CommonConstant.HTTP_SUCCESS_CODE,CommonConstant.BASE_SUCCESS,null);
    }

    public static <T> R<T> ok(T data){
        return new R<>(CommonConstant.HTTP_SUCCESS_CODE,CommonConstant.BASE_SUCCESS,data);
    }

    public static <T> R<T> ok(T data,String message){
        return new R<>(CommonConstant.HTTP_SUCCESS_CODE,message,data);
    }

    public static <T> R<T> fail(){
        return new R<>(CommonConstant.HTTP_ERROR_CODE,CommonConstant.BASE_ERROR,null);
    }

    public static <T> R<T> fail(String message){
        return new R<>(CommonConstant.HTTP_ERROR_CODE,message,null);
    }

    public static <T> R<T> fail(int code,String message){
        return new R<>(code,message,null);
    }
}
