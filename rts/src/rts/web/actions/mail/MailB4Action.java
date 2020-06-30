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
 * $Id: MailB4Action.java,v 1.8 2005/10/05 10:19:02 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.mail;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.mail.MailForm;
import rts.web.beans.user.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.utility.*;
import rts.web.beans.scheduler.*;
import rts.web.actionforms.filesystem.DocMailForm;
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

/**
 *	Purpose:            To populate mailing.jsp
 *  @author             Mishra Maneesh 
 *  @version            1.0
 * 	Date of creation:   25-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class MailB4Action extends Action {
/**
 * This is the main action called from the Struts framework.
 * @param mapping The ActionMapping used to select this instance.
 * @param form The optional ActionForm bean for this request.
 * @param request The HTTP Request we are processing.
 * @param response The HTTP Response we are processing.
 */
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
  Logger logger = Logger.getLogger("DbsLogger");
  logger.info("Preforming MailB4Action ...");
  ActionErrors errors=new ActionErrors();
  String[] mailFileNames=null;
  DbsLibrarySession dbsLibrarySession = null;
  HttpSession httpSession = null;
  try{   
   DocMailForm docMailForm = (DocMailForm)form;
   logger.debug("docMailForm : " + docMailForm);
   httpSession = request.getSession(false);
   UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
   //httpSession.removeAttribute("currentFolderDocId4Select");             
   dbsLibrarySession = userInfo.getDbsLibrarySession();
   Long[] documentIds = docMailForm.getChkFolderDocIds();
   if(documentIds!=null){
     logger.debug("documentId : " + documentIds);
     mailFileNames =new String[documentIds.length];
     // obtain mailFileNames, they are to be used as attachments 
     for(int index=0; index < mailFileNames.length ; index++){
       mailFileNames[index]= dbsLibrarySession.getPublicObject( 
                                       documentIds[index]).getAnyFolderPath();
       logger.info(mailFileNames[index]);
     }
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
  if (!errors.isEmpty()){
    saveErrors(request, errors);
    return (mapping.getInputForward());
  }
  /* prepopulating mailForm begins ... */
  MailForm mailForm=new MailForm();
  try{  
  if(mailFileNames!=null){
      mailForm.setLstAttachment(mailFileNames);
      mailForm.setHdnTargetFolderPath(dbsLibrarySession.getUser().getPrimaryUserProfile().getHomeFolder().getName());
  }
  
  /* code added to account for obtaining emailIds from a selected folder */
  if(request.getParameter("grabIds")!=null && 
    ( request.getParameter("grabIds").equalsIgnoreCase("true") )){
    
    String folderPath = request.getParameter("hdnTargetFolderPath");
    logger.debug("folderPath: "+folderPath);
    
    UserPreferences userPreferences = (UserPreferences)
                          httpSession.getAttribute("UserPreferences");
    
    FolderDocInfo folderDocInfo = (FolderDocInfo)
                          httpSession.getAttribute("FolderDocInfo");
    
    DbsFileSystem dbsFs = new DbsFileSystem(dbsLibrarySession);
    DbsFolder targetFolder = (DbsFolder)
                              dbsFs.findPublicObjectByPath(folderPath);
    logger.debug("FolderDocInfo:" +folderDocInfo);
    FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
    ArrayList documentLists = folderDoc.getFolderDocList(
                                  targetFolder.getId(),folderDocInfo,
                                  userPreferences,null,true);
    String emailIds = null;
    if( documentLists.size() != 0 ){                          
      emailIds = folderDoc.getEmailIds(documentLists);
      /*emailIds = emailIds+folderDoc.getEmailIds(documentLists);
      emailIds.trim();*/
      logger.debug("emailIds: "+emailIds);
    }else{
      emailIds = "No emailIds found ...";
    }
    mailForm.setTxtTo(emailIds);      
  }
  /* code added for obtaining emailIds from a selected folder ends ... */
  }catch( DbsException dbsEx ){
    dbsEx.printStackTrace();
  }
  /* prepopulating mailForm ends ... */
  logger.debug("mailForm: "+mailForm);
  request.setAttribute("MailForm",mailForm);
  return mapping.findForward("success");
}
}
