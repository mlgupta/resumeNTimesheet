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
 * $Id: FolderNewAction.java,v 1.18 2005/10/20 09:54:50 suved Exp $
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
import rts.web.beans.user.UserInfo;
/* Java API */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;
import rts.web.beans.utility.SearchUtil;

/**
 * Purpose : To facilitate new folder creation      
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   07-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class FolderNewAction extends Action  {
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
        logger.info("Creating New Folder");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        UserPreferences userPreferences = null;
        Treeview treeview = null;
        try{
            httpSession = request.getSession(false);
            FolderNewForm folderNewForm = (FolderNewForm)form;
            logger.debug("folderNewForm : " + folderNewForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");
            
            if( userPreferences.getNavigationType() == 
                UserPreferences.TREE_NAVIGATION ){
              /* obtain appropriate treeview */  
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
            //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");

            String currentFolderPath = folderDocInfo.getCurrentFolderPath();
            Long currentFolderId = folderDocInfo.getCurrentFolderId();
            logger.debug("currentFolderId : " + currentFolderId);
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            
            FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
            String folderName = folderNewForm.getHdnFolderName().trim();
            String folderDesc = null;
            int rtsType = folderDocInfo.getRtsType();
            /* append folderDesc withh appropriate prefix */
            switch( rtsType ){
              case 1:
                      folderDesc = "R"+folderNewForm.getHdnFolderDesc();
                      break;
                      
              case 2:
                      folderDesc = "T"+folderNewForm.getHdnFolderDesc();
                      break;
                      
              case 3:
                      folderDesc = "R"+folderNewForm.getHdnFolderDesc();
                      break;
                      
              case 4:
                      folderDesc = "T"+folderNewForm.getHdnFolderDesc();
                      break;

              case 5:
                      folderDesc = "P"+folderNewForm.getHdnFolderDesc();
                      break;
                      
              case 6:
                      folderDesc = "P"+folderNewForm.getHdnFolderDesc();
                      break;
             default:
                      break;
            }
            logger.debug("folderDesc : " + folderDesc);
            DbsFolder newFolder = null;
            
            /* create new Folder */
            if(folderName.trim().length() != 0){
                folderDoc.newFolder(folderName,folderDesc,currentFolderId,treeview);
                newFolder = folderDoc.getFolderWhenAdded();
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
                    newFolder.setAcl(compACL);
                  }
                }else{    // for folder in user's home folder,set ACL to private
                  DbsAccessControlList privateACL = (DbsAccessControlList)
                                                      SearchUtil.findObject(
                                                      dbsLibrarySession,
                                                      DbsAccessControlList.CLASS_NAME,
                                                      "Private");
                  newFolder.setAcl(privateACL);
                }
                logger.debug("ACL applied to the new Folder is: "+
                              newFolder.getAcl().getName());
                              
                logger.info("Folder's FullPath : " + currentFolderPath + "/" + folderName );
            }else{
                ExceptionBean exb = new ExceptionBean();
                exb.setMessage("Folder Name Can't be blank");
                exb.setMessageKey("errors.foldername.required");
                throw exb;
            }
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.NewFolderCreated");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
                
        }catch(ExceptionBean exb){
            logger.error(exb.getMessage());
            httpSession.setAttribute("ActionErrors",exb.getActionErrors());
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            /*if( dex.containsErrorCode(68005) ){
              exceptionBean.setMessage("Drawer already exists...");
            }*/
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            logger.info("Creation Aborted");
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            logger.info("Creation Aborted");                    
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
        logger.info("Creating New Folder Complete");
        
//        forward = (folderDocInfo.getRtsType() == 1)?"resume":"timesheet";
        switch( folderDocInfo.getRtsType() ){
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
