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
 * $Id: SchedulerListBean.java,v 1.1.1.1 2005/05/26 12:32:48 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.scheduler;

/**
 *	Purpose: To Populate the User List Table in scheduler_list.jsp
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    02-04-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SchedulerListBean {
    private String jobName;
    private String jobType;
    private String user;
    private String createdDate;
    private String dispatchDate;
    private String retrial_count;

    /**
     * Purpose : Returns the jobName.
     * @return : String
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Purpose : Sets the jobName.
     * @param  : newJobName Value of jobName 
     */
    public void setJobName(String newJobName) {
        jobName = newJobName;
    }

    /**
     * Purpose : Returns the jobType.
     * @return : String
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * Purpose : Sets the jobType.
     * @param  : newJobType Value of jobType 
     */
    public void setJobType(String newJobType) {
        jobType = newJobType;
    }

    /**
     * Purpose : Returns the user.
     * @return : String
     */
    public String getUser() {
        return user;
    }

    /**
     * Purpose : Sets the user.
     * @param  : newUser Value of user 
     */
    public void setUser(String newUser) {
        user = newUser;
    }

    /**
     * Purpose : Returns the createdDate.
     * @return : String
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Purpose : Sets the createdDate.
     * @param  : newCreatedDate Value of createdDate 
     */
    public void setCreatedDate(String newCreatedDate) {
        createdDate = newCreatedDate;
    }

    /**
     * Purpose : Returns the dispatchDate.
     * @return : String
     */
    public String getDispatchDate() {
        return dispatchDate;
    }

    /**
     * Purpose : Sets the dispatchDate.
     * @param  : newDispatchDate Value of dispatchDate 
     */
    public void setDispatchDate(String newDispatchDate) {
        dispatchDate = newDispatchDate;
    }

    public String getRetrial_count()
    {
        return retrial_count;
    }

    public void setRetrial_count(String newRetrial_count)
    {
        retrial_count = newRetrial_count;
    }
}
