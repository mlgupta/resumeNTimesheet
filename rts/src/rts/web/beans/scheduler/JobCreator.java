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
 * $Id: JobCreator.java,v 1.1.1.1 2005/05/26 12:32:46 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.scheduler;
/* Java API */ 
import java.util.*;
/* Quartz API */
import org.quartz.JobDataMap;
/**
 *	Purpose: This class is used to create JobDataMap for MAIL and FAX jobs.Each job has its
 *           its own JobDataMap which contains properties as to,from,cc,bcc,attachments in case of
 *           MAIL jobs and fax no. ,creator and file to fax in case of FAX jobs.
 *           
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   10-03-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class JobCreator 
{
   /**
     *	Purpose: To create JobDataMap fro MAIL jobs.
     *  @param   to - 'to' address/addresses for the email.
     *  @param   from - 'from' address/addresses for the email.     *  
     *  @param   cc - 'cc' address/addresses for the email.
     *  @param   bcc - 'bcc' address/addresses for the email.
     *  @param   subject - 'subject' for the email.
     *  @param   message - the message text for the email.
     *  @param   creator - To creator of email.
     *  @param   smtpHost - Host on which the SMTP server is running.
     *  @param   startTime - Time when the mail is scheduled to be delivered.
     *  @param   timezone - Timezone for the time.
     *  @param   fileList - An arraylist of attachments.
     */
   public JobDataMap createMailData(String to,String from,String cc,String bcc,String subject,String message,String creator,String smtpHost, Date startTime,String timezone,String retrial_count,String retrial_interval,String max_count,String error_mesg,ArrayList fileList)
    {   
        JobDataMap mailData=new JobDataMap();
        mailData.put(SchedulerConstants.JOB_CREATOR,creator);
        mailData.put(SchedulerConstants.JOB_TYPE,SchedulerConstants.MAIL_JOB);
        mailData.put(SchedulerConstants.EXECUTION_TIME,startTime);
        mailData.put(SchedulerConstants.CREATE_TIME,new Date());
        mailData.put(SchedulerConstants.TIMEZONE,timezone);
        mailData.put(SchedulerConstants.PROP_SMTP_HOST,smtpHost);
        mailData.put(SchedulerConstants.PROP_RECIPIENT,to);
        mailData.put(SchedulerConstants.PROP_CC_RECIPIENT,cc);
        mailData.put(SchedulerConstants.PROP_BCC_RECIPIENT,bcc);
        mailData.put(SchedulerConstants.PROP_SENDER,from);
        mailData.put(SchedulerConstants.PROP_SUBJECT,subject);
        mailData.put(SchedulerConstants.PROP_MESSAGE,message);
        mailData.put(SchedulerConstants.RETRIAL_COUNT,retrial_count);
        mailData.put(SchedulerConstants.RETRIAL_INTERVAL,retrial_interval);
        mailData.put(SchedulerConstants.MAX_COUNT,max_count);
        mailData.put(SchedulerConstants.ERROR_MESG,error_mesg);
        mailData.put(SchedulerConstants.PROP_ATTACHMENTS,fileList);        
        return mailData;
    }

    public JobDataMap createPrintData()
    {
        return null;
    }

    /**
     *	Purpose: To create JobDataMap fro FAX jobs.
     *  @param   startTime - Time when the mail is scheduled to be delivered.
     *  @param   timezone - Timezone for the time.
     *  @param   fileList - An arraylist of files to be faxed in form of byte array.
     */
    public JobDataMap createFaxData(String to,String from,String companyName, String faxNo,String comments,Date startTime,String timezone,String creator,String faxTempDir,String retrial_count,String retrial_interval,String max_count,String error_mesg,ArrayList fileList)
    {
        JobDataMap faxData=new JobDataMap();
        faxData.put(SchedulerConstants.PROP_SENDER,from);
        faxData.put(SchedulerConstants.JOB_CREATOR,creator);
        faxData.put(SchedulerConstants.JOB_TYPE,SchedulerConstants.FAX_JOB);
        faxData.put(SchedulerConstants.EXECUTION_TIME,startTime);
        faxData.put(SchedulerConstants.CREATE_TIME,new Date());
        faxData.put(SchedulerConstants.TIMEZONE,timezone);
        faxData.put(SchedulerConstants.PROP_FAX_TO,to);     
        faxData.put(SchedulerConstants.PROP_FAX_NUMBER,faxNo);
        faxData.put(SchedulerConstants.COMPANY_NAME,companyName);
        faxData.put(SchedulerConstants.PROP_FAX_COMMENTS,comments);
        faxData.put(SchedulerConstants.FAX_TEMP_DIR,faxTempDir);
        faxData.put(SchedulerConstants.RETRIAL_COUNT,retrial_count);
        faxData.put(SchedulerConstants.PROP_ATTACHMENTS,fileList);        
        return faxData;
    }
    
}
