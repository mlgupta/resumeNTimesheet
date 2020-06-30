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
 * $Id: RescheduleForm.java,v 1.1.1.1 2005/05/26 12:32:12 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.scheduler;

/**
 *	Purpose: To store the values of the html controls of 
 *  RescheduleForm in re_schedule.jsp
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    02-04-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
//Struts API
import org.apache.struts.validator.ValidatorForm;

public class RescheduleForm extends ValidatorForm {

    private String txtJobName;
    private String txtJobType;
    private String txtUser;
    private String txtCreateDate;
    private String txtDispatchDate;
    private String[] cboHour=new String[24];
    private String[] cboMinute=new String[60];
    private String[] cboSeconds=new String[60];
    private String triggerName;
    private String triggerType;
    private String day;
    private String month;
    private String year;
    private String hours;
    private String minutes;
    private String seconds;
    private String timezone;

    public RescheduleForm() {
    
        for(int index=0;index<60;index++){
            if(index < 10){
                cboMinute[index]="0"+new String().valueOf(index);
                cboSeconds[index]="0"+new String().valueOf(index);
            }else{
                
            }
        }
        for(int indexHour=0;indexHour<24;indexHour++){
            if(indexHour < 10){
               cboHour[indexHour]="0"+new String().valueOf(indexHour);
            }else{
               cboHour[indexHour]=new String().valueOf(indexHour); 
            }
        }
    }

    /**
     * Purpose : Returns txtJobName.
     * @return : String
     */
    public String getTxtJobName() {
        return txtJobName;
    }

    /**
     * Purpose : Sets the value of txtJobName.
     * @param  : newTxtJobName Value of txtJobName from the form
     */
    public void setTxtJobName(String newTxtJobName) {
        txtJobName = newTxtJobName;
    }

    /**
     * Purpose : Returns txtJobType.
     * @return : String
     */
    public String getTxtJobType() {
        return txtJobType;
    }

    /**
     * Purpose : Sets the value of txtJobType.
     * @param  : newTxtJobType Value of txtJobType from the form
     */
    public void setTxtJobType(String newTxtJobType) {
        txtJobType = newTxtJobType;
    }

    /**
     * Purpose : Returns txtUser.
     * @return : String
     */
    public String getTxtUser() {
        return txtUser;
    }

    /**
     * Purpose : Sets the value of txtUser.
     * @param  : newTxtUser Value of txtUser from the form
     */
    public void setTxtUser(String newTxtUser) {
        txtUser = newTxtUser;
    }

    /**
     * Purpose : Returns txtCreateDate.
     * @return : String
     */
    public String getTxtCreateDate() {
        return txtCreateDate;
    }

    /**
     * Purpose : Sets the value of txtCreateDate.
     * @param  : newTxtCreateDate Value of txtCreateDate from the form
     */
    public void setTxtCreateDate(String newTxtCreateDate) {
        txtCreateDate = newTxtCreateDate;
    }

    /**
     * Purpose : Returns txtDispatchDate.
     * @return : String
     */
    public String getTxtDispatchDate() {
        return txtDispatchDate;
    }

    /**
     * Purpose : Sets the value of txtDispatchDate.
     * @param  : newTxtDispatchDate Value of txtDispatchDate from the form
     */
    public void setTxtDispatchDate(String newTxtDispatchDate) {
        txtDispatchDate = newTxtDispatchDate;
    }

    /**
     * Purpose : Returns cboHour.
     * @return : String
     */
    public String[] getCboHour() {
        return cboHour;
    }

    /**
     * Purpose : Sets the value of cboHour.
     * @param  : newCboHour Value of cboHour from the form
     */
    public void setCboHour(String[] newCboHour) {
        cboHour = newCboHour;
    }

    /**
     * Purpose : Returns cboMinute.
     * @return : String
     */
    public String[] getCboMinute() {
        return cboMinute;
    }

    /**
     * Purpose : Sets the value of cboMinute.
     * @param  : newCboMinute Value of cboMinute from the form
     */
    public void setCboMinute(String[] newCboMinute) {
        cboMinute = newCboMinute;
    }

    /**
     * Purpose : Returns cboSeconds.
     * @return : String
     */
    public String[] getCboSeconds() {
        return cboSeconds;
    }

    /**
     * Purpose : Sets the value of cboSeconds.
     * @param  : newCboSeconds Value of cboSeconds from the form
     */
    public void setCboSeconds(String[] newCboSeconds) {
        cboSeconds = newCboSeconds;
    }

    public String getTriggerName()
    {
        return triggerName;
    }

    public void setTriggerName(String newTriggerName)
    {
        triggerName = newTriggerName;
    }

    public String getTriggerType()
    {
        return triggerType;
    }

    public void setTriggerType(String newTriggerType)
    {
        triggerType = newTriggerType;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String newDay)
    {
        day = newDay;
    }

    public String getMonth()
    {
        return month;
    }

    public void setMonth(String newMonth)
    {
        month = newMonth;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String newYear)
    {
        year = newYear;
    }

    public String getHours()
    {
        return hours;
    }

    public void setHours(String newHours)
    {
        hours = newHours;
    }

    public String getMinutes()
    {
        return minutes;
    }

    public void setMinutes(String newMinutes)
    {
        minutes = newMinutes;
    }

    public String getSeconds()
    {
        return seconds;
    }

    public void setSeconds(String newSeconds)
    {
        seconds = newSeconds;
    }

    public String getTimezone()
    {
        return timezone;
    }

    public void setTimezone(String newTimezone)
    {
        timezone = newTimezone;
    }



}
