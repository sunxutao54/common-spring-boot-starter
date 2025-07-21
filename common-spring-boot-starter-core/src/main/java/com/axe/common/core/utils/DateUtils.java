package com.axe.common.core.utils;

import com.axe.common.core.constant.CommonConstant;
import com.axe.common.core.exception.AxeIllegalParameterException;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO 日期时间工具类
 * @Date: 2025/7/10
 * @Author: Sxt
 * @Version: v1.0
 */
public class DateUtils {

    private DateUtils(){}


    private static final long OFFSET = 1000L;


    /**
     * TODO 转成Date对象
     * @param localDateTime 日期时间
     * @return Date对象
     * @author: sxt
     * @date: 2025/07/10 17:05:07
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * TODO 转成Date对象
     * @param localDate 日期时间
     * @return Date对象
     * @author: sxt
     * @date: 2025/07/10 17:05:07
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * TODO 转成Date对象
     * @param timestamp 时间戳
     * @return Date对象
     * @author: sxt
     * @date: 2025/07/10 17:06:54
     */
    public static Date toDate(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * TODO 转成LocalDateTime对象
     * @param date 日期
     * @return LocalDateTime对象
     * @author: sxt
     * @date: 2025/07/16 14:03:55
     */
    public static LocalDateTime toLocalDateTime(Date date){
        if (date == null) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * TODO 转成LocalDateTime对象
     * @param timestamp 时间戳
     * @return LocalDateTime对象
     * @author: sxt
     * @date: 2025/07/16 14:03:55
     */
    public static LocalDateTime toLocalDateTime(long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * TODO 转成LocalDate对象
     * @param date 日期
     * @return LocalDate对象
     * @author: sxt
     * @date: 2025/07/16 14:16:32
     */
    public static LocalDate toLocalDate(Date date){
        if (date == null) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * TODO 转成LocalDate对象
     * @param timestamp 时间戳
     * @return LocalDate对象
     * @author: sxt
     * @date: 2025/07/16 14:16:32
     */
    public static LocalDate toLocalDate(long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * TODO 转成timestamp时间戳
     * @param localDateTime 日期
     * @return LocalDate对象
     * @author: sxt
     * @date: 2025/07/16 14:16:32
     */
    public static long toTimestamp(LocalDateTime localDateTime){
        if (localDateTime == null) {
            return 0L;
        }
        return localDateTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    /**
     * TODO 转成timestamp时间戳
     * @param localDate 日期
     * @return 时间戳
     * @author: sxt
     * @date: 2025/07/16 14:16:32
     */
    public static long toTimestamp(LocalDate localDate){
        if (localDate == null) {
            return 0L;
        }
        return localDate.atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    /**
     * TODO 转成字符串 yyyy-MM-dd HH:mm:ss
     * @param date 日期
     * @return 时间戳
     * @author: sxt
     * @date: 2025/07/16 14:16:32
     */
    public static String toString(Date date){
        if (date == null) {
            throw new AxeIllegalParameterException("日期参数不能为null");
        }
        // 创建日期格式化对象
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DEFAULT_DATETIME_FORMAT);
        // 格式化日期为字符串
        return sdf.format(date);
    }

    /**
     * TODO 转成字符串 yyyy-MM-dd HH:mm:ss
     * @param dateTime 日期时间
     * @return 一串
     * @author: sxt
     * @date: 2025/07/17 10:45:35
     */
    public static String toString(LocalDateTime dateTime){
        if (dateTime == null) {
            throw new AxeIllegalParameterException("日期参数不能为null");
        }
        // 定义格式化模式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.DEFAULT_DATETIME_FORMAT);
        // 格式化日期时间
        return dateTime.format(formatter);
    }

    /**
     * TODO 判断两个日期是否在同一天
     * @param first  日期1
     * @param second 日期2
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 17:14:22
     */
    public static boolean isSameDate(Date first,Date second){
        if (first == null || second == null) {
            return first == second;
        }
        return toLocalDate(first).equals(toLocalDate(second));
    }

    /**
     * TODO 判断给定日期时间是否已过期（即是否早于当前时间）
     * @param targetDate 预定日期
     * @return boolean
     * @author: sxt
     * @date: 2025/07/16 14:23:14
     */
    public static boolean isExpired(LocalDateTime targetDate){
        if (targetDate == null) {
            return false;
        }
        return targetDate.isBefore(LocalDateTime.now());
    }

    /**
     * TODO 判断给定日期时间是否已过期（即是否早于当前时间）
     * @param targetDate 预定日期
     * @return boolean
     * @author: sxt
     * @date: 2025/07/16 14:23:14
     */
    public static boolean isExpired(Date targetDate){
       return isExpired(toLocalDateTime(targetDate));
    }


    /**
     * TODO 在...天之后的日期
     * @param date 日期
     * @param days 天数
     * @return 日期
     * @author: sxt
     * @date: 2025/07/17 10:50:40
     */
    public static Date after(Date date,int days){
        if (date == null) {
            throw new AxeIllegalParameterException("日期参数不能为null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * TODO 计算指定日期若干天前的日期
     * @param date 日期
     * @param days 天数
     * @return 日期
     * @author: sxt
     * @date: 2025/07/17 10:51:06
     */
    public static Date before(Date date,int days){
        if (date == null) {
            throw new AxeIllegalParameterException("日期参数不能为null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        return calendar.getTime();
    }

    /**
     * TODO 获取指定日期的0点时刻Date对象
     * @param date 日期
     * @return 日期
     * @author: sxt
     * @date: 2025/07/17 11:02:05
     */
    public static Date getStartOfDay(Date date){
        if (date == null) {
            throw new AxeIllegalParameterException("日期参数不能为null");
        }
        // 获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        long timestamp = date.getTime();
        // 计算该时间戳在本地时区的偏移量
        ZoneOffset offset = zoneId.getRules().getOffset(Instant.ofEpochMilli(timestamp));
        int offsetSeconds = offset.getTotalSeconds();
        // 将时间戳转换为本地时间的午夜0点
        long localMidnightMillis = timestamp -
                ((timestamp + offsetSeconds * OFFSET) % (CommonConstant.MILLISECONDS_IN_DAY));

        return new Date(localMidnightMillis);
    }

    /**
     * TODO 获取指定日期的最后一刻(23:59:59.999)
     * @param date 日期
     * @return 日期
     * @author: sxt
     * @date: 2025/07/17 11:03:31
     */
    public static Date getEndOfDay(Date date){
        if (date == null) {
            throw new AxeIllegalParameterException("日期参数不能为null");
        }
        ZoneId zoneId = ZoneId.systemDefault();
        long timestamp = date.getTime();
        ZoneOffset offset = zoneId.getRules().getOffset(Instant.ofEpochMilli(timestamp));
        // 计算当天开始时刻
        long startOfDayMillis = timestamp -
                ((timestamp + offset.getTotalSeconds() * OFFSET) % (CommonConstant.MILLISECONDS_IN_DAY));

        // 计算当天结束时刻（开始时刻 + 1天 - 1毫秒）
        return new Date(startOfDayMillis + CommonConstant.MILLISECONDS_IN_DAY - 1);
    }

    /**
     * TODO 区间内全部日期
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 列表<日期>
     * @author: sxt
     * @date: 2025/07/17 11:21:01
     */
    public static List<Date> between(Date startDate, Date endDate){
        List<Date> dates = new ArrayList<>();
        // 获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        // 计算开始日期的本地0点时间戳
        long startMillis = getLocalMidnightMillis(startDate, zoneId);
        // 计算结束日期的本地0点时间戳
        long endMillis = getLocalMidnightMillis(endDate, zoneId);
        // 逐日生成日期
        for (long current = startMillis; current <= endMillis; current += CommonConstant.MILLISECONDS_IN_DAY) {
            dates.add(new Date(current));
        }
        return dates;
    }

    private static long getLocalMidnightMillis(Date date, ZoneId zoneId) {
        long timestamp = date.getTime();
        ZoneOffset offset = zoneId.getRules().getOffset(Instant.ofEpochMilli(timestamp));
        return timestamp - ((timestamp + offset.getTotalSeconds() * OFFSET) % CommonConstant.MILLISECONDS_IN_DAY);
    }
}
