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
 * $Id: B4DocUnZipAction.java,v 1.4 2005/07/15 10:55:29 rajan Exp $
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
 
public class B4DocUnZipAction extends Action  {
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
        logger.info("Performing B4UnZipp action ...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        DbsDocument dbsDocument = null;
        try{
            DocUnZipForm docUnZipForm = (DocUnZipForm)form;
            logger.debug("docZipForm : " + docUnZipForm);
            HttpSession httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            FolderDocInfo folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long documentId = docUnZipForm.getChkFolderDocIds()[0];
            logger.debug("documentId : " + documentId);
            dbsDocument = (DbsDocument)dbsLibrarySession.getPublicObject(documentId);
            String currentFolderPath = folderDocInfo.getCurrentFolderPath();
            String documentName = dbsDocument.getName();
            String extractToFolderName;
            int indexOfDot = documentName.lastIndexOf(".");
            if(indexOfDot > 0){
                extractToFolderName = documentName.substring(0,indexOfDot);
            }else{
                extractToFolderName = documentName;
            }
            if(currentFolderPath.equals("/")){
                docUnZipForm.setHdnExtractToLocation(currentFolderPath + extractToFolderName);
            }else{
                docUnZipForm.setHdnExtractToLocation(currentFolderPath + "/" + extractToFolderName);
            }
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            if(dex.getErrorCode() == 68004){
                exceptionBean.setMessageKey("msg.folder.path.not.found");
            }
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
            forward = "failure";
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
            forward = "failure";
        }
        logger.info("Performing B4UnZipp action complete");
        return mapping.findForward(forward);
    }
}
