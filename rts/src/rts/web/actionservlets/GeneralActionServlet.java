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
 * $Id: GeneralActionServlet.java,v 1.12 2005/11/25 14:08:34 suved Exp $
 *****************************************************************************
 */
/*package name*/ 
package rts.web.actionservlets;
/*rts package references */
import java.text.DecimalFormat;
import rts.beans.DbsCleartextCredential;
import rts.beans.DbsDirectoryGroup;
import rts.beans.DbsDirectoryUser;
import rts.beans.DbsDocument;
import rts.beans.DbsFileSystem;
import rts.beans.DbsLibraryService;
import rts.beans.DbsLibrarySession;
import rts.beans.DbsException;
import rts.beans.DbsPublicObject;
import rts.web.beans.filesystem.FolderDoc;
import rts.web.beans.filesystem.TotalSizeFoldersDocs;
import rts.web.beans.user.*;
import rts.web.beans.utility.GeneralUtil;
import rts.web.beans.utility.ParseXMLTagUtil;
/* Java API */

import java.io.File;
import java.io.IOException; 
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;

/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.ActionServlet;

/**
 *	Purpose:  A Servlet that is used to route every request in the application.
 *            It is loaded at startup.
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   27-06-2005
 * 	Last Modfied by :    
 * 	Last Modfied Date:    
 */

/*Class declaration and definition*/
public class GeneralActionServlet extends ActionServlet{
    private static ArrayList popupActionList = new ArrayList();
    String nonAdminSecurity =null;
    String jobRetrialInterval=null;
    String jobRetrialCount=null;
    static {
        popupActionList.add("b4DocUploadAction.do");
        popupActionList.add("docHistoryDeleteAction.do");
        popupActionList.add("docHistoryDetailAction.do");
        popupActionList.add("docHistoryListAction.do");
        popupActionList.add("docHistoryRollbackAction.do");
        popupActionList.add("folderDocB4ApplyAclAction.do");
        popupActionList.add("folderDocB4CheckInAction.do");
        popupActionList.add("folderDocB4CheckOutAction.do");
        popupActionList.add("folderDocB4CopyMoveAction.do");
        popupActionList.add("folderDocB4PropertyAction.do");
        popupActionList.add("folderDocB4RenameAction.do");
        popupActionList.add("userListSelectAdminAction.do");
        popupActionList.add("groupListSelectAdminAction.do");
        popupActionList.add("aclListSelectAction.do");
        popupActionList.add("folderDocSelectB4Action.do");
        popupActionList.add("changeEncryptionPasswordB4Action.do");
        popupActionList.add("setEncryptionPasswordB4Action.do");   
        popupActionList.add("changePasswordB4Action.do");        
        popupActionList.add("themePreviewAction.do");                
        popupActionList.add("userPreferenceThemePreviewAction.do");
        popupActionList.add("setURLEncryptPasswordAction.do");
    }
    private ServletContext  context = null;
    private String prefix = null;
    private String relPath = null;
    private String sysAdUser = null;
    private String sysAdPwd = null;
    private ParseXMLTagUtil parseUtil = null;
    private DbsLibraryService sysLibService = null;
    private DbsLibrarySession sysLibSession = null;
    private DbsCleartextCredential sysctC = null;
    private DbsDirectoryUser sysDU = null;
    private String ifsService;
    private String ifsSchemaPassword;
    private String serviceConfiguration;
    private String domain;
    
