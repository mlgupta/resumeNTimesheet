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
 * $Id: B4DocUploadAction.java,v 1.11 2005/11/25 14:07:16 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;

import rts.beans.DbsAttributeValue;
import rts.beans.DbsDirectoryGroup;
import rts.beans.DbsException;
import rts.beans.DbsLibrarySession;
import rts.web.actionforms.filesystem.DocUploadForm;
import rts.web.beans.filesystem.FolderDocInfo;
import rts.web.beans.user.*;

/* Java API */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/* Struts API */
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.log4j.*;

/**
 * Purpose : To set specific values prior to resume uploading    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   28-06-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class B4DocUploadAction extends Action  {
    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Initialize logger
        HttpSession httpSession = null;
        DbsAttributeValue dbsAttrVal = null;
        UserInfo userInfo = null;
        DbsLibrarySession dbsLibrarySession = null;
        Logger logger = Logger.getLogger("DbsLogger");
        logger.info("Serving JSP for Upload");
        DocUploadForm docUploadForm = null;
        FolderDocInfo folderDocInfo = null;
        
        try{

          httpSession = request.getSession(false);

          userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
          
          folderDocInfo = (FolderDocInfo)
                                      httpSession.getAttribute("FolderDocInfo");

          dbsLibrarySession = userInfo.getDbsLibrarySession();
          
          docUploadForm = new DocUploadForm(); 
          /* obtain the groups with whom the current user is associated */
          DbsDirectoryGroup[] listOfGroups = 
                                      dbsLibrarySession.getUser().getAllAncestors();
          
          if( listOfGroups !=null ){
          /* obtain the values of custom labels set for the group currently in use */
          try{
          
            dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM1_LBL");
            logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
            
            docUploadForm.setTxtCustom1Lbl(
                                (dbsAttrVal != null && 
                                dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                                dbsAttrVal.getString(dbsLibrarySession):"Custom1_Label");
            
    
            dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM2_LBL");
            logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
            
            docUploadForm.setTxtCustom2Lbl(
                                (dbsAttrVal != null && 
                                dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                                dbsAttrVal.getString(dbsLibrarySession):"Custom2_Label");
                                
            dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM3_LBL");
            logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
            
            docUploadForm.setTxtCustom3Lbl(
                                (dbsAttrVal != null && 
                                dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                                dbsAttrVal.getString(dbsLibrarySession):"Custom3_Label");
                                
    
            dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM4_LBL");
            logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
            
            docUploadForm.setTxtCustom4Lbl(
                                (dbsAttrVal != null && 
                                dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                                dbsAttrVal.getString(dbsLibrarySession):"Custom4_Label");
          
          }catch( DbsException dbsEx ){
            logger.error("Error : "+dbsEx.getErrorMessage());
            docUploadForm.setTxtCustom1Lbl("Custom1_Label");
            docUploadForm.setTxtCustom2Lbl("Custom2_Label");
            docUploadForm.setTxtCustom3Lbl("Custom3_Label");
            docUploadForm.setTxtCustom4Lbl("Custom4_Label");
          }
                
  
          }else{
            docUploadForm.setTxtCustom1Lbl("Custom1_Label");
            docUploadForm.setTxtCustom2Lbl("Custom2_Label");
            docUploadForm.setTxtCustom3Lbl("Custom3_Label");
            docUploadForm.setTxtCustom4Lbl("Custom4_Label");
          }              
        String [] folderReferencesPath = new String[1];  
        folderReferencesPath[0] = folderDocInfo.getCurrentFolderPath();
        /* set the path where this doc is to be uploaded */
        //docUploadForm.setLstAttachment(null);
        //docUploadForm.setTxtCommunicationSkill("GOOD");
        }catch( DbsException dbsEx ){
          dbsEx.printStackTrace();
        }
        request.setAttribute("fromClient",new Boolean(false));
        logger.debug("docUploadForm : "+docUploadForm);
        request.setAttribute("docUploadForm",docUploadForm);
        logger.info("Serving JSP for Upload Complete");
        return mapping.findForward("success");
    }
}
