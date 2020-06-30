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
 * $Id: ToggleFolderSearchAction.java,v 1.9 2005/09/12 06:39:30 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/*rts package references*/
import rts.web.actions.filesystem.AdvanceSearchAction;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
import rts.web.beans.user.UserPreferences;
/*java API*/
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/*Struts API*/
import org.apache.log4j.*;
import org.apache.struts.action.*;

     /**
      * Purpose : Action code to toggle between search and folderdoc listing.
      */

 
public class ToggleFolderSearchAction extends Action  {
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
        logger.info("Toggling between treeview and searchview");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        UserPreferences userPreferences = null;
        try{
        
            httpSession = request.getSession(false);
            AdvanceSearchForm advanceSearchForm = (AdvanceSearchForm)form;
            logger.info("AdvanceSearchForm:"+advanceSearchForm);
            advanceSearchForm.setTxtFolderOrDocName(new String());
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");            
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            logger.debug("folderDocInfo : " + folderDocInfo);
            
            /* if tree visible toggle to search */
            if(folderDocInfo.isTreeVisible()){
                folderDocInfo.setTreeVisible(false);
                advanceSearchForm.setTxtLookinFolderPath(folderDocInfo.getCurrentFolderPath());
                logger.info("AdvanceSearchForm after setTxtLookinFolderPath"+advanceSearchForm);
                ResetBean resetBean=new ResetBean();
                advanceSearchForm=resetBean.resetAll(advanceSearchForm,userPreferences.getNavigationType());
                logger.info("AdvanceSearchForm in Toggle now: "+advanceSearchForm);                
                httpSession.setAttribute("advanceSearchForm",advanceSearchForm);
            }
            /* toggle from search to tree */
            else{
                folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);
                folderDocInfo.setTreeVisible(true);
                httpSession.removeAttribute("advanceSearchForm");
            }
            
            logger.debug("folderDocInfo : " + folderDocInfo);
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            logger.info("Creation Aborted");                    
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
        logger.info("Toggling between treeview and searchview complete");
        //forward = ( folderDocInfo.getRtsType() ==1 )?"resume":"timesheet";
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