    public void init(ServletConfig config) throws ServletException {
      try{
          super.init(config);
          log("Initializing Logger...");
          context = config.getServletContext();
          prefix =  context.getRealPath("/");
          relPath=prefix+"WEB-INF"+File.separator+"params_xmls"+File.separator+"GeneralActionParam.xml";
//            docLoggingAgent.generalActionParamLocation=relPath;
          parseUtil= new ParseXMLTagUtil(relPath);
          
          sysAdUser = parseUtil.getValue("sysaduser","Configuration");
          sysAdPwd = parseUtil.getValue("sysadpwd","Configuration");
          sysctC = new DbsCleartextCredential(sysAdUser,sysAdPwd);
          ifsService = parseUtil.getValue("IfsService","Configuration");  
          //ifsService = context.getInitParameter("IfsService");
          if(DbsLibraryService.isServiceStarted(ifsService)){
              log(ifsService + " is running");
              sysLibService = DbsLibraryService.findService("IfsDefaultService");
          }else{
              log("Starting Library Service...");            
              ifsSchemaPassword = parseUtil.getValue("IfsSchemaPassword","Configuration");
              serviceConfiguration = parseUtil.getValue("ServiceConfiguration","Configuration");
              domain = parseUtil.getValue("Domain","Configuration");
              log("IfsService : " + ifsService);
              log("ServiceConfiguration : " + serviceConfiguration);
              log("Domain : " + domain);
              sysLibService = DbsLibraryService.startService(ifsService,ifsSchemaPassword,serviceConfiguration,domain);
              log("Library Service Started ");
          }
          try{
              sysLibSession = sysLibService.connect(sysctC,null);
                  
          }catch(DbsException dex){
              dex.getErrorMessage();
              dex.printStackTrace();                
              if(dex.containsErrorCode(10170)){
                 throw dex;
              }
              else if(dex.getErrorCode() == 21008){
                  ifsSchemaPassword = parseUtil.getValue("IfsSchemaPassword","Configuration");
                  serviceConfiguration = parseUtil.getValue("ServiceConfiguration","Configuration");
                  domain = parseUtil.getValue("Domain","Configuration");
                  
                  
                  log("Disposing Library Service...");
                  sysLibService.dispose(ifsSchemaPassword);
                  log("Disposing Library Service Complete");
                  log("ReStarting Library Service...");            
                  sysLibService = DbsLibraryService.startService(ifsService,ifsSchemaPassword,serviceConfiguration,domain);
                  log("ReStarting Library Service Complete");
                  sysLibSession = sysLibService.connect(sysctC,null);
              }
          }
           sysLibSession.setAdministrationMode(true);
          sysDU = sysLibSession.getUser();
          context.setAttribute("sysLibSession",sysLibSession);
          
          String logFile= parseUtil.getValue("log4j-init-file","Configuration");
          nonAdminSecurity= parseUtil.getValue("nonadminsecurity","Configuration");
          String faxtempdir= parseUtil.getValue("faxtempdir","Configuration");
          jobRetrialInterval= parseUtil.getValue("jobRetrialInterval","Configuration");
          jobRetrialCount= parseUtil.getValue("jobRetrialCount","Configuration");
          
          
          /*try{
            docLoggingAgent.startAgent();
            log("LoggingAgent started: ");
          }catch(Exception ex){             
            log("Exception occurred in Document Log Agent Initialization");             
          }*/
        
          context.setAttribute("nonAdminSecurity",nonAdminSecurity);
          context.setAttribute("faxtempdir",prefix+faxtempdir);
          context.setAttribute("contextPath",prefix);
          context.setAttribute("jobRetrialInterval",jobRetrialInterval);
          context.setAttribute("jobRetrialCount",jobRetrialCount);
          if(logFile != null) {
              PropertyConfigurator.configure(prefix + logFile);
          }else{
              log("Unable to find log4j-initialization-file : " + logFile);
          }
          log("Logger initialized successfully");
        }catch( DbsException dbsEx ){
          log(" Unable to initialize logger : " + dbsEx.toString());
          dbsEx.printStackTrace();
        }catch(Exception e){
          log(" Unable to initialize logger : " + e.toString());
        }
    }

    //All the request will pass through this method

