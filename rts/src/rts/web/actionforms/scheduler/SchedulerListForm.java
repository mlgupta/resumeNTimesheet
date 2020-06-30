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
 * $Id: SchedulerListForm.java,v 1.1.1.1 2005/05/26 12:32:12 suved Exp $
 *****************************************************************************
 */
package rts.web.actionforms.scheduler;

/**
 *	Purpose: To store the values of the html controls of 
 *  schedulerListForm in scheduler_list.jsp
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    02-04-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
//Struts API
import org.apache.struts.validator.ValidatorForm;

public class SchedulerListForm extends ValidatorForm {
    private String radSelect;
    private String[] cboSearchByJobType;
    private String txtSearchByUserName;
    private String operation;
    private String txtPageNo;
    private String txtPageCount;
    private String txtCreateFromDate;
    private String txtCreateToDate;
    private String txtDispatchFromDate;
    private String txtDispatchToDate;
    private String isSchedulerStopped;
    private String timezone;
    private String jobType;

    /**
     * Purpose : Returns an Array of cboSearchByJobType.
     * @return : String Array
     */
    public String[] getCboSearchByJobType() {
        return cboSearchByJobType;
    }

    /**
     * Purpose : Sets the value of cboSearchByJobType.
     * @param  : newCboSearchByJobType Value of cboSearchByJobType from the form
     */
    public void setCboSearchByJobType(String[] newCboSearchByJobType) {
        cboSearchByJobType = newCboSearchByJobType;
    }

    /**
     * Purpose : Returns value of operation
     * @return : String 
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Purpose : Sets the value of operation.
     * @param  : newOperation Value of operation from the form
     */
    public void setOperation(String newOperation) {
        operation = newOperation;
    }

    /**
     * Purpose : Returns value of radSelect
     * @return : String 
     */
    public String getRadSelect() {
        return radSelect;
    }

    /**
     * Purpose : Sets the value of radSelect.
     * @param  : newRadSelect Value of radSelect from the form
     */
    public void setRadSelect(String newRadSelect) {
        radSelect = newRadSelect;
    }

    /**
     * Purpose : Returns value of txtPageCount
     * @return : String 
     */
    public String getTxtPageCount() {
        return txtPageCount;
    }

    /**
     * Purpose : Sets the value of txtPageCount.
     * @param  : newTxtPageCount Value of txtPageCount from the form
     */
    public void setTxtPageCount(String newTxtPageCount) {
        txtPageCount = newTxtPageCount;
    }

    /**
     * Purpose : Returns value of txtPageNo
     * @return : String 
     */
    public String getTxtPageNo() {
        return txtPageNo;
    }

    /**
     * Purpose : Sets the value of txtPageNo.
     * @param  : newTxtPageNo Value of txtPageNo from the form
     */
    public void setTxtPageNo(String newTxtPageNo) {
        txtPageNo = newTxtPageNo;
    }

    /**
     * Purpose : Returns value of txtSearchByUserName
     * @return : String 
     */
    public String getTxtSearchByUserName() {
        return txtSearchByUserName;
    }

    /**
     * Purpose : Sets the value of txtSearchByUserName.
     * @param  : newTxtSearchByUserName Value of txtSearchByUserName from the form
     */
    public void setTxtSearchByUserName(String newTxtSearchByUserName) {
        txtSearchByUserName = newTxtSearchByUserName;
    }

    /**
     * Purpose : Returns value of txtCreatedFromDate
     * @return : String 
     */
    public String getTxtCreateFromDate() {
    return txtCreateFromDate;
    }

    /**
     * Purpose : Sets the value of txtCreatedFromDate.
     * @param  : newTxtCreatedFromDate Value of txtCreatedFromDate from the form
     */
    public void setTxtCreateFromDate(String newTxtCreateFromDate) {
    txtCreateFromDate = newTxtCreateFromDate;
    }

    /**
     * Purpose : Returns value of txtCreatedToDate
     * @return : String 
     */
    public String getTxtCreateToDate() {
    return txtCreateToDate;
    }

    /**
     * Purpose : Sets the value of txtCreatedToDate.
     * @param  : newTxtCreatedToDate Value of txtCreatedToDate from the form
     */
    public void setTxtCreateToDate(String newTxtCreateToDate) {
    txtCreateToDate = newTxtCreateToDate;
    }

    /**
     * Purpose : Returns value of txtDispatchFromDate
     * @return : String 
     */
    public String getTxtDispatchFromDate() {
        return txtDispatchFromDate;
    }

    /**
     * Purpose : Sets the value of txtDispatchFromDate.
     * @param  : newTxtDispatchFromDate Value of txtDispatchFromDate from the form
     */
    public void setTxtDispatchFromDate(String newTxtDispatchFromDate) {
        txtDispatchFromDate = newTxtDispatchFromDate;
    }

    /**
     * Purpose : Returns value of jobtype
     * @return : String 
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * Purpose : Sets the value of txtDispatchToDate.
     * @param  : newTxtDispatchToDate Value of txtDispatchToDate from the form
     */
    public void setJobType(String newJobType) {
        jobType = newJobType;
    }



    /**
     * Purpose : Returns value of txtDispatchToDate
     * @return : String 
     */
    public String getTxtDispatchToDate() {
        return txtDispatchToDate;
    }

    /**
     * Purpose : Sets the value of txtDispatchToDate.
     * @param  : newTxtDispatchToDate Value of txtDispatchToDate from the form
     */
    public void setTxtDispatchToDate(String newTxtDispatchToDate) {
        txtDispatchToDate = newTxtDispatchToDate;
    }

    public String getIsSchedulerStopped()
    {
        return isSchedulerStopped;
    }

    public void setIsSchedulerStopped(String newIsSchedulerStopped)
    {
        isSchedulerStopped = newIsSchedulerStopped;
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
