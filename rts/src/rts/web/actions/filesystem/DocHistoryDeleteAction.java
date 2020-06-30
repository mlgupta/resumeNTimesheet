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
 * $Id: DocHistoryDeleteAction.java,v 1.4 2005/07/15 10:55:29 rajan Exp $
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
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;
 
public class DocHistoryDeleteAction extends Action  {
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
        logger.info("Deleting Document From Document History...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        HttpSession httpSession = null;        
        try{
            httpSession = request.getSession(false);
            DocHistoryListForm docHistoryListForm = (DocHistoryListForm)form;
            logger.debug("docHistoryListForm : " + docHistoryListForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");

            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long docId = docHistoryListForm.getRadDocId();
            logger.debug("docId : " + docId);
            Long familyId = docHistoryListForm.getChkFolderDocIds()[0];
            logger.debug("familyId : " + familyId);

            DbsPublicObject publicObject = dbsLibrarySession.getPublicObject(docId);
            logger.info("Deleting Version Number " + publicObject.getVersionNumber() + " of " + publicObject.getName());
            //Call delete
            Version version = new Version(dbsLibrarySession);
            version.deleteDocHistory(docId);
                    
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.SelectedVersionDeleted");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
                
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            if(dex.containsErrorCode(30033)){
              exceptionBean.setMessageKey("errors.30033.insufficient.access.to.delete.or.free.a.PO");
            }
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
            logger.info("Delete Aborted");
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
            logger.info("Delete Aborted");
        }
        logger.info("Deleting Document From Document History Complete");
        return mapping.findForward(forward);
    }
}
