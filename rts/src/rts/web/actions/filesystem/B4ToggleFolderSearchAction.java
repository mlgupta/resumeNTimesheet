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
 * $Id: B4ToggleFolderSearchAction.java,v 1.10 2005/10/03 10:10:20 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/*rts package references*/
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
/*java API */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/*Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;

   /**
     * Purpose : To reconstruct folder tree when new search is required
     * @author              Suved Mishra
     * @version             1.0
     * 	Date of creation:   17-12-2004
     * 	Last Modified by :     
     * 	Last Modified Date:    
    */


public class B4ToggleFolderSearchAction extends Action  {
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
    logger.info("Performing menu click operation ...");
    
    //variable declaration
    ExceptionBean exceptionBean;
    String forward = "success";
    HttpSession httpSession = null;
    String type = null;
    Treeview treeview4list = null;
    Treeview treeview4TimesheetList = null;
    Treeview treeview4PersonalList = null;
    FolderDocInfo folderDocInfo = null;
    DbsPrimaryUserProfile dbsPrimaryUserProfile = null;
    try{
        logger.debug("Initializing variables ...");
        httpSession = request.getSession(false);
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
        UserPreferences userPreferences = (UserPreferences)
                                          httpSession.getAttribute(
                                          "UserPreferences");
        folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
        logger.debug("folderDocInfo : " + folderDocInfo);
        FolderDocListForm folderdoclistform=(FolderDocListForm)form;
        logger.info("FolderDocListForm: "+folderdoclistform);
        //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");
        DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
        
        //The following code is to check is users home folder is set.
        //If not an error is thrown
        DbsDirectoryUser dbsDirectoryUser = dbsLibrarySession.getUser();            
        dbsPrimaryUserProfile = dbsDirectoryUser.getPrimaryUserProfile();
        DbsFolder dbsFolder = dbsPrimaryUserProfile.getHomeFolder();
        DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
        httpSession.removeAttribute("currentFolderDocId4Select");
        /*httpSession.removeAttribute("Treeview4Select");*/
        DbsFolder rtsRoot = null;
        
        /* set corresponding root folder according to rtsType */
        if((folderDocInfo.getRtsType() == FolderDocInfo.RESUME_TYPE) ||  
           (folderDocInfo.getRtsType() == FolderDocInfo.RESUME_DRAWER_TYPE)){
          // set root as "/companyName/resumes"
          rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                            "/"+folderDocInfo.getRtsBase()+"/"+userInfo.getResumeFolderName());
          /* set rtsType to "RESUME_TYPE" */
          folderDocInfo.setRtsType(folderDocInfo.RESUME_TYPE);
          /* initialize treeview */
          if( (Treeview)httpSession.getAttribute("Treeview4List") == null ){
            treeview4list = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
            folderDocInfo.setJsFileName(treeview4list.getJsFileName());
            treeview4list.forAddressBar(rtsRoot.getId());
            httpSession.setAttribute("Treeview4List",treeview4list);
          }

        }else if((folderDocInfo.getRtsType() == FolderDocInfo.TIMESHEET_TYPE) ||  
           (folderDocInfo.getRtsType() == FolderDocInfo.TIMESHEET_DRAWER_TYPE)){
          // set root as "/companyName/timesheets"
          rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                            "/"+folderDocInfo.getRtsBase()+"/"+userInfo.getTimesheetFolderName());
          /* set rtsType to "TIMESHEET_TYPE" */  
          folderDocInfo.setRtsType(folderDocInfo.TIMESHEET_TYPE);
          /* initialize treeview */
          if( (Treeview)httpSession.getAttribute("Treeview4TimesheetList") == null ){
            treeview4TimesheetList = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
            folderDocInfo.setJsFileName(treeview4TimesheetList.getJsFileName());
            treeview4TimesheetList.forAddressBar(rtsRoot.getId());
            httpSession.setAttribute("Treeview4TimesheetList",treeview4TimesheetList);
          }
          
        }else{
          // set root as user's homeFolder
          rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectById(
                                            folderDocInfo.getHomeFolderId());
          /* set rtsType to "PERSONAL_TYPE" */
          folderDocInfo.setRtsType(folderDocInfo.PERSONAL_TYPE);
          /* initialize treeview */
          if( (Treeview)httpSession.getAttribute("Treeview4PersonalList") == null ){
            treeview4PersonalList = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
            folderDocInfo.setJsFileName(treeview4PersonalList.getJsFileName());
            treeview4PersonalList.forAddressBar(rtsRoot.getId());
            httpSession.setAttribute("Treeview4PersonalList",treeview4PersonalList);
          }
          
        }
          
                            
        if(dbsFolder == null){
            ExceptionBean exb = new ExceptionBean();
            exb.setMessage("Home Folder Not Set");
            exb.setMessageKey("errors.homefolder.notset");
            throw exb;
        }else{  /* reset navigation specific parameters */

            /*if (treeview == null){
                logger.info("Building Folder Tree...");
                treeview = new Treeview(dbsLibrarySession,"FolderDocumentList",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/")  , userPreferences.getTreeIconPath() + "/",httpSession.getId(),true);
                folderDocInfo.setJsFileName(treeview.getJsFileName());*/
                Long currentFolderId = rtsRoot.getId();
                //treeview.forAddressBar(currentFolderId);
                logger.debug("currentFolderId : " + currentFolderId);
                //logger.info("Building Folder Tree Complete");
                    
                //initialize folderDocInfo for nevigation
                folderDocInfo.initializeNevigation();

                Long homeFolderId = dbsFolder.getId();
                logger.debug("homeFolderId : " + homeFolderId);
                folderDocInfo.setHomeFolderId(homeFolderId);
                folderDocInfo.setCurrentFolderId(currentFolderId);
                folderDocInfo.setDbsLibrarySession(dbsLibrarySession);
                folderDocInfo.setPageNumber(1);
                folderDocInfo.setSetNo(1);
                folderDocInfo.setHierarchySetNo(1);
                folderDocInfo.addFolderDocId(folderDocInfo.getCurrentFolderId());
                //httpSession.setAttribute("Treeview", treeview);
                logger.debug("folderDocInfo : " + folderDocInfo);
            //}
            folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);
            folderDocInfo.setTreeVisible(true);
            httpSession.removeAttribute("advanceSearchForm");
            }
            httpSession.setAttribute("IS_SIMPLE_NAVIGATION","TRUE");      
            logger.info("FolderDocListForm at the end: "+folderdoclistform);
            
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getMessage());
            saveErrors(request, exceptionBean.getActionErrors());
            forward = "failure";
        }catch(ExceptionBean exb){
            logger.error(exb.getMessage());
            saveErrors(request,exb.getActionErrors());
            forward = "failure";
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
            forward = "failure";
        }
        
        return mapping.findForward(forward);
    }
}
