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
 * $Id: FolderDocCancleCheckoutAction.java,v 1.4 2005/07/15 10:55:29 rajan Exp $
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
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;
/* Strust API */
import org.apache.log4j.*;
import org.apache.struts.action.*;
 
public class FolderDocCancleCheckoutAction extends Action  {
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
        logger.info("Performing Cancle Checkout Operation");

        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        Locale locale = getLocale(request);
        HttpSession httpSession = request.getSession(false);
        if(httpSession != null){
            try{
                logger.debug("Initializing variables...");      
                FolderDocListForm folderDocListForm = (FolderDocListForm)form;
                logger.debug("folderDocListForm : " + folderDocListForm);
                UserInfo userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
                FolderDocInfo folderDocInfo = (FolderDocInfo) httpSession.getAttribute("FolderDocInfo");
                UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            
                DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
                Long currentFolderId = folderDocInfo.getCurrentFolderId();
                logger.debug("currentFolderId : " + currentFolderId);
            
                Long[] folderDocIds = folderDocListForm.getChkFolderDocIds();
                logger.debug("folderDocIds.length : " + folderDocIds.length);
                ActionMessages actionMessages = new ActionMessages();
                ActionErrors actionErrors = new ActionErrors();
                
                Version version  = new Version(dbsLibrarySession);
                switch(version.cancelCheckout(folderDocIds)){
                    case 0:
                        ActionError actionError = new ActionError("msg.CancelCheckOutOperationUnsuccessful");
                        actionErrors.add(ActionMessages.GLOBAL_MESSAGE,actionError);
                        httpSession.setAttribute("ActionErrors",actionErrors);
                        break;
                    case 1:
                        ActionMessage actionMessage = new ActionMessage("msg.CancelCheckOutOperationSuccessful");
                        actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                        httpSession.setAttribute("ActionMessages",actionMessages);
                        break;
                }
                
            }catch(DbsException dex){
                exceptionBean = new ExceptionBean(dex);
                logger.error(exceptionBean.getMessage());
                logger.debug(exceptionBean.getErrorTrace());
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
        logger.info("Performing Cancle Checkout Operation Complete");
        return mapping.findForward(forward);
    }
}
