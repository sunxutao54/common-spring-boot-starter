package com.axe.common.core.utils;

import com.axe.common.core.constant.CommonConstant;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO 字符串工具类
 * @Date: 2025/7/10
 * @Author: Sxt
 * @Version: v1.0
 */
public class StringUtils {

    private StringUtils(){}

    /**
     * TODO 判断字符序列是否为空
     * @param charSequence 字符序列
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 16:53:23
     */
    public static boolean isEmpty(final CharSequence charSequence){
        return charSequence == null || charSequence.length() == 0;
    }

    /**
     * TODO 判断字符序列是否不为空
     * @param charSequence 字符序列
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 16:56:27
     */
    public static boolean isNotEmpty(final CharSequence charSequence){
        return !isEmpty(charSequence);
    }

    /**
     * TODO 判断字符串是否不为空
     * @param str 字符串
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 16:56:36
     */
    public static boolean isNotEmpty(String... str) {
        if (str == null) {
            return false;
        }
        for (String s : str) {
            if (s == null || s.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    /**
     * TODO 判断字符串是否为空白
     * @param charSequence 字符序列
     * @return boolean
     * @author: sxt
     * @date: 2025/07/17 15:00:46
     */
    public static boolean isBlank(final CharSequence charSequence){
        if (charSequence == null || charSequence.length() == 0) {
            return true;
        }
        int strLen = charSequence.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * TODO 判断字符串是否不为空白
     * @param charSequence 字符序列
     * @return boolean
     * @author: sxt
     * @date: 2025/07/17 15:00:46
     */
    public static boolean isNotBlank(final CharSequence charSequence){
        return !isBlank(charSequence);
    }


    /**
     * TODO 判断字符串是否为手机号
     * @param str 待检查的字符串
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 16:59:50
     */
    public static boolean isPhone(final String str) {
        return isNotEmpty(str) && CommonConstant.PHONE_PATTERN.matcher(str).matches();
    }


    /**
     * TODO 判断字符串是否为邮箱
     * @param str 待检查的字符串
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 17:00:24
     */
    public static boolean isEmail(final String str) {
        return isNotEmpty(str) && CommonConstant.EMAIL_PATTERN.matcher(str).matches();
    }

    /**
     * TODO 格式化身份证
     * @param idCard 身份证
     * @return 身份证
     * @author: sxt
     * @date: 2025/07/11 17:22:53
     */
    public static String formatIdCard(String idCard){
        if (isEmpty(idCard)){
            return CommonConstant.EMPTY;
        }
        int length = idCard.length();
        if (length > 0 && idCard.charAt(length - 1) == 'x') {
            return idCard.substring(0, length - 1) + 'X';
        }
        return idCard;
    }

    /**
     * TODO 拼接
     * @param collection 集合
     * @param symbol     连接符
     * @return 一串
     * @author: sxt
     * @date: 2025/07/16 14:59:19
     */
    public static String joint(Collection<?> collection, String symbol){
        StringBuilder sb = new StringBuilder();
        collection.forEach(c -> sb.append(c).append(symbol));
        return sb.substring(0,sb.length() - 1);
    }


    /**
     * TODO 切割
     * @param param  参数
     * @param symbol 切割符
     * @param clazz  类模板
     * @return 列表<t>
     * @author: sxt
     * @date: 2025/07/16 15:27:52
     */
    public static <T> List<T> split(String param, String symbol, Class<T> clazz) {
        if (isEmpty(param)){
            return new ArrayList<>();
        }
        String[] parts = param.split(symbol);
        try {
            // 处理String类型的特殊情况
            if (clazz == String.class) {
                return Arrays.stream(parts)
                        .map(s -> (T) s)
                        .collect(Collectors.toList());
            }
            // 处理基本类型的包装类
            if (clazz == Integer.class) {
                return Arrays.stream(parts)
                        .map(Integer::valueOf)
                        .map(clazz::cast)
                        .collect(Collectors.toList());
            } else if (clazz == Long.class) {
                return Arrays.stream(parts)
                        .map(Long::valueOf)
                        .map(clazz::cast)
                        .collect(Collectors.toList());
            } else if (clazz == Double.class) {
                return Arrays.stream(parts)
                        .map(Double::valueOf)
                        .map(clazz::cast)
                        .collect(Collectors.toList());
            } else if (clazz == Boolean.class) {
                return Arrays.stream(parts)
                        .map(Boolean::valueOf)
                        .map(clazz::cast)
                        .collect(Collectors.toList());
            }
            // 处理其他类型（需要有String参数的构造函数）
            Constructor<T> constructor = clazz.getConstructor(String.class);
            return Arrays.stream(parts)
                    .map(part -> {
                        try {
                            return constructor.newInstance(part);
                        } catch (InstantiationException | IllegalAccessException |
                                 InvocationTargetException e) {
                            throw new IllegalArgumentException("无法实例化类型: " + clazz.getName(), e);
                        }
                    })
                    .collect(Collectors.toList());

        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("类型 " + clazz.getName() + " 没有String参数的构造函数", e);
        }
    }

    /**
     * 重复字符生成字符串
     * @param c 要重复的字符
     * @param repeatTimes 重复次数
     * @return 生成的字符串
     */
    public static String repeat(char c, int repeatTimes) {
        if (repeatTimes <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(repeatTimes);
        for (int i = 0; i < repeatTimes; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 替换字符串中的部分内容为指定字符
     * @param str 原始字符串
     * @param start 开始位置
     * @param end 结束位置
     * @param replaceChar 替换字符
     * @return 替换后的字符串
     */
    public static String replace(String str, int start, int end, char replaceChar) {
        if (isEmpty(str)) {
            return str;
        }
        if (start < 0) {
            start = 0;
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start > end) {
            return str;
        }
        String prefix = substring(str, 0, start);
        String middle = repeat(replaceChar, end - start);
        String suffix = substring(str, end, str.length());

        return prefix + middle + suffix;
    }

    /**
     * 截取字符串
     * @param str 原始字符串
     * @param start 开始位置
     * @param end 结束位置
     * @return 截取后的字符串
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }
        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start > end) {
            return CommonConstant.EMPTY;
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        return str.substring(start, end);
    }
}
