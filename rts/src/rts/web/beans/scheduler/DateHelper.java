/*
 *****************************************************************************
 *                       Confidentiality Information                         *
 *                                                                           *
 * This module is the confidential and proprietary information of            *
 * DBSentry Corp.; it is not to be copied, reproduced, or transmitted in any *
 * form, by any means, in whole or in part, nor is it to be used for any     *
 * purpose other than that for which it is expressly provided without the    *
 * written permission of DBSentry Corp.                                      *
 *                                                                           *
 * Copyright (c) 2004-2005 DBSentry Corp.  All Rights Reserved.              *
 *                                                                           *
 *****************************************************************************
 * $Id: DateHelper.java,v 1.1.1.1 2005/05/26 12:32:45 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.scheduler;
/* Java API */ 
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.*;
/* Logger API */
import org.apache.log4j.*;

/**
 *	Purpose: This class contains helper methods for dealing with
 *           Date objects.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   10-03-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public final class DateHelper {
     

    /**
     * Purpose   : Returns a Date with the specified time elements.
     * @param    year - Year part of date.
     * @param    month - Month part of date.
     * @param    day - Day part of date.   
     * @param    hour - Hour part of date(it should be in 24 hrs format ie 0-23)
     * @param    minute - Minute part of date
     * @return   Date() object constructed with the time elements 
     *             passed as parameter.
     */
    public static final Date getDate(int year, int month, int day,
        int hour, int minute) {     
       Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month), 
            day, hour, minute);
        return cal.getTime();
    }

    /**
     * Purpose   : Returns a Date with the specified time elements.
     * @param    year - Year part of date.
     * @param    month - Month part of date.
     * @param    day - Day part of date.   
     * @param    hour - Hour part of date(it should be in 24 hrs format ie 0-23)
     * @param    minute - Minute part of date
     * @param    second - Second part of date
     * @return   Date() object constructed with the time elements 
     *             passed as parameter.
     */
     public static final Date getDate(int year, int month, int day,
        int hour, int minute,int sec) {

    // returns a Date with the specified time elements
       Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month), 
            day, hour, minute,sec);
        return cal.getTime();
    }  


    /**
     * Purpose   : Returns a Date with the specified time elements.
     * @param    year - Year part of date.
     * @param    month - Month part of date.
     * @param    day - Day part of date.   
     * @param    hour - Hour part of date(it should be in 24 hrs format ie 0-23)
     * @param    minute - Minute part of date
     * @param    second - Second part of date
     * @return   Date() object constructed with the time elements 
     *             passed as parameter.
     */
     public static final Date getDate(int year, int month, int day,
        int hour, int minute,int sec,TimeZone timeZone) {

    // returns a Date with the specified time elements
       Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month), 
            day, hour, minute,sec);
        cal.setTimeZone(timeZone);
        return cal.getTime();
    }  

    

    /**
     * Purpose   : Add days to a given date.
     * @param    target - Date to which days are to be added.
     * @param    days - Number of days to add.
     * @return   Date that is the sum of the target Date
     *           and the specified number of days;
     *           to subtract days from the target Date, the days
     *           argument should be negative
     */
    static public final Date addDays(Date target, int days) {        

        long msPerDay = 1000 * 60 * 60 * 24;
        long msTarget = target.getTime();
        long msSum = msTarget + (msPerDay * days);
        Date result = new Date();
        result.setTime(msSum);
        return result;
    } // addDays

