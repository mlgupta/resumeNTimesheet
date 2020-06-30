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
 * $Id: JobFilter.java,v 1.1.1.1 2005/05/26 12:32:46 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.scheduler;
/* Java API */ 
import java.util.*;
/* Quartz API */
import org.quartz.*;
/**
 *	Purpose: This class filters out jobs according to the search criteria given.It is useful
 *           for providing search functionality in job listing.
 *           
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   10-03-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class JobFilter 
{
   public JobFilter()
    {
    }
    
    /**
     * Purpose   : To filter out job according to the crieteria given.If any one of
     *             parameters is null then they are not included in the criteria. 
     * @param    jobData - JobDataMap associated with the job.
     * @param    jobTrigger - Trigger associated with the job.
     * @param    creatorName - Name of creator of job.   
     * @param    jobType - Type of job(Mail,Fax or Print).
     * @param    fromCreateTime - Creation time should be on or after  fromCreateTime.  
     * @param    toCreateTime - Creation time should be on or before  fromCreateTime.
     * @param    fromExecutionTime - Creation time should be on or after  fromExecutionTime.  
     * @param    toExecutionTime - Creation time should be on or before  toExecutionTime.
     * @return   true if the job satisfies the criteria otherwise false.
     */
    public boolean filterJob(JobDataMap jobData,String creatorName,
    String jobType,Date fromCreateTime,Date toCreateTime,Date fromExecutionTime,Date toExecutionTime)
    {   
        String sJobName=null;
        String sCreatorName=null;
        String sJobType=null;
        Date sCreateTime=null;
        Date sExecutionTime=null;
        
        if(creatorName!=null && !(creatorName.equals(""))){
            if(!(jobData.getString(SchedulerConstants.JOB_CREATOR).startsWith(creatorName))){
                return false;
            }
        }

        if(fromCreateTime!=null){
            if(((Date)jobData.get(SchedulerConstants.CREATE_TIME)).compareTo(fromCreateTime) <= 0){
                return false;
            }
        }

        if(toCreateTime!=null){
            if(((Date)jobData.get(SchedulerConstants.CREATE_TIME)).compareTo(toCreateTime) >=0){
                return false;
            }
        }

        if(fromExecutionTime!=null){
            if(((Date)jobData.get(SchedulerConstants.EXECUTION_TIME)).compareTo(fromExecutionTime) <= 0){
                return false;
            }
        }

        if(toExecutionTime!=null){
            if(((Date)jobData.get(SchedulerConstants.EXECUTION_TIME)).compareTo(toExecutionTime) >= 0){
                return false;
            }
        }

        if(!jobType.equals(SchedulerConstants.ALL_JOBS)){
            if(!jobData.getString(SchedulerConstants.JOB_TYPE).equals(jobType)){
                return false;
            }
        }

        return true;
    }
    

    
}
