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
 * $Id: DocHistoryDetailAction.java,v 1.7 2005/10/03 11:44:17 suved Exp $
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
 
public class DocHistoryDetailAction extends Action  {
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
        logger.info("Fetching Document History Detail...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        FolderDocInfo folderDocInfo = null;
        
        try{
            HttpSession httpSession = request.getSession(false);
            DocHistoryListForm docHistoryListForm = (DocHistoryListForm)form;
            logger.debug("docHistoryListForm : " + docHistoryListForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long docId = docHistoryListForm.getRadDocId();
            logger.debug("docId : " + docId);
            Long familyId = docHistoryListForm.getChkFolderDocIds()[0];
            logger.debug("familyId : " + familyId);

            DbsPublicObject publicObject = dbsLibrarySession.getPublicObject(familyId);
            logger.info("Family Name : " + publicObject.getName());
            /* obtain version detail here */
            Version version = new Version(dbsLibrarySession);
            DocumentHistoryDetail documentHistoryDetail = version.getVersionedDocProperty(familyId,docId,(byte)folderDocInfo.getRtsType());
            request.setAttribute("documentHistoryDetail",documentHistoryDetail);
            
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }
        logger.info("Fetching Document History Detail Complete");
        forward = ( folderDocInfo.getRtsType() == 1 )?"resume":"timesheet";
        return mapping.findForward(forward);
    }
}
