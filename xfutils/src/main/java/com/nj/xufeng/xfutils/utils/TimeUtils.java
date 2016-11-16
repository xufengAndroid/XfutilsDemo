package com.nj.xufeng.xfutils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }


    public static long getDatetimeInLong(String datetime, String patten){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        try {
            Date date = simpleDateFormat.parse(datetime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * @param from
     * @param to
     * @return
     */
    public static long between(Date from, Date to) {
        return to.getTime() - from.getTime();
    }


    public static String getFormatDateTime(String datetime, String patten, String nowPatten) {
        String result = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
            SimpleDateFormat nowsimpleDateFormat = new SimpleDateFormat(nowPatten);
            result = nowsimpleDateFormat.format(simpleDateFormat.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


}
