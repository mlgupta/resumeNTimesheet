package rts.web.actions.filesystem;
/**
 *	Purpose: To Populate Treeview for Link Selection
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:   12-09-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
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

public class FolderDocLinkB4Action extends Action  {

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
    String forward = null;
    HttpSession httpSession = null;
    UserInfo userInfo=null;
    Treeview treeview4Select =null;
    Treeview treeview4Timesheet =null;
    Treeview treeview4Personal = null;
    DbsLibrarySession dbsLibrarySession = null;
    UserPreferences userPreferences=null; 
    String physicalPath=null;
    boolean foldersOnly=true;
    Long currentFolderDocId4Select =null;
    Long currentDocumentId4Select=null;
    boolean treeAppend=false;
    FolderDocInfo folderDocInfo=null;
    String openerControl=null;
    String currentFolderPath=null; 
    DbsPublicObject dbsPublicObject=null; 
    FolderDocSelectForm folderDocSelectForm=null;
    boolean isDocument=false;
    boolean recreateTree=false;
    String heading="";
    Boolean isForMail = null;
    DbsFileSystem dbsFileSystem = null;
    
    try{
      httpSession = request.getSession(false);
      physicalPath=request.getSession().getServletContext().getRealPath("/");
      logger.debug("physicalPath : "+physicalPath);
      userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
      userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
      folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
      dbsLibrarySession = userInfo.getDbsLibrarySession();

      if(request.getParameter("recreate")!=null){
        recreateTree=(new Boolean(request.getParameter("recreate"))).booleanValue();
      }
      
      /* code to pop-up tree for grabbing email-ids */
      isForMail = (request.getParameter("hdnIsForMail")!=null && 
                  request.getParameter("hdnIsForMail").equalsIgnoreCase("true") )?
                  new Boolean(true):new Boolean(false);
        
      
      logger.debug("rtsType : "+folderDocInfo.getRtsType());
      logger.debug("isForMail: "+isForMail.booleanValue());
      DbsFolder dbsFolder = null;
      dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
      /*if( folderDocInfo.getRtsType() ==1 ){
        dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
        dbsFolder = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                              "/"+folderDocInfo.getRtsBase()+"/resumes");
        currentFolderDocId4Select = dbsFolder.getId();
      }else if( folderDocInfo.getRtsType() ==2 ){
        dbsFolder = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                              "/"+folderDocInfo.getRtsBase()+"/"+"timesheets");                
        currentFolderDocId4Select = dbsFolder.getId();
      }*/
      if( isForMail.booleanValue() ){
        //dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
        dbsFolder = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getResumeFolderName());
                                
        currentFolderDocId4Select = dbsFolder.getId();
      }
      else if(request.getParameter("currentFolderDocId4Select")!=null){
        currentFolderDocId4Select =new Long(request.getParameter("currentFolderDocId4Select"));
      }else if (httpSession.getAttribute("currentFolderDocId4Select")!=null){
        currentFolderDocId4Select =(Long)httpSession.getAttribute("currentFolderDocId4Select");
      }else if( folderDocInfo.getRtsType() ==1 || folderDocInfo.getRtsType() ==3){
        //dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
        dbsFolder = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getResumeFolderName());
        
        currentFolderDocId4Select = dbsFolder.getId();
      }else if( folderDocInfo.getRtsType() ==2 || folderDocInfo.getRtsType() ==4){
        dbsFolder = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                "/"+folderDocInfo.getRtsBase()+"/"+
                                userInfo.getTimesheetFolderName());
        
        currentFolderDocId4Select = dbsFolder.getId();
      
      }else{
        currentFolderDocId4Select =folderDocInfo.getHomeFolderId();
      }
      logger.debug("currentFolderDocId4Select: "+currentFolderDocId4Select+
                   "FolderName : "
                   +dbsFileSystem.findPublicObjectById(currentFolderDocId4Select).getName());
      
      if(request.getParameter("heading")!=null){
        heading=request.getParameter("heading");
      }else if(httpSession.getAttribute("heading")!=null) {
        heading=(String)httpSession.getAttribute("heading");
      }
      logger.debug("heading: "+heading);
      
      if(request.getParameter("foldersOnly")!=null){
        foldersOnly=(new Boolean(request.getParameter("foldersOnly"))).booleanValue();
      }else if(httpSession.getAttribute("foldersOnly")!=null) {
        foldersOnly=(new Boolean(httpSession.getAttribute("foldersOnly").toString())).booleanValue();
      }
      logger.debug("foldersOnly: "+foldersOnly);      
            
      if(request.getParameter("openerControl")!=null){
        openerControl=request.getParameter("openerControl");
      }else if(httpSession.getAttribute("foldersOnly")!=null) {
        openerControl=httpSession.getAttribute("openerControl").toString();
      }
      logger.debug("openerControl: "+openerControl);
     
      if(request.getParameter("treeAppend")!=null){
        treeAppend=true;
      }
      logger.debug("icon path :" + userPreferences.getTreeIconPath());
      logger.debug("folderDocInfo.getRtsType: "+folderDocInfo.getRtsType());
      /* */
      if( folderDocInfo.getRtsType() ==1 || folderDocInfo.getRtsType() ==3 ||
          isForMail.booleanValue() ){
          
        if (httpSession.getAttribute("Treeview4Select")!=null){
          treeview4Select=(Treeview)httpSession.getAttribute("Treeview4Select");
          //logger.debug("1");
            if (treeview4Select!=null){
              if (treeview4Select.isFoldersOnly()!=foldersOnly){
                //logger.debug("2");
                treeview4Select.free();              
                treeview4Select = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),physicalPath,userPreferences.getTreeIconPath()+ "/",httpSession.getId(),foldersOnly,folderDocInfo.getRtsBase(),isForMail.booleanValue(),folderDocInfo.getRtsType());
                //logger.debug("3");
              }
          }
        }
        //logger.debug("4");      
        if (currentFolderDocId4Select !=null){
          //logger.debug("5");
          if (treeview4Select==null || recreateTree){
            //logger.debug("6");
            if (treeview4Select!=null){
              treeview4Select.free();
              //logger.debug("7");
            }
            treeview4Select = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),physicalPath,userPreferences.getTreeIconPath()+ "/",httpSession.getId(),foldersOnly,folderDocInfo.getRtsBase(),isForMail.booleanValue(),folderDocInfo.getRtsType());
            //logger.debug("8");
          }
  
          dbsPublicObject=dbsLibrarySession.getPublicObject(currentFolderDocId4Select ).getResolvedPublicObject();
          currentFolderPath=dbsPublicObject.getAnyFolderPath();
          isDocument=(dbsPublicObject instanceof DbsDocument);
          
          if(treeAppend){
            //logger.debug("9");
            treeview4Select.appendTree(currentFolderDocId4Select );
          }
          //logger.debug("10");
          treeview4Select.forAddressBar(currentFolderDocId4Select );
        }
        httpSession.setAttribute("Treeview4Select",treeview4Select);
        httpSession.setAttribute("currentFolderDocId4Select",currentFolderDocId4Select );    
        forward = new String("resumes");
      }else if( folderDocInfo.getRtsType() ==2 || folderDocInfo.getRtsType() ==4) {
        if (httpSession.getAttribute("Treeview4Timesheet")!=null){
          treeview4Timesheet=(Treeview)httpSession.getAttribute("Treeview4Timesheet");
          //logger.debug("1");
            if (treeview4Timesheet!=null){
              if (treeview4Timesheet.isFoldersOnly()!=foldersOnly){
                //logger.debug("2");
                treeview4Timesheet.free();              
                treeview4Timesheet = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),physicalPath,userPreferences.getTreeIconPath()+ "/",httpSession.getId(),foldersOnly,folderDocInfo.getRtsBase(),isForMail.booleanValue(),folderDocInfo.getRtsType());
                //logger.debug("3");
              }
          }
        }
        //logger.debug("4");      
        if (currentFolderDocId4Select !=null){
          //logger.debug("5");
          if (treeview4Timesheet==null || recreateTree){
            //logger.debug("6");
            if (treeview4Timesheet!=null){
              treeview4Timesheet.free();
              //logger.debug("7");
            }
            treeview4Timesheet = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),physicalPath,userPreferences.getTreeIconPath()+ "/",httpSession.getId(),foldersOnly,folderDocInfo.getRtsBase(),isForMail.booleanValue(),folderDocInfo.getRtsType());
            //logger.debug("8");
          }
  
          dbsPublicObject=dbsLibrarySession.getPublicObject(currentFolderDocId4Select ).getResolvedPublicObject();
          currentFolderPath=dbsPublicObject.getAnyFolderPath();
          isDocument=(dbsPublicObject instanceof DbsDocument);
          
          if(treeAppend){
            //logger.debug("9");
            treeview4Timesheet.appendTree(currentFolderDocId4Select );
          }
          //logger.debug("10");
          treeview4Timesheet.forAddressBar(currentFolderDocId4Select );
        }
        httpSession.setAttribute("Treeview4Timesheet",treeview4Timesheet);
        httpSession.setAttribute("currentFolderDocId4Select",currentFolderDocId4Select );    
        forward = new String("timesheet");
      }else{
        if (httpSession.getAttribute("Treeview4Personal")!=null){
          treeview4Personal=(Treeview)httpSession.getAttribute("Treeview4Personal");
          //logger.debug("1");
            if (treeview4Personal!=null){
              if (treeview4Personal.isFoldersOnly()!=foldersOnly){
                //logger.debug("2");
                treeview4Personal.free();              
                treeview4Personal = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),physicalPath,userPreferences.getTreeIconPath()+ "/",httpSession.getId(),foldersOnly,folderDocInfo.getRtsBase(),isForMail.booleanValue(),folderDocInfo.getRtsType());
                //logger.debug("3");
              }
          }
        }
        //logger.debug("4");      
        if (currentFolderDocId4Select !=null){
          //logger.debug("5");
          if (treeview4Personal==null || recreateTree){
            //logger.debug("6");
            if (treeview4Personal!=null){
              treeview4Personal.free();
              //logger.debug("7");
            }
            treeview4Personal = new Treeview(dbsLibrarySession,"FolderDocSelect",userPreferences.getTreeLevel(),physicalPath,userPreferences.getTreeIconPath()+ "/",httpSession.getId(),foldersOnly,folderDocInfo.getRtsBase(),isForMail.booleanValue(),folderDocInfo.getRtsType());
            //logger.debug("8");
          }
  
          dbsPublicObject=dbsLibrarySession.getPublicObject(currentFolderDocId4Select ).getResolvedPublicObject();
          currentFolderPath=dbsPublicObject.getAnyFolderPath();
          isDocument=(dbsPublicObject instanceof DbsDocument);
          
          if(treeAppend){
            //logger.debug("9");
            treeview4Personal.appendTree(currentFolderDocId4Select );
          }
          //logger.debug("10");
          treeview4Personal.forAddressBar(currentFolderDocId4Select );
        }
        httpSession.setAttribute("Treeview4Personal",treeview4Personal);
        httpSession.setAttribute("currentFolderDocId4Select",currentFolderDocId4Select );    
        forward = new String("personal");
        
      }
        /* */
        
        folderDocSelectForm= new  FolderDocSelectForm();
        folderDocSelectForm.setFolderDocument(currentFolderPath);
        folderDocSelectForm.setHdnFolderDocument(currentFolderPath); 
        folderDocSelectForm.setHdnOpenerControl(openerControl);
        folderDocSelectForm.setHdnFoldersOnly(foldersOnly);
        
        /*httpSession.setAttribute("Treeview4Select",treeview4Select);
        httpSession.setAttribute("currentFolderDocId4Select",currentFolderDocId4Select );*/    
        request.setAttribute("isDocument",new Boolean(isDocument));    
        request.setAttribute("folderDocSelectForm",folderDocSelectForm);
        request.setAttribute("hdnIsForMail",isForMail);
        httpSession.setAttribute("heading",heading);
            

        if (httpSession.getAttribute("actionerrors")!=null){
            ActionErrors actionErrors = (ActionErrors) httpSession.getAttribute("actionerrors");
            saveErrors(request,actionErrors); 
            httpSession.removeAttribute("actionerrors");
        }
        
    }catch(DbsException e){
      exceptionBean = new ExceptionBean(e);
      logger.error(exceptionBean.getMessage());
      saveErrors(request,exceptionBean.getActionErrors()); 
      return mapping.findForward(forward);
    }catch(Exception e){
      exceptionBean = new ExceptionBean(e);
      logger.error(exceptionBean.getMessage());
      saveErrors(request,exceptionBean.getActionErrors());
      return mapping.findForward(forward);
    }

    return mapping.findForward(forward);
    
  }

}