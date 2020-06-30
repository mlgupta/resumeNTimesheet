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
 * $Id: LoginAction.java,v 1.15 2005/10/27 08:26:25 suved Exp $
 *****************************************************************************
 */
 

package rts.web.actions.loginout;
                                                           
/* rts package references */
import rts.beans.DbsAccessControlList;
import rts.beans.DbsAttributeValue;
import rts.beans.DbsCleartextCredential;
import rts.beans.DbsCollection;
import rts.beans.DbsDirectoryGroup;
import rts.beans.DbsDirectoryUser;
import rts.beans.DbsException;
import rts.beans.DbsFileSystem;
import rts.beans.DbsFolder;
import rts.beans.DbsLibraryService;
import rts.beans.DbsLibrarySession;
import rts.beans.DbsPrimaryUserProfile;
import rts.beans.DbsProperty;
import rts.beans.DbsPropertyBundle;
import rts.web.actionforms.loginout.LoginForm;
import rts.web.beans.exception.ExceptionBean;
import rts.web.beans.filesystem.DateHelperForFileSystem;
import rts.web.beans.filesystem.FolderDoc;
import rts.web.beans.filesystem.FolderDocInfo;
import rts.web.beans.filesystem.Treeview;
import rts.web.beans.user.UserInfo;
import rts.web.beans.user.UserPreferences;
import rts.web.beans.utility.ParseXMLTagUtil;
/* java API */
import java.io.IOException;
import java.io.*;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/* Struts API */
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class LoginAction extends Action  {

private static String DATE_FORMAT="MM/dd/yyyy HH:mm:ss";
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
  logger.info("Logging User ...");
  DateHelperForFileSystem dateHelper = new DateHelperForFileSystem();

  //Variable declaration
  ExceptionBean exceptionBean;        
  String forward = "success";
  DbsLibraryService dbsLibraryService;
  DbsCleartextCredential dbsCleartextCredential;
  DbsLibrarySession dbsLibrarySession = null;
  ServletContext context;
  HttpSession httpSession;
  LoginForm loginForm;
  String ifsService;
  String ifsSchemaPassword;
  String serviceConfiguration;
  String domain;
  String davpath=null;
  String themeName=null;
  ActionErrors actionErrors = new ActionErrors();
  ActionError actionError;
  Treeview treeview4Select = null;
  Treeview treeview4Timesheet = null;
  UserPreferences userPreferences = null;
  try{
    loginForm = (LoginForm)form;
    context = getServlet().getServletContext();
    loginForm.setUserID(loginForm.getUserID().toUpperCase());
    logger.info("User ID : " + loginForm.getUserID()); 
    //logger.debug("contextPath: "+(String)context.getAttribute("contextPath"));
    String relativePath= (String)context.getAttribute("contextPath")+
                         "WEB-INF"+File.separator+"params_xmls"+
                         File.separator+"GeneralActionParam.xml";
                         
    ParseXMLTagUtil parseUtil= new ParseXMLTagUtil(relativePath);            
    //request.getSession().invalidate();            
    //logger.debug("request.getSession().isNew() : " + request.getSession().isNew());
    //logger.debug("request.getSession().getId() : " + request.getSession().getId());

    if(context.getAttribute(loginForm.getUserID())==null){
      httpSession = request.getSession(true);
      logger.debug("httpSession id: "+httpSession.getId());
      logger.debug("context Attribute set to null initially...");
    }else{
      HttpSession oldHttpSession=
              (HttpSession)context.getAttribute(loginForm.getUserID());
       
       if (oldHttpSession!=null){
         logger.debug("OldSessionExists.");
       }
       if(oldHttpSession.getId()==null){
          logger.debug("oldHttpSession id is null.");
       }else{
          logger.debug("oldHttpSession id is not null.");
       }
      if (oldHttpSession!=null && oldHttpSession.getId()!=null){
        logger.debug("oldHttpSession id: "+oldHttpSession.getId());
        oldHttpSession.invalidate();
      }
      httpSession = request.getSession(true);
      
      logger.debug("User logged in already...");
      //user is already logged in
    }
    
    dbsCleartextCredential = 
      new DbsCleartextCredential(loginForm.getUserID(),
                                 loginForm.getUserPassword());
//            String systemUserId = context.getInitParameter("SystemUserId");
//            String systemUserPassword = context.getInitParameter("SystemUserPassword");
//            adminCredential = new DbsCleartextCredential(systemUserId,systemUserPassword);

    ifsService = parseUtil.getValue("IfsService","Configuration");  
    //ifsService = context.getInitParameter("IfsService");
    davpath = parseUtil.getValue("DavPath","Configuration");
    logger.debug("DavPath : " + davpath);
    if(DbsLibraryService.isServiceStarted(ifsService)){
      logger.info(ifsService + " is running");
      dbsLibraryService = 
        DbsLibraryService.findService("IfsDefaultService");
    }else{
      logger.info("Starting Library Service...");
      
      ifsSchemaPassword = 
                  parseUtil.getValue("IfsSchemaPassword","Configuration");
      serviceConfiguration =  
                  parseUtil.getValue("ServiceConfiguration","Configuration");
      domain = parseUtil.getValue("Domain","Configuration");
      
      
      /*ifsSchemaPassword = context.getInitParameter("IfsSchemaPassword");
      serviceConfiguration = context.getInitParameter("ServiceConfiguration");
      domain = context.getInitParameter("Domain");*/
      //logger.info(""+);
      
      logger.info("IfsService : " + ifsService);
      logger.info("ServiceConfiguration : " + serviceConfiguration);
      logger.info("Domain : " + domain);
      dbsLibraryService = DbsLibraryService.startService(ifsService,
                          ifsSchemaPassword,serviceConfiguration,
                          domain);
      logger.info("Library Service Started ");
    }
    try{
      dbsLibrarySession = 
        dbsLibraryService.connect(dbsCleartextCredential,null);
    }catch(DbsException dex){
      logger.error(dex.getMessage());
      dex.getErrorMessage();
      dex.printStackTrace();                
      if(dex.containsErrorCode(10170)){
         throw dex;
      }else if(dex.getErrorCode() == 21008){
        logger.info("This exception is thrown when library service started successfully");
        logger.info("and then database went down.");
        logger.info("In this case Library Service need to be restarted");
        
        ifsSchemaPassword = 
                parseUtil.getValue("IfsSchemaPassword","Configuration");
                
        serviceConfiguration = 
                parseUtil.getValue("ServiceConfiguration","Configuration");
        
        domain = parseUtil.getValue("Domain","Configuration");
        
        
        /*ifsSchemaPassword = context.getInitParameter("IfsSchemaPassword");
        serviceConfiguration = context.getInitParameter("ServiceConfiguration");
        domain = context.getInitParameter("Domain");*/
        logger.info("Disposing Library Service...");
        dbsLibraryService.dispose(ifsSchemaPassword);
        logger.info("Disposing Library Service Complete");
        logger.info("ReStarting Library Service...");            
        dbsLibraryService = DbsLibraryService.startService(ifsService, 
                            ifsSchemaPassword,serviceConfiguration,domain);
        logger.info("ReStarting Library Service Complete");
        dbsLibrarySession = dbsLibraryService.connect(
                            dbsCleartextCredential,null);
      }
    }
    
    if (dbsLibrarySession.isConnected() ) {
      logger.debug("Session Id: "+dbsLibrarySession.getId());
      UserInfo userInfo = new UserInfo();
      FolderDocInfo folderDocInfo = new FolderDocInfo();
      userPreferences = new UserPreferences();
      DbsDirectoryUser connectedUser=dbsLibrarySession.getUser();
      context.setAttribute(connectedUser.getName(),httpSession); 
      //  for selected tree access level,displaying number of rows per page,
      //  Navigation type,Theme selected and Open Doc Option selected.
      if (dbsLibrarySession.getUser().getPropertyBundle()!=null){
        DbsPropertyBundle propertyBundleToEdit= 
          dbsLibrarySession.getUser().getPropertyBundle();         
        
        DbsProperty property=
          propertyBundleToEdit.getProperty("PermittedTreeAccessLevel");                    
        if (property!=null){
            DbsAttributeValue attrValue=property.getValue();
            userPreferences.setTreeLevel(
                            attrValue.getInteger(dbsLibrarySession)); 
        }
        
        property=propertyBundleToEdit.getProperty(
                                      "ItemsToBeDisplayedPerPage");                    
        if (property!=null) {
            DbsAttributeValue attrValue=property.getValue();
            userPreferences.setRecordsPerPage(
                            attrValue.getInteger(dbsLibrarySession)); 
        }

        property=propertyBundleToEdit.getProperty("NavigationType");                    
        if (property!=null) {
            DbsAttributeValue attrValue=property.getValue();
            userPreferences.setNavigationType(
                            attrValue.getInteger(dbsLibrarySession)); 
        }
        /* code added on account of open doc option */
        property=propertyBundleToEdit.getProperty("OpenDocOption");                  
        if (property!=null) {
          DbsAttributeValue attrValue=property.getValue();
          userPreferences.setChkOpenDocInNewWin(
                          attrValue.getInteger(dbsLibrarySession));     
        }
      }
      /* code added for custom group labels */
      /* getAllAncestors() on a Directory User returns all the groups
       * the user is associated with . */
      DbsDirectoryGroup[] listOfGroups = connectedUser.getAllAncestors();
      
      if( listOfGroups!= null ){
        try{
          /* 
            listOfGroups[0] will fetch the current group associated with 
             this user . Extract Custom Labels from listOfGroups[0]. 
          */ 
          DbsAttributeValue dbsAttrVal =  
                            listOfGroups[0].getAttribute("CUSTOM1_LBL");
          userPreferences.setCustom1_lbl(
                          dbsAttrVal.getString(dbsLibrarySession));

          dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM2_LBL");
          userPreferences.setCustom2_lbl(
                          dbsAttrVal.getString(dbsLibrarySession));
                                  
          dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM3_LBL");
          userPreferences.setCustom3_lbl(
                          dbsAttrVal.getString(dbsLibrarySession));
                                  
          dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM4_LBL");
          userPreferences.setCustom4_lbl(
                          dbsAttrVal.getString(dbsLibrarySession));
        
        }catch( DbsException dbsEx ){
          userPreferences.setCustom1_lbl("Custom1_Label");
          userPreferences.setCustom2_lbl("Custom2_Label");
          userPreferences.setCustom3_lbl("Custom3_Label");
          userPreferences.setCustom4_lbl("Custom4_Label");
        }
      }else{  // no group found for the user , user belongs to "WORLD" group
        userPreferences.setCustom1_lbl("Custom1_Label");
        userPreferences.setCustom2_lbl("Custom2_Label");
        userPreferences.setCustom3_lbl("Custom3_Label");
        userPreferences.setCustom4_lbl("Custom4_Label");
      }
      themeName=(themeName==null)?"Default":themeName;
      userPreferences.setTreeIconPath("themes/images");
      userInfo.setUserID(connectedUser.getName());
      userInfo.setDbsLibrarySession(dbsLibrarySession);
      userInfo.setLogger(logger);
      /* obtain primaryUserProfile to fetch the user's home Folder and
       * user-specific information. */
      DbsPrimaryUserProfile connectedUserProf=
                            connectedUser.getPrimaryUserProfile();
      
      userInfo.setLocale(connectedUserProf.getLocale());
      userInfo.setLanguage(connectedUserProf.getLanguage());
      userInfo.setTimeZone(connectedUserProf.getTimeZone());
      userInfo.setCharSet(connectedUserProf.getCharacterSet());
      userInfo.setDavPath(davpath);
      logger.debug("davpath: "  + davpath);
      httpSession.setAttribute("UserInfo",userInfo );
      httpSession.setAttribute("UserPreferences",userPreferences );
      httpSession.setAttribute("FolderDocInfo",folderDocInfo);
      httpSession.setAttribute("Treeview4Select",null);
      httpSession.setAttribute("Treeview4Timesheet",null);
      httpSession.setAttribute("Treeview4Personal",null);
      httpSession.setAttribute("Treeview4List",null);
      httpSession.setAttribute("Treeview4TimesheetList",null);
      httpSession.setAttribute("Treeview4PersonalList",null);
      httpSession.setAttribute("password",loginForm.getUserPassword());
      if(dbsLibrarySession.getUser().isSystemAdminEnabled()){
        userInfo.setSystemAdmin(true);
        dbsLibrarySession.setAdministrationMode(true);
      }else{
        if(dbsLibrarySession.getUser().isAdminEnabled()){
          dbsLibrarySession.setAdministrationMode(true);
          userInfo.setAdmin(true);
        }else{
        }
      }
      forward = "success";
      //check and set home folder id if it exist
      DbsFolder dbsFolder = connectedUserProf.getHomeFolder();
      if(dbsFolder == null){
        folderDocInfo.setHomeFolderId(null);
      }else{
        Long homeFolderId = dbsFolder.getId();
        logger.debug("homeFolderId : " + homeFolderId);
        folderDocInfo.setHomeFolderId(homeFolderId);
        folderDocInfo.setCurrentFolderPath(dbsFolder.getAnyFolderPath());
        String [] rtsBase = folderDocInfo.getCurrentFolderPath().split("/");
        /*for( int index=0; index<rtsBase.length; index++ ){
          logger.debug("rtsBase["+index+"]: "+rtsBase[index]);
        }*/
        folderDocInfo.setRtsBase(rtsBase[1]);
        folderDocInfo.setCurrentFolderId(homeFolderId);
        logger.debug("folderDocInfo : " + folderDocInfo);
      }
   
      logger.debug("userInfo : " + userInfo);
      logger.debug("userPreferences : " + userPreferences);
      Date loginDate = new Date();
      String userLoginDate = dateHelper.format(loginDate,DATE_FORMAT);
      logger.info(userInfo.getUserID() + " has logged in at "+userLoginDate);
      //logger.debug("Default User ACL : ");
      
      /* to be deleted later begins ....*/
      /*DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
      DbsFolder resumeDir =(DbsFolder)dbsFileSystem.findPublicObjectByPath(
                            "/"+folderDocInfo.getRtsBase()+"/resumes");
      DbsFolder timesheetDir =(DbsFolder)dbsFileSystem.findPublicObjectByPath(
                            "/"+folderDocInfo.getRtsBase()+"/timesheets");
      DbsFolder personalDir =(DbsFolder)dbsFileSystem.findPublicObjectByPath(
                            "/"+folderDocInfo.getRtsBase()+"/personal");

      DbsCollection systemAclColl= dbsLibrarySession.getSystemAccessControlListCollection();
      DbsAccessControlList privateAcl=null;
      for(int index =0;index< systemAclColl.getItemCount() ; index++){
        if(systemAclColl.getItems(index).toString().equalsIgnoreCase("Private")){            
            privateAcl= systemAclColl.getSystemAccessControlList(systemAclColl.getItems(index));
            break;
        }
      }
      FolderDoc folderDoc = null;
      if(privateAcl!=null){
        dbsFolder.setAcl(privateAcl);
        folderDoc = new FolderDoc(dbsLibrarySession);
        folderDoc.changeFolderAclRecursively(dbsFolder,privateAcl);
      }
      
      DbsDirectoryGroup [] groups=dbsLibrarySession.getUser().getAllAncestors();
      DbsDirectoryGroup companyGroup = null;
      DbsAccessControlList compACL = null;
      dbsLibrarySession.setAdministrationMode(true);
      if( groups!=null ){
        companyGroup = groups[0];
        logger.debug("Company Group: "+companyGroup.getName());
        compACL = companyGroup.getAcl();
        logger.debug("compACL : "+compACL.getName());
        // set ACL to companyACL
        resumeDir.setAcl(compACL);
        folderDoc.changeFolderAclRecursively(resumeDir,compACL);
        timesheetDir.setAcl(compACL);
        folderDoc.changeFolderAclRecursively(timesheetDir,compACL);
        personalDir.setAcl(compACL);
      }*/
                            
      /* to be deleted later ends ...*/
    }else{
      actionError = new ActionError("login.invalid_user");
      actionErrors.add(ActionMessages.GLOBAL_MESSAGE,actionError);
      saveErrors(request,actionErrors);
      logger.error("Invalid User");
      forward = "failure";
    }
  }catch(DbsException dex)  {
    if(dex.containsErrorCode(10170)){
      ActionErrors errors=new ActionErrors();
      ActionError loginError=new ActionError("msg.login.incorrect");
      errors.add(ActionErrors.GLOBAL_ERROR,loginError);
      saveErrors(request,errors);
      forward = "failure";
      dex.printStackTrace();                
    }else{
      exceptionBean = new ExceptionBean(dex);
      logger.error(exceptionBean.getMessage());
      saveErrors(request,exceptionBean.getActionErrors());
      forward = "failure";
      dex.printStackTrace();                
    }
  }catch(Exception ex)  {
    exceptionBean = new ExceptionBean(ex);
    logger.error(exceptionBean.getMessage());
    logger.debug(exceptionBean.getErrorTrace());
    saveErrors(request,exceptionBean.getActionErrors());
    forward = "failure";
    ex.printStackTrace();
  }
  logger.info("Logging User Complete");
  
  if( !forward.equals("failure") ){
    forward = ( userPreferences.getNavigationType() == 
              UserPreferences.FLAT_NAVIGATION )?
              "forwardToFlatNav":"forwardToTreeNav";
  }       
  return mapping.findForward(forward);
}
}