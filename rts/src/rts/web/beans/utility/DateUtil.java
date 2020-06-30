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
 * $Id: DateUtil.java,v 1.1.1.1 2005/05/26 12:32:49 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.utility;

/**
 *	Purpose: To get the formatted date. 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    05-01-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* Java API */
import java.util.*;
import java.text.*;

public class DateUtil{

    public DateUtil() { }

    /**
     * Purpose   : Returns formatted date in dd-MMM-yyyy format.
     * @param    : date - Date
     * @returns  : formattedDate
     */
    public static String getFormattedDate(Date date){
        Date unformattedDate=date;
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = dateFormat.format(unformattedDate);
        return formattedDate;
    }

    /**
     * Purpose   : Returns formatted date in dd-MMM-yy format.
     * @param    : strDate - String
     * @returns  : date in dd-MMM-yyyy
     */
    public static Date parseDate(String strDate) throws ParseException{
        Date date;
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yy");
        date = dateFormat.parse(strDate);
        return date;
    }
} 
