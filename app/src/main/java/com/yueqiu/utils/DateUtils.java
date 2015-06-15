/**
 * Copyright (c) 2010-2014 meituan.com
 * All rights reserved.
 */
package com.yueqiu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具类
 *
 * @author lidehua
 * @version 1.0
 * @created 2014-8-8
 */
public class DateUtils {

    public static final String F_DATE = "yyyy-MM-dd";

    public static final String F_TIME = "HH:mm:ss";

    public static final String F_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String F_YEAR = "yyyy";

    public static final String F_MONTH = "MM";

    public static final String F_DAY = "dd";

    public static final String F_HOUR_MIN = "HH:mm";

    public static final String F_DATE_CHN = "yyyy年MM月dd日";

    public static final String F_PART_TIME = "yyyy-MM-dd E HH:mm";

    private static final Calendar TODAY = Calendar.getInstance();

    /**
     * 处理php后端返回的日期
     *
     * @param timestamp 时间的秒数
     * @param format
     * @return
     */
    public static String getDateStr(String timestamp, String format) {
        try {
            long unixTime = Long.parseLong(timestamp) * 1000;
            SimpleDateFormat sd = new SimpleDateFormat(format, Locale.CHINA);
            String strDate = sd.format(new Date(unixTime));
            return strDate;
        } catch (Exception e) {
            return timestamp;
        }
    }

    /**
     * 处理后端传过来的日期
     *
     * @param timesamp 时间的秒数
     * @return
     */
    public static Date parseTimestamp(String timesamp) {
        try {
            long s = Long.parseLong(timesamp) * 1000;
            return new Date(s);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static String format(long t, String format) {
        SimpleDateFormat sd = new SimpleDateFormat(format, Locale.CHINA);
        String strDate = sd.format(new Date(t));
        return strDate;
    }

    public static String format(Date date, String format) {
        SimpleDateFormat sd = new SimpleDateFormat(format, Locale.CHINA);
        String strDate = sd.format(date);
        return strDate;
    }

    /**
     * 返回yyyy-MM-dd格式的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_DATE, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 返回HH:mm:ss格式的时间字符串
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_TIME, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 返回yyyy-MM-dd HH:mm:ss格式的日期时间字符串
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_DATE_TIME, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 返回yyyy格式的年
     *
     * @param date
     * @return
     */
    public static String formatYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_YEAR, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 返回MM格式的月
     *
     * @param date
     * @return
     */
    public static String formatMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_MONTH, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 返回dd格式的日期
     *
     * @param date
     * @return
     */
    public static String formatDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_DAY, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 返回HH:mm格式的小时分钟
     *
     * @param date
     * @return
     */
    public static String formatShortTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_HOUR_MIN, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 返回yyyy-MM-dd格式的日期字符串
     *
     * @param t
     * @return
     */
    public static String formatDate(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_DATE, Locale.CHINA);
        return sdf.format(t);
    }

    /**
     * 返回HH:mm:ss格式的时间字符串
     *
     * @param t
     * @return
     */
    public static String formatTime(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_TIME, Locale.CHINA);
        return sdf.format(t);
    }

    /**
     * 返回yyyy-MM-dd HH:mm:ss格式的日期时间字符串
     *
     * @param t
     * @return
     */
    public static String formatDateTime(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_DATE_TIME, Locale.CHINA);
        return sdf.format(t);
    }

    /**
     * 返回yyyy格式的年
     *
     * @param t
     * @return
     */
    public static String formatYear(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_YEAR, Locale.CHINA);
        return sdf.format(t);
    }

    /**
     * 返回MM格式的月
     *
     * @param t
     * @return
     */
    public static String formatMonth(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_MONTH, Locale.CHINA);
        return sdf.format(t);
    }

    /**
     * 返回dd格式的日期
     *
     * @param t
     * @return
     */
    public static String formatDay(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_DAY, Locale.CHINA);
        return sdf.format(t);
    }

    /**
     * 返回HH:mm格式的小时分钟
     *
     * @param t
     * @return
     */
    public static String formatShortTime(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_HOUR_MIN, Locale.CHINA);
        return sdf.format(t);
    }

    public static boolean isSameDay(Calendar cale1, Calendar cale2) {
        return cale1.get(Calendar.YEAR) == cale2.get(Calendar.YEAR)
                && cale1.get(Calendar.DAY_OF_YEAR) == cale2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isBeforeToday(Calendar cale) {
        return (cale.get(Calendar.YEAR) == TODAY.get(Calendar.YEAR) && cale.get(Calendar.DAY_OF_YEAR) <= TODAY
                .get(Calendar.DAY_OF_YEAR)) || cale.get(Calendar.YEAR) < TODAY.get(Calendar.YEAR);
    }

    /**
     * @param mRangeMinDate
     * @param string
     * @return
     */
    public static Date parse(String dateStr, String format) {
        SimpleDateFormat sd = new SimpleDateFormat(format, Locale.CHINA);
        try {
            return sd.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 把后端返回的日期组合成用于前端显示的格式，如上线时间：2014-12-12
     *
     * @param prefix
     * @param dateStr
     * @param defaultStr
     * @return
     */
    public static String format(String prefix, String dateStr, String defaultStr) {
        if (StringUtils.isBlank(dateStr) || dateStr.equals("0")) {
            dateStr = defaultStr;
        }

        return prefix + dateStr.replace(" ", "  ");
    }

    public static String formatPartTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(F_PART_TIME, Locale.CHINA);
        return sdf.format(new Date(timestamp));
    }
}
