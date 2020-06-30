package rts.web.actions.filesystem;
/*rts package references*/
import rts.web.beans.exception.*;
import rts.beans.*;
import rts.web.beans.user.UserInfo;
import rts.web.beans.filesystem.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.user.*;
/*java API*/
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;
/*Struts API*/
import org.apache.struts.action.*;
import org.apache.struts.upload.*;
import org.apache.log4j.*;

/**
 * Purpose  : Action code for uploading multiple files with appropriate checks.
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   24-08-2005
 * 	Last Modified by :  Suved Mishra   
 * 	Last Modified Date: 29-08-2005   

 */
 
public class NewDocumentUploadAction extends Action  {
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
  logger.debug("Entering NewDocumentUploadAction ...");
  logger.info("Upload File operation begins ...");
  
  //variable declaration
  ExceptionBean exceptionBean;
  String forward = "success";
  String fileNames =new String();
  HttpSession httpSession = null;
  Integer errorIndex=new Integer(-1);
  FolderDocInfo folderDocInfo = null;
  UserPreferences userPreferences = null;
  try{
    httpSession = request.getSession(false);
    NewDocUploadForm newDocUploadForm = (NewDocUploadForm)form;
    logger.debug("NewDocUploadForm : " + newDocUploadForm);
    
    UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
    folderDocInfo = (FolderDocInfo) httpSession.getAttribute("FolderDocInfo");            
    userPreferences = (UserPreferences)
                                    httpSession.getAttribute("UserPreferences");
    
    DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
    /*set the path where the doc is to be uploaded*/
    newDocUploadForm.setTxtPath(folderDocInfo.getCurrentFolderPath());
    String currentFolderPath = newDocUploadForm.getTxtPath();
    logger.info("Upload Location : " + currentFolderPath);
    logger.debug("currentFolderPath : " + currentFolderPath);
    /*obtain the destination folder's id*/
    Long currentFolderId = folderDocInfo.getCurrentFolderId();
    logger.debug("currentFolderId : " + currentFolderId);
    
    /*obtain an array of FormFile and the resp description string */ 
    FormFile[] frmFile= newDocUploadForm.getFleFiles();
    String[] fileDesc=newDocUploadForm.getTxaFileDescs();
    FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
    /*check if total upload limit set in controller tag of struts-config is beign violated*/ 
    Boolean isLimitExceeded=(Boolean)request.getAttribute(
                            MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
  
    if(isLimitExceeded!=null){
      logger.info("Total Upload Limit Violated!!!");
      errorIndex=new Integer(0);
    }
    /*if no violation, upload the valid files and their resp descriptions*/
    else{
      for(int fileCount=0;fileCount<frmFile.length;fileCount++){
        
        logger.debug("Filename: "+frmFile[fileCount].getFileName()+";"+
                     "Filesize: "+frmFile[fileCount].getFileSize()+";"+
                     "Filetype: "+frmFile[fileCount].getContentType()+";"+
                     "File Description : " + fileDesc[fileCount]);
                     
        /*check for valid file-names and proceed with uploading*/
        if((frmFile[fileCount].getFileName()!=null) && 
           (frmFile[fileCount].getFileName().trim().length()>0)){
          
          /* append prefix "P" for file description */ 
          folderDoc.uploadDocument(
                  frmFile[fileCount],currentFolderPath,"P"+fileDesc[fileCount]);
                  
          if(fileNames.length()==0){
            fileNames += frmFile[fileCount].getFileName();
            fileNames.trim();
          }
          else{
            fileNames +=","+frmFile[fileCount].getFileName();
          }
          logger.info("File: "+frmFile[fileCount].getFileName()+
                      " has been uploaded successfully.");
        }
      }
    }
    /*success, set up an action message*/
    if(errorIndex.intValue() == -1){
      ActionMessages actionMessages = new ActionMessages();
      
      ActionMessage actionMessage = 
                    new ActionMessage("msg.FileUploadedSuccessfully",fileNames);
      
      actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
      request.setAttribute("ActionMessages",actionMessages);
      /*httpSession.setAttribute("ActionMessages",actionMessages);*/
    }
    /*else set up an error message*/
    else{
      ActionErrors actionErrors= new ActionErrors();
      ActionError actionError= new ActionError("errors.limitViolation");
      actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
      if(!actionErrors.isEmpty()){
        saveErrors(request,actionErrors);
      }
    }
    request.setAttribute("errorIndex",errorIndex);
  }catch(DbsException dex){
    exceptionBean = new ExceptionBean(dex);
    /* IFS-34611,30659 contain error msgs for version series also.*/
    
    /* 34611 is for inability of a user to upload a new version,due to
     * access rights limitations viz:-inability to chkout a ver doc. */
    if(dex.containsErrorCode(34611)){
      exceptionBean.setMessageKey("errors.34611.error.reserving.version.series");
    }
    /* 30659 is for inability of a user to upload a new version,due to
     * lock acquired on the versioned doc by another user, viz:- doc is
     * chkd out by one user,while another wishes to chk in/out. */            
    if(dex.containsErrorCode(30659)){
      exceptionBean.setMessageKey("errors.30659.PO.has.userlock.unable.to.change");
    }
    /* 30063 is for inability of a user to upload a new version,due to
     * insufficient access to change PublicObject's PolicyBundle. */
    if(dex.containsErrorCode(30063)){
      exceptionBean.setMessageKey("errors.30063.insufficient.access.tochange.PO's.PB");
    }
    /* 30002 is for inability of a user to upload a new document,due to
     * a possible duplication. */
    if( dex.containsErrorCode(30002) ){
      exceptionBean.setMessageKey("errors.30002.duplicationerror");
    }
    logger.error(exceptionBean.getMessage());
    logger.debug(exceptionBean.getErrorTrace());     
    saveErrors(request,exceptionBean.getActionErrors());
    forward= new String("failure");
  }catch(Exception ex){
    exceptionBean = new ExceptionBean(ex);
    logger.error(exceptionBean.getMessage());
    logger.debug(exceptionBean.getErrorTrace());
    httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
  }
  logger.info("Upload File operation terminates ...");
  logger.debug("Exiting NewDocUploadAction ...");
  if( !forward.equals("failure") ){
    forward= (userPreferences.getNavigationType() == 
              UserPreferences.FLAT_NAVIGATION)?"personal":
                                               "personal4Tree";
  }
  return mapping.findForward(forward);
}
}
