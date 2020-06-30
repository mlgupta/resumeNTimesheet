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
 * $Id: DocHistoryListAction.java,v 1.8 2005/10/03 11:45:27 suved Exp $
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
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.struts.action.*;
import org.apache.log4j.*;
 
public class DocHistoryListAction extends Action  {

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
        logger.info("Fetching document history...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";

        ArrayList documentHistoryDetails;
        try{
            DocHistoryListForm docHistoryListForm = (DocHistoryListForm)form;
            logger.debug("docHistoryListForm : " + docHistoryListForm);
            HttpSession httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            FolderDocInfo folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");            
            //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");

            Long docId = docHistoryListForm.getChkFolderDocIds()[0];
            logger.debug("docId : " + docId);
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            int recordPerPage = userPreferences.getRecordsPerPage();
            logger.debug("recordPerPage : " + recordPerPage);
            int pageNumber = docHistoryListForm.getTxtHistoryPageNo();
            
            if(pageNumber == 0 ){
                pageNumber = 1;
                docHistoryListForm.setTxtHistoryPageNo(pageNumber);
            }
            logger.debug("pageNumber : " + pageNumber);
            
            String documentName = dbsLibrarySession.getPublicObject(docId).getName();
            logger.debug("documentName : " + documentName);
            docHistoryListForm.setDocumentName(documentName);
            
            /* obtain version history list here */
            Version version = new Version(dbsLibrarySession);
            documentHistoryDetails = version.getDocumentHistoryDetails(docId);

            int startIndex = recordPerPage * (pageNumber - 1) ;
            
            int endIndex = startIndex + recordPerPage;
            logger.debug("startIndex : " + startIndex);
            
            if(endIndex > documentHistoryDetails.size()){
                endIndex = documentHistoryDetails.size();
            }
            logger.debug("endIndex : " + endIndex);
            
            int pageCount = (int)StrictMath.ceil((double)documentHistoryDetails.size() / recordPerPage);
            if(pageCount > 0){
                docHistoryListForm.setTxtHistoryPageCount(pageCount);
            }else{
                docHistoryListForm.setTxtHistoryPageCount(1);
            }
            logger.debug("pageCount : " + pageCount);
            
            int itemCount = documentHistoryDetails.size();
            logger.debug("itemCount : " + itemCount);
            
            for(int index = endIndex; index < itemCount; index++){
                documentHistoryDetails.remove(endIndex);
            }

            for(int index = 0; index < startIndex; index++){
                documentHistoryDetails.remove(0);
            }
            
            logger.debug("docHistoryListForm : " + docHistoryListForm);
            
            request.setAttribute("documentHistoryDetails",documentHistoryDetails);
            request.setAttribute("rtsType",new Integer(folderDocInfo.getRtsType()));

            ActionErrors actionErrors = (ActionErrors)httpSession.getAttribute("ActionErrors");
            if(actionErrors != null){
                logger.debug("Saving action error in request stream");
                saveErrors(request,actionErrors);
                httpSession.removeAttribute("ActionErrors");
            }else{                        
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage("msg.DocumentHistory");
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                saveMessages(request,actionMessages);
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
        
        logger.info("Fetching document history complete");
        return mapping.findForward(forward);
        
    }
}
