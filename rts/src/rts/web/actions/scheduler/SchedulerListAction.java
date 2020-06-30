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
 * $Id: SchedulerListAction.java,v 1.4 2005/10/04 06:35:04 suved Exp $
 *****************************************************************************
 */ 
package rts.web.actions.scheduler;

import rts.beans.*;
import rts.web.beans.scheduler.SchedulerConstants;
import rts.web.beans.user.*;
import rts.web.actionforms.scheduler.*;
import rts.web.beans.scheduler.*;
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
 *	Purpose:            To list jobs currently in scheduler.
 *  @author             Mishra Maneesh 
 *  @version            1.0
 * 	Date of creation:   25-07-2005     
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class SchedulerListAction extends Action {
  private static String DATE_FORMAT="MM/dd/yyyy HH:mm:ss"; //"MM/dd/yyyy HH:mm:ss z"; //also present in JobListBean
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
    logger.info("Fetching Job List ...");
    UserInfo userInfo = null;
    HttpSession httpSession = null;        
    Scheduler jobScheduler=null;
    int numRecords=0;
    int pageNumber=1;
    int pageCount=0;
    String searchByUserName=null;
    String searchByJobType="ALL";
    String createFromDate=null;
    Date dCreateFromDate=null;
    String createToDate=null;
    Date dCreateToDate=null;
    String dispatchFromDate=null;
    Date dDispatchFromDate=null;
    String dispatchToDate=null;
    Date dDispatchToDate=null;
    Locale locale = getLocale(request);
    ArrayList jobs=new ArrayList();
    String retrial_count="0";
    // Validate the request parameters specified by the user
    SchedulerListForm schedulerForm=null;    
    ActionErrors errors = new ActionErrors();
    try{        
      logger.debug("Initializing Variable ...");        
      httpSession = request.getSession(false);
      userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
      /* lookUp users facilitated only for admin or system admin */
      if(userInfo.isSystemAdmin() || userInfo.isAdmin()){
        request.setAttribute("isUserDisabled","false");
      }else{            
        httpSession.setAttribute("txtSearchByUserName",userInfo.getUserID());                 
        request.setAttribute("isUserDisabled","true");
      }
      /* obtain display specific data */
      UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");        
      numRecords= userPreferences.getRecordsPerPage();
      if(httpSession.getAttribute("pageNumber")!=null){
        pageNumber=Integer.parseInt(httpSession.getAttribute("pageNumber").toString());
        httpSession.removeAttribute("pageNumber");
      }
      /* actions messages */
      if(httpSession.getAttribute("messages")!=null){
        logger.debug("Saving action message in request stream");
        saveMessages(request,(ActionMessages)httpSession.getAttribute("messages"));
        httpSession.removeAttribute("messages");
      } 
      /* error messages */
      if(httpSession.getAttribute("errors")!=null){
        logger.debug("Saving action errors in request stream");
        saveErrors(request,(ActionErrors)httpSession.getAttribute("errors"));
        httpSession.removeAttribute("errors");
      }
      /* scheduler form to be populated */ 
      schedulerForm=new SchedulerListForm();
      dbsLibrarySession = userInfo.getDbsLibrarySession();
      /* obtain servlet context */
      ServletContext context=httpSession.getServletContext();
      /* obtain scheduler from it */
      jobScheduler=(Scheduler)context.getAttribute("scheduler");
      ActionMessages messages = new ActionMessages();
      ActionMessage msg =null;
      
      if(jobScheduler.isShutdown()){ // if scheduler shut down 
        schedulerForm.setIsSchedulerStopped("true"); 
        schedulerForm.setJobType(SchedulerConstants.ALL_JOBS);   
        msg = new ActionMessage("scheduler.start");
        messages.add("message1", msg);
        saveMessages(request,messages);
      }else{    // if scheduler up, obtain data
        schedulerForm.setIsSchedulerStopped("false");
        // search job by user name option
        if(httpSession.getAttribute("txtSearchByUserName")!=null){
          searchByUserName=(String)httpSession.getAttribute("txtSearchByUserName");
          httpSession.removeAttribute("txtSearchByUserName");
          schedulerForm.setTxtSearchByUserName(searchByUserName);
        }
        // search job by create from date option
        if(httpSession.getAttribute("txtCreateFromDate")!=null){
          createFromDate=(String)httpSession.getAttribute("txtCreateFromDate");
          dCreateFromDate=DateHelper.parse(createFromDate,DATE_FORMAT);
          httpSession.removeAttribute("txtCreateFromDate");
          schedulerForm.setTxtCreateFromDate(createFromDate);
        }
        // search job by create to date option
        if(httpSession.getAttribute("txtCreateToDate")!=null){
          createToDate=(String)httpSession.getAttribute("txtCreateToDate");
          dCreateToDate=DateHelper.parse(createToDate,DATE_FORMAT);
          httpSession.removeAttribute("txtCreateToDate");
          schedulerForm.setTxtCreateToDate(createToDate);
        }
        // search job by dispatch from date option
        if(httpSession.getAttribute("txtDispatchFromDate")!=null){
          dispatchFromDate=(String)httpSession.getAttribute("txtDispatchFromDate");
          dDispatchFromDate=DateHelper.parse(dispatchFromDate,DATE_FORMAT);
          httpSession.removeAttribute("txtDispatchFromDate");
          schedulerForm.setTxtDispatchFromDate(dispatchFromDate);
        }
        // search job by dispatch to date option
        if(httpSession.getAttribute("txtDispatchToDate")!=null){
          dispatchToDate=(String)httpSession.getAttribute("txtDispatchToDate");
          dDispatchToDate=DateHelper.parse(dispatchToDate,DATE_FORMAT);
          httpSession.removeAttribute("txtDispatchToDate");
          schedulerForm.setTxtDispatchToDate(dispatchToDate);
        }
        /* added by rajan on 14/07/2005 for jobTypeSearch starts here */
        if(httpSession.getAttribute("cboSearchByJobType")!=null){
          searchByJobType=(String)httpSession.getAttribute("cboSearchByJobType");
          httpSession.removeAttribute("cboSearchByJobType");
          
          schedulerForm.setCboSearchByJobType(new String [] {searchByJobType});
          schedulerForm.setJobType(schedulerForm.getCboSearchByJobType()[0]);
        }else{
          schedulerForm.setJobType(SchedulerConstants.ALL_JOBS);
        }
        /* added by rajan on 14/07/2005 for jobTypeSearch ends here */
        schedulerForm.setTimezone(TimeZone.getDefault().getID());
        //findJobs(Scheduler scheduler,String[] orderColumns,String creatorName,String jobType,Date fromCreateTime,Date toCreateTime,Date fromExecutionTime,Date toExecutionTime){
        logger.debug("Time when search started "+new Date());
        // search jobs to be displayed
        SearchJobs searchJobs=new SearchJobs();
        jobs=searchJobs.findJobs(jobScheduler,new String[]{"executionTime"}/*null*/,searchByUserName,searchByJobType,dCreateFromDate,dCreateToDate,dDispatchFromDate,dDispatchToDate,pageNumber,numRecords);
        pageCount=searchJobs.pageCount;
        if(pageNumber>pageCount){
          pageNumber=pageCount;
        }
      }
      
      /* commented by rajan on 14/07/2005 for jobTypeSearch starts here */
      /*if(httpSession.getAttribute("cboSearchByJobType")!=null){
        searchByJobType=(String)httpSession.getAttribute("cboSearchByJobType");
        httpSession.removeAttribute("cboSearchByJobType");
        schedulerForm.setCboSearchByJobType(new String[]{searchByJobType});
        logger.debug("schedulerForm.getCboSearchByJobType().length: "+schedulerForm.getCboSearchByJobType().length);
        for(int i=0;i<schedulerForm.getCboSearchByJobType().length;i++)
          logger.debug("schedulerForm.getCboSearchByJobType()["+i+"]: "+schedulerForm.getCboSearchByJobType()[i]);
        schedulerForm.setJobType(schedulerForm.getCboSearchByJobType()[0]);
      }else{
          schedulerForm.setCboSearchByJobType(new String[]{SchedulerConstants.ALL_JOBS});
          //logger.debug("schedulerForm.getCboSearchByJobType(): "+schedulerForm.getCboSearchByJobType());
          logger.debug("schedulerForm.getCboSearchByJobType().length: "+schedulerForm.getCboSearchByJobType().length);
          for(int i=0;i<schedulerForm.getCboSearchByJobType().length;i++)
            logger.debug("schedulerForm.getCboSearchByJobType()["+i+"]: "+schedulerForm.getCboSearchByJobType()[i]);
          schedulerForm.setJobType(SchedulerConstants.ALL_JOBS);                    
      }*/

      
      schedulerForm.setTxtPageCount(new String().valueOf(pageCount));
      logger.debug("pageCount : " + pageCount);                        
      schedulerForm.setTxtPageNo(new String().valueOf(pageNumber));
      logger.debug("pageNumber : " + pageNumber);             
      request.setAttribute("jobs",jobs);
      request.setAttribute("SchedulerListForm",schedulerForm);
      logger.debug(jobs);            
    }catch(Exception exception){
      logger.info("Fetching Job List Aborted");          
      ActionError editError=new ActionError("errors.catchall",exception);
      errors.add(ActionErrors.GLOBAL_ERROR,editError);
    }
    if(!errors.isEmpty()) {      
      saveErrors(request, errors);
      return (mapping.getInputForward());
    }
    logger.info("Number of records fetched : "+jobs.size());
    logger.info("Fetching Job List Complete");
    
    return mapping.findForward("success");
  }
}
