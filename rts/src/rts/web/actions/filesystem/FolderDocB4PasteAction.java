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
 * $Id: FolderDocB4PasteAction.java,v 1.13 2005/10/06 10:13:02 suved Exp $
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
import rts.web.actionforms.filesystem.FolderDocCopyMoveForm;
/* Java API */
import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.struts.action.*;
import org.apache.log4j.*;

/**
 * Purpose : To perform paste operation after copy/cut has been performed    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   06-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class FolderDocB4PasteAction extends Action  {
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
        logger.info("Pasting Copied OR Cut Folder or Document");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        Locale locale = getLocale(request);
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        boolean askToOverWrite = false;
        double fileSizeInMb = 0;
        UserPreferences userPreferences = null;
        Treeview treeview = null;
        DbsFileSystem dbsFileSystem = null;
        boolean noDocAtRoot = true;
        
        try{
            httpSession = request.getSession(false);
            FolderDocPasteForm folderDocPasteForm = (FolderDocPasteForm)form;
            logger.debug("folderDocPasteForm : " + folderDocPasteForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
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
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            Long targetFolderId = folderDocInfo.getCurrentFolderId();
            logger.debug("targetFolderId : " + targetFolderId);
            Long[] folderDocIds = folderDocInfo.getClipBoard();

            /* set corresponding root folder according to rtsType */
            DbsFolder rtsRoot = null;
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
            }else{      // set root as homeFolder
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectById(
                                                folderDocInfo.getHomeFolderId());
            }
           
            if(folderDocIds != null){
                int folderDocLength = folderDocIds.length;
                DbsPublicObject targetFolder = dbsLibrarySession.getPublicObject(targetFolderId);
                // check for doc's existence in targetFolder
                for(int index = 0 ; index < folderDocLength; index ++){
                    DbsPublicObject dbsPublicObjectToSearch = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                    
                    DbsPublicObject[] dbsPublicObjectFound = dbsFileSystem.searchByName(dbsPublicObjectToSearch.getName(),(DbsFolder)targetFolder, 1);
                    if(dbsPublicObjectFound != null){
                        askToOverWrite = true;
                        break;
                    }
                }
                if( askToOverWrite){  // overWrite doc
                    httpSession.setAttribute("OverWrite",new Integer(1));
                }else{    // perform copy/move operation as asked for
                    logger.debug("folderDocIds.length : " + folderDocIds.length);            
                    byte actionType = folderDocInfo.getClipBoardContent();
                    FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
                    // copy operation
                    if( actionType == FolderDocInfo.CLIPBOARD_CONTENT_COPY){
                        DbsPublicObject dbsPo = null;
                        DbsDocument dbsDoc = null;
                        // check for root copy violation and quota violation
                        for( int index = 0; index < folderDocIds.length; index++ ){
                           dbsPo = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                           logger.debug("Document Name: "+dbsPo.getName());
                           // no doc should be pasted on root 
                           if( dbsPo.getResolvedPublicObject() instanceof DbsDocument){ 
                            if(targetFolder.getId() == rtsRoot.getId() ){
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
                          logger.debug("actionType : FolderDocInfo.CLIPBOARD_CONTENT_COPY");
                          int prevRtsType = ((Integer)httpSession.getAttribute("prevRtsType")).intValue();
                          /* copy a resume only in the resume module & so on */
                          if( prevRtsType == folderDocInfo.getRtsType() ){
                            try {
                              folderDoc.copy(targetFolderId,folderDocIds,
                                             new Boolean(false),treeview);
                            }catch (DbsException dbsEx) {
                              throw dbsEx;
                            }catch( Exception ex ){
                              throw ex;
                            }
                          }else{ // copy operation violation
                           ActionErrors actionErrors= new ActionErrors();
                           ActionError actionError= null;
                           actionError= new ActionError("errors.illegal.paste.operation");
                           actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
                           httpSession.setAttribute("ActionErrors",actionErrors);                                                              
                          }
                        }else{ // quota exceeded
                           ActionErrors actionErrors= new ActionErrors();
                           ActionError actionError= null;
                           actionError= new ActionError("errors.QuotaExceedsCopy");
                           actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
                           httpSession.setAttribute("ActionErrors",actionErrors);                                  
                        }
                    }                         
                    }else{ // cut operation viz: move
                        if( actionType == FolderDocInfo.CLIPBOARD_CONTENT_CUT){
                            logger.debug("actionType : FolderDocInfo.CLIPBOARD_CONTENT_CUT");
                            int prevRtsType = ((Integer)httpSession.getAttribute("prevRtsType")).intValue();
                            /* move a resume only in the resume module & so on */
                            if( prevRtsType == folderDocInfo.getRtsType() ){  
                              DbsPublicObject dbsPo = null;
                              DbsDocument dbsDoc = null;
                              // check for root copy violation 
                              for( int index = 0; index < folderDocIds.length; index++ ){
                                 dbsPo = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                                 logger.debug("Document Name: "+dbsPo.getName());
                                 if( dbsPo.getResolvedPublicObject() instanceof DbsDocument){ 
                                  if(targetFolder.getId() == rtsRoot.getId() ){
                                    noDocAtRoot = false;
                                    break;
                                  }
                                 }
                              }
                            // move document to targetFolder  
                            if(noDocAtRoot){
                              folderDoc.move(targetFolderId,folderDocIds,new Boolean(false),treeview);
                              folderDocInfo.setClipBoardContent(FolderDocInfo.CLIPBOARD_CONTENT_COPY);
                            }
                            }else{    // illegal move operation performed
                               ActionErrors actionErrors= new ActionErrors();
                               ActionError actionError= null;
                               actionError= new ActionError("errors.illegal.paste.operation");
                               actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
                               httpSession.setAttribute("ActionErrors",actionErrors);                                                              
                          }
                        }
                    }
                    folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);

                    if( !noDocAtRoot ){   // operation successfully accomplished 
                      ActionErrors actionErrors = new ActionErrors();
                      ActionError actionError = new ActionError("msg.CanPasteAtRoot");
                      actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
                      httpSession.setAttribute("ActionErrors",actionErrors);
                    }else{                // cannot paste a doc at root 
                      ActionMessages actionMessages = new ActionMessages();
                      ActionMessage actionMessage = new ActionMessage("msg.PastePerformed");
                      actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                      httpSession.setAttribute("ActionMessages",actionMessages);
                    }
                }
            }else{    // no doc cut/copied for paste
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage("msg.cutorcopy.documentorfolder.topaste");
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                httpSession.setAttribute("ActionMessages",actionMessages);
            }

        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
        logger.info("Pasting Copied OR Cut Folder or Document Complete");
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
