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
 * $Id: SendFaxJob.java,v 1.1.1.1 2005/05/26 12:32:48 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.scheduler;
/* Java API */
import java.util.*;
import java.io.*;
/* Struts API */
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.*;
import org.quartz.*;

/**
 *	Purpose: This class does the job of sending fax with the configured content
 *           to the configured recipient.It implements the org.quartz.Job interface
 *           and overrides its execute() method.
 *           note: This program will only work on systems which have HylaFAX client program
 *                 installed and configured properly with the HylaFAX server.
 *                 
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SendFaxJob implements Job {
    
    
    private Logger logger=Logger.getLogger("DbsLogger");
    private boolean errorOccured=false;
    /**
     *	Purpose: To override execute method of org.quartz.Job interface and 
     *           provide send fax functionality
     *  @param   context - JobExecutionContext associated with this Job.The context
     *           provides all the necessary information to execute this job.
     */
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        String faxDesc = null;
        JobDataMap data=null;
        Scheduler sched=null;
        JobDetail jobDetail=null;
        try {
            sched=(Scheduler)context.getScheduler();
            logger.debug("Acquired Scheduler");
            jobDetail=context.getJobDetail();
            logger.debug("Acquired JobDetail");
            data = context.getJobDetail().getJobDataMap();
            logger.debug("Acquired JobDataMap");
            logger.debug("From SendFaxJob:"+context.getJobDetail().getFullName()+" Executing");
            String faxNo = data.getString(SchedulerConstants.PROP_FAX_NUMBER);  
            String to=  data.getString(SchedulerConstants.PROP_FAX_TO);
            String companyName=data.getString(SchedulerConstants.COMPANY_NAME);
            String comments=data.getString(SchedulerConstants.PROP_FAX_COMMENTS);
            String faxTempDir=data.getString(SchedulerConstants.FAX_TEMP_DIR);
            String creator=data.getString(SchedulerConstants.JOB_CREATOR);
            logger.info("Fax Number is:"+faxNo);
                       
            String from = data.getString(SchedulerConstants.PROP_SENDER);
            ArrayList attachments=(ArrayList)data.get(SchedulerConstants.PROP_ATTACHMENTS);
                   
            if (faxNo == null || faxNo.trim().length() == 0){
                    throw new IllegalArgumentException("PROP_FAX_NUMBER not specified.");
            }
            
            if (from == null || from.trim().length() == 0){
                    throw new IllegalArgumentException("PROP_SENDER not specified.");   
            }
            
            if (attachments == null || attachments.size() == 0){
                    throw new IllegalArgumentException("PROP_ATTACHMENTS not specified.");
            }

            faxDesc = "' to: " + faxNo;
                        
            logger.debug("From SendFaxJob:Sending fax " + faxDesc);
                      
        
            sendFax(to,faxNo,from,companyName,comments,faxTempDir,creator, attachments);            
        }catch (Exception e){
            logger.error(e.toString());
            //System.out.println("1");
          try{
             if(data!=null){
                    
                    String jobName=jobDetail.getName();
                    String jobGroup=jobDetail.getGroup();
                     String jobMaxCount=(String)data.get(SchedulerConstants.MAX_COUNT);//   System.out.println("2");
                    ArrayList attachments=(ArrayList)data.get(SchedulerConstants.PROP_ATTACHMENTS);//  System.out.println(attachments.size());
                    String jobRetrialCount=(String)data.get(SchedulerConstants.RETRIAL_COUNT);//   System.out.println("3");
                    int maxCount=Integer.parseInt(jobMaxCount);//     System.out.println("4");
                    int retrialCount=Integer.parseInt(jobRetrialCount);//       System.out.println("5");
                    if(retrialCount<maxCount){                
                    String jobRetrialInterval=(String)data.get(SchedulerConstants.RETRIAL_INTERVAL);//  System.out.println("7");
                    Date oldStartTime=(Date)data.get(SchedulerConstants.EXECUTION_TIME);//  System.out.println("8");
                    Date newStartTime=DateHelper.addMinutes(oldStartTime,Integer.parseInt(jobRetrialInterval));//  System.out.println("9");
                    data.put(SchedulerConstants.EXECUTION_TIME,newStartTime);//  System.out.println("10");
                    data.put(SchedulerConstants.RETRIAL_COUNT,new String().valueOf(++retrialCount));//   System.out.println("11");
                    data.put(SchedulerConstants.ERROR_MESG,e.toString()); 
                    sched.deleteJob(jobName,jobGroup);//   System.out.println("New ExecutionTime"+(Date)data.get(SchedulerConstants.EXECUTION_TIME));
                    JobDetail faxJobDetail=new JobDetail(jobName,SchedulerConstants.FAX_JOB,SendFaxJob.class,false,true,true);
                         // jobDetail.setJobDataMap(data);
                    faxJobDetail.setJobDataMap(data);
                    Date dateOfSubmission=(Date)data.get(SchedulerConstants.CREATE_TIME);
                    logger.info("Job Create Time"+dateOfSubmission);
                    String creator=(String)data.get(SchedulerConstants.JOB_CREATOR);
                    String namePrefix=creator+DateHelper.format(newStartTime,"HH:mm:ss--yyyy-MM-dd-z");            
                    String newTriggerName=SchedulerConstants.TRIGGER_NAME_SUFFIX+namePrefix;
                    SimpleTrigger newTrigger=null;
                    if(newStartTime.compareTo(new Date())<=0){
                         newTrigger=new SimpleTrigger(newTriggerName,SchedulerConstants.FAX_JOB);
                    }else{                   
                         newTrigger=new SimpleTrigger(newTriggerName,SchedulerConstants.FAX_JOB,newStartTime);               
                    }
                    Trigger trigger[] = sched.getTriggersOfJob(jobName,SchedulerConstants.FAX_JOB);
                    faxJobDetail.validate();
                    if(trigger==null || trigger.length==0){
                          sched.scheduleJob(faxJobDetail,newTrigger);
                          logger.debug("Old Trigger Not Found");
                    }else {
                          String oldTriggerName=trigger[0].getName();
                          sched.rescheduleJob(oldTriggerName,SchedulerConstants.MAIL_JOB,newTrigger);
                          logger.debug("Old Trigger Found");
                     }
                       
                       // JobScheduler jobScheduler=new JobScheduler(sched);        
                       
                   }
                 
                 }
               
            }catch(Exception jobAddExcep)
            {
               logger.error(jobAddExcep);
            }
            throw new JobExecutionException("Unable to send fax: " + faxDesc,e, false);
        }

    }
    /**
     *	Purpose: Fax a document.It calls a native program 'sendfax' to do this job,so HylaFAX client
     *           should be properly installed prior to its usage.
     *  @param   faxNo - Recipient's fax number.
     *  @param   filesToFax - List of all the files to be faxed.                
     */
    public void  sendFax(String to,String faxNo,String from,String companyName,String comments ,String faxTempDir,String creator,ArrayList filesToFax) throws Exception{       
        try
        {
            String fileNamesWithPath="";
            ArrayList fileList=new ArrayList();
            filesToFax.trimToSize();
            int numFiles=filesToFax.size(); 
            faxTempDir=faxTempDir+"/"+creator+"/";
            File dir=new File(faxTempDir);
            if(!dir.isDirectory()){
                dir.mkdirs();
            } 
            for(int index = 0; index < numFiles; index++){
                FileByteArray fileBytes=(FileByteArray)filesToFax.get(index);
                String fileNameWithPath=faxTempDir+fileBytes.getFileName();
                fileNameWithPath=fileNameWithPath.replaceAll(" ","_");
                fileList.add(fileNameWithPath);
                fileNamesWithPath=fileNamesWithPath+" "+fileNameWithPath;
                File fileToFax=new File(fileNameWithPath);
                FileOutputStream fos = new FileOutputStream(fileToFax,false);           
                fos.write(fileBytes.getFileBytes());
                logger.debug("From SendFaxJob:File "+fileNameWithPath+" written successfully");
                fos.close();              
            } // end for loop
    		
            
               String cmd=SchedulerConstants.FAX_CMD+"-c '"+comments+"' -f '"+from+"' -x '"+companyName+"' -d '"+to+"@"+faxNo+"' "+fileNamesWithPath;            
                Runtime rt = Runtime.getRuntime();
                logger.info("Executing "+cmd );
                //Process proc = rt.exec(cmd);
                Process proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
                int exitVal = proc.waitFor();
                // any error message?
                StreamHandler errorHandler = new
                StreamHandler(proc.getErrorStream(), "TYPE_ERROR");            
            
                // any output?
                StreamHandler outputHandler = new
                StreamHandler(proc.getInputStream(), "TYPE_OUTPUT");
                
                // start them 
                errorHandler.start();
                outputHandler.start();
                                    
                // any error???
                
                
                if(exitVal==0){
                    logger.info("From SendFaxJob:Fax "+fileNamesWithPath +" sent successfully to the Fax Server");
                }else{
                    logger.info("From SendFaxJob:Fax "+fileNamesWithPath +" failed");
                    logger.info("Possible reasons:");
                    logger.info("HylaFAX client is not installed or 'sendfax' is not present in PATH");
                }          
            
            for(int index=0;index < fileList.size();index++){
                String fileNameWithPath=(String)fileList.get(index);
                File fileToFax=new File(fileNameWithPath);
                if(fileToFax.exists()){
                    fileToFax.delete();                
                }  	
            }
                      
                
                    
        } catch (Exception e){
                logger.error(e);
                throw e;
            } 
    } // end func sendFax

    
}// end Class sendFaxJob

    
    class StreamHandler extends Thread{
        private static String TYPE_ERROR="ERROR";
        private static String TYPE_OUTPUT="OUTPUT";
        InputStream is;
        String type;
        private Logger logger=Logger.getLogger("DbsLogger");
        StreamHandler(InputStream is, String type){
            this.is = is;
            this.type = type;
        }
    
        public void run(){
            try{
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line=null;
                while ( (line = br.readLine()) != null){
                    logger.info(type + ">" + line);  
                }
            }catch (IOException ioe){
                logger.error(ioe);
            }
        }
    }


