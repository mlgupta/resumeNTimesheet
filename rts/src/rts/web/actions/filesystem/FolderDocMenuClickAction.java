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
 * $Id: FolderDocMenuClickAction.java,v 1.18 2005/10/03 14:16:57 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.filesystem.Treeview;
import rts.web.beans.user.*;
/* Java API */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API*/
import org.apache.log4j.*;
import org.apache.struts.action.*;

/**
 * Purpose : To set module specific data for "Resume","Timesheet","Personal" 
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   01-07-2004
 * 	Last Modified by :  Suved Mishra   
 * 	Last Modified Date: 12-08-2005   
*/

public class FolderDocMenuClickAction extends Action  {
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
        Treeview treeview4Select = null;
        Treeview treeview4Timesheet = null;
        Treeview treeview4Personal = null;
        FolderDocInfo folderDocInfo = null;
        try{
            logger.debug("Initializing variables ...");
            httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            logger.debug("folderDocInfo : " + folderDocInfo);
            FolderDocListForm folderdoclistform=(FolderDocListForm)form;
            logger.info("FolderDocListForm: "+folderdoclistform);
            //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            
            //The following code is to check is users home folder is set.
            //If not an error is thrown
            DbsDirectoryUser dbsDirectoryUser = dbsLibrarySession.getUser();            
            DbsPrimaryUserProfile dbsPrimaryUserProfile = dbsDirectoryUser.getPrimaryUserProfile();
            DbsFolder dbsFolder = dbsPrimaryUserProfile.getHomeFolder();
            DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            httpSession.removeAttribute("currentFolderDocId4Select");
            httpSession.setAttribute("Treeview4List",null);
            httpSession.setAttribute("Treeview4TimesheetList",null);
            httpSession.setAttribute("Treeview4PersonalList",null);
            /*httpSession.removeAttribute("Treeview4Select");*/
            type = request.getParameter("type");
            DbsFolder rtsRoot = null;
            /*DbsFolder rtsRoot = ( type.equals("resume") )?
                                (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+"resumes"):
                                (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+"timesheets");*/
            
            /* set root folder according to "type" request parameter */
            if(type.equals("resume")){
              // set root as "/companyName/resumes"
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+userInfo.getResumeFolderName());
              /* set rtsType to "RESUME_TYPE" */
              folderDocInfo.setRtsType(folderDocInfo.RESUME_TYPE);
              /* initialize treeview */
              if( (Treeview)httpSession.getAttribute("Treeview4Select") == null ){
                treeview4Select = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
                httpSession.setAttribute("Treeview4Select",treeview4Select);
              }

            }else if(type.equals("timesheet")){
              // set root as "/companyName/timesheets"
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+userInfo.getTimesheetFolderName());
              /* set rtsType to "TIMESHEET_TYPE" */
              folderDocInfo.setRtsType(folderDocInfo.TIMESHEET_TYPE);
              /* initialize treeview */
              if( (Treeview)httpSession.getAttribute("Treeview4Timesheet") == null ){
                treeview4Timesheet = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
                httpSession.setAttribute("Treeview4Timesheet",treeview4Timesheet);
              }
              
            }else{
              // set root as user's homeFolder
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectById(
                                                folderDocInfo.getHomeFolderId());
              /* set rtsType to "PERSONAL_TYPE" */
              folderDocInfo.setRtsType(folderDocInfo.PERSONAL_TYPE);
              /* initialize treeview */
              if( (Treeview)httpSession.getAttribute("Treeview4Personal") == null ){
                treeview4Personal = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
                httpSession.setAttribute("Treeview4Personal",treeview4Personal);
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
            
/*            
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.MenuClickPerformed");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
*/                
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getMessage());
            saveErrors(request, exceptionBean.getActionErrors());
            //forward = "failure";
        }catch(ExceptionBean exb){
            logger.error(exb.getMessage());
            saveErrors(request,exb.getActionErrors());
            //forward = "failure";
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
            //forward = "failure";
        }
        logger.info("Performing menu click operation complete");
        //forward = (type.equals("resume"))?"resume":"timesheet";
        switch( folderDocInfo.getRtsType() ){
          case 1:
          forward= "resume";
          break;
          
          case 2:
          forward= "timesheet";
          break;
          
          case 3:
          forward= "resumeDrawer";
          break;
          
          case 4:
          forward= "timesheetDrawer";
          break;

          case 5:
          forward= "personal";
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
