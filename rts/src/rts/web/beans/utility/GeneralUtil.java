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
 * $Id: GeneralUtil.java,v 1.2 2005/12/01 08:23:51 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.utility;

/**
 *	Purpose: General Util class to convert in the required format.
 * 
 *  @author              Jeetendra Prasad
 *  @version             1.0
 * 	Date of creation:    05-01-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* Java API  */
import java.util.*;
import java.text.*;
 
public class GeneralUtil  {
    public static String DATE_FORMAT="MM/dd/yyyy HH:mm:ss z";
    public GeneralUtil() {}

/*    public static void main(String args[]){
        System.out.println("Default Locale : " + Locale.getDefault());
        System.out.println("Today's Date : " + getDateForDisplay(new Date(),Locale.getDefault()));
      }
*/

    /**
     * Purpose : To get contentsize of document and others for display in defined format.
     * @param  : size - size to be formatted.
     * @param  : locale - locale in which to format the size.
     * @return : formatted size.
     */    
    public static String getDocSizeForDisplay(long size,Locale locale){
        String pattern = "###,###,###.##";
        String output = null;
        double value = (((double)size)/1024);
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern(pattern);
/*        
        if(value > 1024){
            value = (value/1024);
            output = df.format(value) + " MB";
        }else{
            output = df.format(value) + " KB";
        }
*/        
        output = df.format(value);
        return  output;
    }


    /**
     * Purpose : To get date display in defined format.
     * @param  : date - date to be formatted.
     * @param  : locale - locale in which to format the date.
     * @return : output - String.
     */    
    public static String getDateForDisplay(Date date,Locale locale){
        SimpleDateFormat formatter = null;
        String output = null;
        formatter = new SimpleDateFormat(DATE_FORMAT, locale);
        output = formatter.format(date);
        return output;

//        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,locale);
//        return formatter.format(date);
    }
}
