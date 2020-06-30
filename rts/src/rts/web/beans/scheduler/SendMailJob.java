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
 * $Id: SendMailJob.java,v 1.3 2005/07/07 07:20:23 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.scheduler;
/* Java API */
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.*;
/* mail API */
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/* Struts API */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.*;

/**
 *	Purpose: This class does the job of sending e-mails with the configured content
 *           to the configured recipient.It implements the org.quartz.Job interface
 *           and overrides its execute() method.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SendMailJob implements Job {
    
   
    Logger logger= Logger.getLogger("DbsLogger"); 
    JobDataMap data=null;
    Scheduler sched=null;
    String smtpHost=null;
    JobDetail jobDetail=null;
    public String authenticate;
    public String SMTP_AUTH_USER;
    public String SMTP_AUTH_PWD;
    /**
     *	Purpose: To override execute method of org.quartz.Job interface and 
     *           provide send mail functionality
     *  @param   context - JobExecutionContext associated with this Job.The context
     *           provides all the necessary information to execute this job.
     */
    public void execute(JobExecutionContext context)
            throws JobExecutionException {     
            String mailDesc=null;
        try {
            sched=(Scheduler)context.getScheduler();
            logger.debug("Acquired Scheduler");
            jobDetail=context.getJobDetail();
            logger.debug("Acquired JobDetail");
            data = context.getJobDetail().getJobDataMap();
            logger.debug("Acquired JobDataMap");
            logger.info(context.getJobDetail().getFullName()+" Executing");
            smtpHost = sched.getContext().getString("smtp-host");
            
            /* obtaining parameters for authentication */
            authenticate = sched.getContext().getString("authenticate");
            logger.debug("authenticate: "+authenticate);
            if(authenticate.equalsIgnoreCase("true")){
              SMTP_AUTH_USER = sched.getContext().getString("smtp-userId");
              SMTP_AUTH_PWD = sched.getContext().getString("smtp-password");
            }
            String to = data.getString(SchedulerConstants.PROP_RECIPIENT);
            String subject = data.getString(SchedulerConstants.PROP_SUBJECT);
            mailDesc = "'" + subject + "' to: " + to;
            String cc = data.getString(SchedulerConstants.PROP_CC_RECIPIENT);
            String bcc = data.getString(SchedulerConstants.PROP_BCC_RECIPIENT);
            String from = data.getString(SchedulerConstants.PROP_SENDER);
            String replyTo = data.getString(SchedulerConstants.PROP_REPLY_TO);            
            String message = data.getString(SchedulerConstants.PROP_MESSAGE);
            ArrayList attachments=(ArrayList)data.get(SchedulerConstants.PROP_ATTACHMENTS);
        
            if (smtpHost == null || smtpHost.trim().length() == 0)
                    throw new IllegalArgumentException(
                            "PROP_SMTP_HOST not specified.");
            if (to == null || to.trim().length() == 0)
                    throw new IllegalArgumentException(
                            "PROP_RECIPIENT not specified.");
            if (from == null || from.trim().length() == 0)
                    throw new IllegalArgumentException("PROP_SENDER not specified.");
            /*if (subject == null || subject.trim().length() == 0)
                    throw new IllegalArgumentException(
                            "PROP_SUBJECT not specified.");
            if (message == null || message.trim().length() == 0)
                    throw new IllegalArgumentException(
                            "PROP_MESSAGE not specified.");
            if (attachments == null || attachments.size() == 0)
                    throw new IllegalArgumentException(
                            "PROP_ATTACHMENTS not specified."); */

            if (cc != null && cc.trim().length() == 0) cc = null;
            if (bcc != null && bcc.trim().length() == 0) bcc = null;
            if (replyTo != null && replyTo.trim().length() == 0) replyTo = null;           

            logger.info("Sending message " + mailDesc);

            //int b=1/0;
            boolean success=sendMail(smtpHost, to, cc,bcc, from, replyTo, subject, message,attachments);
            if(success){                
                logger.info("Mail sent successfully");                
            }else{
               logger.info("Unable to send mail: " + mailDesc); 
            }
        }catch (Exception e) {
            logger.error(e.toString());
            //System.out.println("1");
          try{
             if(data!=null){
                    data.put(SchedulerConstants.PROP_SMTP_HOST,smtpHost);
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
                    JobDetail mailJobDetail=new JobDetail(jobName,SchedulerConstants.MAIL_JOB,SendMailJob.class,false,true,true);
                         // jobDetail.setJobDataMap(data);
                    mailJobDetail.setJobDataMap(data);
                    Date dateOfSubmission=(Date)data.get(SchedulerConstants.CREATE_TIME);
                    logger.info("Job Create Time"+dateOfSubmission);
                    String creator=(String)data.get(SchedulerConstants.JOB_CREATOR);
                    String namePrefix=creator+DateHelper.format(newStartTime,"HH:mm:ss--yyyy-MM-dd-z");            
                    String newTriggerName=SchedulerConstants.TRIGGER_NAME_SUFFIX+namePrefix;
                    SimpleTrigger newTrigger=null;
                    if(newStartTime.compareTo(new Date())<=0){
                         newTrigger=new SimpleTrigger(newTriggerName,SchedulerConstants.MAIL_JOB);
                    }else{                   
                         newTrigger=new SimpleTrigger(newTriggerName,SchedulerConstants.MAIL_JOB,newStartTime);               
                    }
                    Trigger trigger[] = sched.getTriggersOfJob(jobName,SchedulerConstants.MAIL_JOB);
                    mailJobDetail.validate();
                    if(trigger==null || trigger.length==0){
                          sched.scheduleJob(mailJobDetail,newTrigger);
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
           // throw new JobExecutionException("Unable to send mail: " + mailDesc,
                   // e, false);
        }
    }

   

    /**
     *	Purpose: Sends the mail.
     *           
     *  @param   smtpHost - The SMTP server IP/Domain name.
     *  @param   to - Email address of the recipient of mail.
     *  @param   cc - Email address of the recipient who will receive a carbon copy of mail.
     *  @param   from - Email address of the sender of mail.
     *  @param   replyTo - Email address of the person to whom reply should be forwarded to.
     *  @param   subject - Subject of the message.
     *  @param   message - The message written in plane text.
     *  @param   attachments - A list of all the attachments.                 
     */
    private boolean sendMail(String smtpHost, String to, String cc,String bcc, String from,
            String replyTo, String subject, String message,ArrayList attachments)throws Exception{
            boolean success=false;
            try{            
                MimeMessage mimeMessage = prepareMimeMessage(smtpHost, to, cc,bcc, from,
                replyTo, subject, message,attachments); 
                if(mimeMessage!=null){
                    Transport.send(mimeMessage);
                    success=true;
                }
            }catch(Exception e){
                logger.error(e);
                throw e;
            }
            return success;
    }

    /**
     *	Purpose: Creates a mime message with the parameters passed.This mime message 
     *           contains all the required properties for the mail. 
     *           
     *  @param   smtpHost - The SMTP server IP/Domain name.
     *  @param   to - Email address of the recipient of mail.
     *  @param   cc - Email address of the recipient who will receive a carbon copy of mail.
     *  @param   from - Email address of the sender of mail.
     *  @param   replyTo - Email address of the person to whom reply should be forwarded to.
     *  @param   subject - Subject of the message.
     *  @param   message - The message written in plane text.
     *  @param   attachments - A list of all the attachments.
     *  @return  Mime message which is actually sent.                 
     */
    private MimeMessage prepareMimeMessage(String smtpHost, String to,
            String cc,String bcc, String from, String replyTo, String subject,String message,ArrayList attachments) throws Exception
     {
        MimeMessage mimeMessage =null;
      try{
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        logger.info("SMTP Host="+smtpHost);
        Session session = null;
       if(authenticate.equalsIgnoreCase("true")){
          properties.put("mail.smtp.auth", "true");
          Authenticator auth = new SMTPAuthenticator();
          session = Session.getDefaultInstance(properties, auth);
       }else{
          session = Session.getDefaultInstance(properties, null);
       }
        
        mimeMessage = new MimeMessage(session);        
        Address[] toAddresses = InternetAddress.parse(to);
        mimeMessage.setRecipients(Message.RecipientType.TO, toAddresses);
        if (cc != null) {
            Address[] ccAddresses = InternetAddress.parse(cc);
            mimeMessage.setRecipients(Message.RecipientType.CC, ccAddresses);
        }

        if (bcc != null) {
            Address[] bccAddresses = InternetAddress.parse(bcc);
            mimeMessage.setRecipients(Message.RecipientType.BCC, bccAddresses);
        }

        mimeMessage.setFrom(new InternetAddress(from));
        if (replyTo != null)
                mimeMessage.setReplyTo(new InternetAddress[]{new InternetAddress(
                                replyTo)});
        mimeMessage.setSubject(subject);
        
        mimeMessage.setSentDate(new Date());
        
        MimeBodyPart messageText = new MimeBodyPart();        

		messageText.setText(message);
        
        Multipart mimeMultiPart=new MimeMultipart();
        mimeMultiPart.addBodyPart(messageText);
        attachments.trimToSize();
        int numAttachments=attachments.size();
       
        if(numAttachments > 0){ 
            logger.info("Processing attachments");
            for(int index = 0; index < numAttachments; index++){
                FileByteArray fileBytes=(FileByteArray)attachments.get(index);
            
                ByteArrayDataSource bds = new ByteArrayDataSource(fileBytes.getFileBytes(),fileBytes.getMimeType(),fileBytes.getFileName());
           
                MimeBodyPart attachment = new MimeBodyPart(); 
            
                attachment.setDataHandler(new DataHandler(bds));
                    
                attachment.setFileName(bds.getName());
                      
                mimeMultiPart.addBodyPart(attachment);
                      
            }
            logger.info("Attachment process complete");
        }
        mimeMessage.setContent(mimeMultiPart);        
        }catch(Exception e){
            logger.error(e);
            throw e;
        }
        return mimeMessage;
    }
    
    /**
* SimpleAuthenticator is used to do simple authentication
* when the SMTP server requires it.
*/
private class SMTPAuthenticator extends javax.mail.Authenticator
{

    public PasswordAuthentication getPasswordAuthentication()
    {
        String username = SMTP_AUTH_USER;
        String password = SMTP_AUTH_PWD;
        return new PasswordAuthentication(username, password);
    }
}

}
