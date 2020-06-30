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
 * $Id: JobListBean.java,v 1.2 2006/01/06 10:53:15 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.scheduler;
/* Java API */
import java.util.Date;
/**
 *	Purpose: This class represents each row in job listing.
 *           It also provides getter and setter methods for its row elements.
 *           
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   10-03-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class JobListBean
{
    public static String DATE_FORMAT="MM/dd/yyyy HH:mm:ss z"; //also present in SchedulerListAction
    public String jobName;
    public String creatorName;
    public String jobType;
    public Date createTime;
    public Date executionTime;
    private String retrialCount;
    private String jobRetrialInterval;
    private String jobMaxCount;
    private String jobErrorMessage;
    public String trimJobName;      // added by suved to present trimmed jobname 
    public String trimCreatorName;  // added by suved to present trimmed creatorname

    /**
     * Purpose   : To create a Jobrow with the given parameters.
     * @param    jobName - Name of job.
     * @param    creatorName - Name of creator of job.    
     * @param    jobType - Type of job ie mail,fax or print.
     * @param    createTime - Creation time of job.    
     * @param    executionTime - Execution time of job.       
     */     
    public JobListBean(String jobName,String creatorName,String jobType,Date createTime,Date executionTime,String retrialCount,String jobRetrialInterval,String jobMaxCount,String jobErrorMessage)
    {
        this.jobName=jobName;
        if( jobName.length() >=36 ){
          this.trimJobName = jobName.substring(0,32)+"...";
        }else{
          this.trimJobName = jobName;
        }
        this.creatorName=creatorName;
        if( creatorName.length() > 13 ){
          this.trimCreatorName = creatorName.substring(0,9)+"...";
        }else{
          this.trimCreatorName = creatorName;
        }
        this.jobType=jobType;
        this.createTime=createTime;
        this.executionTime=executionTime;
        this.retrialCount=retrialCount;
        this.jobRetrialInterval=jobRetrialInterval;
        this.jobErrorMessage=jobErrorMessage;
        this.jobMaxCount=jobMaxCount;
    }

    public String getJobName(){
      return jobName;
    }

    public void setJobName(String newJobName){
      jobName = newJobName;
    }

    public String getCreatorName(){
      return creatorName;
    }

    public void setCreatorName(String newCreatorName){
      creatorName = newCreatorName;
    }

    public String getJobType(){
      return jobType;
    }

    public void setJobType(String newJobType){
      jobType = newJobType;
    }   

    public String getCreateTime(){
      return DateHelper.format(createTime,DATE_FORMAT);
    }

    public void setCreateTime(String newCreateTime){
      createTime =DateHelper.parse(newCreateTime,DATE_FORMAT);
    }

    public String getExecutionTime(){
      return DateHelper.format(executionTime,DATE_FORMAT);
    }

    public void setExecutionTime(String newExecutionTime){
      executionTime =DateHelper.parse(newExecutionTime,DATE_FORMAT);
    }

    public String toString(){
      return ("["+jobName+","+creatorName+","+jobType+","+createTime+","+executionTime+"]");
    }

    public String getRetrialCount(){
      return retrialCount;
    }

    public void setRetrialCount(String newRetrialCount){
      retrialCount = newRetrialCount;
    }

    public String getJobRetrialInterval(){
      return jobRetrialInterval;
    }

    public void setJobRetrialInterval(String newJobRetrialInterval){
      jobRetrialInterval = newJobRetrialInterval;
    }

    public String getJobMaxCount(){
        return jobMaxCount;
    }

    public void setJobMaxCount(String newJobMaxCount){
      jobMaxCount = newJobMaxCount;
    }

    public String getJobErrorMessage(){
      return jobErrorMessage;
    }

    public void setJobErrorMessage(String newJobErrorMessage){
      jobErrorMessage = newJobErrorMessage;
    }

    public String getTrimJobName(){
      return trimJobName;
    }
  
    public void setTrimJobName(String trimJobName) {
      this.trimJobName = trimJobName;
    }
  
    public String getTrimCreatorName() {
      return trimCreatorName;
    }
  
    public void setTrimCreatorName(String trimCreatorName) {
      this.trimCreatorName = trimCreatorName;
    }
    
}
