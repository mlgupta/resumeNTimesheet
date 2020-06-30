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
 * $Id: FolderDocGoToFolderAction.java,v 1.9 2005/10/03 13:07:19 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.beans.user.UserInfo;
import rts.web.beans.filesystem.Treeview;
import rts.web.beans.filesystem.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.user.*;
/* Java API */
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.struts.action.*;
import org.apache.log4j.*;

/**
 * Purpose : To facilitate navigation by means of an address bar     
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   05-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class FolderDocGoToFolderAction extends Action  {
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
        logger.info("Performing goto operation ...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        UserPreferences userPreferences = null;
        try{
            httpSession = request.getSession(false);
            FolderDocListForm folderDocListForm = (FolderDocListForm)form;
            logger.debug("folderDocListForm : " + folderDocListForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");
                                    
            folderDocInfo = (FolderDocInfo)
                                      httpSession.getAttribute("FolderDocInfo");
                                      
            //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");

            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            String currentFolderPath = folderDocListForm.getTxtAddress();
            logger.debug("currentFolderPath : " + currentFolderPath);
            String homeFolderName = dbsLibrarySession.getUser().getPrimaryUserProfile().getHomeFolder().getName();
            
            logger.info("Verifying Folder Path");
            DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            DbsFolder dbsFolder = null;
            Long currentFolderId = null;
            logger.debug("currentFolderId : " + currentFolderId);

            /* check for path validity in each module */
            /* if current module is "resumes" / "resumes drawers",
             * path must commence with "/companyName/resumes/" */
            if( folderDocInfo.getRtsType() ==1 || folderDocInfo.getRtsType() == 3){
              if( !currentFolderPath.startsWith("/"+folderDocInfo.getRtsBase()+"/"+
                                                userInfo.getResumeFolderName()) ){
                currentFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                                    userInfo.getResumeFolderName();
              }
            }else if(folderDocInfo.getRtsType() ==2 || folderDocInfo.getRtsType() == 4){
            /* if current module is "timesheets" / "timesheets drawers",
             * path must commence with "/companyName/timesheets/" */
              if( !currentFolderPath.startsWith("/"+folderDocInfo.getRtsBase()+"/"+
                                                userInfo.getTimesheetFolderName()) ){
                currentFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                                    userInfo.getTimesheetFolderName();  
              }          
            }else{    // for personal, path must commence with homeFolderName
              if( !currentFolderPath.startsWith("/"+folderDocInfo.getRtsBase()+"/"+
                                                userInfo.getPersonalFolderName()+"/"
                                                +homeFolderName) ){
                                                
                currentFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                                    userInfo.getPersonalFolderName()+"/"+
                                    homeFolderName;  
              }                    
            }
            /* navigate to the path demanded / set */
            dbsFolder =(DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                                            currentFolderPath);
            currentFolderId = dbsFolder.getId();
            folderDocInfo.setCurrentFolderId(currentFolderId);
            folderDocInfo.addFolderDocId(currentFolderId);
            folderDocInfo.setPageNumber(1);
            logger.debug("folderDocInfo : " + folderDocInfo);
            //to update treeview
            //treeview.forAddressBar(currentFolderId);
            logger.info("Verifying Folder Path Complete");
            folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);

            httpSession.setAttribute("IS_SIMPLE_NAVIGATION","TRUE");            
/*            
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.GotoActionPerformed");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
*/                
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            if(dex.containsErrorCode(30619)){
                exceptionBean.setMessageKey("errors.30619.folderdoc.invalidpath");
            }
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
        logger.info("Performing goto operation complete");
        //forward = ( folderDocInfo.getRtsType() == 1 )?"resume":"timesheet";
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
