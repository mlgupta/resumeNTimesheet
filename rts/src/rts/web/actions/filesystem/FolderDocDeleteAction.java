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
 * $Id: FolderDocDeleteAction.java,v 1.11 2005/10/03 12:48:37 suved Exp $
 *****************************************************************************
 */

package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.beans.user.UserInfo;
import rts.web.beans.filesystem.Treeview;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.actionforms.filesystem.*;
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
 * Purpose : To perform delete operation    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   06-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class FolderDocDeleteAction extends Action  {
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
  logger.info("Deleting Folders And Document...");
  
  //variable declaration
  ExceptionBean exceptionBean;
  String forward = null;
  HttpSession httpSession = null;
  FolderDocInfo folderDocInfo = null;
  Treeview treeview = null;
  UserPreferences userPreferences = null;
  try{
      httpSession = request.getSession(false);            
      FolderDocListForm folderDocListForm = (FolderDocListForm)form;
      logger.debug("folderDocListForm : " + folderDocListForm);
      UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
      folderDocInfo = (FolderDocInfo)
                                httpSession.getAttribute("FolderDocInfo");
      userPreferences = (UserPreferences)
                                httpSession.getAttribute("UserPreferences");

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
                                
     // Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");

      DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
      Long[] selectedFolderDocIds = folderDocListForm.getChkFolderDocIds();
      logger.debug("selectedFolderDocIds.length : " 
                    + selectedFolderDocIds.length);
          
      FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
      boolean recursively = true;
      logger.debug("recursively : " + recursively);
      if(recursively){
          logger.info("Folder will be deleted recursively");
      }
      /* delete selected drawer(s)/document(s) */
      folderDoc.deleteFolderDoc(folderDocInfo,selectedFolderDocIds,
                                recursively,treeview);
      
      ActionMessages actionMessages = new ActionMessages();
      ActionMessage actionMessage = new ActionMessage("msg.DeletePerformed");
      actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
      httpSession.setAttribute("ActionMessages",actionMessages);
          
  }catch(DbsException dex){
      exceptionBean = new ExceptionBean(dex);
      if(dex.containsErrorCode(30659)){
          exceptionBean.setMessageKey(
                        "errors.30659.folderdoc.cannotdelete.checkedout");
      }
      logger.error(exceptionBean.getMessage());
      logger.debug(exceptionBean.getErrorTrace());
      logger.info("Delete Aborted");
      httpSession.setAttribute("ActionErrors",
                                exceptionBean.getActionErrors());
  }catch(Exception ex){
      exceptionBean = new ExceptionBean(ex);
      logger.error(exceptionBean.getMessage());
      logger.debug(exceptionBean.getErrorTrace());
      logger.info("Delete Aborted");
      httpSession.setAttribute("ActionErrors",
                                exceptionBean.getActionErrors());
  }
  logger.info("Deleting Folders And Document complete");
  //forward = ( folderDocInfo.getRtsType() == 1 )?"resume":"timesheet";
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
