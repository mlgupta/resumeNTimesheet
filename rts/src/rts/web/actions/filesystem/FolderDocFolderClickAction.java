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
 * $Id: FolderDocFolderClickAction.java,v 1.14 2005/10/03 12:56:17 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
/* Java API */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;

/**
 * Purpose : To obtain contents of a folder upon a folder click     
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   05-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class FolderDocFolderClickAction extends Action  {
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
        FolderDoc folderDoc;
        ArrayList folderDocLists = new ArrayList();
        logger.info("Folder Click operation performed");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        UserPreferences userPreferences = null;
        DbsLibrarySession dbsLibrarySession = null;
        Treeview treeview = null;
        DbsFileSystem dbsFileSystem = null;
        boolean objNotFound = false;
        
        try{
            logger.debug("Initializing Variables...");
            httpSession = request.getSession(false);
            FolderDocListForm folderDocListForm = (FolderDocListForm)form;
            logger.debug("folderDocListForm : " + folderDocListForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            
            userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");
            
            folderDocInfo = (FolderDocInfo)
                                    httpSession.getAttribute("FolderDocInfo");
                                    
            logger.debug("folderDocInfo : " + folderDocInfo);
            //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");
            if( userPreferences.getNavigationType() == 
                UserPreferences.TREE_NAVIGATION ){
              /* obtain corr. treeview */  
              switch( folderDocInfo.getRtsType() ){
                case 1:
                        treeview = (Treeview)
                                    httpSession.getAttribute("Treeview4List");
                        break;
                
                case 2:
                        treeview = (Treeview)
                                    httpSession.getAttribute("Treeview4TimesheetList");
                        break;
                
                case 5:
                        treeview = (Treeview)
                                    httpSession.getAttribute("Treeview4PersonalList");
                        break;
                
                default:
                          break;
              }
              
            }
            
            dbsLibrarySession = userInfo.getDbsLibrarySession();
            DbsFolder rtsRoot = null;
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            /* set corresponding root folder according to rtsType */
            if((folderDocInfo.getRtsType() == 1) ||
               (folderDocInfo.getRtsType() == 3) ){
              // set root as "/companyName/resumes"
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getResumeFolderName());
            }else if((folderDocInfo.getRtsType() == 2) || 
                     (folderDocInfo.getRtsType() == 4)){
              // set root as "/companyName/timesheets"
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getTimesheetFolderName());
            }else{  // set root as homeFolder
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectById(
                                                folderDocInfo.getHomeFolderId());
            }
            
            Long currentFolderId = folderDocListForm.getCurrentFolderId();
            logger.debug("currentFolderId : " + currentFolderId);
            DbsFolder dbsCurrentFolder = null;
            /* this is to deal with treeview inconsistency. a deleted folder
             * might not be erased from treeview.when you click on such a 
             * folder this piece of code handles the situation prompting that
             * the folder doesn't exist. */
            try {
              dbsCurrentFolder = (DbsFolder)dbsLibrarySession.getPublicObject(
                                                                  currentFolderId);
            }catch (DbsException dbsEx) {
              objNotFound = true;
              exceptionBean = new ExceptionBean(dbsEx);
              logger.error(exceptionBean.getMessage());
              logger.debug(exceptionBean.getErrorTrace());
              if( dbsEx.containsErrorCode(10201) ){
                exceptionBean.setMessageKey("errors.10201.folderdoc.folder.notexist");
              }
              httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
              
            }
            
            /* if treeview consistent, reset nav data */
            if( !objNotFound ){
            
              folderDocInfo.setCurrentFolderId(currentFolderId);
              //folderDocInfo.setPageNumber(1);
              if( (folderDocInfo.getRtsType() == 1) || 
                  (folderDocInfo.getRtsType() == 2) ||
                  (folderDocInfo.getRtsType() == 5) ){
                folderDocInfo.setPageNumber(1);
              }else{
                folderDocInfo.setDrawerPageNumber(1);
              }
              
              folderDocInfo.addFolderDocId(currentFolderId);
              /*folderDocInfo.setHierarchySetNo(1);
              folderDocInfo.setSetNo(1);*/
              
              String currentFolderPath = dbsCurrentFolder.getAnyFolderPath();
              logger.debug("currentFolderPath : " + currentFolderPath);
              /* update treeview */
              if( !currentFolderId.equals(rtsRoot.getId())){
                if( treeview != null ){
                  treeview.forAddressBar(currentFolderId);
                }
                folderDocInfo.setCurrentFolderPath(currentFolderPath);
  //                folderDocInfo.setDavPath(userInfo.getDavPath());
                folderDoc = new FolderDoc(dbsLibrarySession);
                /* obtain folder contents */
                folderDocLists = folderDoc.getFolderDocList(
                                              folderDocInfo.getCurrentFolderId(),
                                              folderDocInfo,userPreferences,
                                              userInfo.getDavPath(),false);
                
                request.setAttribute("folderDocLists", folderDocLists);            
                logger.debug("resume subfolders obtained successfully");
              }
              
            }
            
            folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);
            httpSession.setAttribute("IS_SIMPLE_NAVIGATION","TRUE");
/*            
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.NavigatedSuccessfully");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
*/                
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
        logger.info("Folder Click operation performed complete");
        //forward = (folderDocInfo.getRtsType() == 1)?"resume":"timesheet";
        switch (folderDocInfo.getRtsType()){
          case 1:
                  forward= (userPreferences.getNavigationType() == 
                            UserPreferences.FLAT_NAVIGATION)?"resume":
                                                             "resume4Tree";
                  break;
          
          case 2:
                  forward= (userPreferences.getNavigationType() == 
                            UserPreferences.FLAT_NAVIGATION)?"timesheet":
                                                             "timesheet4Tree"; 
                  break;
          
          case 3:
                  forward= "resumeDrawer";
                  break;
          
          case 4:
                  forward= "timesheetDrawer";
                  break;

          case 5:
                  forward= (userPreferences.getNavigationType() == 
                            UserPreferences.FLAT_NAVIGATION)?"personal":
                                                             "personal4Tree"; 
                  break;
          
          case 6:
                  forward= "personalDrawer";
                  break;
          
          default:
                    break;
        } 
        
        return mapping.findForward(forward);
    }
}