    public void process(HttpServletRequest request,HttpServletResponse response) {
        Logger logger = Logger.getLogger("DbsLogger");
        HttpSession httpSession=request.getSession(true);
        UserInfo userInfo=(UserInfo)httpSession.getAttribute("UserInfo");
        String requestedURI=request.getRequestURI();
        String redirector = parseUtil.getValue("redirector","Configuration");
        logger.debug("Entering process() now...");
        logger.debug("Processing URI : \""+ requestedURI + "\" in GeneralActionServlet");
        if(userInfo==null && !(request.getRequestURI().endsWith("loginAction.do")) && !(request.getRequestURI().endsWith("relayAction.do")) && !(request.getRequestURI().endsWith("showContentAction.do")) && !(request.getRequestURI().endsWith("loginClientAction.do")) && !(request.getRequestURI().endsWith("directoryHierarchyAction.do"))){
            try{  
                logger.debug("Trying to access non admin pages when session has expired");
                String actionName = requestedURI.substring((requestedURI.lastIndexOf("/") + 1));
                if(popupActionList.contains(actionName)){
                    logger.debug("Serving : session_expired_4_pop_up.jsp");
                    response.sendRedirect(redirector+requestedURI.substring(0,requestedURI.lastIndexOf("/")+1)+"session_expired_4_pop_up.jsp");
                }else{
                    logger.debug("Serving : login.jsp");
                    response.sendRedirect(redirector+requestedURI.substring(0,requestedURI.lastIndexOf("/")+1)+"login.jsp?sessionExpired=true");
                }
            }catch(Exception ex){
                logger.error(ex);
            }
      }else{
          try{
              boolean isNonAdminAllowed=false;
              if(nonAdminSecurity.equals("1")){
                isNonAdminAllowed=((requestedURI.indexOf("AdminAction.do")!= -1) );                
              }else if(nonAdminSecurity.equals("0")){
                isNonAdminAllowed=(requestedURI.indexOf("AdminAction.do")!= -1);
              }              

              //if((requestedURI.indexOf("AdminAction.do")!= -1) && requestedURI.indexOf("acl")==-1 && requestedURI.indexOf("ace")==-1&& requestedURI.indexOf("permission")==-1){

              if(isNonAdminAllowed){
                 if(userInfo==null){
                    logger.debug("Trying to access Admin pages when session has expired or user is not logged in");
                    logger.debug("Serving : login.jsp");
                    response.sendRedirect(redirector+requestedURI.substring(0,requestedURI.lastIndexOf("/")+1)+"login.jsp?sessionExpired=true"); 
                 }else{
                    DbsLibrarySession dbsSession=((UserInfo)(httpSession.getAttribute("UserInfo"))).getDbsLibrarySession();
                    boolean isAdminEnabled=dbsSession.getUser().isAdminEnabled();
                    if(isAdminEnabled==false){
                        logger.debug("Non Admin User is logged in but he is trying to access admin pages");
                        logger.debug("Serving : restricted_resource.jsp");
                        response.sendRedirect(redirector+requestedURI.substring(0,requestedURI.lastIndexOf("/")+1)+"restricted_resource.jsp"); 
                    }else{
                        logger.debug("dbsLibrarySession id in process: "+dbsSession.getId());
                        super.process(request,response);
                    }
                 }
              }else{
                  if(userInfo!=null){
                    logger.debug("dbsLibrarySession id in process if userInfo not null: "+userInfo.getDbsLibrarySession().getId());
                    DbsDirectoryUser owner=(DbsDirectoryUser)userInfo.getDbsLibrarySession().getUser();
                    DbsLibrarySession ownerSession = userInfo.getDbsLibrarySession();
                    logger.debug("User" + owner.getName().toString() );
                    DbsDirectoryGroup ownerGroup[]=owner.getAllAncestors();
                    DbsDirectoryGroup companyGroup=ownerGroup[0];
                    logger.debug("Group" + companyGroup.getName());
                    String quota= (companyGroup.getAttribute("QUOTA").getString(userInfo.getDbsLibrarySession()));
                    logger.debug("Quota: " + quota);
                    if (quota!=null){
                      double dblAllocatedQuota= Double.parseDouble(quota);
                      logger.debug("Quota: " + dblAllocatedQuota);
                      userInfo.setAllocatedQuota(dblAllocatedQuota);
                    }
                    //ownerSession.impersonateUser(sysDU);
                   
                    DbsFileSystem fileSystem=new DbsFileSystem(sysLibSession);
                    DbsPublicObject dbsPublicObject=fileSystem.findPublicObjectByPath("/" + companyGroup.getName()).getResolvedPublicObject();
                    
                    FolderDoc folderDoc = new FolderDoc(sysLibSession);
                    TotalSizeFoldersDocs total = folderDoc.findTotalSizeFoldersDocs(dbsPublicObject.getId());
                    double size = (new Double(total.getSize())).doubleValue()/(new Double((1024*1024))).doubleValue();
                    size=(Math.round(size * 100)) / 100.00;
                    //logger.debug("size: " + size);
                    //ownerSession.impersonateUser(null);
                    userInfo.setUsedQuota(size);
                  }
                    super.process(request,response);
              }
              
          }catch(DbsException dbsException){
              logger.error(dbsException.getErrorMessage());
          }catch(Exception ex){
              logger.error(ex);
          }
      }
  }
}