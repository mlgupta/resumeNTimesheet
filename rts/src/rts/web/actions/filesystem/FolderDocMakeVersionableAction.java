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
 * $Id: FolderDocMakeVersionableAction.java,v 1.12 2005/10/20 09:54:50 suved Exp $
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
import rts.web.beans.utility.SearchUtil;

/**
 *	Purpose: To make a document versionable
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    18-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
 
public class FolderDocMakeVersionableAction extends Action  {
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
        logger.info("Making document versionable");

        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        Locale locale = getLocale(request);
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        UserPreferences userPreferences = null;
        
        try{
            logger.info("Initializing variables...");
            httpSession = request.getSession(false);
            FolderDocListForm folderDocListForm = (FolderDocListForm)form;
            logger.debug("folderDocListForm : " + folderDocListForm);
            UserInfo userInfo = (UserInfo) httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo) httpSession.getAttribute("FolderDocInfo");
            userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            //dbsLibrarySession.setAdministrationMode(true);

            Long currentFolderId = folderDocInfo.getCurrentFolderId();
            logger.debug("currentFolderId : " + currentFolderId);
            
            Long[] folderDocIds = folderDocListForm.getChkFolderDocIds();
            logger.debug("folderDocIds.length : " + folderDocIds.length);
            String POName = dbsLibrarySession.getPublicObject(
                            folderDocIds[0]).getName();
            
            /* make the selected document versionable ... */
            Version version  = new Version(dbsLibrarySession);
            version.makeVersioned(folderDocIds);
            /*DbsPublicObject dbsPO = (DbsPublicObject)SearchUtil.findObject(
                                                     dbsLibrarySession,"RESUME1",
                                                     POName);
            if( dbsPO != null ){
              DbsFamily dbsFamily = dbsPO.getFamily();
              DbsVersionDescription dbsVd = null;
              DbsVersionSeries dbsVs = null;
              if( dbsFamily != null ){
                logger.debug("Family is not Null...");
                dbsFamily.setAcl(dbsPO.getAcl());
                dbsVs = dbsFamily.getPrimaryVersionSeries();
                dbsVs.setAcl(dbsPO.getAcl());
                dbsVd = dbsVs.getLastVersionDescription();
                dbsVd.setAcl(dbsPO.getAcl());
              }
            }*/
            /* make the selected document versionable ends ... */
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.DocumentMadeVersionable");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
            
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            if(dex.containsErrorCode(30049)){
              exceptionBean.setMessageKey("errors.30049.insufficient.access.to.remove.items");
            }
            if(dex.containsErrorCode(68015)){
              exceptionBean.setMessageKey("errors.68015.item.already.versioned");
            }            
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
        logger.info("Making document versionable complete");
        //forward = ( folderDocInfo.getRtsType() == 1 )?"resume":"timesheet";
        switch( folderDocInfo.getRtsType() ){
          case 1:
                  forward= (userPreferences.getNavigationType() == 
                            UserPreferences.FLAT_NAVIGATION)?"resume":
                                                             "resume4Tree";
                  break;
          
          case 2:
                  forward= (userPreferences.getNavigationType() == 
                            UserPreferences.FLAT_NAVIGATION)?"timesheet":
                                                             "timesheet4Tree"; 
                  break;
          
          default:
                    break;
        }
        
        return mapping.findForward(forward);
    }
}
