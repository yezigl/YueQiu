/**
 * Copyright (c) 2010-2014 meituan.com
 * All rights reserved.
 */
package com.yueqiu.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 字符串的处理
 *
 * @author lidehua
 * @version 1.0
 * @created 2014-8-8
 */
public class StringUtils {

    private static final String BLANK = "";

    private static final NumberFormat CF = NumberFormat.getCurrencyInstance(Locale.CHINA);

    private static final DecimalFormat DF_1 = new DecimalFormat("0.0");

    /**
     * 判断字符串是否为空
     * <ul>
     * <li>StringUtils.isBlank(null) = true</li>
     * <li>StringUtils.isBlank("") = true</li>
     * <li>StringUtils.isBlank("  ") = true</li>
     * <li>StringUtils.isBlank("aaabbb") = false</li>
     * <li>StringUtils.isBlank(" aaabbb ") = false</li>
     * </ul>
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符串是否为空
     * <ul>
     * <li>StringUtils.isBlank(null) = true</li>
     * <li>StringUtils.isBlank("") = true</li>
     * <li>StringUtils.isBlank("  ") = false</li>
     * <li>StringUtils.isBlank("aaabbb") = false</li>
     * <li>StringUtils.isBlank(" aaabbb ") = false</li>
     * </ul>
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 如果字符串为空，则返回提供的默认字符串
     *
     * @param str
     * @param defaultStr
     * @return
     */
    public static String defaultIfBlank(String str, String defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * 截取字符串，后面加上...
     *
     * @param len
     * @return
     */
    public static String truncate(String str, int len) {
        return truncate(str, len, "...");
    }

    public static String truncate(String str, int len, String suffix) {
        if (isBlank(str)) {
            return BLANK;
        }
        if (str.length() <= len) {
            return str;
        }

        return str.substring(0, len) + suffix;
    }

    /**
     * 格式化字符串为货币格式
     *
     * @param d      输入的货币金额
     * @param symbol 是否带货币符号
     * @return 格式化后的货币格式
     * @see StringUtils#formatCurrency(double, boolean)
     */
    public static String formatCurrency(String d, boolean symbol) {
        try {
            return formatCurrency(Double.parseDouble(d), symbol);
        } catch (Exception e) {
            return d;
        }
    }

    public static String formatCurrency(String d) {
        return formatCurrency(d, false);
    }

    /**
     * 格式化数字为货币格式
     *
     * @param d      输入的货币金额
     * @param symbol 是否带货币符号
     * @return 格式化后的货币格式
     */
    public static String formatCurrency(double d, boolean symbol) {
        boolean negative = d < 0;
        String s = CF.format(Math.abs(d));
        if (!symbol) {
            return (negative ? "-" : "") + s.substring(1);
        }
        return s.substring(0, 1) + (negative ? "-" : "") + s.substring(1);
    }

    public static String formatCurrency(double d) {
        return formatCurrency(d, false);
    }

    /**
     * 格式化小数:如果小数位部分不为0，小数点后保留1位；否则，作为整数
     *
     * @param value
     * @return
     */
    public static String formatDecimalIfNeed(double value) {
        int temp = (int) value;
        if (temp == value) {
            return String.valueOf(temp);
        }
        return DF_1.format(value);
    }

    /**
     * 格式化小数，小数点后保留1位
     *
     * @param value
     * @return
     */
    public static String formatDecimal(double value) {
        return DF_1.format(value);
    }

    public static String formatDecimal(String value) {
        try {
            return DF_1.format(Double.parseDouble(value));
        } catch (Exception e) {
            return "0.0";
        }
    }

    /**
     * 格式化数字
     *
     * @param value
     * @param pattern 格式化的格式
     * @return
     */
    public static String formatNumber(double value, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * 根据指定的字符串切分原始的字符串，并获取指定位置的值
     *
     * @param string
     * @param splitExp
     * @param index
     * @return
     */
    public static String split(String string, String splitExp, int index) {
        if (isBlank(string)) {
            return "";
        }
        String[] ss = string.split(splitExp);
        if (ss.length <= index) {
            return "";
        }
        return ss[index];
    }

    public static String trim(String s) {
        if (isBlank(s)) {
            return "";
        }
        return s.trim();
    }

    /**
     * @param price
     * @return
     */
    public static String getPrice(String price) {
        return "￥" + price;
    }

    public static String join(Object[] ss, char sep) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ss.length; i++) {
            if (i > 0) {
                builder.append(sep);
            }
            builder.append(ss[i]);
        }
        return builder.toString();
    }

    public static String join(Object[] ss, String sep) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ss.length; i++) {
            if (i > 0) {
                builder.append(sep);
            }
            builder.append(ss[i]);
        }
        return builder.toString();
    }

    public static String join(String sep, String... args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                builder.append(sep);
            }
            builder.append(args[i]);
        }
        return builder.toString();
    }

    public static String encodeURL(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    public static String toLabel(String label, String... args) {
        return label + "：" + StringUtils.join(args, "");
    }

    public static float toFloat(String s, float defaultValue) {
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int toInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
