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
 * $Id: FolderDocCheckOutAction.java,v 1.4 2005/07/15 10:55:29 rajan Exp $
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
 
public class FolderDocCheckOutAction extends Action  {
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
        logger.info("Checking Out...");

        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        Locale locale = getLocale(request);
        HttpSession httpSession = request.getSession(false);
        if(httpSession != null){
            try{
                //Initializing variables...        
                FolderDocCheckOutForm folderDocCheckOutForm = (FolderDocCheckOutForm)form;
                logger.debug("folderDocCheckOutForm : " + folderDocCheckOutForm);
                UserInfo userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
                FolderDocInfo folderDocInfo = (FolderDocInfo) httpSession.getAttribute("FolderDocInfo");
                UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            
                DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
                Long currentFolderId = folderDocInfo.getCurrentFolderId();
                logger.debug("currentFolderId : " + currentFolderId);
                
                Long[] folderDocIds = folderDocCheckOutForm.getChkFolderDocIds();
                logger.debug("folderDocIds.length : " + folderDocIds.length);
                String comment = folderDocCheckOutForm.getTxaComment();
                logger.debug("comment : " + comment);
                logger.info("Comment : " + comment);
                
                Version version  = new Version(dbsLibrarySession);
                ActionMessages actionMessages = new ActionMessages();
                ActionErrors actionErrors = new ActionErrors();
                ActionMessage actionMessage = null;
                int resultCode = version.checkOut(folderDocIds,comment);
                switch (resultCode){
                    case 0:
                        ActionError actionError = new ActionError("msg.CheckOutOperationUnsuccessful");
                        actionErrors.add(ActionMessages.GLOBAL_MESSAGE,actionError);
                        httpSession.setAttribute("ActionErrors",actionErrors);
                        break;
                    case 1:
                        actionMessage = new ActionMessage("msg.CheckOutOperationSuccessful");
                        actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                        httpSession.setAttribute("ActionMessages",actionMessages);
                        break;
                }
                
            }catch(DbsException dex){
                exceptionBean = new ExceptionBean(dex);
                /* IFS-34611 is for inability of a user to upload a new version,due 
                 * to access rights limitations viz:-inability to chkout a ver doc. */                
                if(dex.containsErrorCode(34611)){
                  exceptionBean.setMessageKey("errors.34611.error.reserving.version.series");
                }
                logger.error(exceptionBean.getMessage());
                httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
            }catch(Exception ex){
                exceptionBean = new ExceptionBean(ex);
                logger.error(exceptionBean.getMessage());
                httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
            }
        }
        logger.info("CheckOut  Complete");
        return mapping.findForward(forward);
    }
}
