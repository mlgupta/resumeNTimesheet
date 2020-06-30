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
 * $Id: FolderDocSelectGoAction.java,v 1.10 2005/10/03 13:47:35 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.beans.exception.*;
import rts.web.beans.user.*;
import rts.web.beans.filesystem.*;
import rts.web.actionforms.filesystem.*;
/* Java API */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
/* Struts API */
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.log4j.*;

/**
 * Purpose : To facilitate navigation by means of an address bar in a popUp     
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   07-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class FolderDocSelectGoAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    Logger logger = Logger.getLogger("DbsLogger");
    logger.info("Performing Folder Doc Tree Append Action ...");
        
    //variable declaration
    ExceptionBean exceptionBean;
    String forward = "success";
    HttpSession httpSession = null;
    UserInfo userInfo=null;
    DbsLibrarySession dbsLibrarySession = null;
    boolean foldersOnly=true;
    String openerControl=null;
    String currentFolderPath=null; 
    FolderDocSelectForm folderDocSelectForm=null;
    DbsFileSystem dbsFileSystem=null;
    DbsPublicObject dbsPublicObject=null;   
    FolderDocInfo folderDocInfo = null;
    String homeFolderName = null;
    try{
      httpSession = request.getSession(false);
      userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
      folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
      dbsLibrarySession = userInfo.getDbsLibrarySession();
      folderDocSelectForm= (FolderDocSelectForm) form;
      foldersOnly=folderDocSelectForm.isHdnFoldersOnly();
      openerControl=folderDocSelectForm.getHdnOpenerControl();
      currentFolderPath=folderDocSelectForm.getFolderDocument(); 
      dbsFileSystem= new DbsFileSystem(dbsLibrarySession);       
      homeFolderName = dbsLibrarySession.getUser().getPrimaryUserProfile().getHomeFolder().getName();
      /* validate path */
      try{
        if( !currentFolderPath.startsWith("/"+folderDocInfo.getRtsBase()) ){
          currentFolderPath = "/"+folderDocInfo.getRtsBase();
        }
        if( folderDocInfo.getRtsType() ==1 || folderDocInfo.getRtsType() == 3){
          if( !currentFolderPath.startsWith("/"+folderDocInfo.getRtsBase()+"/"+
                                            userInfo.getResumeFolderName()) ){
            currentFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getResumeFolderName();  
          }
        }else if(folderDocInfo.getRtsType() ==2 || folderDocInfo.getRtsType() == 4){
          if( !currentFolderPath.startsWith("/"+folderDocInfo.getRtsBase()+"/"+
                                            userInfo.getTimesheetFolderName()) ){
            currentFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getTimesheetFolderName();  
          }          
        }else{
          if( !currentFolderPath.startsWith("/"+folderDocInfo.getRtsBase()+"/"+
                                            userInfo.getPersonalFolderName()+"/"
                                            +homeFolderName) ){
                                            
            currentFolderPath = "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getPersonalFolderName()+"/"+
                                homeFolderName;  
          }                    
        }
        /* obtain PO corr. to currentPath demanded / set .*/
        dbsPublicObject = (dbsFileSystem.findPublicObjectByPath(currentFolderPath)).getResolvedPublicObject();
        httpSession.setAttribute("currentFolderDocId4Select",dbsPublicObject.getId());    
      }catch(DbsException dex){
        exceptionBean = new ExceptionBean(dex);
        if(dex.containsErrorCode(30619)){
            exceptionBean.setMessageKey("errors.30619.folderdoc.invalidpath");
        }
        logger.error(exceptionBean.getMessage());
        logger.debug(exceptionBean.getErrorTrace());
        httpSession.setAttribute("actionerrors",exceptionBean.getActionErrors());    
      }
      
      httpSession.setAttribute("foldersOnly",new Boolean(foldersOnly));
      httpSession.setAttribute("openerControl",openerControl);  
      
    }catch(DbsException e){
      forward = "failure";
      exceptionBean = new ExceptionBean(e);
      logger.error(exceptionBean.getMessage());
      httpSession.setAttribute("actionerrors",exceptionBean.getActionErrors());    
    }catch(Exception e){
      forward = "failure";
      exceptionBean = new ExceptionBean(e);
      logger.error(exceptionBean.getMessage());
      httpSession.setAttribute("actionerrors",exceptionBean.getActionErrors());    
      }
    if( !forward.equals("failure")){
      if( folderDocInfo.isLinkForPOs() ){
        forward="popupforlink";
      }else{
        forward="popupforselect";
      }
    }
    return mapping.findForward(forward);
  }
}
