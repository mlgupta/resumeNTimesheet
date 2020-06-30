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
 * $Id: ExceptionBean.java,v 1.2 2005/08/25 11:46:04 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.exception;

/**
 *	Purpose: To handle common Exceptions and prepare Action Errors collection to 
 *           be displayed to User.
 *  @author              Jeetendra Prasad
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/*rts package references */ 
import rts.beans.*;
import org.apache.struts.action.*;
import org.apache.log4j.*;
 
public class ExceptionBean extends Throwable {
    private int errorCode;
    private String errorTrace;
    private String message;
    private String messageKey;
    private String[] replacementValues;
    /**
     * Purpose   : Constructor to create ExceptionBean Object to handle our own Exception.
     */
    public ExceptionBean(){
        Logger logger = Logger.getLogger("DbsLogger");        
        errorCode = 0;
        logger.debug("errorCode : " + errorCode);
        errorTrace = "";
        logger.debug("errorTrace : " + errorTrace);
        message = "";
        logger.debug("message : " + message);
        messageKey = "errors.catchall";
        logger.debug("messageKey : " + messageKey);
        replacementValues = null;
        logger.debug("replacementValues : " + replacementValues);
    }

    /**
     * Purpose   : Constructor to create ExceptionBean Object to handle DbsException.
     * @param    : dex - DbsException
     */
    public ExceptionBean(DbsException dex) {
        Logger logger = Logger.getLogger("DbsLogger");
        message = dex.getMessage();
        errorCode = dex.getErrorCode();
        
        StackTraceElement[] st = dex.getStackTrace();
        errorTrace = message;
        for(int index = 0; index < st.length; index++){
            errorTrace += "\n\t" + st[index].toString();
        }
        if(dex.getMessageKey() == null){
            messageKey = "errors.catchall";
            logger.debug("messageKey : " + messageKey);
            replacementValues = new String[1] ;
            replacementValues[0] = message;
            logger.debug("replacementValues[0] : " + replacementValues[0]);
        }else{
            messageKey = dex.getMessageKey();
            logger.debug("messageKey : " + messageKey);
            replacementValues = dex.getReplacementValues();
            logger.debug("replacementValues.length : " + replacementValues.length);
        }

     /*   switch(errorCode){

          case 10656 :
                        messageKey = "errors.10656.connection.unavailable";
                        logger.debug("messageKey : " + messageKey);
                        replacementValues = null;
                        logger.debug("replacementValues : " + replacementValues);
                        break;

          case 20102 :
                        messageKey = "errors.20102.connection.unavailable";
                        logger.debug("messageKey : " + messageKey);
                        replacementValues = null;
                        logger.debug("replacementValues : " + replacementValues);
                        break;

          case 30002 :
                        messageKey = "errors.30002.folderdoc.insufficientright";
                        logger.debug("messageKey : " + messageKey);
                        replacementValues = null;
                        logger.debug("replacementValues : " + replacementValues);
                        break;
                        
          case 30048 :
                        messageKey="errors.30048.insufficient.access.to.add.items";
                        logger.debug("messageKey : "+ messageKey);
                        replacementValues = null;
                        logger.debug("replacementValue : "+replacementValues);
                        break;
                        
          case 34805 :
                        messageKey="errors.34805.content.quota.exceeded";
                        logger.debug("messageKey : "+ messageKey);
                        replacementValues = null;
                        logger.debug("replacementValue : "+replacementValues);
                        break;
                        
          case 68006 :
                          messageKey = "errors.68006.folderdoc.itemexist";
                          logger.debug("messageKey : " + messageKey);
                          replacementValues = null;
                          logger.debug("replacementValues : " + replacementValues);
                          break;
        }*/
        if(errorCode == 30002){
            messageKey = "errors.30002.folderdoc.insufficientright";
            logger.debug("messageKey : " + messageKey);
            replacementValues = null;
            logger.debug("replacementValues : " + replacementValues);
        }else if(errorCode == 68006){
            messageKey = "errors.68006.folderdoc.itemexist";
            logger.debug("messageKey : " + messageKey);
            replacementValues = null;
            logger.debug("replacementValues : " + replacementValues);
        }else if(errorCode == 10656){
            messageKey = "errors.10656.connection.unavailable";
            logger.debug("messageKey : " + messageKey);
            replacementValues = null;
            logger.debug("replacementValues : " + replacementValues);
        }else{
            if(dex.containsErrorCode(20102)){
                messageKey = "errors.20102.connection.unavailable";
                logger.debug("messageKey : " + messageKey);
                replacementValues = null;
                logger.debug("replacementValues : " + replacementValues);
            }
        } 
        if(dex.containsErrorCode(34805)){
                messageKey="errors.34805.content.quota.exceeded";
                logger.debug("messageKey : "+ messageKey);
                replacementValues = null;
                logger.debug("replacementValue : "+replacementValues);
        }
        if(dex.containsErrorCode(30048)){
                messageKey="errors.30048.insufficient.access.to.add.items";
                logger.debug("messageKey : "+ messageKey);
                replacementValues = null;
                logger.debug("replacementValue : "+replacementValues);
        }
        if (dex.containsErrorCode(30011)){
                messageKey="errors.30011.folderdoc.unabletorename";
                logger.debug("messageKey : "+ messageKey);
                replacementValues = null;
                logger.debug("replacementValue : "+replacementValues);
        }

        
    }

