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
 * Purpose : To generate hard link(s) for a given document   
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   18-09-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class FolderDocLinkAction extends Action  {

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
        boolean noLinkAtRoot = true;
        
        try{
            httpSession = request.getSession(false);
            FolderDocCopyMoveForm folderDocCopyMoveForm = (FolderDocCopyMoveForm)form;
            logger.debug("folderDocCopyMoveForm : " + folderDocCopyMoveForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo)
                                    httpSession.getAttribute("FolderDocInfo");
            userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");
            
            /*if( userPreferences.getNavigationType() == 
                UserPreferences.TREE_NAVIGATION ){
                
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
              
            }*/
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            DbsFolder rtsRoot = null;
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            /* for "resumes" / "resumes drawers", set rtsRoot to 
             * "/companyName/resumes/" */
            if((folderDocInfo.getRtsType() == 1) ||
               (folderDocInfo.getRtsType() == 3) ){

              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getResumeFolderName());
            }else if((folderDocInfo.getRtsType() == 2) || 
                     (folderDocInfo.getRtsType() == 4)){
            /* for "timesheets" / "timesheets drawers", set rtsRoot to 
             * "/companyName/timesheets/" */
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getTimesheetFolderName());
            }else{
              /* for "personal" / "personal drawers", set rtsRoot to
               * "homeFolderName" */
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectById(
                                                folderDocInfo.getHomeFolderId());
            }
            
            String targetFolderPath = folderDocCopyMoveForm.getHdnTargetFolderPath();
            logger.debug("targetFolderPath : " + targetFolderPath);

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
            
            Long[] folderDocIds = folderDocCopyMoveForm.getChkFolderDocIds();
            logger.debug("folderDocIds.length : " + folderDocIds.length);

            actionType = folderDocCopyMoveForm.getHdnActionType();

            FolderDoc folderDoc = null;
            folderDoc = new FolderDoc(dbsLibrarySession);
            if( actionType == FolderOperation.LINK){
              logger.info("actionType : FolderOperation.Link");
              logger.info("Linking Folders and documents...");
              DbsPublicObject dbsPo = null;
              DbsDocument dbsDoc = null;
              // check for root link violation
              for( int index = 0; index < folderDocIds.length; index++ ){
                 dbsPo = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                 logger.debug("Document Name: "+dbsPo.getName());
                 if( dbsPo.getResolvedPublicObject() instanceof DbsDocument ){
                  if(targetFolderId == rtsRoot.getId() ){
                    noLinkAtRoot = false;
                    break;
                  }
                   dbsDoc = (DbsDocument)dbsPo.getResolvedPublicObject(); 
                   
                 }
              }
              // if no link at root, link the PO with target Folder
              if(noLinkAtRoot){
                try{
                  folderDoc.linkPOs(targetFolderId,folderDocIds);
                }catch( DbsException dbsEx ){
                  throw dbsEx;
                }catch( Exception ex){
                  throw ex;
                }
                logger.info("Linking Completed");
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage(
                                                    "msg.linksuccessfullycreated");
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
                httpSession.setAttribute("ActionMessages",actionMessages);
              
                folderDocInfo.setCurrentFolderId(targetFolderId);
                Long currentFolderId = targetFolderId;
                folderDocInfo.addFolderDocId(currentFolderId);
                folderDocInfo.setPageNumber(1);
              }else{
                ActionErrors actionErrors = new ActionErrors();
                ActionError actionError = new ActionError("msg.CanShareAtRoot");
                actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
                httpSession.setAttribute("ActionErrors",actionErrors);
              }
            }
            folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);
        }catch(ExceptionBean exb){
            logger.error(exb.getMessage());
            logger.debug(exb.getErrorTrace());
            httpSession.setAttribute("ActionErrors",exb.getActionErrors());
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
        logger.info("Performing link operation complete");
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