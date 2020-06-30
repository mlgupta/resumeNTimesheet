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
 * $Id: FolderDocCheckInAction.java,v 1.4 2005/07/15 10:55:29 rajan Exp $
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
 
public class FolderDocCheckInAction extends Action  {
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
        logger.info("Checking In...");

        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        Locale locale = getLocale(request);
        HttpSession httpSession = request.getSession(false);
        if(httpSession != null){
            try{
                //Initializing variables...        
                FolderDocCheckInForm folderDocCheckInForm = (FolderDocCheckInForm)form;
                logger.debug("folderDocCheckInForm : " + folderDocCheckInForm);
                UserInfo userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
                FolderDocInfo folderDocInfo = (FolderDocInfo) httpSession.getAttribute("FolderDocInfo");
                UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            
                DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
                Long currentFolderId = folderDocInfo.getCurrentFolderId();
                logger.debug("currentFolderId : " + currentFolderId);       
            
                Long[] folderDocIds = folderDocCheckInForm.getChkFolderDocIds();
                logger.debug("folderDocIds.length : " + folderDocIds.length);
                String comment = folderDocCheckInForm.getTxaComment();
                logger.debug("comment : " + comment);
                logger.info("Comment : " + comment);
                boolean keepCheckedOut = folderDocCheckInForm.isChkKeepCheckedOut();
                logger.debug("keepCheckedOut : " + keepCheckedOut);
                ActionErrors actionErrors = new ActionErrors();
                ActionMessages actionMessages = new ActionMessages();
                
                Version version  = new Version(dbsLibrarySession);
                if(keepCheckedOut){
                    logger.info("The document will remain checked out");
                }
                switch(version.checkIn(folderDocIds,comment,keepCheckedOut)){
                    case 0:
                        ActionError actionError = new ActionError("msg.CheckInOperationUnsuccessful");
                        actionErrors.add(ActionMessages.GLOBAL_MESSAGE,actionError);
                        httpSession.setAttribute("ActionErrors",actionErrors);
                        break;
                    case 1:
                        ActionMessage actionMessage = new ActionMessage("msg.CheckInOperationSuccessful");
                        actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                        httpSession.setAttribute("ActionMessages",actionMessages);
                        break;

                }
            }catch(DbsException dex){
                exceptionBean = new ExceptionBean(dex);
                logger.error(exceptionBean.getMessage());
                logger.debug(exceptionBean.getErrorTrace());
                /* IFS-30063 is for inability of a user to upload a new version,
                 * due to insufficient access to change PublicObject's 
                 * PolicyBundle. */
                if(dex.containsErrorCode(30063)){
                  exceptionBean.setMessageKey("errors.30063.insufficient.access.tochange.PO's.PB");
                }
                
                if(dex.getErrorCode() == 68017){
                    exceptionBean.setMessageKey("errors.68017.checkedout.byotheruser");
                }
                httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
            }catch(Exception ex){
                exceptionBean = new ExceptionBean(ex);
                logger.error(exceptionBean.getMessage());
                logger.debug(exceptionBean.getErrorTrace());
                httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
            }
        }
        logger.info("Checking In Complete");
        return mapping.findForward(forward);
    }
}
