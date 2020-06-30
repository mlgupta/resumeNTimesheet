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
 * $Id: FolderDocCutAction.java,v 1.10 2005/10/03 12:46:35 suved Exp $
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
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;

/**
 * Purpose : To perform cut operation    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   06-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class FolderDocCutAction extends Action  {
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
        logger.info("Storing \"Folder Or Doc to Cut\" in the buffer");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        Integer prevRtsType = null;
        UserPreferences userPreferences = null;
        try{
            httpSession = request.getSession(false);
            FolderDocListForm folderDocListForm = (FolderDocListForm)form;
            logger.debug("folderDocListForm : " + folderDocListForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");
            /* set ClipBoard to folderDocIds, ClipBoardContent to operation CUT */
            folderDocInfo.setClipBoard(folderDocListForm.getChkFolderDocIds());
            folderDocInfo.setClipBoardContent(FolderDocInfo.CLIPBOARD_CONTENT_CUT);
            /* set prevRtsType to current rtsType, so that this value can be  
             * compared with the rtsType during paste operation to prevent 
             * illegal paste. */            
            prevRtsType = new Integer(folderDocInfo.getRtsType());
            httpSession.setAttribute("prevRtsType",prevRtsType);
            logger.debug("folderDocInfo : " + folderDocInfo);
            
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.CutPerformed");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
                
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
        logger.info("Storing \"Folder Or Doc to Cut\" in the buffer complete");
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
