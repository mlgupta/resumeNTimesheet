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
 * $Id: DocDownloadAction.java,v 1.9 2006/01/06 10:25:07 suved Exp $
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
/* Oracle API */
import oracle.ifs.beans.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;

/**
 * Purpose  : To download  the document selected.
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   08-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
 */
 
public class DocDownloadAction extends Action  {
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
        logger.info("Downloading File ...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        DbsDocument dbsDocument = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        
        try{
            FolderDocListForm folderDocListForm = (FolderDocListForm)form;
            logger.debug("folderDocListForm : " + folderDocListForm);
            HttpSession httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            FolderDocInfo folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            /*obtain selected doc-id */
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long documentId = folderDocListForm.getDocumentId();
            logger.debug("documentId : " + documentId);

            DbsPublicObject dbsPublicObject = dbsLibrarySession.getPublicObject(documentId).getResolvedPublicObject();
            if(dbsPublicObject instanceof DbsDocument){
                dbsDocument = (DbsDocument)dbsPublicObject;

                logger.info("Document Name : " + dbsDocument.getName());
                inputStream = dbsDocument.getContentStream();
                /* obtain document format to response setContentType */
                DbsFormat format = dbsDocument.getFormat();
                if(format!=null){
                
                  String mimeType;
                  if(folderDocListForm.isBlnDownload()){
                      mimeType = "application/octet-stream";
                  }else{
                      mimeType = format.getMimeType();
                  }
                  /* setup the corr. response */            
                  logger.debug("dbsDocument.getFormat().getMimeType() : " + mimeType);
                  /* set response contentType */
                  response.setContentType(mimeType);
                  response.setHeader("Content-Disposition","filename=\""+ dbsDocument.getName() + "\"");           
                  int contentSize = (int)dbsDocument.getContentSize();
                  logger.debug("Document Size : " + contentSize);
                  /* set response content size */
                  response.setContentLength(contentSize);
                  byte[] content = new byte[contentSize];
                  inputStream.read(content,0,contentSize);
                  inputStream.close();
                  inputStream = null;
                  /* obtain and write o/p stream to response */
                  outputStream = response.getOutputStream();
                  outputStream.write(content);
  
  /*
                  ActionMessages actionMessages = new ActionMessages();
                  ActionMessage actionMessage = new ActionMessage("msg.document.name", dbsDocument.getName());
                  actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                  httpSession.setAttribute("ActionMessages" , actionMessages);
  */                
                  outputStream.close();
                  outputStream = null;
                  response.flushBuffer();
                  
                  /*DocEventLogBean logBean = new DocEventLogBean();
                  logBean.logEvent(dbsLibrarySession,dbsDocument.getId(),"Doc Downloaded");*/
                  logger.info("Downloading File Complete");
                }else{
                  logger.error("Format is null for "+dbsDocument.getName());
                  logger.error("Unable to download file");
                }
            }
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }finally{
          if( inputStream != null ){
            inputStream.close();
          }
          if( outputStream != null ){
            outputStream.close();
          }
        }
        
        return null; //mapping.findForward(forward);
    }
}
