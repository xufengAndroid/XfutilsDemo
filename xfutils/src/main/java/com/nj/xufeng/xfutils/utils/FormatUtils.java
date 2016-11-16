package com.nj.xufeng.xfutils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by xufeng on 15/10/15.
 */
public class FormatUtils {

    private FormatUtils() {
        throw new AssertionError();
    }

    /**
     *
     * @param datetime  2015-01-01 10:10:10
     * @param formatter yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateTime(String datetime,String formatter){
        String result = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
            Date from = simpleDateFormat.parse(datetime);
            Date to = new Date();
            long between = to.getTime()-from.getTime();
            long seconds = between/1000;
            if(seconds<=0){
                result = "刚刚";
            }else if(seconds<60){
                result = seconds+"秒前";
            }else if(seconds<3600){
                result = seconds/60+"分钟前";
            }else if(seconds<86400){
                result =  seconds/3600+"小时前";
            }else if(seconds<172800){
                result = "昨天"+new SimpleDateFormat("HH:mm").format(from);
            }else if(seconds<259200){
                result = "前天"+new SimpleDateFormat("HH:mm").format(from);
            }else{
                result = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(from);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 昨天 今天 明天 2015-01-01
     * @param datetime  2015-01-01 10:10:10
     * @param formatter yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateTimeAll(String datetime,String formatter){
        String result = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
            Date from = simpleDateFormat.parse(datetime);
            Date to = new Date();
            if(from.getYear()==to.getYear()&&from.getMonth()==to.getMonth() ){
                long dd = from.getDate()-to.getDate();
                if(dd==0){
                    result = "今天";
                }else if(dd==1){
                    result = "昨天";
                }else if(dd==-1){
                    result = "明天";
                }else{
                    result = new SimpleDateFormat("yyyy-MM-dd").format(from);
                }
            }else{
                result = new SimpleDateFormat("yyyy-MM-dd").format(from);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 最大显示10万   100000+
     * @param number
     * @return
     */
    public static String number(int number,int maxNumber){
        return number>maxNumber?maxNumber+"+":number+"";
    }
    public static String number(String number,int maxNumber){
        return number(Integer.parseInt(number),maxNumber);
    }

}
