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
 * $Id: FolderDocForwardAction.java,v 1.9 2005/10/03 12:59:14 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
/* Java API */ 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;

/**
 * Purpose : To facilitate forward navigation in item listing     
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   05-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class FolderDocForwardAction extends Action  {
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
        logger.info("Fetching Next Folder Path...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        UserPreferences userPreferences = null;
        
        try{
            httpSession = request.getSession(false);
            userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");
            folderDocInfo = (FolderDocInfo)
                                    httpSession.getAttribute("FolderDocInfo");
            logger.debug("folderDocInfo : " + folderDocInfo);            
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();

            /* reset navigation-specific data */
            if(folderDocInfo.getListingType() != FolderDocInfo.DISPLAY_PAGE){
                Long folderDocId = folderDocInfo.getNextFolderDocId();
                if(folderDocId == null){
                    ExceptionBean exb = new ExceptionBean();
                    exb.setMessage("Home Folder Deleted");
                    exb.setMessageKey("errors.homefolder.delete");
                    throw exb;
                }
                DbsPublicObject dbsPublicObject = dbsLibrarySession.getPublicObject(folderDocId);
                folderDocInfo.setCurrentFolderId(folderDocId);
                if( (folderDocInfo.getRtsType() == 1) ||    // resumes
                    (folderDocInfo.getRtsType() == 2) ||    // timesheets
                    (folderDocInfo.getRtsType() == 5)){     // personal
                  folderDocInfo.setPageNumber(1);
                }else{  //resumes drawers, timesheets drawers, personal drawers
                  folderDocInfo.setDrawerPageNumber(1);
                }
                
            }
            folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);            
            httpSession.setAttribute("IS_SIMPLE_NAVIGATION","TRUE");
/*            
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.ForwardActionPerformed");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
*/            

        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }catch(ExceptionBean exb){
            logger.error(exb.getMessage());
            saveErrors(request,exb.getActionErrors());
            forward = "failure";
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.info(exceptionBean.getMessage());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
           
        logger.info("Fetching Next Folder Path Complete");
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
