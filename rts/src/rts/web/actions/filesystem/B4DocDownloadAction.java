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
 * $Id: B4DocDownloadAction.java,v 1.8 2005/10/03 10:17:58 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/*rts Package references*/
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
/*java API*/
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/*Struts API*/
import org.apache.log4j.*;
import org.apache.struts.action.*;
/**
 * Purpose : To store specific information of the doc to be downloaded   
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   05-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class B4DocDownloadAction extends Action  {
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
        logger.info("Performing B4 B4DocDownloadAction");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        try{
            httpSession = request.getSession(false);
            FolderDocListForm folderDocListForm = (FolderDocListForm)form;
            logger.debug("folderDocListForm : " + folderDocListForm);
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            logger.debug("folderDocInfo: "+folderDocInfo);
            logger.debug("userPreferences: "+userPreferences);

        /* when doc is to be opened in new window, donot set it's listing type to DISPLAY_PAGE 
            if(userPreferences.getChkOpenDocInNewWin() == UserPreferences.IN_NEW_WINDOW)
              forward= new String("forNewWindow");
            else
              folderDocInfo.setListingType(FolderDocInfo.DISPLAY_PAGE);*/
            
            Long docId = folderDocListForm.getDocumentId();
            logger.debug("docId : " + docId);
            /* keep track of the doc to be downloaded */
            folderDocInfo.setDocId(docId);
            folderDocInfo.addFolderDocId(docId);

            logger.debug("folderDocInfo : " + folderDocInfo);
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            logger.info("Creation Aborted");                    
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
        logger.info("Performing B4 B4DocDownloadAction complete");
        forward = "forNewWindow";
        return mapping.findForward(forward);
    }
}
