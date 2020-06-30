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
 * $Id: DrawerTimesheetListingAction.java,v 1.7 2005/10/03 12:02:14 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;

/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.AdvanceSearchForm;
import rts.web.beans.exception.ExceptionBean;
import rts.web.beans.filesystem.AdvanceSearchBean;
import rts.web.beans.filesystem.DrawerList;
import rts.web.beans.filesystem.FolderDoc;
import rts.web.beans.filesystem.FolderDocInfo;
import rts.web.beans.filesystem.FolderDocList;
import rts.web.beans.user.UserPreferences;
import rts.web.beans.user.UserInfo;

/* java API */
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.ServletException;

/* Struts API */
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.*;

/**
 * Purpose : To list timesheets drawers in drawer module    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   16-08-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class DrawerTimesheetListingAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */

public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
  
  String target = new String("success");
  HttpSession httpSession = null; 
  Logger logger = Logger.getLogger("DbsLogger");
  UserPreferences userPreferences = null;
  UserInfo userInfo = null;
  FolderDoc folderDoc = null;
  DbsLibrarySession dbsLibrarySession = null;
  FolderDocInfo folderDocInfo = null;
  ArrayList drawerLists= new ArrayList();
  DbsFolder dbsFolder = null;
  try{
    logger.debug("Entering drawerListingAction... ");
    httpSession = (HttpSession)request.getSession(false);
    userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
    userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
    dbsLibrarySession = userInfo.getDbsLibrarySession();
    folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
    folderDocInfo.setRtsType(4);
    Long currentFolderId = folderDocInfo.getCurrentFolderId();
    logger.debug("CurrentFolderId : " + currentFolderId);
    //DbsFolder dbsFolder = null;
    try{
        dbsFolder = (DbsFolder)dbsLibrarySession.getPublicObject(currentFolderId);
    }catch(DbsException dex){
        dex.printStackTrace();
    }
    
    String currentFolderPath = dbsFolder.getAnyFolderPath();
    folderDocInfo.setCurrentFolderId(dbsFolder.getId());
    logger.debug("currentFolderPath : " + currentFolderPath);
    logger.info("Listing items present in the folder : " + currentFolderPath);
    folderDocInfo.setCurrentFolderPath(currentFolderPath);
    
    /* check if current folder is the uppermost limit viz:"/companyName/timesheets" */
    logger.debug("folderDocInfo : " + folderDocInfo);
    boolean isRootFolder = ( folderDocInfo.getCurrentFolderId() == 
                              folderDocInfo.getHomeFolderId() || 
                              folderDocInfo.getCurrentFolderPath().equals(
                              "/"+folderDocInfo.getRtsBase()+"/"+
                              userInfo.getTimesheetFolderName()) )?true:false;
    
    logger.debug("isRootFolder : "+isRootFolder);
    request.setAttribute("isRootFolder",new Boolean(isRootFolder));
    /* SIMPLE_LISTING type,list drawers under the current folder */ 
    if( folderDocInfo.getListingType() == FolderDocInfo.SIMPLE_LISTING){ 
      folderDoc = new FolderDoc(dbsLibrarySession);
      
      drawerLists= folderDoc.getDrawerList(folderDocInfo.getCurrentFolderId(),folderDocInfo,userPreferences);
      request.setAttribute("drawerLists",drawerLists);
      for(int index=0; index<drawerLists.size(); index++){
        logger.debug("DrawerListingAction :");
        logger.debug(((DrawerList)drawerLists.get(index)).getName());
      }
    }else if(folderDocInfo.getListingType() == FolderDocInfo.SEARCH_LISTING){
        // set up search object and list drawers satisfying search criteria
        AdvanceSearchForm advanceSearchForm = (AdvanceSearchForm)httpSession.getAttribute("advanceSearchForm");
        advanceSearchForm.setCurrentFolderId(folderDocInfo.getCurrentFolderId());
        AdvanceSearchBean advanceSearchBean = new AdvanceSearchBean(dbsLibrarySession);
        DbsSearch dbsSearch = null;
        try {
          dbsSearch = advanceSearchBean.getSearchDrawerObjects(advanceSearchForm,folderDocInfo.getRtsType());
        }
        catch (ExceptionBean exceptionBean) {
          exceptionBean.printStackTrace();
        }
        
        folderDoc = new FolderDoc(dbsLibrarySession);
        // list drawers
        drawerLists = folderDoc.buildDrawerList(dbsSearch,
              FolderDocInfo.SEARCH_LISTING,folderDocInfo,userPreferences);
        request.setAttribute("drawerLists",drawerLists);                        
    }else{
        // refresh case
    }
    logger.debug("folderDocInfo : " + folderDocInfo);
    logger.debug("userPreferences : " + userPreferences);
    //request.setAttribute("drawerLists",drawerLists);
    ActionErrors actionErrors = (ActionErrors)httpSession.getAttribute("ActionErrors");
    if(actionErrors != null){
        logger.debug("Saving action error in request stream");
        saveErrors(request,actionErrors);
        httpSession.removeAttribute("ActionErrors");
    }    
  }catch( DbsException dbsEx){
    dbsEx.printStackTrace();
  }catch( Exception ex ){
    ex.printStackTrace();
  }
  logger.debug("Exiting drawerTimesheetListingAction... ");    
  return mapping.findForward(target);
}
}