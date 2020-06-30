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
 * $Id: MailForm.java,v 1.4 2005/10/05 10:19:06 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.mail;

/**
 *	Purpose: To store and retrive the values of the html controls of
 *  MailForm in mailing.jsp
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    03-04-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* rts package references */
import rts.web.beans.scheduler.*;
/* Java API */
import java.util.*;
//Struts API
import org.apache.log4j.*;
import org.apache.struts.validator.ValidatorForm;

public class MailForm extends ValidatorForm {
  private String txtTo;                     /* designated authority for mail */
  private String txtCc;                     /* Cc */
  private String txtBCc;                    /* BCc */
  private String txtSubject;                /* Subject */
  private String txaMail;                   /* mail contents */
  private String[] lstAttachment;           /* docs attached to be mailed */
  private String txtSendTime=DateHelper.format(new Date(),"MM/dd/yyyy HH:mm:ss");
  private String day;                       /* day parsed from txtSendTime */    
  private String month;                     /* month parsed from txtSendTime */
  private String year;                      /* year parsed from txtSendTime */
  private String hours;                     /* hrs parsed from txtSendTime */
  private String minutes;                   /* mins parsed from txtSendTime */
  private String timezone;                  /* timezone */
  private String seconds;                   /* seconds parsed from txtSendTime */
  private String hdnTargetFolderPath;

/**
 * Purpose   : Returns txtTo.
 * @return   : String
 */
  public String getTxtTo(){
      return txtTo;
  }

/**
 * Purpose   : Sets the value of txtTo.
 * @param    : newTxtTo Value of txtTo from the form
 */
  public void setTxtTo(String newTxtTo){
      txtTo = newTxtTo;
  }

/**
 * Purpose   : Returns txtCc.
 * @return   : String
 */
  public String getTxtCc(){
      return txtCc;
  }

/**
 * Purpose   : Sets the value of txtCc.
 * @param    : newTxtCc Value of txtCc from the form
 */
  public void setTxtCc(String newTxtCc){
      txtCc = newTxtCc;
  }

/**
 * Purpose   : Returns txtBCc.
 * @return   : String
 */
  public String getTxtBCc(){
      return txtBCc;
  }

/**
 * Purpose   : Sets the value of txtBCc.
 * @param    : newTxtBCc Value of txtBCc from the form
 */
  public void setTxtBCc(String newTxtBCc){
      txtBCc = newTxtBCc;
  }

/**
 * Purpose   : Returns txtSubject.
 * @return   : String
 */
  public String getTxtSubject(){
      return txtSubject;
  }

/**
 * Purpose   : Sets the value of txtSubject.
 * @param    : newTxtSubject Value of txtSubject from the form
 */
  public void setTxtSubject(String newTxtSubject){
      txtSubject = newTxtSubject;
  }

/**
 * Purpose   : Returns txaMail.
 * @return   : String
 */
  public String getTxaMail(){
      return txaMail;
  }

/**
 * Purpose   : Sets the value of txaMail.
 * @param    : newTxaMail Value of txaMail from the form.
 */
  public void setTxaMail(String newTxaMail){
      txaMail = newTxaMail;
  }

/**
 * Purpose   : Returns array of Attachment.
 * @return   : A String Array.
 */
  public String[] getLstAttachment(){
      return lstAttachment;
  }

/**
 * Purpose   : Sets the value of lstAttachment.
 * @param    : newLstAttachment Value of lstAttachment from the form
 */
  public void setLstAttachment(String[] newLstAttachment){
      lstAttachment = newLstAttachment;
  }

/**
 * Purpose   :  Returns  txtSendTime.
 * @return   :  String 
 */
  public String getTxtSendTime(){
      return txtSendTime;
  }

/**
 * Purpose   : Sets the value of txtSendTime.
 * @param    : newTxtSendTime Value of txtSendTime from the form
 */
  public void setTxtSendTime(String newTxtSendTime){
      txtSendTime = newTxtSendTime;
  }

/**
 * Purpose   :  Returns  day.
 * @return   :  String 
 */
  public String getDay(){
      return day;
  }

/**
 * Purpose   : Sets the value of day.
 * @param    : newDay Value of day from the form
 */
  public void setDay(String newDay){
      day = newDay;
  }

/**
 * Purpose   :  Returns  hours.
 * @return   :  String 
 */
  public String getHours(){
      return hours;
  }

/**
 * Purpose   : Sets the value of hours.
 * @param    : newHours Value of hours from the form
 */
  public void setHours(String newHours){
      hours = newHours;
  }

/**
 * Purpose   :  Returns  minutes.
 * @return   :  String 
 */
  public String getMinutes(){
      return minutes;
  }

/**
 * Purpose   : Sets the value of minutes.
 * @param    : newMinutes Value of minutes from the form
 */
  public void setMinutes(String newMinutes){
      minutes = newMinutes;
  }

/**
 * Purpose   :  Returns  month.
 * @return   :  String 
 */
  public String getMonth(){
      return month;
  }

/**
 * Purpose   : Sets the value of month.
 * @param    : newMonth Value of month from the form
 */
  public void setMonth(String newMonth){
      month = newMonth;
  }

/**
 * Purpose   :  Returns  year.
 * @return   :  String 
 */
  public String getYear(){
      return year;
  }

/**
 * Purpose   : Sets the value of year.
 * @param    : newYear Value of year from the form
 */
  public void setYear(String newYear){
      year = newYear;
  }

/**
 * Purpose   :  Returns  seconds.
 * @return   :  String 
 */
  public String getSeconds(){
      return seconds;
  }

/**
 * Purpose   : Sets the value of seconds.
 * @param    : newSeconds Value of seconds from the form
 */
  public void setSeconds(String newSeconds){
      seconds = newSeconds;
  }

/**
 * Purpose   :  Returns  timezone.
 * @return   :  String 
 */
  public String getTimezone(){
      return timezone;
  }

/**
 * Purpose   : Sets the value of timezone.
 * @param    : newTimezone Value of timezone from the form
 */
  public void setTimezone(String newTimezone){
      timezone = newTimezone;
  }

/**
 * Purpose   :  Returns  hdnTargetFolderPath.
 * @return   :  String 
 */
  public String getHdnTargetFolderPath() {
    return hdnTargetFolderPath;
  }

/**
 * Purpose   : Sets the value of hdnTargetFolderPath.
 * @param hdnTargetFolderPath Value of hdnTargetFolderPath from the form
 */
  public void setHdnTargetFolderPath(String hdnTargetFolderPath) {
    this.hdnTargetFolderPath = hdnTargetFolderPath;
  }
  
  public String toString(){
  
    String strTemp = "";
    Logger logger = Logger.getLogger("DbsLogger");
    if( logger.getLevel() == Level.DEBUG ){
      strTemp += "\n\ttxtTo : "+txtTo;
      strTemp += "\n\ttxtCc : "+txtCc;
      strTemp += "\n\ttxtSubject : "+txtSubject;
      strTemp += "\n\ttxaMail : "+txaMail;
      strTemp += "\n\tday : "+day;
      strTemp += "\n\tmonth : "+month;
      strTemp += "\n\tyear : "+year;
      strTemp += "\n\thours : "+hours;
      strTemp += "\n\tminutes : "+minutes;
      strTemp += "\n\ttimezone : "+timezone;
      strTemp += "\n\tseconds : "+seconds;
      strTemp += "\n\thdnTargetFolderPath : "+hdnTargetFolderPath;
      if( lstAttachment== null || lstAttachment.length == 0 ){
        strTemp += "\n\tlstAttachment : "+lstAttachment;  
      }else{
        for( int index = 0; index < lstAttachment.length; index++ ){
          strTemp += "\n\tlstAttachment["+index+"] : "+lstAttachment[index];
        }
      }
    }
    return strTemp;
  }
}
