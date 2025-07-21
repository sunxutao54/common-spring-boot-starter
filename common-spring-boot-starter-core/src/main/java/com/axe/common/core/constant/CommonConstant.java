package com.axe.common.core.constant;

import java.util.regex.Pattern;

/**
 * @Description: TODO 常量类
 * @Date: 2025/7/9
 * @Author: Sxt
 * @Version: v1.0
 */
public interface CommonConstant {

    /** HTTP 请求成功code */
    int HTTP_SUCCESS_CODE = 200;

    /** HTTP 请求失败code */
    int HTTP_ERROR_CODE = 500;

    /** HTTP 错误code */
    int HTTP_REQUEST_ERROR_CODE = 400;

    /** 保存成功信息 */
    String SAVE_SUCCESS = "保存成功";

    /** 保存失败信息 */
    String SAVE_ERROR = "保存失败";

    /** 修改成功信息 */
    String UPDATE_SUCCESS = "修改成功";

    /** 修改失败信息 */
    String UPDATE_ERROR = "修改失败";

    /** 删除成功信息 */
    String DELETE_SUCCESS = "删除成功";

    /** 删除失败信息 */
    String DELETE_ERROR = "删除失败";

    /** 查询失败信息 */
    String SELECT_ERROR = "查询失败";

    /** 查询成功信息 */
    String SELECT_SUCCESS = "查询成功";

    /** 基础成功信息 */
    String BASE_SUCCESS = "操作成功";

    /** 基础失败信息 */
    String BASE_ERROR = "操作失败";

    /** NULL 字符串 */
    String STRING_NULL = "null";

    /** 空字符串 */
    String EMPTY = "";

    /** 空格字符串 */
    String SPACE = " ";

    /** 正则表达式：手机号 */
    Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /** 正则表达式：邮箱 */
    Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    /** 默认日期格式 */
    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /** 默认日期时间格式 */
    String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** JSON 内容类型常量 */
    String CONTENT_TYPE_JSON = "application/json";

    /** 表单内容类型常量 */
    String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    /** 一天的毫秒数 */
    long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;
}
