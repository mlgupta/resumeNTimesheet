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
 * $Id: FolderDocCopyMoveAction.java,v 1.14 2005/10/03 12:44:13 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.beans.user.UserInfo;
import rts.web.beans.filesystem.Treeview;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.user.*;
import rts.web.actionforms.filesystem.FolderDocCopyMoveForm;
/* Java API */
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.struts.action.*;
import org.apache.log4j.*;

/**
 * Purpose : To perform copyTo/moveTo operation using drawers lookUp    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   06-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/
 
public class FolderDocCopyMoveAction extends Action  {
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
        logger.info("Performing copy or move operation ...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        Locale locale = getLocale(request);
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        double fileSizeInMb = 0;
        byte actionType = 0;
        UserPreferences userPreferences = null;
        Treeview treeview = null;
        DbsFileSystem dbsFileSystem = null;
        boolean noDocAtRoot = true;
        
        try{
            httpSession = request.getSession(false);
            FolderDocCopyMoveForm folderDocCopyMoveForm = (FolderDocCopyMoveForm)form;
            logger.debug("folderDocCopyMoveForm : " + folderDocCopyMoveForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo)
                                    httpSession.getAttribute("FolderDocInfo");
            userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");
            //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");
            
            if( userPreferences.getNavigationType() == 
                UserPreferences.TREE_NAVIGATION ){
              /* obtain corr. treeview */
              switch( folderDocInfo.getRtsType() ){
                case 1:
                        treeview = (Treeview)
                                    httpSession.getAttribute("Treeview4List");
                        break;
                
                case 2:
                        treeview = (Treeview)
                                    httpSession.getAttribute("Treeview4TimesheetList");
                        break;
                
                case 5:
                        treeview = (Treeview)
                                    httpSession.getAttribute("Treeview4PersonalList");
                        break;
                
                default:
                          break;
              }
              
            }
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            DbsFolder rtsRoot = null;
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            /* set corresponding root folder according to rtsType */
            if((folderDocInfo.getRtsType() == 1) ||
               (folderDocInfo.getRtsType() == 3) ){
              // set root as "/companyName/resumes"
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getResumeFolderName());
            }else if((folderDocInfo.getRtsType() == 2) || 
                     (folderDocInfo.getRtsType() == 4)){
              // set root as "/companyName/timesheets"
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getTimesheetFolderName());
            }else{        // set root as homeFolder
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectById(
                                                folderDocInfo.getHomeFolderId());
            }
            
            String targetFolderPath = folderDocCopyMoveForm.getHdnTargetFolderPath();
            DbsFolder dbsFolder = null;
            if( targetFolderPath!=null && targetFolderPath.trim().length()!=0 ){
                // find folder Object 
              dbsFolder = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                                          targetFolderPath);                
              logger.debug("targetFolderPath : " + targetFolderPath);
            }else{ // set targetFolderPath to root of respective modules,
                   // resumes (1,3),timesheets (2,4),personal (3,6).
              dbsFolder = rtsRoot;
            }
            

            
            Long targetFolderId = dbsFolder.getId();
            logger.debug("targetFolderId : " + targetFolderId);
            /* obtain target Folder id */
            Long[] folderDocIds = folderDocCopyMoveForm.getChkFolderDocIds();
            logger.debug("folderDocIds.length : " + folderDocIds.length);
            /* obtain actionType */
            actionType = folderDocCopyMoveForm.getHdnActionType();
            Boolean overwrite = folderDocCopyMoveForm.isHdnOverWrite();
            logger.debug("overwrite : " + overwrite);
            if(overwrite.booleanValue()){
                logger.info("The copy is being performed with overwrite option set to true");
            }
            FolderDoc folderDoc = null;
            folderDoc = new FolderDoc(dbsLibrarySession);
            /* perform COPY operation */
            if( actionType == FolderOperation.COPY){
              DbsPublicObject dbsPo = null;
              DbsDocument dbsDoc = null;
              // check for root copy violation and quota violation
              for( int index = 0; index < folderDocIds.length; index++ ){
                 dbsPo = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                 logger.debug("Document Name: "+dbsPo.getName());
                 if( dbsPo.getResolvedPublicObject() instanceof DbsDocument ){
                  // no doc should be pasted on root
                  if(targetFolderId == rtsRoot.getId() ){
                    noDocAtRoot = false;
                    break;
                  }
                   dbsDoc = (DbsDocument)dbsPo.getResolvedPublicObject(); 
                   fileSizeInMb += dbsDoc.getContentSize();
                 }
                 if( dbsPo.getResolvedPublicObject() instanceof DbsFolder ){
                   DbsFolder dbsFo = (DbsFolder)dbsPo;
                   TotalSizeFoldersDocs total = folderDoc.findTotalSizeFoldersDocs(
                                                  dbsFo.getId());
                   fileSizeInMb += total.getSize();
                 }
              }
              // calculate filesize
              if(noDocAtRoot){
              
              fileSizeInMb = fileSizeInMb/(1024*1024);
              logger.debug("fileSizeInMb b4 rounding off : "+fileSizeInMb);
              fileSizeInMb = (Math.round(fileSizeInMb*100)/100.00);
              logger.debug("fileSizeInMb : "+fileSizeInMb);
              
              /* fileSizeInMb <= remaining space, go ahead and upload file */
              if( fileSizeInMb <= (userInfo.getAllocatedQuota() - userInfo.getUsedQuota())){
                /* perform copy */                
                logger.info("actionType : FolderOperation.COPY");
                logger.info("Copying Folders and documents...");
                try{
                  folderDoc.copy(targetFolderId,folderDocIds,overwrite,treeview);
                }catch( DbsException dbsEx ){
                  throw dbsEx;
                }catch( Exception ex){
                  throw ex;
                }
                logger.info("Copy Completed");
  
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage("msg.CopyToPerformed");
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                httpSession.setAttribute("ActionMessages",actionMessages);                      
              }else{  // quota exceeded
                 ActionErrors actionErrors= new ActionErrors();
                 ActionError actionError= null;
                 actionError= new ActionError("errors.QuotaExceedsCopy");
                 actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
                 httpSession.setAttribute("ActionErrors",actionErrors);              
              }
            }
            }else{    // perform MOVE operation
                if( actionType == FolderOperation.MOVE){
                  logger.info("actionType : FolderOperation.MOVE");
                  logger.info("Moving Folders and documents...");
                  DbsPublicObject dbsPo = null;
                  DbsDocument dbsDoc = null;
                  // check for root copy violation 
                  for( int index = 0; index < folderDocIds.length; index++ ){
                     dbsPo = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                     logger.debug("Document Name: "+dbsPo.getName());
                     if( dbsPo.getResolvedPublicObject() instanceof DbsDocument ){
                      // no doc should be pasted on root
                      if(targetFolderId == rtsRoot.getId() ){
                        noDocAtRoot = false;
                        break;
                      }
                       dbsDoc = (DbsDocument)dbsPo.getResolvedPublicObject(); 
                       fileSizeInMb += dbsDoc.getContentSize();
                     }
                  }
                  // move document to targetFolder
                  if( noDocAtRoot ){
                    try{
                      folderDoc.move(targetFolderId,folderDocIds,overwrite,treeview);
                    }catch( DbsException dbsEx ){
                      throw dbsEx;
                    }catch( Exception ex){
                      throw ex;
                    }
                    logger.info("Move Complete");
                  }
                  
                  ActionMessages actionMessages = new ActionMessages();
                  ActionMessage actionMessage = new ActionMessage("msg.MoveToPerformed");
                  actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                  httpSession.setAttribute("ActionMessages",actionMessages);
                }
              }
              if( !noDocAtRoot ){         // operation successfully accomplished
                ActionErrors actionErrors = new ActionErrors();
                ActionError actionError = new ActionError("msg.CanPasteAtRoot");
                actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
                httpSession.setAttribute("ActionErrors",actionErrors);
              }else{                     // cannot paste a doc at root     
                folderDocInfo.setCurrentFolderId(targetFolderId);
                Long currentFolderId = targetFolderId;
                folderDocInfo.addFolderDocId(currentFolderId);
                folderDocInfo.setPageNumber(1);
              }
            folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            if(dex.getErrorCode() == 30049){
              exceptionBean.setMessageKey("errors.30049.insufficient.access.to.remove.items");
            }
            if(dex.containsErrorCode(30041)){
              exceptionBean.setMessageKey("errors.30041.folderdoc.insufficient.access.updatePO");
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
        logger.info("Performing copy or move operation complete");
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
          
          default:
                    break;
        }
        
        return mapping.findForward(forward);
    }
}
