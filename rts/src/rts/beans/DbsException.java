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
 * $Id: DbsException.java,v 1.1.1.1 2005/05/26 12:31:44 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of IfsException class provided by CMSDK API.
 * 
 *  @author              Prasad Jeetendra
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 *	Last Modfied Date:    
 */

import oracle.ifs.common.IfsException;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionErrors;

public class DbsException extends Throwable {

    private IfsException ifsException=null;
    private String messageKey;
    private String[] replacementValues;

    /**
	   * Purpose : To create DbsException using IfsException class
	   * @param  : iex - An IfsException Object  
	   */    
    public DbsException(IfsException iex) {
        this.ifsException = iex;
    }

    /**
	   * Purpose  : Returns the Error Code.
     * @returns : ErrorCode - int 
	   */
    public int getErrorCode() {
        return ifsException.getErrorCode();
    }

    /**
	   * Purpose  : Returns the Error Message.
     * @returns : ErrorCode - String 
	   */
    public String getMessage() {
        return ifsException.getMessage();
    }

    /**
	   * Purpose : Returns the Error Message.
     * returns : ErrorMessage - String
	   */
    public String  getErrorMessage(){
           String errorMessage="An Error Has Occured : "+ ifsException.getMessage();
           ifsException.printStackTrace();
    /*        switch (errorCode) {
                case 1:  System.out.println("January"); break;
                case 2:  System.out.println("February"); break;
                case 3:  System.out.println("March"); break;
                case 4:  System.out.println("April"); break;
                case 5:  System.out.println("May"); break;
                case 6:  System.out.println("June"); break;
                case 7:  System.out.println("July"); break;
                case 8:  System.out.println("August"); break;
                case 9:  System.out.println("September"); break;
                case 10: System.out.println("October"); break;
                case 11: System.out.println("November"); break;
                case 12: System.out.println("December"); break;
            }
    */        return errorMessage;
    }

    /**
	   * Purpose : Returns the Contains Error Code.
     * @param  : errorCode - int
	   */
    public boolean containsErrorCode(int errorCode){
      return this.ifsException.containsErrorCode(errorCode);
    }

    /**
	   * Purpose : Returns the Message Key.
	   */
    public String getMessageKey() {
        return messageKey;
    }

    /**
	   * Purpose : Sets the message key
     * @param  : newMessageKey
	   */
    public void setMessageKey(String newMessageKey) {
        messageKey = newMessageKey;
    }

    /**
	   * Purpose : Sets the message keys
     * @param  : newMessageKey
     * @param  : values -  An Array
	   */
    public void setMessageKey(String newMessageKey,String[] values) {
        messageKey = newMessageKey;
        replacementValues = values;
    }

    /**
	   * Purpose   : Returns an array of the replacement values.
     * @returns  : replacementValues - An String array
	   */
    public String[] getReplacementValues() {
        return replacementValues;
    }

    /**
	   * Purpose : Sets the ReplacementValues
     * @param  : newReplacementValues
	   */
    public void setReplacementValues(String[] newReplacementValues) {
        replacementValues = newReplacementValues;
    }
}
