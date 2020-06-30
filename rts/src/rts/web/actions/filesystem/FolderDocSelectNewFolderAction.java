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
 * $Id: FolderDocSelectNewFolderAction.java,v 1.10 2005/10/20 09:54:50 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.beans.exception.*;
import rts.web.beans.user.*;
import rts.web.beans.filesystem.*;
import rts.web.actionforms.filesystem.*;
/* Java API */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
/* Struts API */
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.log4j.*;
import rts.web.beans.utility.SearchUtil;

/**
 * Purpose : To facilitate new folder creation in a folder/doc lookUp     
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   07-08-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class FolderDocSelectNewFolderAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    Logger logger = Logger.getLogger("DbsLogger");
    logger.info("Performing Folder Doc Tree Append Action ...");
        
    //variable declaration
    ExceptionBean exceptionBean;
    String forward = "success";
    HttpSession httpSession = null;
    UserInfo userInfo=null;
    DbsLibrarySession dbsLibrarySession = null;
    boolean foldersOnly=true;
    String openerControl=null;
    String currentFolderPath=null; 
    FolderDocSelectForm folderDocSelectForm=null;
    DbsFileSystem dbsFileSystem=null;
    DbsFolder dbsFolder=null;   
    Long currentFolderId4Select=null;
    Treeview treeview4Select =null;
    Treeview treeview4Timesheet = null;
    String folderName=null;
    
    try{
      httpSession = request.getSession(false);
      userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
      dbsLibrarySession = userInfo.getDbsLibrarySession();
      folderDocSelectForm= (FolderDocSelectForm) form;
      logger.debug("folderDocSelectForm: "+folderDocSelectForm);
      FolderDocInfo folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
      logger.debug("folderDocInfo : " + folderDocInfo);
      String prefix = null;
      /* set prefix */
      if( folderDocInfo.getRtsType() ==1 || folderDocInfo.getRtsType() ==3){
        prefix = "R";
      }else if( folderDocInfo.getRtsType() == 2 || 
                folderDocInfo.getRtsType() == 4 ){
        prefix = "T";
      }else{
        prefix = "P";
      }
      String folderDesc = prefix+folderDocSelectForm.getHdnFolderDesc();
      
      foldersOnly=folderDocSelectForm.isHdnFoldersOnly();
      openerControl=folderDocSelectForm.getHdnOpenerControl();
      folderName=folderDocSelectForm.getHdnFolderName();
      currentFolderPath=folderDocSelectForm.getHdnFolderDocument();
      dbsFileSystem= new DbsFileSystem(dbsLibrarySession);
      FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
      if( currentFolderPath == null || currentFolderPath.trim().length() == 0 ){
        if( prefix.equals("R") ){
          currentFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                              userInfo.getResumeFolderName();
        }else if( prefix.equals("T") ){
          currentFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                              userInfo.getTimesheetFolderName();
        }else{
          currentFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                              userInfo.getPersonalFolderName()+"/"+
                              dbsLibrarySession.getUser().getPrimaryUserProfile().getHomeFolder().getName();
        }
      }
      currentFolderId4Select=(dbsFileSystem.findPublicObjectByPath(currentFolderPath)).getId();
      if( folderDocInfo.getRtsType() ==1 || folderDocInfo.getRtsType() ==3  ){
        treeview4Select=(Treeview)httpSession.getAttribute("Treeview4Select");
      }else if( folderDocInfo.getRtsType() ==2 || folderDocInfo.getRtsType() ==4 ){
        treeview4Select = (Treeview)httpSession.getAttribute("Treeview4Timesheet");
      }else{
        treeview4Select = (Treeview)httpSession.getAttribute("Treeview4Personal");
      }
      try{ // create new folder
        dbsFolder = (DbsFolder)dbsFileSystem.findPublicObjectById(currentFolderId4Select);
        //dbsFolder = dbsFileSystem.createFolder(folderName,dbsFolder,true,null);
        folderDoc.newFolder(folderName,folderDesc,currentFolderId4Select,null);
        dbsFolder = folderDoc.getFolderWhenAdded();
        /* set ACL to companyACL for folders created under "resumes"
         * and "timesheets" */
        String resumeFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                                  userInfo.getResumeFolderName();
        String timesheetFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                                      userInfo.getTimesheetFolderName();
                                  
        if( currentFolderPath.startsWith(resumeFolderPath) ||
            currentFolderPath.startsWith(timesheetFolderPath)  ) {
          /* obtain ACL on the group currently associated with the user */
          DbsDirectoryGroup [] groups=dbsLibrarySession.getUser().getAllAncestors();
          DbsDirectoryGroup companyGroup = null;
          DbsAccessControlList compACL = null;
          if( groups!=null ){
            companyGroup = groups[0];
            logger.debug("Company Group: "+companyGroup.getName());
            compACL = companyGroup.getAcl();
            logger.debug("compACL : "+compACL.getName());
            // set ACL to companyACL
            dbsFolder.setAcl(compACL);
          }
        }else{    // for folder in user's home folder,set ACL to private
          DbsAccessControlList privateACL = (DbsAccessControlList)
                                              SearchUtil.findObject(
                                              dbsLibrarySession,
                                              DbsAccessControlList.CLASS_NAME,
                                              "Private");
          dbsFolder.setAcl(privateACL);
        }
        logger.debug("ACL applied to the new Folder is: "+
                      dbsFolder.getAcl().getName());
        
        //dbsFolder.setDescription(folderDesc.trim());
        //treeview4Select.ifFolderAdded(dbsFolder.getId(),currentFolderId4Select);
        if( currentFolderId4Select!=null ){
          currentFolderId4Select=dbsFolder.getId();
          if( treeview4Select != null ){
            treeview4Select.forAddressBar(currentFolderId4Select);
          }
        }
        //currentFolderId4Select=dbsFolder.getId();
        httpSession.setAttribute("currentFolderDocId4Select",currentFolderId4Select);
      }catch(DbsException e){
        exceptionBean = new ExceptionBean(e);
        if( e.containsErrorCode(68000) ){
          exceptionBean.setMessage("Invalid Path");
        }
        logger.error(exceptionBean.getMessage());
        logger.debug(exceptionBean.getErrorTrace());
        httpSession.setAttribute("actionerrors",exceptionBean.getActionErrors());    
      }
      
      httpSession.setAttribute("foldersOnly",new Boolean(foldersOnly));
      httpSession.setAttribute("openerControl",openerControl);  
      
    }catch(DbsException e){
      forward = "failure";
      exceptionBean = new ExceptionBean(e);
      logger.error(exceptionBean.getMessage());
      httpSession.setAttribute("actionerrors",exceptionBean.getActionErrors());    
    }catch(Exception e){
      forward = "failure";
      exceptionBean = new ExceptionBean(e);
      logger.error(exceptionBean.getMessage());
      httpSession.setAttribute("actionerrors",exceptionBean.getActionErrors());    
      }

    return mapping.findForward(forward);
  }
}
