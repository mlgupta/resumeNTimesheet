package rts.web.actions.loginout;
/* java API */
import java.io.File;
import java.io.IOException;

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
/* rts package references */
import rts.beans.DbsAttributeValue;
import rts.beans.DbsCleartextCredential;
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

import rts.web.actionforms.filesystem.DocUploadForm;
import rts.web.actionforms.loginout.LoginForm;
import rts.web.beans.exception.ExceptionBean;
import rts.web.beans.filesystem.DateHelperForFileSystem;
import rts.web.beans.filesystem.FolderDocInfo;
import rts.web.beans.filesystem.Treeview;
import rts.web.beans.user.UserInfo;
import rts.web.beans.user.UserPreferences;
import rts.web.beans.utility.ParseXMLTagUtil;


public class LoginClientAction extends Action  {

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
  DbsFolder resumeRoot = null;
  DocUploadForm docUploadForm = null;
  ServletContext context;
  HttpSession httpSession = null;
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
  Treeview treeview4Personal = null;
  Treeview treeview4list = null;
  Treeview treeview4TimesheetList = null;
  Treeview treeview4PersonalList = null;
  DbsFileSystem dbsFileSystem = null;
  UserPreferences userPreferences = null;
  String folderPath = null;
  
  try{
    //loginForm = (LoginForm)form;
    context = getServlet().getServletContext();
    //loginForm.setUserID(loginForm.getUserID().toUpperCase());
    //logger.info("User ID : " + loginForm.getUserID()); 
    //logger.debug("contextPath: "+(String)context.getAttribute("contextPath"));

                                  
    String relativePath= (String)context.getAttribute("contextPath")+
                         "WEB-INF"+File.separator+"params_xmls"+
                         File.separator+"GeneralActionParam.xml";
                         
    ParseXMLTagUtil parseUtil= new ParseXMLTagUtil(relativePath);            
    //request.getSession().invalidate();            
    //logger.debug("request.getSession().isNew() : " + request.getSession().isNew());
    //logger.debug("request.getSession().getId() : " + request.getSession().getId());

    if(context.getAttribute((request.getParameter("userID")).toUpperCase())==null){
      httpSession = request.getSession(true);
      logger.debug("httpSession id: "+httpSession.getId());
      logger.debug("context Attribute set to null initially...");
    }else{
      HttpSession oldHttpSession=
              (HttpSession)context.getAttribute((request.getParameter("userID")).toUpperCase());
       
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
      new DbsCleartextCredential((request.getParameter("userID")).toUpperCase(),
                                 (request.getParameter("userPassword")));
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
          docUploadForm = new DocUploadForm();
          DbsAttributeValue dbsAttrVal =  
                            listOfGroups[0].getAttribute("CUSTOM1_LBL");
          userPreferences.setCustom1_lbl(
                          dbsAttrVal.getString(dbsLibrarySession));
          docUploadForm.setTxtCustom1Lbl(
                        (dbsAttrVal != null && 
                        dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                        dbsAttrVal.getString(dbsLibrarySession):"Custom1_Label");


          dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM2_LBL");
          userPreferences.setCustom2_lbl(
                          dbsAttrVal.getString(dbsLibrarySession));
          docUploadForm.setTxtCustom2Lbl(
                        (dbsAttrVal != null && 
                        dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                        dbsAttrVal.getString(dbsLibrarySession):"Custom2_Label");
                                  
          dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM3_LBL");
          userPreferences.setCustom3_lbl(
                          dbsAttrVal.getString(dbsLibrarySession));
          docUploadForm.setTxtCustom3Lbl(
                        (dbsAttrVal != null && 
                        dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                        dbsAttrVal.getString(dbsLibrarySession):"Custom3_Label");
                                  
          dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM4_LBL");
          userPreferences.setCustom4_lbl(
                          dbsAttrVal.getString(dbsLibrarySession));
          docUploadForm.setTxtCustom4Lbl(
                        (dbsAttrVal != null && 
                        dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                        dbsAttrVal.getString(dbsLibrarySession):"Custom4_Label");
        
        }catch( DbsException dbsEx ){
          userPreferences.setCustom1_lbl("Custom1_Label");
          userPreferences.setCustom2_lbl("Custom2_Label");
          userPreferences.setCustom3_lbl("Custom3_Label");
          userPreferences.setCustom4_lbl("Custom4_Label");
          docUploadForm.setTxtCustom1Lbl("Custom1_Label");
          docUploadForm.setTxtCustom2Lbl("Custom2_Label");
          docUploadForm.setTxtCustom3Lbl("Custom3_Label");
          docUploadForm.setTxtCustom4Lbl("Custom4_Label");
        }
      }else{  // no group found for the user , user belongs to "WORLD" group
        userPreferences.setCustom1_lbl("Custom1_Label");
        userPreferences.setCustom2_lbl("Custom2_Label");
        userPreferences.setCustom3_lbl("Custom3_Label");
        userPreferences.setCustom4_lbl("Custom4_Label");
        docUploadForm.setTxtCustom1Lbl("Custom1_Label");
        docUploadForm.setTxtCustom2Lbl("Custom2_Label");
        docUploadForm.setTxtCustom3Lbl("Custom3_Label");
        docUploadForm.setTxtCustom4Lbl("Custom4_Label");
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
      httpSession.setAttribute("password",request.getParameter("userPassword"));
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
      dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
      httpSession.removeAttribute("currentFolderDocId4Select");
      // set root as "/companyName/resumes"
      resumeRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath("/"+
                                            folderDocInfo.getRtsBase()+"/"+
                                            userInfo.getResumeFolderName());
      /* set rtsType to "RESUME_TYPE" */
      folderDocInfo.setRtsType(folderDocInfo.RESUME_TYPE);
      folderPath = request.getParameter("ServerPathToFolder");
      String [] splitVals = folderPath.split("/");
      for( int index =0; index < splitVals.length; index++ ){
        logger.debug("splitVals["+index+"]: "+splitVals[index]);
      }
      if( splitVals.length > 3 ){
      
        String reconstructedPath = "/"+folderDocInfo.getRtsBase();
        StringBuffer appendThisBuf = new StringBuffer();
        for( int index = 2;index < splitVals.length;index++ ){
          appendThisBuf.append("/"+splitVals[index]);
        }
        reconstructedPath += appendThisBuf.toString();
        folderPath = reconstructedPath;
        
        dbsFolder = (DbsFolder)dbsFileSystem.findPublicObjectByPath(folderPath);
                     
        if( userPreferences.getNavigationType() == UserPreferences.TREE_NAVIGATION ){
          /* initialize treeview */
          if( (Treeview)httpSession.getAttribute("Treeview4List") == null ){
            treeview4list = new Treeview(dbsLibrarySession,"FolderDocTree",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
            folderDocInfo.setJsFileName(treeview4list.getJsFileName());
            treeview4list.forAddressBar(resumeRoot.getId());
            httpSession.setAttribute("Treeview4List",treeview4list);
          }else{
            treeview4list = (Treeview)httpSession.getAttribute("Treeview4List");
           // treeview4list.free();
            //treeview4list = new Treeview(dbsLibrarySession,"FolderDocTree",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
            folderDocInfo.setJsFileName(treeview4list.getJsFileName());
            treeview4list.forAddressBar(resumeRoot.getId());
            httpSession.setAttribute("Treeview4List",treeview4list);
          }
          
        }else{
          if( (Treeview)httpSession.getAttribute("Treeview4Select") == null ){
            treeview4Select = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
            httpSession.setAttribute("Treeview4Select",treeview4Select);
          }
        }
  
        //initialize folderDocInfo for nevigation
        folderDocInfo.initializeNevigation();
  
        folderDocInfo.setCurrentFolderId(dbsFolder.getId());
        folderDocInfo.setCurrentFolderPath(folderPath);
        folderDocInfo.setDbsLibrarySession(dbsLibrarySession);
        folderDocInfo.setPageNumber(1);
        folderDocInfo.setSetNo(1);
        folderDocInfo.setHierarchySetNo(1);
        folderDocInfo.addFolderDocId(folderDocInfo.getCurrentFolderId());
        //httpSession.setAttribute("Treeview", treeview);
        logger.debug("folderDocInfo : " + folderDocInfo);
  
        folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);
        folderDocInfo.setTreeVisible(true);
        httpSession.removeAttribute("advanceSearchForm");
        httpSession.setAttribute("IS_SIMPLE_NAVIGATION","TRUE");      
        
        request.setAttribute("fromClient",new Boolean(true));
        logger.debug("docUploadForm : "+docUploadForm);
        request.setAttribute("docUploadForm",docUploadForm);
        logger.info("Serving JSP for Upload Complete");
        forward = "success";
      }else{
        forward = "failure";
        actionError = new ActionError("msg.CantUploadAtRoot");
        actionErrors.add(ActionMessages.GLOBAL_MESSAGE,actionError);
        saveErrors(request,actionErrors);
        logger.error("Permission denied to upload resume on root");
      }
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
  
  if( forward.equals("failure") ){
    logger.info("Logging User Failed");
    if( dbsLibrarySession != null ){
      try {
        dbsLibrarySession.disconnect();
      }catch ( DbsException dbsEx ) {
        logger.debug(dbsEx.toString());
      }
    }
    httpSession.removeAttribute("UserInfo");
    httpSession.removeAttribute("UserPreferences");
    httpSession.removeAttribute("FolderDocInfo");
    httpSession.removeAttribute("Treeview4List");
    httpSession.removeAttribute("Treeview4TimesheetList");
    httpSession.removeAttribute("Treeview4PersonalList");
    httpSession.removeAttribute("Treeview4Select");
    httpSession.removeAttribute("Treeview4Timesheet");
    httpSession.removeAttribute("Treeview4Personal");
    httpSession.removeAttribute("password");
    httpSession.removeAttribute("IS_SIMPLE_NAVIGATION");
  }else
    logger.info("Logging User Complete");
    
  return mapping.findForward(forward);
}
}