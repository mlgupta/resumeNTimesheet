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
 * $Id: FolderDocListAction.java,v 1.18 2005/10/20 09:54:50 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;
/* java API */
import java.io.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;

/**
 *	Purpose: To display resumes and drawers list
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    01-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class FolderDocListAction extends Action  {
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
        logger.info("Building Folder Doc List...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";

        FolderDoc folderDoc;
        ArrayList folderDocLists = new ArrayList();
        ActionMessages actionMessages = null;
        String davPath=null;
        ArrayList pOLists = null;
        boolean forMail = false;
        
        try{
            //Initializing variables
            HttpSession httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            FolderDocInfo folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            logger.debug("folderDocInfo : " + folderDocInfo);
            logger.debug("userPreferences : " + userPreferences);
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            FolderDocListForm folderdoclistform=(FolderDocListForm)form;
            davPath = userInfo.getDavPath();
            logger.debug("davPath : " + davPath);
            logger.info("FolderDocListForm: "+folderdoclistform);
            DbsFolder dbsFolder = null;
            
            //If its simple listing then only update currentFolderPath and add cuttentFolderId
            //in the navigation history
            /*if(!folderDocInfo.isTreeVisible()){
                Object[] formats = dbsLibrarySession.getFormatCollection().getItems();
                request.setAttribute("formats",formats);
               // logger.info(formats);
            }*/


            Long currentFolderId = folderDocInfo.getCurrentFolderId();
            logger.debug("CurrentFolderId : " + currentFolderId);
            //DbsFolder dbsFolder = null;
            try{
                dbsFolder = (DbsFolder)dbsLibrarySession.getPublicObject(currentFolderId);
            }catch(DbsException dex){
                try{
                    dbsFolder = (DbsFolder)dbsLibrarySession.getPublicObject(folderDocInfo.getHomeFolderId());
                    folderDocInfo.setCurrentFolderId(dbsFolder.getId());
                }catch(DbsException dex1){
                    ExceptionBean exb = new ExceptionBean();
                    exb.setMessage("Home Folder Not Set");
                    exb.setMessageKey("errors.homefolder.notset");
                    throw exb;
                }
            }
            String currentFolderPath = dbsFolder.getAnyFolderPath();
            folderDocInfo.setCurrentFolderId(dbsFolder.getId());
            logger.debug("currentFolderPath : " + currentFolderPath);
            logger.info("Listing items present in the folder : " + currentFolderPath);
            folderDocInfo.setCurrentFolderPath(currentFolderPath);
            /* check for root limit */
            boolean isRootFolder = ( folderDocInfo.getCurrentFolderId() == 
                                    folderDocInfo.getHomeFolderId() || 
                                    folderDocInfo.getCurrentFolderPath().equals(
                                    "/"+folderDocInfo.getRtsBase()+"/"+
                                    userInfo.getResumeFolderName()))?true:false;
            
            logger.debug("isRootFolder : "+isRootFolder);
            
            request.setAttribute("isRootFolder",new Boolean(isRootFolder));
            
            /* obtain arraylist of parents of the current folder */
            ArrayList listOfParents = folderDocInfo.getListOfParents();
            ArrayList listOfParentsId = folderDocInfo.getListOfParentsId();
            listOfParents.clear();
            listOfParentsId.clear();
            
            String currentFolderNameTemp = dbsFolder.getName();
            String currentFolderIdTemp = dbsFolder.getId().toString();
            listOfParents.add(0,currentFolderNameTemp);
            listOfParentsId.add(0,currentFolderIdTemp);
            String parentFolder = null;
            String rootFolder = folderDocInfo.getRtsBase();
            
            /* restrict parents to "/companyName/resumes/" and not above it */
            while(!dbsFolder.getAnyFolderPath().equals(
                  "/"+rootFolder+"/"+userInfo.getResumeFolderName())){
              
              parentFolder = dbsFolder.getName();
              dbsFolder = dbsFolder.getFolderReferences(0);
              currentFolderNameTemp = dbsFolder.getName();                      
              currentFolderIdTemp = dbsFolder.getId().toString();

              if( currentFolderNameTemp.equalsIgnoreCase(
                  userInfo.getResumeFolderName()) &&
                  parentFolder.equals(folderDocInfo.getRtsBase())){
                
                currentFolderNameTemp = userInfo.getResumeFolderName();
                listOfParents.add(0,currentFolderNameTemp);
                listOfParentsId.add(0,currentFolderIdTemp);
                break;
              }
              
              listOfParents.add(0,currentFolderNameTemp);
              logger.debug("listOfParents[]: "+listOfParents.get(0));
              listOfParentsId.add(0,currentFolderIdTemp);
            }
            for( int index = 0; index < listOfParents.size(); index++ ){
            
              logger.debug("listOfParents["+index+"] :"+listOfParents.get(index));
              
            }
            /* logic for setting nav specific data begins ... */
            int drawersCount = listOfParents.size();
            int drawers = drawersCount;
            int setNo = folderDocInfo.getHierarchySetNo();
            //if( drawersCount >= folderDocInfo.getHierarchySetNo()*7 ){

              int startIndex = ( setNo == 0 )?0:(setNo-1)*8;
              int endIndex = startIndex + 7;
              if( endIndex >= drawers-1 )
                endIndex = drawers-1;
              int difference = endIndex - startIndex;
              
              logger.debug("drawersCount: "+drawersCount);               
              logger.debug("startIndex: "+startIndex);
              logger.debug("endIndex: "+endIndex);    
            
              for( int index = 0; index < startIndex; index++ ){
                logger.debug("Name: "+listOfParents.get(0));
                listOfParents.remove(0);
                listOfParentsId.remove(0);
              }
              endIndex = difference;
              drawers -= startIndex;
              logger.debug("drawers: "+drawers);               

              if( endIndex < drawers ){
                for( int index = endIndex+1; index < drawers; index++ ){
                  logger.debug("Name next : "+listOfParents.get(endIndex+1));
                  listOfParents.remove(endIndex+1);
                  listOfParentsId.remove(endIndex+1);
                }
              }
            //}

            /* isFirstSet will take care of previous drawers navigation */
            boolean isFirstSet = (folderDocInfo.getHierarchySetNo() == 1)?true:false;
      
            /* isLastSet will take care of next drawers navigation */
            boolean isLastSet = ( drawersCount <= (8*(folderDocInfo.getHierarchySetNo())) ) ?
                                  true:false;
            
            logger.debug("isLastSet: "+isLastSet);               

            request.setAttribute("isFirstSet",new Boolean(isFirstSet));
            
            request.setAttribute("isLastSet",new Boolean(isLastSet));
            /* logic for setting nav-specific data ens ... */
            
            /* SIMPLE_LISTING, obtain document list here, drawers list will be 
             * obtained seperately . */
            if( (folderDocInfo.getListingType() == FolderDocInfo.SIMPLE_LISTING) ){
              folderDoc = new FolderDoc(dbsLibrarySession);
              /* obtain documents list */
              folderDocLists = folderDoc.getFolderDocList(folderDocInfo.getCurrentFolderId(),
                                folderDocInfo,userPreferences, davPath,forMail);
              
              
              request.setAttribute("folderDocLists", folderDocLists);
              
              pOLists = folderDoc.getPublicObjectFoundLists();
              
              logger.debug("pOLists.size(): " + pOLists.size());
              /* this list is used to extract drawer list later on. */
              httpSession.setAttribute("pOLists", pOLists);
                                        
                                        
              if(httpSession.getAttribute("OverWrite") != null){
              
                  request.setAttribute("OverWrite",
                              (Integer)httpSession.getAttribute("OverWrite"));
                              
                  httpSession.removeAttribute("OverWrite");
                  
              }
              logger.info("folderDocInfo: "+folderDocInfo);    
              /* added for appropriate message display in message bar for both nav types */
              if(userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION){
                  dbsFolder=(DbsFolder)dbsLibrarySession.getPublicObject(folderDocInfo.getCurrentFolderId());
                  actionMessages = new ActionMessages();
                  if(dbsFolder.getItemCount()<=1){
                    ActionMessage actionMessage = new ActionMessage("msg.folderdoc.folder_content_item",dbsFolder.getName(), String.valueOf(dbsFolder.getItemCount()));
                    actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                  }else{
                    ActionMessage actionMessage = new ActionMessage("msg.folderdoc.folder_content_items",dbsFolder.getName(), String.valueOf(dbsFolder.getItemCount()));
                    actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);        
                  }
                             
              }else{
                  actionMessages = new ActionMessages();
                  if(dbsFolder.getItemCount()<=1){
                    ActionMessage actionMessage = new ActionMessage("msg.folderdoc.folder_content_item",dbsFolder.getName(), String.valueOf(dbsFolder.getItemCount()));
                    actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                  }else{
                    ActionMessage actionMessage = new ActionMessage("msg.folderdoc.folder_content_items",dbsFolder.getName(), String.valueOf(dbsFolder.getItemCount()));
                    actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);        
                  }
                  
              }

          }else if(folderDocInfo.getListingType() == FolderDocInfo.SEARCH_LISTING){
              /* for SEARCH_LISTING, build search obj */
              AdvanceSearchForm advanceSearchForm = (AdvanceSearchForm)httpSession.getAttribute("advanceSearchForm");
              
              /*if( advanceSearchForm.getTxtLookinFolderPath() == null ||
                  advanceSearchForm.getTxtLookinFolderPath().trim().length() == 0){
                advanceSearchForm.setTxtLookinFolderPath(folderDocInfo.getCurrentFolderPath());
                advanceSearchForm.setCurrentFolderId(folderDocInfo.getCurrentFolderId());
              }*/
              logger.debug("TxtLookinFolderPath : "+advanceSearchForm.getTxtLookinFolderPath());
              AdvanceSearchBean advanceSearchBean = new AdvanceSearchBean(dbsLibrarySession);
              DbsSearch dbsSearch = advanceSearchBean.getSearchObject(advanceSearchForm,folderDocInfo.getRtsType());
              folderDoc = new FolderDoc(dbsLibrarySession);
              /* obtain documents list that satisfy search criteria */
              folderDocLists = folderDoc.buildFolderDocList(dbsSearch,
                    FolderDocInfo.SEARCH_LISTING,folderDocInfo,userPreferences,
                    davPath,forMail);
                    
              request.setAttribute("folderDocLists", folderDocLists);                
              pOLists = folderDoc.getPublicObjectFoundLists();
              
              logger.debug("pOLists.size(): " + pOLists.size());
              /* this list is used to extract drawer list later on. */
              httpSession.setAttribute("pOLists",pOLists);
                                        
              actionMessages = new ActionMessages();
              ActionMessage actionMessage = new ActionMessage("msg.folderdoc.number_of_item_found", String.valueOf(dbsSearch.getItemCount()));
              actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
              httpSession.setAttribute("ActionMessages" , actionMessages);
              
          }
          else{
              //page listing
          }
          
          
          ActionErrors actionErrors = (ActionErrors)httpSession.getAttribute("ActionErrors");
          if(actionErrors != null){
              logger.debug("Saving action error in request stream");
              saveErrors(request,actionErrors);
              httpSession.removeAttribute("ActionErrors");
          }else{
              //if it is simple navigation then display itemcount in the folder
              if(httpSession.getAttribute("IS_SIMPLE_NAVIGATION") != null){
                  httpSession.removeAttribute("IS_SIMPLE_NAVIGATION");
                  saveMessages(request,actionMessages);
              }else{
                  ActionMessages actionMessagesSession = (ActionMessages)httpSession.getAttribute("ActionMessages");
                  if(actionMessagesSession != null){
                      logger.debug("Saving action message in request stream");
                      saveMessages(request,actionMessagesSession);
                      httpSession.removeAttribute("ActionMessages");
                  }else{
                      //this is to handle the refresh case
                      saveMessages(request,actionMessages);
                  }
              }
          }
          logger.debug("folderDocLists.size() :"+((ArrayList)request.getAttribute("folderDocLists")).size());
        }catch( ParseException pex){
            exceptionBean = new ExceptionBean(pex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            saveErrors(request,exceptionBean.getActionErrors());
            forward = "failure";
        }catch(ExceptionBean eb){
            logger.error(eb.getMessage());
            logger.debug(eb.getErrorTrace());
            saveErrors(request,eb.getActionErrors());
            forward = "failure";
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            if(dex.getErrorCode() == 10201){
                exceptionBean.setMessageKey("errors.10201.folderdoc.folder.notexist");
            }
            saveErrors(request,exceptionBean.getActionErrors());
            forward = "failure";
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            saveErrors(request,exceptionBean.getActionErrors());
            forward = "failure";
        }

        logger.info("Building Folder Doc List Complete");
        return mapping.findForward(forward);
    }
} 



//Variour operation that call it directly are
    //1. When Non-Admin logs in
    //2. When menu is clicked
    //3. when folder is clicked
    //4. when tree leaf is clicked
    //5. when goto button is clicked
    //6. when back button is clicked
    //7. when forward button is clicked
    //8. when up button is clicked
    //9. when refresh button is clicked

//Various operations when it is not called directly are
    //1. when rename button is clicked
    //2. when newfolder button is clicked 
    //4. when paste button is clicked
    //5. when upload button is clicked
    //6. when delete button is clicked
    //7. when property button is clicked
    //8. when checkout button is clicked
    //9. when checkin button is clicked
    //10. when undo checkout is called
    //11. when make version button is clicked

//various operation when it is not called at all are.
    //1. when history button is clicked
    //2. when download button is clicked
    //3. when copyTo button is clicked
    //4. when moveTo button is clicked 
    //5. when cut button is clicked
    //6. when copy button is clicked
