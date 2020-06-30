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
 * $Id: B4DocZipAction.java,v 1.4 2005/07/15 10:55:29 rajan Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.crypto.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
/* Java API */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.zip.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;
 
public class B4DocZipAction extends Action  {
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
        logger.info("Preforming B4UnZipAction ...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        DbsDocument dbsDocument = null;
        try{
            DocZipForm docZipForm = (DocZipForm)form;
            logger.debug("docZipForm : " + docZipForm);
            HttpSession httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long documentId = docZipForm.getChkFolderDocIds()[0];
            logger.debug("documentId : " + documentId);
            String zipFileName = dbsLibrarySession.getPublicObject(documentId).getName();
            logger.debug("zipFileName : " + zipFileName);
            int indexOfDot = zipFileName.lastIndexOf(".");
            if( indexOfDot == -1){
                indexOfDot = zipFileName.length();
            }
            docZipForm.setHdnZipFileName(zipFileName.substring(0,indexOfDot) + ".zip");
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }
        logger.info("Preforming B4UnZipAction complete");
        return mapping.findForward(forward);
    }
}
