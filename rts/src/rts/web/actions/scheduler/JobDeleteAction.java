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
 * $Id: JobDeleteAction.java,v 1.2 2005/10/04 06:11:48 suved Exp $
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
 *	Purpose:            To delete jobs.
 *  @author             Mishra Maneesh 
 *  @version            1.0
 * 	Date of creation:   25-07-2005     
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class JobDeleteAction extends Action {
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
  Logger logger = Logger.getLogger("DbsLogger");
  Locale locale = getLocale(request);
  UserInfo userInfo = null;
  HttpSession httpSession = null;
  RescheduleForm rescheduleForm=new RescheduleForm();
  Scheduler jobScheduler=null;
  Date createDate = null;
  String creator=null;
  JobDataMap jobData=null;
  ActionErrors errors = new ActionErrors();
  try{
    logger.debug("Initializing Variable ...");        
    httpSession = request.getSession(false); 
    userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
    dbsLibrarySession = userInfo.getDbsLibrarySession();
    /* obtain servlet context */
    ServletContext context=httpSession.getServletContext();
    /* obtain scheduler from it */
    if(context.getAttribute("scheduler")!=null){
        jobScheduler=(Scheduler)context.getAttribute("scheduler");
    }else{
        
    }
    /* obtain job info */
    String jobWithGroupName= (String)httpSession.getAttribute("radSelect");                        
    String[] jobInf=new String(jobWithGroupName).split(" ");
    /* delete the job */
    jobScheduler.deleteJob(jobInf[1],jobInf[0]);
    ActionMessages messages = new ActionMessages();
    ActionMessage msg = new ActionMessage("job.delete.ok",jobInf[1],jobInf[0]);
    messages.add("message1", msg);
    httpSession.setAttribute("messages",messages);
    
    }catch(Exception exception){
      logger.info(exception);          
      ActionError editError=new ActionError("errors.catchall",exception);
      errors.add(ActionErrors.GLOBAL_ERROR,editError);
    }
  if(!errors.isEmpty()){
      httpSession.setAttribute("errors",errors);
      return mapping.findForward("success");
  }  
  
  return mapping.findForward("success");
}
}
