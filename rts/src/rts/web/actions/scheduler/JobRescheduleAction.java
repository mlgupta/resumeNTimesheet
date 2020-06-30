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
 * $Id: JobRescheduleAction.java,v 1.2 2005/10/04 06:19:24 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.scheduler;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.scheduler.*;
import rts.web.beans.scheduler.*;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;
//Java API
import java.io.*;
import java.util.*;
//Servlet API
import javax.servlet.*;
import javax.servlet.http.*;
//Struts API
import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.quartz.*;

/**
 *	Purpose:            To reschedule jobs.
 *  @author             Mishra Maneesh 
 *  @version            1.0
 * 	Date of creation:   25-07-2005     
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class JobRescheduleAction extends Action {

  private static String DATE_FORMAT="MM/dd/yyyy HH:mm:ss"; //also present in JobListBean    
  DbsLibraryService dbsLibraryService = null;
  DbsCleartextCredential dbsCleartextCredential = null;
  DbsLibrarySession dbsLibrarySession = null;
 
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {       
    //Initialize logger
    Logger logger = Logger.getLogger("DbsLogger");
    
    Locale locale = getLocale(request);       
    UserInfo userInfo = null;
    HttpSession httpSession = null;       
    Scheduler jobScheduler=null;
    String creator=null;
    Trigger newTrigger=null;
    String newTriggerName=null;
    String oldTriggerName=null;
    String triggerType=null;
    String jobName=null;
    String jobType=null;
    int day;
    int month;
    int year;
    int hours;
    int minutes;
    int seconds;
    String timezone;
    ActionErrors errors = new ActionErrors();
    try{
      logger.debug("Entering JobRescheduleAction");
      httpSession = request.getSession(false);
      /* obtain form data spedifying reschedule details */
      oldTriggerName=(String)PropertyUtils.getSimpleProperty(form, "triggerName");
      triggerType=(String)PropertyUtils.getSimpleProperty(form, "triggerType");
      creator=(String)PropertyUtils.getSimpleProperty(form, "txtUser");
      jobName=(String)PropertyUtils.getSimpleProperty(form, "txtJobName");
      jobType=(String)PropertyUtils.getSimpleProperty(form, "txtJobType");
      day=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "day")).trim());
      month=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "month")).trim());
      year=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "year")).trim());
      hours=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "hours")).trim());
      minutes=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "minutes")).trim());
      seconds=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "seconds")).trim());
      timezone=((String)PropertyUtils.getSimpleProperty(form, "timezone")).trim();
      /* obtain servlet context */
      ServletContext context=httpSession.getServletContext();
      /* obtain scheduler from it */
      if(context.getAttribute("scheduler")!=null){
        jobScheduler=(Scheduler)context.getAttribute("scheduler");
      }else{
          
      }
      logger.info("Scheduler Acquired");
      TimeZone userTimeZone=TimeZone.getTimeZone(timezone);
      Date startTime=DateHelper.getDate(year,month,day,hours,minutes,seconds,userTimeZone);
      /* obtain the job required to be rescheduled */
      JobDataMap jobData=jobScheduler.getJobDetail(jobName,jobType).getJobDataMap();
      logger.info("JobData Acquired");
      Date dateOfSubmission=(Date)jobData.get(SchedulerConstants.CREATE_TIME);
      logger.info("Job Create Time"+dateOfSubmission);
     // String namePrefix=creator+DateHelper.format(dateOfSubmission,"HH:mm:ss--yyyy-MM-dd-z");            
      String namePrefix=creator+DateHelper.format(new Date(),"HH:mm:ss--yyyy-MM-dd-z"); 
      /* create new trigger begins ... */
      newTriggerName=SchedulerConstants.TRIGGER_NAME_SUFFIX+namePrefix;
      if(startTime.compareTo(new Date())<=0){
         newTrigger=new SimpleTrigger(newTriggerName,triggerType);
      }else{
         newTrigger=new SimpleTrigger(newTriggerName,triggerType,startTime);               
      }
      jobData.put(SchedulerConstants.EXECUTION_TIME,startTime);
      logger.debug(timezone);
      jobData.put(SchedulerConstants.TIMEZONE,timezone);
      JobDetail jobDetail= jobScheduler.getJobDetail(jobName,jobType);
      jobDetail.setJobDataMap(jobData);
      newTrigger.setJobGroup(jobType);
      newTrigger.setJobName(jobName);
      /* create new trigger ends ... */
      //jobScheduler.rescheduleJob(oldTriggerName,triggerType,newTrigger);
      /* delete the prev job */
      jobScheduler.deleteJob(jobName,jobType);
      /* schedule the job with new trigger */
      jobScheduler.scheduleJob(jobDetail,newTrigger);
      ActionMessages messages = new ActionMessages();
      ActionMessage msg = new ActionMessage("job.reschedule.ok",jobName,jobType);
      messages.add("message1", msg);
      httpSession.setAttribute("messages",messages);
      logger.debug("Exiting JobRescheduleAction");
    }catch(Exception exception){
      logger.info(exception);          
      ActionError editError=new ActionError("errors.catchall",exception);
      errors.add(ActionErrors.GLOBAL_ERROR,editError);
    }
    if(!errors.isEmpty()) {      
      saveErrors(request, errors);
      return (mapping.getInputForward());
    }   
    
    return mapping.findForward("success");
  }
}
