/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间,日期类工具
 *
 * @author ----zhaoruyang----
 */
public class DateUtil {

    public static final String FORMAT_TIME      = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_TIME_DATE = "yyyy-MM-dd";

    /**
     * 时间转化成Date
     *
     * @param formatTime Format格式
     * @param time       时间
     * @return Date
     * @throws java.text.ParseException
     */
    public static Date getTimeFromString(String formatTime, String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatTime, Locale.CHINA);
        return format.parse(time);
    }

    /**
     * 转化成string
     *
     * @param formatTime Format格式
     * @param time       时间
     * @return String
     * @throws ParseException
     */
    public static String getTimeFromLong(String formatTime, long time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatTime, Locale.CHINA);
        return format.format(new Date(time));
    }

    /**
     * @param pastTime 过去的时间
     * @return 获取多少时间前
     */
    public static String getPastTime(Date pastTime) {

        long l    = new Date().getTime() - pastTime.getTime();
        long day  = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min  = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s    = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day).append("天前");
            return sb.toString();
        }
        if (hour > 0) {
            sb.append(hour).append("小时前");
            return sb.toString();

        }
        if (min > 0) {
            sb.append(min).append("分前");
            return sb.toString();
        }
        sb.append(s).append("秒 前");
        return sb.toString();
    }


}
