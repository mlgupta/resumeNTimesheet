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
 * $Id: SearchJobs.java,v 1.2 2005/08/26 13:57:35 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.scheduler;
/* Java API */
import java.util.*;
/* Struts API */
import org.quartz.*;
import org.apache.log4j.*;

/**
 *	Purpose: This class is used to initiate the search and sort actions on the
 *  jobs present in the scheduler.
 *                 
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SearchJobs 
{
    private static final String[] jobGroupNames={"MAIL","FAX"};
    private static final  String[] defaultColumnOrder={"executionTime","creatorName"};
    public int pageCount=1;
    
    /**
     *	Purpose: To search and sort for jobs according to the given criteria.It passes all
     *           the search criteria to the job filter class and sort criteria to JobComparator 
     *           class.
     *  @param   scheduler - The main scheduler which in which jobs are present.
     *  @param   orderColumns - A String array to represent the sorting criteria.The position
     *                          of array elements corrosponds to the sorting order
     *  @param   creatorName - Name of the creator of job as search criteria.
     *  @param   jobType - Type of job as search criteria.Currently MAIL and FAX are supported.
     *  @param   fromCreateTime - From create time/date for job as search criteria.
     *  @param   toCreateTime - To create time/date for job as search criteria.
     *  @param   fromExecutionTime - From execution time/date for job as search criteria.
     *  @param   toExecutionTime - To execution time/date for job as search criteria.
     *  
     *  @return  ArrayList - An arraylist of jobs conforming to the search criteria
     *                       and sorted according to the given sort order
     */
    public  ArrayList findJobs(Scheduler scheduler,String[] orderColumns,String creatorName,
        String jobType,Date fromCreateTime,Date toCreateTime,Date fromExecutionTime,Date toExecutionTime,
        int pageNumber,int numRecords){
        ArrayList jobList=new ArrayList();
        ArrayList jobListToReturn=new ArrayList();
        JobFilter jobFilter=new JobFilter();
        Logger logger=Logger.getLogger("DbsLogger");
        try{
            for(int index=0;index < jobGroupNames.length ; index++){
                String[] jobNames=scheduler.getJobNames(jobGroupNames[index]);
                for(int jIndex = 0; jIndex < jobNames.length ; jIndex++){
                    Trigger trigger[] = scheduler.getTriggersOfJob(jobNames[jIndex],jobGroupNames[index]);
                                      
                     JobDataMap jobData=scheduler.getJobDetail(jobNames[jIndex],jobGroupNames[index]).getJobDataMap();
                                          
                     if(jobFilter.filterJob(jobData,creatorName,jobType,fromCreateTime,toCreateTime,fromExecutionTime,toExecutionTime)){
                    
                         String sJobName=jobNames[jIndex];
                         String sCreatorName=jobData.getString(SchedulerConstants.JOB_CREATOR);
                         String sJobType=jobData.getString(SchedulerConstants.JOB_TYPE);
                         String sRetrialcount=jobData.getString(SchedulerConstants.RETRIAL_COUNT);
                         String sRetrialInterval=jobData.getString(SchedulerConstants.RETRIAL_INTERVAL);
                         String sMaxCount=jobData.getString(SchedulerConstants.MAX_COUNT);
                         Date sCreateTime=(Date)jobData.get(SchedulerConstants.CREATE_TIME);
                         Date sExecutionTime=(Date)jobData.get(SchedulerConstants.EXECUTION_TIME);
                          String sErrorMessage=null;
                         if(trigger==null || trigger.length==0)
                         {
                           sErrorMessage="Job Dispatched";
                         }else{
                           sErrorMessage=jobData.getString(SchedulerConstants.ERROR_MESG);
                         }
                         JobListBean jobBean=new JobListBean(sJobName,sCreatorName,sJobType,sCreateTime,sExecutionTime,sRetrialcount,sRetrialInterval,sMaxCount,sErrorMessage);
                         jobList.add(jobBean);
                     }
                     
                }
                         
            }

            JobComparator jc=null;
            if(orderColumns==null || orderColumns.length == 0){
                 jc= new JobComparator(defaultColumnOrder);        
            }else{
                 jc= new JobComparator(orderColumns);
            }
            Collections.sort(jobList,jc);
            int length = jobList.size();
            if(length!=0){
                int startIndex=(pageNumber*numRecords) - numRecords;
                int endIndex=(startIndex + numRecords) - 1;
                if(endIndex >= length){
                    endIndex=length-1;
                }                
                for (int index=0; startIndex <= endIndex; ){
                    jobListToReturn.add(jobList.get(startIndex));                    
                    startIndex++;
                }
            }
            int itemCount=length;
            if(itemCount!=0){
                int pageMod=itemCount%numRecords;
                if(pageMod==0){
                    this.pageCount=itemCount/numRecords;
                    logger.debug("form sj page count0="+pageCount);
                }else{
                    this.pageCount=(itemCount/numRecords) + 1 ;
                    logger.debug("form sj page count1="+pageCount);
                }
            }
        }catch(SchedulerException schedExcep){
            logger.error(schedExcep);
            schedExcep.printStackTrace();
        }
       return jobListToReturn;
    }
}
