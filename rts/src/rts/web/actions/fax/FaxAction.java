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
 * $Id: FaxAction.java,v 1.4 2005/07/15 10:48:40 rajan Exp $
 *****************************************************************************
 */
package rts.web.actions.fax;


/**
 *	Purpose: To send mails.
 *  @author              Mishra Maneesh 
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
import rts.beans.*;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;
import rts.web.beans.scheduler.*;

import rts.web.actionforms.fax.FaxForm;

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
import org.apache.struts.validator.*;

import org.quartz.*;

public class FaxAction extends Action {
    private static String DATE_FORMAT="MM/dd/yyyy HH:mm:ss";
    DbsLibrarySession dbsLibrarySession = null;
    //Initialize logger
    Logger logger = Logger.getLogger("DbsLogger");
    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Locale locale = getLocale(request);
        UserInfo userInfo = null;
        HttpSession httpSession = null;
        DbsDirectoryUser sender=null;
        String creator=null;
        String senderEmailAddress=null;
        String txtTo=null;
        String txtCompanyName=null;
        String txtFaxNumber=null;
        String txaComments=null;        
        String txtSendTime=null;
        String[] lstAttachment;        
        int day;
        int month;
        int year;
        int hours;
        int minutes;
        int seconds;
        String timezone; 
        String jobRetrialCount="0";
        String errorMesg="";
        String jobMaxCount=null;
        String jobRetrialInterval=null;
        String jobErrorMessage="NA";
        ActionErrors errors = new ActionErrors();
        ArrayList attachments=new ArrayList();
        try{
            logger.debug("Initializing Variable ...");        
            httpSession = request.getSession(false);
            userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
            dbsLibrarySession = userInfo.getDbsLibrarySession(); 
            DbsFileSystem fileSystem=new DbsFileSystem(dbsLibrarySession);
            DbsExtendedUserProfile emailProfile=SearchUtil.getEmailUserProfile(dbsLibrarySession,dbsLibrarySession.getUser());
            creator=dbsLibrarySession.getUser().getName();
            if(emailProfile!=null){
                DbsAttributeValue mailAttr=emailProfile.getAttribute("EMAILADDRESS");
                if(mailAttr!=null){  
                    senderEmailAddress= mailAttr.getString(dbsLibrarySession);
                }
            }
                    ServletContext context = httpSession.getServletContext();                    
                    Scheduler sched=(Scheduler)context.getAttribute("scheduler");
                    String faxTempDir=(String)context.getAttribute("faxtempdir");
                    if(sched.isShutdown())
                    {
                        ActionError mailError=new ActionError("scheduler.stopped");
                        errors.add(ActionErrors.GLOBAL_ERROR,mailError); 
                    }else{
                        JobScheduler jobSched=new JobScheduler(sched);            
                
                        txtTo=((String)PropertyUtils.getSimpleProperty(form, "txtTo")).trim();
                        txtCompanyName=((String)PropertyUtils.getSimpleProperty(form, "txtCompanyName")).trim();            
                        txtFaxNumber=((String)PropertyUtils.getSimpleProperty(form, "txtFaxNumber")).trim();
                        txaComments=((String)PropertyUtils.getSimpleProperty(form, "txaComments")).trim();                       
                        logger.debug("Day is"+((String)PropertyUtils.getSimpleProperty(form, "day")).trim());
                        day=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "day")).trim());
                        month=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "month")).trim());
                        year=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "year")).trim());
                        hours=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "hours")).trim());
                        minutes=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "minutes")).trim());
                        seconds=Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "seconds")).trim());
                        timezone=((String)PropertyUtils.getSimpleProperty(form, "timezone")).trim();
                        
          
                        lstAttachment=((String[])PropertyUtils.getSimpleProperty(form, "lstAttachment"));
                        FileByteArray[] fileArr=null;
                        if(lstAttachment!=null){
                            fileArr=new FileByteArray[lstAttachment.length];
                            for(int index = 0 ; index < lstAttachment.length ; index++){
                                fileArr[index]=new FileByteArray();
                                DbsPublicObject attachment=fileSystem.findPublicObjectByPath(lstAttachment[index]).getResolvedPublicObject();
                                //DbsPublicObject dbsDocument=fileSystem.findPublicObjectByPath(lstAttachment[index]);
                                //Long documentId=dbsDocument.getId();
                                //DbsPublicObject attachment = dbsLibrarySession.getPublicObject(documentId).getResolvedPublicObject();
                                
                                InputStream fileInputStream=((DbsDocument)attachment).getContentStream();
                                int fileSize=(int)((DbsDocument)attachment).getContentSize();
                                byte[] fileBytes=new byte[fileSize];
                                fileInputStream.read(fileBytes,0,fileSize);
                                String fileName=((DbsDocument)attachment).getName();
                                String mimeType=((DbsDocument)attachment).getFormat().getMimeType();
                                fileArr[index].setFileBytes(fileBytes);
                                fileArr[index].setFileName(fileName);
                                fileArr[index].setMimeType(mimeType);
                                attachments.add(fileArr[index]);
                                fileInputStream.close();
                            }
                        }
                        if(senderEmailAddress==null){
                            senderEmailAddress=dbsLibrarySession.getUser().getName();
                        }
                        JobCreator jc = new JobCreator();
                        //Date startTime=DateHelper.parse(txtSendTime,DATE_FORMAT);
                        TimeZone userTimeZone=TimeZone.getTimeZone(timezone);
                        Date startTime=DateHelper.getDate(year,month,day,hours,minutes,seconds,userTimeZone);
                        /*if(startTime.compareTo(new Date())<=0){
                            startTime=null;
                        }*/
                        JobDataMap jobData=jc.createFaxData(txtTo,senderEmailAddress,txtCompanyName,txtFaxNumber,txaComments,startTime,timezone,dbsLibrarySession.getUser().getName(),faxTempDir,jobRetrialCount,jobRetrialInterval,jobMaxCount,jobErrorMessage,attachments);
                        jobSched.addJob(jobData);
                        ActionMessages messages = new ActionMessages();
                        ActionMessage msg = new ActionMessage("fax.job.scheduled.ok");
                        messages.add("message1", msg);
                        saveMessages(request,messages);
                    }           
                
            
            
            }catch(DbsException dbsException){
                logger.error(dbsException);
                ActionError editError=new ActionError("errors.catchall",dbsException.getErrorMessage());
                errors.add(ActionErrors.GLOBAL_ERROR,editError);
            }catch(Exception exception){
                logger.error(exception);
                ActionError editError=new ActionError("errors.catchall",exception);
                errors.add(ActionErrors.GLOBAL_ERROR,editError);
            }
            if (!errors.isEmpty()) {
                saveErrors(request, errors);
                return (mapping.getInputForward());
            }
        return mapping.findForward("success");
    }
}
