package rts.web.actions.filesystem;

import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.action.Action;

/**
 * Purpose : To facilitate rename operation     
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   06-07-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class FolderDocRenameAction extends Action  {
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
        logger.info("Renaming Folders And Document...");

        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        UserPreferences userPreferences = null;
        Treeview treeview = null;
        try{
            httpSession = request.getSession(false);
            FolderDocRenameForm folderDocRenameForm = (FolderDocRenameForm)form;
            logger.debug("folderDocRenameForm : " + folderDocRenameForm);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
            //Treeview treeview = (Treeview)httpSession.getAttribute("Treeview");
            if( userPreferences.getNavigationType() == 
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
              
            }

            Long currentFolderId = folderDocInfo.getCurrentFolderId();
            logger.debug("currentFolderId : " + currentFolderId);
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            /* obtain folder(s),name(s),desc(s) . */
            FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
            Long[] selectedFolderDocIds = folderDocRenameForm.getTxtId();
            logger.debug("selectedFolderDocIds.length : " + selectedFolderDocIds.length);
            String[] selectedFolderDocNames = folderDocRenameForm.getTxtNewName();
            logger.debug("selectedFolderDocNames.length : " + selectedFolderDocNames.length);
            String[] selectedFolderDocDescs = folderDocRenameForm.getTxtNewDesc();
            logger.debug("selectedFolderDocDescs.length : " + selectedFolderDocDescs.length);
            /* perform "rename" operation */
            folderDoc.renameFolderDoc(selectedFolderDocIds,selectedFolderDocNames,selectedFolderDocDescs,folderDocInfo.getRtsType(),treeview);
                
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.ItemsRenamed");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
        }catch(ExceptionBean exb){
            logger.error(exb.getMessage());
            httpSession.setAttribute("ActionErrors",exb.getActionErrors());
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            /*if (dex.getErrorCode() == 30011){
                exceptionBean.setMessageKey("errors.30011.folderdoc.unabletorename");
            }*/
            logger.info("Rename Aborted");                    
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            logger.info("Rename Aborted");                    
            httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
        }
        logger.info("Renaming Folders And Document Complete");
        //forward = ( folderDocInfo.getRtsType() == 3 )?"resumeDrawer":"timesheetDrawer";
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