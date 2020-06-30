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
 * $Id: JobRescheduleB4Action.java,v 1.1.1.1 2005/05/26 12:32:37 suved Exp $
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

public class JobRescheduleB4Action extends Action {

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
        MessageResources messages = getResources(request);
        UserInfo userInfo = null;
        HttpSession httpSession = null;
        RescheduleForm rescheduleForm=new RescheduleForm();
        Scheduler jobScheduler=null;
        Date createDate = null;
        Date executionDate = null;
        String creator=null;
        JobDataMap jobData=null;
        String timezone=null;
        ActionErrors errors = new ActionErrors();
        try{
            logger.debug("Initializing Variable ...");        
            httpSession = request.getSession(false); 
            userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
            dbsLibrarySession = userInfo.getDbsLibrarySession();
            ServletContext context=httpSession.getServletContext();
            if(context.getAttribute("scheduler")!=null){
                jobScheduler=(Scheduler)context.getAttribute("scheduler");
            }else{
                
            }
            String jobWithGroupName= (String)httpSession.getAttribute("radSelect");    
            //System.out.println("hello1");
            String[] jobInf=new String(jobWithGroupName).split(" ");            
            //System.out.println("hello2");
            //System.out.println(jobInf[1]);
            //System.out.println(jobInf[0]);
            Trigger trigger[] = jobScheduler.getTriggersOfJob(jobInf[1],jobInf[0]);
            //System.out.println("hello3");
            if(trigger==null || trigger.length==0){
                ActionError editError=new ActionError("job.dispatch.reschedule");
                //System.out.println("hello4");
                errors.add(ActionErrors.GLOBAL_ERROR,editError);
                saveErrors(request, errors);
            return (mapping.getInputForward());
            }
            JobDetail jobDetail=jobScheduler.getJobDetail(jobInf[1],jobInf[0]);
            //System.out.println("hello5");
            jobData=jobDetail.getJobDataMap();
            createDate=(Date)jobData.get(SchedulerConstants.CREATE_TIME);
            executionDate=trigger[0].getStartTime();
            //timezone=jobData.getString(SchedulerConstants.TIMEZONE);
            //executionDate=DateHelper.convertToTimezone(executionDate,TimeZone.getTimeZone(timezone));
            creator=jobData.getString(SchedulerConstants.JOB_CREATOR);
            rescheduleForm.setTriggerName(trigger[0].getName());
            rescheduleForm.setTriggerType(trigger[0].getGroup());
            rescheduleForm.setTxtJobName(jobInf[1]);
            rescheduleForm.setTxtJobType(jobInf[0]);
            rescheduleForm.setTxtUser(creator);            
            rescheduleForm.setTxtCreateDate(DateHelper.format(createDate,DATE_FORMAT));
            rescheduleForm.setYear(new String().valueOf(DateHelper.getYear(executionDate)));
            rescheduleForm.setMonth(new String().valueOf(DateHelper.getMonth(executionDate)));
            rescheduleForm.setDay(new String().valueOf(DateHelper.getDay(executionDate)));
            rescheduleForm.setHours(new String().valueOf(DateHelper.getHour(executionDate)));
            rescheduleForm.setMinutes(new String().valueOf(DateHelper.getMinute(executionDate)));
            rescheduleForm.setSeconds(new String().valueOf(DateHelper.getSecond(executionDate)));
            rescheduleForm.setTimezone(TimeZone.getDefault().getID());
            
            request.setAttribute("RescheduleForm",rescheduleForm);
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
