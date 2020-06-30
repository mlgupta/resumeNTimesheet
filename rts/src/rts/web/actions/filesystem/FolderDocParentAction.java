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
 * $Id: FolderDocParentAction.java,v 1.9 2005/10/03 13:42:39 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.web.beans.exception.*;
import rts.beans.*;
import rts.web.beans.user.UserInfo;
import rts.web.beans.filesystem.Treeview;
import rts.web.beans.filesystem.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.user.*;
/* Java API */
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* cmsdk API */
import oracle.ifs.beans.*;
import oracle.ifs.adk.filesystem.*;
/* Struts API */
import org.apache.struts.action.*;
import org.apache.log4j.*;

/**
 * Purpose : To facilitate upward navigation in item listing     
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   05-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class FolderDocParentAction extends Action  {
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
        logger.info("Finding Parent");
        
        //variable declaration
        ExceptionBean exceptionBean = null;
        String forward = null;
        FolderDocInfo folderDocInfo = null;
        HttpSession httpSession = null;
        UserPreferences userPreferences = null;
        try{
            httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            
            userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");
                                    
            folderDocInfo = (FolderDocInfo)
                                    httpSession.getAttribute("FolderDocInfo");
            logger.debug("folderDocInfo : " + folderDocInfo);        
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long currentFolderId = folderDocInfo.getCurrentFolderId();
            logger.debug("currentFolderId : " + currentFolderId);
            /* reset navigation-specific data */    
            DbsFolder dbsFolder = (DbsFolder)dbsLibrarySession.getPublicObject(currentFolderId);
            if(!dbsFolder.getAnyFolderPath().equals("/")){ 
                dbsFolder = dbsFolder.getFolderReferences(0);
                currentFolderId = dbsFolder.getId();
                logger.debug("currentFolderId : " + currentFolderId);
                folderDocInfo.setCurrentFolderId(currentFolderId);
                if( (folderDocInfo.getRtsType() == 1) ||  // resumes
                    (folderDocInfo.getRtsType() == 2) ||  // timesheets
                    (folderDocInfo.getRtsType() == 5)){   // personal
                  folderDocInfo.setPageNumber(1);
                }else{ //resumes drawers, timesheets drawers, personal drawers
                  folderDocInfo.setDrawerPageNumber(1);
                }
                folderDocInfo.addFolderDocId(currentFolderId);
                logger.debug("folderDocInfo : " + folderDocInfo);
                folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);
                httpSession.setAttribute("IS_SIMPLE_NAVIGATION","TRUE");
/*                
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage("msg.UpActionPerformed");
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                httpSession.setAttribute("ActionMessages",actionMessages);
*/                
            }else{
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage("msg.CanNotMoveAboveRoot");
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                httpSession.setAttribute("ActionMessages",actionMessages);
            }
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
        logger.info("Finding Parent Complete");
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
