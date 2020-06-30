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
 *	Purpose: To display timesheets and drawers in treeview 
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    09-09-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class TimesheetTreeListAction extends Action  {


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
                                    userInfo.getTimesheetFolderName()))?true:false;
            
            logger.debug("isRootFolder : "+isRootFolder);
            
            request.setAttribute("isRootFolder",new Boolean(isRootFolder));
            
            /* SIMPLE_LISTING, obtain document and drawers list here . */
            if( (folderDocInfo.getListingType() == FolderDocInfo.SIMPLE_LISTING) ){
              folderDoc = new FolderDoc(dbsLibrarySession);
              /* obtain drawers/docs list */
              folderDocLists = folderDoc.getFolderDocList(folderDocInfo.getCurrentFolderId(),
                                folderDocInfo,userPreferences, davPath,forMail);
              
              
              request.setAttribute("folderDocLists", folderDocLists);
              
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
              /* obtain drawers/docs list that satisfy search criteria */
              folderDocLists = folderDoc.buildFolderDocList(dbsSearch,
                    FolderDocInfo.SEARCH_LISTING,folderDocInfo,userPreferences,
                    davPath,forMail);
                    
              request.setAttribute("folderDocLists", folderDocLists);                
              /*pOLists = folderDoc.getPublicObjectFoundLists();
              
              logger.debug("pOLists.size(): " + pOLists.size());
              
              httpSession.setAttribute("pOLists",pOLists);*/
                                        
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