package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
/* Java API */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API*/
import org.apache.log4j.*;
import org.apache.struts.action.*;

/**
 * Purpose : To set specific values prior to drawer listing in drawer module    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   05-08-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class B4DrawerListingAction extends Action  {

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
        logger.info("Performing b4 drawerlisting operation ...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        HttpSession httpSession = null;
        //String type = null;
        int rtsType = 0;
        Treeview treeview4Select = null;
        Treeview treeview4Timesheet = null;
        Treeview treeview4Personal = null;
        DbsPrimaryUserProfile dbsPrimaryUserProfile = null;
        
        try{
            logger.debug("Initializing variables ...");
            httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            UserPreferences userPreferences = (UserPreferences)
                                              httpSession.getAttribute(
                                              "UserPreferences");
                                              
            FolderDocInfo folderDocInfo = (FolderDocInfo)
                                              httpSession.getAttribute(
                                              "FolderDocInfo");
                                              
            logger.debug("folderDocInfo : " + folderDocInfo);
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            httpSession.removeAttribute("currentFolderDocId4Select");
            //The following code is to check is users home folder is set.
            //If not an error is thrown
            DbsDirectoryUser dbsDirectoryUser = dbsLibrarySession.getUser();            
            dbsPrimaryUserProfile = dbsDirectoryUser.getPrimaryUserProfile();
            DbsFolder homeFolder = dbsPrimaryUserProfile.getHomeFolder();
            DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            DbsFolder rtsRoot = null;
            
            /* switching to drawer module, hence set corr. rts type */
            if(request.getParameter("type").equals("list")){
              /* switch from "resumes" to "resumes drawers" and so on... */
              if( folderDocInfo.getRtsType() == 1 ){        // resumes
                rtsType = 3;                        // resumes drawers      
              }else if( folderDocInfo.getRtsType() == 2 ){  // timesheets
                rtsType = 4;                        // timesheets drawers  
              }else{                                        // personal      
                rtsType = 6;                        // personal drawers
              }
              
            }else if(request.getParameter("type").equals("resume")){
              rtsType = 3;
              folderDocInfo.setRtsType(folderDocInfo.RESUME_DRAWER_TYPE);
            }else if( request.getParameter("type").equals("timesheet") ){
              rtsType = 4;
              folderDocInfo.setRtsType(folderDocInfo.TIMESHEET_DRAWER_TYPE);
            }else{
              rtsType = 6;
              folderDocInfo.setRtsType(folderDocInfo.PERSONAL_DRAWER_TYPE);              
            }
            
            /* set corresponding root folder according to rtsType */
            if( rtsType == 3 ){         // set root as "/companyName/resumes"
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                            "/"+folderDocInfo.getRtsBase()+"/"+
                                            userInfo.getResumeFolderName());
              /* initialize treeview */                              
              if( (Treeview)httpSession.getAttribute("Treeview4Select") == null ){
                treeview4Select = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
                httpSession.setAttribute("Treeview4Select",treeview4Select);
              }
                                  
            }else if( rtsType == 4 ){   // set root as "/companyName/timesheets"
              rtsRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                            "/"+folderDocInfo.getRtsBase()+"/"+
                                            userInfo.getTimesheetFolderName());
              /* initialize treeview */                                            
              if( (Treeview)httpSession.getAttribute("Treeview4Timesheet") == null ){
                treeview4Timesheet = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
                httpSession.setAttribute("Treeview4Timesheet",treeview4Timesheet);
              }
                               
            }else{                      // set root as user's homeFolder 
              rtsRoot = homeFolder;
              /* initialize treeview */              
              if( (Treeview)httpSession.getAttribute("Treeview4Personal") == null ){
                treeview4Personal = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),getServlet().getServletContext().getRealPath("/"),userPreferences.getTreeIconPath()+ "/",httpSession.getId(),true,folderDocInfo.getRtsBase(),false,folderDocInfo.getRtsType());
                httpSession.setAttribute("Treeview4Personal",treeview4Personal);
              }
              
            }
              
                                
            if(homeFolder == null){
                ExceptionBean exb = new ExceptionBean();
                exb.setMessage("Home Folder Not Set");
                exb.setMessageKey("errors.homefolder.notset");
                throw exb;
            }else{
                /* reset navigation specific parameters */
                Long currentFolderId = rtsRoot.getId();
                logger.debug("currentFolderId : " + currentFolderId);
                //initialize folderDocInfo for nevigation
                folderDocInfo.initializeNevigation();

                Long homeFolderId = homeFolder.getId();
                logger.debug("homeFolderId : " + homeFolderId);
                folderDocInfo.setHomeFolderId(homeFolderId);
                folderDocInfo.setCurrentFolderId(currentFolderId);
                folderDocInfo.setDbsLibrarySession(dbsLibrarySession);
                folderDocInfo.setDrawerPageNumber(1);
                folderDocInfo.addFolderDocId(folderDocInfo.getCurrentFolderId());
                logger.debug("folderDocInfo : " + folderDocInfo);
                folderDocInfo.setListingType(FolderDocInfo.SIMPLE_LISTING);
                folderDocInfo.setTreeVisible(true);
                httpSession.removeAttribute("advanceSearchForm");
            }
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getMessage());
            saveErrors(request, exceptionBean.getActionErrors());
            forward = "failure";
        }catch(ExceptionBean exb){
            logger.error(exb.getMessage());
            saveErrors(request,exb.getActionErrors());
            forward = "failure";
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
            forward = "failure";
        }
        logger.info("Performing b4 drawerlisting operation complete");
        //forward = (rtsType == 3 )?"resumeDrawer":"timesheetDrawer";
        switch (rtsType){
          case 3:
          forward= "resumeDrawer";
          break;
          
          case 4:
          forward= "timesheetDrawer";
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