    /**
     * Purpose   : Constructor to create ExceptionBean Object to handle Exception.
     * @param    : ex - All type of Exception
     */
    public ExceptionBean(Exception ex) {
        Logger logger = Logger.getLogger("DbsLogger");
        message = ex.getMessage();
        logger.debug("message : " + message);
        if(message==null){
            message=ex.toString();
            logger.debug("message : " + message);
        }
        messageKey = "errors.catchall";
        logger.debug("messageKey : " + messageKey);
        replacementValues = new String[1] ;
        replacementValues[0] = message; 
        logger.debug("replacementValues[0] : " + replacementValues[0]);

        StackTraceElement[] st = ex.getStackTrace();
        errorTrace = message;
        for(int index = 0; index < st.length; index++){
            errorTrace += "\n\t" + st[index].toString();
        }
        logger.debug("errorTrace : " + errorTrace);
    }

    /**
     * Purpose   : To prepare Action Errors
     * @returns  : actionErrors - ActionErrors
     */
    public ActionErrors getActionErrors() {
        ActionErrors actionErrors = new ActionErrors();
        ActionError actionError = new ActionError(messageKey,replacementValues);
        actionErrors.add(ActionMessages.GLOBAL_MESSAGE, actionError);
        return actionErrors;
    }

    /**
     * Purpose   : Sets the value of messageKey.
     * @param    : newMessageKey Value of messageKey from the form.
     */
    public void setMessageKey(String newMessageKey) {
        messageKey = newMessageKey;
    }

    /**
     * Purpose   : Sets the value of messageKey.
     * @param    : newMessageKey Value of messageKey.
     * @param    : newReplacementValues Value of replacementValues. 
     */
    public void setMessageKey(String newMessageKey,String[] newReplacementValues ) {
        messageKey = newMessageKey;
        replacementValues = newReplacementValues;
    }

    /**
     * Purpose   : Sets the value of messageKey.
     * @param    : newReplacementValues Value of replacementValues from the form.
     */
    public void setReplacementValues(String[] newReplacementValues) {
        replacementValues = newReplacementValues;
    }

    /**
     * Purpose   : Sets the value of message.
     * @param    : newMessage Value of message from the form.
     */
    public void setMessage(String newMessage) {
        message = newMessage;
    }

    /**
     * Purpose   : Returns message.
     * @return   : String 
     */
    public String getMessage() {
        return message;
    }

    /**
     * Purpose   : Returns errorTrace.
     * @return   : String 
     */
    public String getErrorTrace() {
        return errorTrace;
    }
}