/**
     * Purpose   : Add minutes to a given date.
     * @param    target - Date to which minutes are to be added.
     * @param    days - Number of minutes to add.
     * @return   Date that is the sum of the target Date
     *           and the specified number of minutes;
     *           to subtract minutes from the target Date, the days
     *           argument should be negative
     */
    static public final Date addMinutes(Date target, int minutes) {        

        long msPerMinute = 1000 * 60 ;
        long msTarget = target.getTime();
        long msSum = msTarget + (msPerMinute * minutes);
        Date result = new Date();
        result.setTime(msSum);
        return result;
    } // addDays

    /**
     * Purpose   : To find date difference
     * @param    first - The first date .
     * @param    second - The second date.
     * @return   The difference, in days, between the first
     *           and second Date arguments
     */
    static public int dayDiff(Date first, Date second) {
        long msPerDay = 1000 * 60 * 60 * 24;
        long diff = (first.getTime() / msPerDay) - (second.getTime() / msPerDay);
        Long convertLong = new Long(diff);
        return convertLong.intValue();
    } 

    /**
     * Purpose   : To get year present in a given date.
     * @param    date - Date object .
     * @return   The year present in a given date.
     */
     static public int getYear(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }  

    /**
     * Purpose   : To get month present in a given date.
     * @param    date - Date object .
     * @return   The month present in a given date.
     */
    static public int getMonth(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int calendarMonth = cal.get(Calendar.MONTH);
        return calendarMonthToInt(calendarMonth);
    } 

    /**
     * Purpose   : To get day present in a given date.
     * @param    date - Date object .
     * @return   The day present in a given date.
     */
    static public int getDay(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    } 


    /**
     * Purpose   : To get hour present in a given date.
     * @param    date - Date object .
     * @return   The hour present in a given date.
     */
    static public int getHour(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * Purpose   : To get minute present in a given date.
     * @param    date - Date object .
     * @return   The minute present in a given date.
     */
    static public int getMinute(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    } 

    /**
     * Purpose   : To get second present in a given date.
     * @param    date - Date object .
     * @return   The second present in a given date.
     */
    static public int getSecond(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    } 

    
    /**
     * Purpose   : To format a given date in the specified pattern.
     * @param    date - Date object .
     * @param    pattern - pattern for formatting.
     * @return   String representation of the date argument,
     *           formatted according to the pattern argument, which
     *           has the same syntax as the argument of the SimpleDateFormat
     *           class.
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);    

    }

    /**
     * Purpose   : To convert a given date in the specified timezone.
     * @param    sourceDate - Date object .
     * @param    targetTimezone - TimeZone in which the source date is to be converted.
     * @return   Date - the converted date.
     */
    public static Date convertToTimezone(Date sourceDate , TimeZone targetTimezone) {
        String conversionFormat="yyyy-MM-dd HH:mm:ss z";
        SimpleDateFormat df = new SimpleDateFormat(conversionFormat);
        df.setTimeZone(targetTimezone);
        String strConvertedDate=df.format(sourceDate);
        return parse(strConvertedDate,conversionFormat);
       
    }

    /**
     * Purpose   : To convert a given String in Date object.
     * @param    date - String representation of date object.
     * @param    pattern - Pattern for the date.
     * @return   Date - the converted date.
     */
    public static Date parse(String date,String pattern){
        Date parsedDate=null;
        Logger logger=Logger.getLogger("DbsLogger");
        try{
            SimpleDateFormat parser = new SimpleDateFormat(pattern);
            parsedDate=parser.parse(date);
        }catch(ParseException pe){
          logger.error(pe);
        }
        return parsedDate;
    }

    /**
     * Purpose   : Converts an int to calendar month value.
     * @param    month - int representation of month.
     * @return   Calendar representation of that month.
     */
     private static int intToCalendarMonth(int month) {
        if (month == 1)
           return Calendar.JANUARY;
        else if (month == 2)
           return Calendar.FEBRUARY;
        else if (month == 3)
           return Calendar.MARCH;
        else if (month == 4)
           return Calendar.APRIL;
        else if (month == 5)
           return Calendar.MAY;
        else if (month == 6)
           return Calendar.JUNE;
        else if (month == 7)
           return Calendar.JULY;
        else if (month == 8)
           return Calendar.AUGUST;
        else if (month == 9)
           return Calendar.SEPTEMBER;
        else if (month == 10)
           return Calendar.OCTOBER;
        else if (month == 11)
           return Calendar.NOVEMBER;
        else if (month == 12)
           return Calendar.DECEMBER;
        else
           return Calendar.JANUARY;
    } 

    /**
     * Purpose   : Converts a calendar month to int value.
     * @param    month - Calendar representation of month.
     * @return   int representation of that month.
     */
    private static int calendarMonthToInt(int calendarMonth) {
        if (calendarMonth == Calendar.JANUARY)
            return 1;
        else if (calendarMonth == Calendar.FEBRUARY)
            return 2;
        else if (calendarMonth == Calendar.MARCH)
            return 3;
        else if (calendarMonth == Calendar.APRIL)
            return 4;
        else if (calendarMonth == Calendar.MAY)
            return 5;
        else if (calendarMonth == Calendar.JUNE)
            return 6;
        else if (calendarMonth == Calendar.JULY)
            return 7;
        else if (calendarMonth == Calendar.AUGUST)
            return 8;
        else if (calendarMonth == Calendar.SEPTEMBER)
            return 9;
        else if (calendarMonth == Calendar.OCTOBER)
            return 10;
        else if (calendarMonth == Calendar.NOVEMBER)
            return 11;
        else if (calendarMonth == Calendar.DECEMBER)
            return 12;
        else
            return 1;

    } 


  

} 

