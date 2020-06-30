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
 * $Id: FolderDocB4RenameAction.java,v 1.8 2005/10/20 09:55:18 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.user.*;
import rts.beans.*;
import rts.web.beans.user.UserInfo;
import rts.web.beans.filesystem.Treeview;
/* Java API */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;
/* Struts API */
import org.apache.struts.action.*;
import org.apache.log4j.*;

/**
 * Purpose : To populate controls of folder_rename.jsp     
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   07-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class FolderDocB4RenameAction extends Action  {
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
        logger.debug("Building Rename List...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        Locale locale = getLocale(request);
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        try{
            httpSession = request.getSession(false);
            DrawerListForm drawerListForm = (DrawerListForm)form;
            logger.debug("folderDocListForm : " + drawerListForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long[] selectedFolderDocIds = drawerListForm.getChkFolderDocIds();
            logger.debug("selectedFolderDocIds.length : " + selectedFolderDocIds.length);
                
            FolderDocList folderDocList;
            DbsPublicObject dbsPublicObject;
            ArrayList folderDocRenameLists = new ArrayList();
            /* for a given array of selected folders, obtain their name and desc */
            for(int counter = 0; counter < selectedFolderDocIds.length; counter++){
                folderDocList = new FolderDocList();
                dbsPublicObject = dbsLibrarySession.getPublicObject(selectedFolderDocIds[counter]);
                Long publicObjectId = dbsPublicObject.getId();
                logger.debug("publicObjectId : " + publicObjectId);
                folderDocList.setId(publicObjectId);             
                /* obtain name */
                String publicObjectName = dbsPublicObject.getName();
                logger.debug("publicObjectName : " + publicObjectName);
                folderDocList.setName(publicObjectName);
                /* obatin description */
                String publicObjectDesc = dbsPublicObject.getDescription();
                logger.debug("publicObjectDesc : " + publicObjectDesc);
                String tempDesc = null;
                if( publicObjectDesc != null ){
                  int rtsType = folderDocInfo.getRtsType();
                  if( rtsType == 1 || rtsType == 3){  // append "R" for resumes
                    tempDesc = publicObjectDesc.replaceFirst("R","");
                  }else if(rtsType == 2 || rtsType == 4){ // append "T" for timesheets
                    tempDesc = publicObjectDesc.replaceFirst("T","");
                  }else{                              // append "P" for personal
                    tempDesc = publicObjectDesc.replaceFirst("P","");
                  }
                }else{      // just to handle cases pertaining to old files
                  tempDesc = "";
                }
                if(tempDesc.trim().length() == 0 ){
                  folderDocList.setDescription("");
                }else{
                  folderDocList.setDescription(tempDesc);
                }
                folderDocRenameLists.add(folderDocList);
            }
            logger.debug("folderDocRenameLists.size() : " + folderDocRenameLists.size());
            request.setAttribute("folderDocRenameLists", folderDocRenameLists );
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            saveErrors(request,exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            saveErrors(request,exceptionBean.getActionErrors());
        }
        logger.debug("Building Complete");
        return mapping.findForward(forward);
    }
}
