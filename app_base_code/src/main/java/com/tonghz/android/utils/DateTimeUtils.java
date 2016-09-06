package com.tonghz.android.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期时间工具类
 * Created by TongHuiZe on 2016/3/30.
 */
public class DateTimeUtils {
    /**
     * 格式化样式：年月日，如：2015-04-14
     */
    public static final String FORMAT_YMD = "yyyy-MM-dd";

    /**
     * 格式化样式：年月日，如：2015年06月4日
     */
    public static final String FORMAT_YMD_CN = "yyyy年MM月dd日";

    /**
     * 格式化样式：年月日时，如：2015年06月4日 14时
     */
    public static final String FORMAT_YMDH_CN = "yyyy年MM月dd日 HH时";

    /**
     * 格式化样式：年月日时分秒，如：2015-04-14 13:37:45
     */
    public static final String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化样式：年月日时分，如：2015-04-14 13:38
     */
    public static final String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

    /**
     * 格式化样式：月日，如：09月05日
     */
    public static final String FORMAT_MD = "MM月dd日";

    /**
     * 格式化样式：时分秒，如：09:30:20
     */
    public static final String FORMAT_HMS = "HH:mm:ss";

    /**
     * 格式化样式：时分，如：09:30
     */
    public static final String FORMAT_HM = "HH:mm";

    /**
     * 获取String类型的日期时间
     *
     * @param timeMillis 时间戳
     * @param format     格式
     * @return 返回指定格式的日期
     */
    public static String getStringDateTime(long timeMillis, String format) {
        String dateTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            dateTime = sdf.format(timeMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 获取String类型的时间
     *
     * @param date   日期
     * @param format 格式
     * @return 返回指定格式的日期
     */
    public static String getStringDateTime(Date date, String format) {
        String dateTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            dateTime = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 根据原有格式和指定的格式，获取String类型的日期时间
     *
     * @param dateTimeStr   日期时间字符串
     * @param defaultFormat 默认格式
     * @param targetFormat  目标格式
     * @return 返回指定格式的日期
     */
    public static String getStringDateTime(String dateTimeStr, String defaultFormat, String targetFormat) {
        String dateTime = null;
        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat sdf1 = new SimpleDateFormat(defaultFormat, Locale.CHINA);
            calendar.setTime(sdf1.parse(dateTimeStr));
            SimpleDateFormat sdf2 = new SimpleDateFormat(targetFormat, Locale.CHINA);
            dateTime = sdf2.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 获取日期时间，格式为：2月17日 下午15:00
     *
     * @param timeStamp 时间戳
     * @return 返回指定格式的日期
     */
    public static String getStringDateTime(String timeStamp) {
        if (StringUtils.isEmpty(timeStamp)) {
            return null;
        }

        GregorianCalendar calendar = new GregorianCalendar();
        long timeMillis = Long.parseLong(timeStamp);
        if (timeStamp.length() == 10) {
            timeMillis = timeMillis * 1000;
        }
        calendar.setTime(new Date(timeMillis));
        StringBuilder result = new StringBuilder();
        result.append(calendar.get(Calendar.MONTH)).append("月");
        result.append(calendar.get(Calendar.DAY_OF_MONTH)).append("日");
        if (calendar.get(GregorianCalendar.AM_PM) == GregorianCalendar.AM) {
            result.append(" 上午");
        } else if (calendar.get(GregorianCalendar.AM_PM) == GregorianCalendar.PM) {
            result.append(" 下午");
        }
        result.append(calendar.get(Calendar.HOUR_OF_DAY)).append(":").append(calendar.get(Calendar.MINUTE));
        return result.toString();
    }

    /**
     * 判断是否为闰年
     *
     * @param year 年份
     * @return 是否为闰年
     */
    public boolean isLeapYear(int year) {
        int changeYear = 1582;
        if (year > changeYear) {
            return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
        }

        return year % 4 == 0;
    }

    /**
     * 根据传入时间获取与当前时间的时间差
     *
     * @param timeMillis 时间戳
     * @return 时间差
     */
    public static String getDifferenceTime(long timeMillis) {
        String dateTime = null;
        try {
            Date startTime = new Date(timeMillis);
            Date endTime = new Date(System.currentTimeMillis());// 当前时间
            long between = (endTime.getTime() - startTime.getTime());
            long day = between / (24 * 60 * 60 * 1000);
            long hour = (between / (60 * 60 * 1000) - day * 24);
            long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            // long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
            if (Math.abs(day) >= 31) {
                dateTime = getDifferenceMonths(startTime, endTime) + "个月前";
            } else if (Math.abs(day) <= 30 && Math.abs(day) >= 1) {
                dateTime = Math.abs(day) + "天前";
            } else if (Math.abs(day) == 0) {
                if (Math.abs(hour) > 0) {
                    dateTime = Math.abs(hour) + "小时前";
                } else {
                    if (Math.abs(min) > 0) {
                        dateTime = Math.abs(min) + "分钟前";
                    } else {
                        dateTime = Math.abs(s) + "秒前";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 根据传入时间获取与当前时间的时间差
     *
     * @param dateTimeStr 日期时间字符串
     * @return 时间差
     */
    public static String getDifferenceTime(String dateTimeStr) {
        String dateTime = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_YMDHMS, Locale.CHINA);
            Date startTime = dateFormat.parse(dateTimeStr);
            Date endTime = new Date(System.currentTimeMillis());// 当前时间
            long between = (endTime.getTime() - startTime.getTime());
            long day = between / (24 * 60 * 60 * 1000);
            long hour = (between / (60 * 60 * 1000) - day * 24);
            long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            // long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
            if (Math.abs(day) >= 31) {
                dateTime = getDifferenceMonths(startTime, endTime) + "个月前";
            } else if (Math.abs(day) <= 30 && Math.abs(day) >= 1) {
                dateTime = Math.abs(day) + "天前";
            } else if (Math.abs(day) == 0) {
                if (Math.abs(hour) > 0) {
                    dateTime = Math.abs(hour) + "小时前";
                } else {
                    if (Math.abs(min) > 0) {
                        dateTime = Math.abs(min) + "分钟前";
                    } else {
                        dateTime = Math.abs(s) + "秒前";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 计算两个时间相差月份
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差月份
     */
    public static int getDifferenceMonths(Date startDate, Date endDate) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c2.setTime(endDate);
        result = (c2.get(Calendar.YEAR) * 12 + c2.get(Calendar.MONTH)) - (c1.get(Calendar.YEAR) * 12 + c1.get(Calendar.MONTH));

        return Math.abs(result);
    }

    /**
     * 计算两个时间相差天数
     *
     * @param timeMillis 时间戳
     * @return 相差天数
     */
    public static long getDifferenceDays(long timeMillis) {
        return (timeMillis - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);
    }
}
