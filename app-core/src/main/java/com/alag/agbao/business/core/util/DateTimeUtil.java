package com.alag.agbao.business.core.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by geely
 */
public class DateTimeUtil {

    //joda-time

    //str->Date
    //Date->str
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date addHours(Date date, int hours) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.HOUR,hours);
        Date time = rightNow.getTime();
        return time;
    }

    public static Date addTime(int second) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.SECOND,second);
        Date time = rightNow.getTime();
        return time;
    }

    public static Date addTime(Date date,int second) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.SECOND,second);
        Date time = rightNow.getTime();
        return time;
    }

    public static Boolean before(Date date1, Date date2) {
        return date1.before(date2);
    }

    public static Date strToDate(String dateTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date,String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }




    public static void main(String[] args) {
//        System.out.println(DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateTimeUtil.strToDate("2010-01-01 11:11:11","yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateTimeUtil.addHours(new Date(),-1));
        Date date1 = DateTimeUtil.addTime(-10);
        Date date2 = new Date();

        System.out.println(DateTimeUtil.before(date1,date2));
    }


}
