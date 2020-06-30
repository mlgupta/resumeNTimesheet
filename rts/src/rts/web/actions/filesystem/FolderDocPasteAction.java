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
 * $Id: FolderDocPasteAction.java,v 1.8 2005/08/24 15:36:27 suved Exp $
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
import rts.web.actionforms.filesystem.FolderDocCopyMoveForm;
/* Java API */
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
/* Struts API */
import org.apache.struts.action.*;
import org.apache.log4j.*;

public class FolderDocPasteAction extends Action  {
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
        double fileSizeInMb = 0;

        try{
            httpSession = request.getSession(false);
            FolderDocPasteForm folderDocPasteForm = (FolderDocPasteForm)form;
            logger.debug("folderDocPasteForm : " + folderDocPasteForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            Long targetFolderId = folderDocInfo.getCurrentFolderId();
            logger.debug("targetFolderId : " + targetFolderId);
            Long[] folderDocIds = folderDocInfo.getClipBoard();
           
            if(folderDocIds != null){
                int folderDocLength = folderDocIds.length;
                DbsPublicObject targetFolder = dbsLibrarySession.getPublicObject(targetFolderId);
                logger.debug("folderDocIds.length : " + folderDocIds.length);            
                
                boolean overwrite = false;
                Integer pasteOption =  folderDocPasteForm.getRadPasteOption();
                if( pasteOption.intValue() == 1){
                    overwrite = true;
                }
                
                if(overwrite){
                    logger.debug("The folder or document is being pasted with overwrite option set to true");
                }
                byte actionType = folderDocInfo.getClipBoardContent();
                                
                FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
                
                if( actionType == FolderDocInfo.CLIPBOARD_CONTENT_COPY){

                  DbsPublicObject dbsPo = null;
                  DbsDocument dbsDoc = null;
                  for( int index = 0; index < folderDocIds.length; index++ ){
                     dbsPo = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                     logger.debug("Document Name: "+dbsPo.getName());
                     if( dbsPo.getResolvedPublicObject() instanceof DbsDocument ){
                       dbsDoc = (DbsDocument)dbsPo.getResolvedPublicObject(); 
                       fileSizeInMb += dbsDoc.getContentSize();
                     }
                  }
                  fileSizeInMb = fileSizeInMb/(1024*1024);
                  logger.debug("fileSizeInMb b4 rounding off : "+fileSizeInMb);
                  fileSizeInMb = (Math.round(fileSizeInMb*100)/100.00);
                  logger.debug("fileSizeInMb : "+fileSizeInMb);

                  /* fileSizeInMb <= remaining space, go ahead and upload file */
                  if( fileSizeInMb <= (userInfo.getAllocatedQuota() - userInfo.getUsedQuota())){
                
                    logger.debug("actionType : FolderDocInfo.CLIPBOARD_CONTENT_COPY");
                    try {
                      folderDoc.copy(targetFolderId,folderDocIds,Boolean.valueOf(overwrite),null);
                    }catch (DbsException dbsEx) {
                      throw dbsEx;
                    }catch( Exception ex ){
                      throw ex;
                    }
                  }else{
                     ActionErrors actionErrors= new ActionErrors();
                     ActionError actionError= null;
                     actionError= new ActionError("errors.QuotaExceedsCopy");
                     actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
                     httpSession.setAttribute("ActionErrors",actionErrors);                                  
                  }
                  
                }else{
                  if( actionType == FolderDocInfo.CLIPBOARD_CONTENT_CUT){
                    logger.debug("actionType : FolderDocInfo.CLIPBOARD_CONTENT_CUT");
                    folderDoc.move(targetFolderId,folderDocIds,Boolean.valueOf(overwrite),null);
                    folderDocInfo.setClipBoardContent(FolderDocInfo.CLIPBOARD_CONTENT_COPY);
                  }
                }
                folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage("msg.PastePerformed");
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                httpSession.setAttribute("ActionMessages",actionMessages);
            }else{
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage("msg.cutorcopy.documentorfolder.topaste");
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                httpSession.setAttribute("ActionMessages",actionMessages);
            }

                
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
        logger.info("Pasting Copied OR Cut Folder or Document Complete");
        //forward = ( folderDocInfo.getRtsType() == 1 )?"resume":"timesheet";
        switch (folderDocInfo.getRtsType()){
          case 1:
          forward= "resume";
          break;
          
          case 2:
          forward= "timesheet";
          break;
          
          case 3:
          forward= "resumeDrawer";
          break;
          
          case 4:
          forward= "timesheetDrawer";
          break;

          case 5:
          forward= "personal";
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
