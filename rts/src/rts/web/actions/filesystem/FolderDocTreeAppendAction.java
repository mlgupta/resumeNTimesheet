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
 * $Id: FolderDocTreeAppendAction.java,v 1.5 2005/09/12 06:38:54 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.FolderDocInfo;
import rts.web.beans.filesystem.Treeview;
import rts.web.beans.user.UserInfo;
/* Java API */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;
import rts.web.beans.user.UserPreferences;
 
public class FolderDocTreeAppendAction extends Action  {
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
        logger.info("Performing Folder Doc Tree Append Action ...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        HttpSession httpSession = null;
        UserPreferences userPreferences = null;
        Treeview treeview = null;
        FolderDocInfo folderDocInfo = null;
        try{
            httpSession = request.getSession(false);
            FolderDocListForm folderDocListForm = (FolderDocListForm)form;
            logger.debug("folderDocListForm : " + folderDocListForm);
            userPreferences = (UserPreferences)
                                   httpSession.getAttribute("UserPreferences");
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo =(FolderDocInfo)
                                      httpSession.getAttribute("FolderDocInfo");
            
            switch( folderDocInfo.getRtsType() ){
              case 1:
                      treeview = (userPreferences.getNavigationType() == 
                                UserPreferences.FLAT_NAVIGATION)?
                                (Treeview)httpSession.getAttribute("Treeview4Select"):
                                (Treeview)httpSession.getAttribute("Treeview4List");
                      break;
              
              case 2:
                      treeview = (userPreferences.getNavigationType() == 
                                UserPreferences.FLAT_NAVIGATION)?
                                (Treeview)httpSession.getAttribute("Treeview4Timesheet"):
                                (Treeview)httpSession.getAttribute("Treeview4TimesheetList");
                      break;
              
              case 3:
                      treeview =(Treeview) 
                                httpSession.getAttribute("Treeview4Select");
                      break;
              
              case 4:
                      treeview =(Treeview) 
                                httpSession.getAttribute("Treeview4Timesheet");
                      break;
    
              case 5:
                      treeview = (userPreferences.getNavigationType() == 
                                UserPreferences.FLAT_NAVIGATION)?
                                (Treeview)httpSession.getAttribute("Treeview4Personal"):
                                (Treeview)httpSession.getAttribute("Treeview4PersonalList");
                      break;
              
              case 6:
                      treeview =(Treeview) 
                                httpSession.getAttribute("Treeview4Personal");
                      break;
              
              default:
                        break;
            }
                                      
            //treeview = (Treeview)httpSession.getAttribute("Treeview");
            
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long currentFolderId = folderDocListForm.getCurrentFolderId();
            logger.debug("currentFolderId : " + currentFolderId);
            treeview.appendTree(currentFolderId);
            
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
        logger.info("Performing Folder Doc Tree Append Action Complete");

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
