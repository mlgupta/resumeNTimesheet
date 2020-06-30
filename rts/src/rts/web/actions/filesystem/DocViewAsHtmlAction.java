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
 * $Id: DocViewAsHtmlAction.java,v 1.4 2005/07/15 10:55:29 rajan Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.user.UserInfo;
/* Java API */
import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;
 
public class DocViewAsHtmlAction extends Action  {
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
        logger.info("View File as Html...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        DbsDocument dbsDocument = null;
        try{
            FolderDocListForm folderDocListForm = (FolderDocListForm)form;
            logger.debug("folderDocListForm : " + folderDocListForm);
            HttpSession httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long documentId = (folderDocListForm.getChkFolderDocIds())[0];
            logger.debug("documentId : " + documentId);

            DbsPublicObject dbsPublicObject = dbsLibrarySession.getPublicObject(documentId).getResolvedPublicObject();
            if(dbsPublicObject instanceof DbsDocument){
                dbsDocument = (DbsDocument)dbsPublicObject;

                dbsDocument.filterContent(false);
                logger.info("Document Name : " + dbsDocument.getName());
                Reader reader = dbsDocument.getFilteredContent();
                response.setContentType("text/html");
                OutputStream outputStream = response.getOutputStream();
                int contentSize = 0;
                int ch = reader.read();
                while(ch != -1){
                    contentSize++;
                    outputStream.write(ch);
                    ch = reader.read();
                }
                logger.debug("Document Size : " + contentSize);
                response.setContentLength(contentSize);
                response.flushBuffer();
            }
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }
        logger.info("View File as Html complete ...");
        return null; //mapping.findForward(forward);
    }
}
