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
 * $Id: FaxForm.java,v 1.3 2005/10/03 07:52:15 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.fax;

/**
 *	Purpose: To store and retrive the values of the html controls of
 *  FaxForm in faxing.jsp
 * 
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    25-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* rts package references */ 
import rts.web.beans.scheduler.*;
/* Java API */
import java.util.*;
//Struts API
import org.apache.struts.validator.ValidatorForm;

public class FaxForm extends ValidatorForm {
  private String txtTo;                     /* designated authority for fax */
  private String txtCompanyName;            /* company name */
  private String txtFaxNumber;              /* fax number */
  private String[] lstAttachment;           /* docs attached to be faxed */
  private String txtSendTime=DateHelper.format(new Date(),"MM/dd/yyyy HH:mm:ss");
  private String day;                       /* day parsed from txtSendTime */    
  private String month;                     /* month parsed from txtSendTime */
  private String year;                      /* year parsed from txtSendTime */
  private String hours;                     /* hrs parsed from txtSendTime */
  private String minutes;                   /* mins parsed from txtSendTime */
  private String timezone;                  /* timezone */
  private String seconds;                   /* seconds parsed from txtSendTime */
  private String txaComments;               /* comments added */

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
 * Purpose   :  Returns  lstAttachment.
 * @return   :  String[] 
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
 * Purpose   :  Returns  txtCompanyName.
 * @return   :  String 
 */
  public String getTxtCompanyName(){
      return txtCompanyName;
  }

/**
 * Purpose   : Sets the value of txtCompanyName.
 * @param    : newTxtCompanyName Value of txtCompanyName from the form
 */
  public void setTxtCompanyName(String newTxtCompanyName){
      txtCompanyName = newTxtCompanyName;
  }

/**
 * Purpose   :  Returns  txtFaxNumber.
 * @return   :  String 
 */
  public String getTxtFaxNumber(){
      return txtFaxNumber;
  }

/**
 * Purpose   : Sets the value of txtFaxNumber.
 * @param    : newTxtFaxNumber Value of txtFaxNumber from the form
 */
  public void setTxtFaxNumber(String newTxtFaxNumber){
      txtFaxNumber = newTxtFaxNumber;
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
 * Purpose   :  Returns  txtTo.
 * @return   :  String 
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
 * Purpose   :  Returns  txaComments.
 * @return   :  String 
 */
  public String getTxaComments(){
      return txaComments;
  }

/**
 * Purpose   : Sets the value of txaComments.
 * @param    : newTxaComments Value of txaComments from the form
 */
  public void setTxaComments(String newTxaComments){
      txaComments = newTxaComments;
  }

}
