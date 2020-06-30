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
 * $Id: AdvanceSearchAction.java,v 1.13 2005/10/03 10:12:47 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.UserInfo;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;
/* java API */
import java.io.IOException;
import java.text.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;

/**
 * Purpose : To facilitate search 
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   04-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class AdvanceSearchAction extends Action  {

public static final int CREATEDATE=1;
public static final int LASTMODIFIEDDATE=2;
public static final int ATLEAST=1;
public static final int ATMOST=2;
Logger logger; 
/**
 * This is the main action called from the Struts framework.
 * @param mapping The ActionMapping used to select this instance.
 * @param form The optional ActionForm bean for this request.
 * @param request The HTTP Request we are processing.
 * @param response The HTTP Response we are processing.
 */
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
  //Initialize logger
  logger = Logger.getLogger("DbsLogger");
  logger.info("Searching for folder and document...");
  
  //variable declaration
  ExceptionBean exceptionBean;
  String forward = null;

  HttpSession httpSession = null;
  FolderDocInfo folderDocInfo = null;
  UserPreferences userPreferences = null;
  DbsLibrarySession dbsLibrarySession = null;
  DbsFolder rtsRoot = null;
  UserInfo userInfo = null;
  DbsFileSystem dbsFileSystem = null;
  
  try{
    httpSession = request.getSession(false);
    AdvanceSearchForm advanceSearchForm = (AdvanceSearchForm)form;
    folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
    userPreferences = (UserPreferences)
                            httpSession.getAttribute("UserPreferences");
    userInfo = (UserInfo)httpSession.getAttribute("UserInfo");                        
    //logger.info("listing type:"+folderDocInfo.getListingType());
    logger.debug("advanceSearchForm : " + advanceSearchForm);
    
    /* selection of "/resumes","/timesheets","/personal/homefolderName"
     * leads to null value in txtLookinFolderPath,so set it according to
     * the corresponding rtsType */
     
    if( advanceSearchForm.getTxtLookinFolderPath() == null ||
        advanceSearchForm.getTxtLookinFolderPath().trim().length() == 0){
      
      dbsLibrarySession = userInfo.getDbsLibrarySession();
      dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
      /* 1,3 corr. to "resumes" folder . 2,4 corr. to "timesheets",
       * whereas 5,6 corr. to user's homeFolder */
      if( (folderDocInfo.getRtsType() == 1) || 
          (folderDocInfo.getRtsType() == 3) ){// set root as "/companyName/resumes"
        rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                    "/"+folderDocInfo.getRtsBase()+"/"+
                                    userInfo.getResumeFolderName());
      }else if( (folderDocInfo.getRtsType() == 2) || 
                (folderDocInfo.getRtsType() == 4)){// set root as "/companyName/timesheets"
        rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                    "/"+folderDocInfo.getRtsBase()+"/"+
                                    userInfo.getTimesheetFolderName());
      }else{                                    // set root as user's homeFolder
        rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectById(
                                        folderDocInfo.getHomeFolderId());
      }
      advanceSearchForm.setTxtLookinFolderPath(rtsRoot.getAnyFolderPath());
      advanceSearchForm.setCurrentFolderId(rtsRoot.getId());
    }
    /* set listing type to "SEARCH_LISTING" */
    folderDocInfo.setListingType(FolderDocInfo.SEARCH_LISTING);

    httpSession.setAttribute("advanceSearchForm",advanceSearchForm); 
    
    /* for rtsType = 3,4,6 setDrawerPageNumber for Drawer module in 
     * Flat nav , else setPageNumber . This is to reset navigation page
     * numbers during search .*/
    if( (folderDocInfo.getRtsType() == 3) || 
        (folderDocInfo.getRtsType() == 4) || 
        (folderDocInfo.getRtsType() == 6)){
      folderDocInfo.setDrawerPageNumber(1);
    }else{
      folderDocInfo.setPageNumber(1);
    }
    
    logger.debug("folderDocInfo : " + folderDocInfo);
    
  }catch(DbsException dbsEx){
      exceptionBean = new ExceptionBean(dbsEx);
      logger.debug(exceptionBean.getMessage());
      logger.debug(exceptionBean.getErrorTrace());
      httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
  }catch(Exception ex){
      exceptionBean = new ExceptionBean(ex);
      logger.debug(exceptionBean.getMessage());
      logger.debug(exceptionBean.getErrorTrace());
      httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
  }
  
  logger.info("Searching for folder and document complete");
  //forward = ( folderDocInfo.getRtsType() == 1 )?"resume":"timesheet";
  switch (folderDocInfo.getRtsType()){
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
    
    case 3:
            forward= "resumeDrawer";
            break;
    
    case 4:
            forward= "timesheetDrawer";
            break;

    case 5:
            forward= (userPreferences.getNavigationType() == 
                      UserPreferences.FLAT_NAVIGATION)?"personal":
                                                       "personal4Tree"; 
            break;
    
    case 6:
            forward= "personalDrawer";
            break;
  } 
  
  return mapping.findForward(forward);
}
}