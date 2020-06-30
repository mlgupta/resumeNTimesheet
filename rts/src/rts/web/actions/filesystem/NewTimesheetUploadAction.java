package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.DbsAccessControlList;
import rts.beans.DbsAttributeValue;
import rts.beans.DbsClassObject;
import rts.beans.DbsCollection;
import rts.beans.DbsDirectoryGroup;
import rts.beans.DbsDocument;
import rts.beans.DbsDocumentDefinition;
import rts.beans.DbsException;
import rts.beans.DbsFileSystem;
import rts.beans.DbsFolder;
import rts.beans.DbsFormat;
import rts.beans.DbsLibrarySession;
import rts.beans.DbsSelector;
import rts.beans.DbsTransaction;
import rts.web.actionforms.filesystem.TimesheetUploadForm;
import rts.web.beans.exception.ExceptionBean;
import rts.web.beans.filesystem.FolderDoc;
import rts.web.beans.filesystem.FolderDocInfo;
import rts.web.beans.user.*;
/* Java API */
import java.io.IOException;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/* Struts API */
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.log4j.*;
import org.apache.struts.upload.FormFile;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.commons.beanutils.PropertyUtils;
/**
 * Purpose  : Action code for uploading timesheet with appropriate checks.
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   09-08-2005
 * 	Last Modified by :  Suved Mishra   
 * 	Last Modified Date: 10-08-2005   
 */

public class NewTimesheetUploadAction extends Action  {

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
  //variable declaration
  ExceptionBean exceptionBean;
  String forward = "success";
  String fileNames =new String();
  HttpSession httpSession = null;
  Integer errorIndex=new Integer(-1);
  double fileSizeInMb = 0;
  UserPreferences userPreferences = null;
  FolderDocInfo folderDocInfo = null;
  DbsFileSystem dbsFileSystem = null;
  DbsFolder timesheetRoot = null;
  
  try{
      httpSession = request.getSession(false);
      TimesheetUploadForm timesheetUploadForm = (TimesheetUploadForm)form;
      logger.debug("NewDocUploadForm : " + timesheetUploadForm);
      FormFile frmFile= null;
      UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
      
      folderDocInfo = (FolderDocInfo)
                                httpSession.getAttribute("FolderDocInfo");            
      
      userPreferences = (UserPreferences)
                              httpSession.getAttribute("UserPreferences");
      
      DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
      logger.debug("1: Entering NewTimesheetUploadAction ...");
      /*set the path where the doc is to be uploaded*/
      timesheetUploadForm.setTxtPath(folderDocInfo.getCurrentFolderPath());
      logger.debug("2 : "  + folderDocInfo.getCurrentFolderPath());
      String currentFolderPath = timesheetUploadForm.getTxtPath();
      logger.debug("3");
      logger.info("Upload Location : " + currentFolderPath);
      logger.debug("currentFolderPath : " + currentFolderPath);
      /*obtain the destination folder's id*/
      Long currentFolderId = folderDocInfo.getCurrentFolderId();
      logger.debug("currentFolderId : " + currentFolderId);
      
      /*obtain FormFile and the resp description string */ 
      frmFile= timesheetUploadForm.getFileOne();
      fileSizeInMb = frmFile.getFileSize();
      fileSizeInMb = fileSizeInMb/(1024*1024);
      logger.debug("fileSizeInMb b4 rounding off : "+fileSizeInMb);
      fileSizeInMb = (Math.round(fileSizeInMb*100)/100.00);
      logger.debug("fileSizeInMb : "+fileSizeInMb);
      /* fileSizeInMb <= remaining space, go ahead and upload file */
      if( fileSizeInMb <= (userInfo.getAllocatedQuota() - userInfo.getUsedQuota())){
      
      logger.debug("4 : " + timesheetUploadForm.getTxtName());
      /* create new document definition */
      DbsDocumentDefinition newDbsDocumentDefinition= 
                              new DbsDocumentDefinition(dbsLibrarySession);
      
      
      logger.debug("5 : newDbsDocumentDefinition"+
                                      "instance created successfully...");
      /* obtain "TIMESHEET" classObject */                                
      DbsClassObject dbsClassObject = 
                        dbsLibrarySession.getClassObjectByName("TIMESHEET"); 
      
       
      logger.debug("6 : timesheet classobject obtained successfully");			
      /* set classObject for document definition */
      if (dbsClassObject != null) {
        newDbsDocumentDefinition.setClassObject(dbsClassObject);
        
        logger.debug("7 : setting newDbsDocumentDefinition classobject now ");
      }
      
      String docName = null;
      if(frmFile!=null){
       docName = frmFile.getFileName().trim();
      }
      DbsFormat format=null;     
      String ext = null;
      if( docName!=null && docName.length()!=0 ){   // if content uploaded
        ext = docName.substring((docName.lastIndexOf(".")+1),docName.length());
        logger.debug("doc extension is: "+ext);
        /*InputStream is= frmFile.getInputStream();
        logger.debug("is" + is);*/
        newDbsDocumentDefinition.setContentStream(frmFile.getInputStream());
        try{
          frmFile.getInputStream().close();
        }catch (Exception e) {
          logger.debug(e.toString());
        }
        logger.debug("InputStream closed");
        logger.debug("8 : " + frmFile.getFileName().trim() + ""
                                                +  frmFile.getFileSize());
      }else{                                      // if no content uploaded
        newDbsDocumentDefinition.setContent("No Document Uploaded "
                                            +"For This Timesheet.");
        ext = "txt";
      }
      /* set document's NAME attribute */  
      newDbsDocumentDefinition.setAttribute(DbsDocument.NAME_ATTRIBUTE,	
                                      DbsAttributeValue.newAttributeValue(
                                      timesheetUploadForm.getTxtName()+"."+ext));
      /* set document's NAME1 attribute */                                
      newDbsDocumentDefinition.setAttribute("NAME1",	
                                      DbsAttributeValue.newAttributeValue(
                                      timesheetUploadForm.getTxtName()));
                                      
      logger.debug("9 : document name and timesheet holder's name set successfully...");
      /* set document's DESCRIPTION attribute */
      newDbsDocumentDefinition.setAttribute("DESCRIPTION", 
                                      DbsAttributeValue.newAttributeValue(
                                      "T" + timesheetUploadForm.getTxtFileDesc()));
                                      
      logger.debug("10 : description set successfully... ");
      
      /* set document's CSV attribute */
      newDbsDocumentDefinition.setAttribute("CSV", 
                                      DbsAttributeValue.newAttributeValue(
                                      (timesheetUploadForm.getTxaCSV()!=null &&
                                       timesheetUploadForm.getTxaCSV().trim().length()!=0)?                                            
                                       timesheetUploadForm.getTxaCSV():""));

      logger.debug("11 : CSV set successfully...");
      /* obtain document format and set it */
      DbsCollection c = dbsLibrarySession.getFormatExtensionCollection();              
      try{
        format = (DbsFormat)c.getItems(ext);          
      }catch (DbsException e) {
        if (e.containsErrorCode(12200)) {
          DbsSelector selector = new DbsSelector(dbsLibrarySession);
          selector.setSearchClassname(DbsFormat.CLASS_NAME);
         
          selector.setSearchSelection(DbsFormat.MIMETYPE_ATTRIBUTE + 
                                        " = 'application/octet-stream'");
         
          format = (DbsFormat) selector.getItems(0);
          e.printStackTrace();
        }                
       }
      logger.debug("12 : document format set successfully...");
      newDbsDocumentDefinition.setFormat(format);

      DbsDirectoryGroup [] groups=dbsLibrarySession.getUser().getAllAncestors();
      DbsDirectoryGroup companyGroup = null;
      DbsAccessControlList compACL = null;
      if( groups!=null ){
        companyGroup = groups[0];
        System.out.println("Company Group: "+companyGroup.getName());
        compACL = companyGroup.getAcl();
        logger.debug("compACL : "+compACL.getName());
        // set ACL to companyACL
        newDbsDocumentDefinition.setAttribute(DbsDocument.ACL_ATTRIBUTE,
                                 DbsAttributeValue.newAttributeValue(compACL));
      }

      /* All attrs set. Now create document from it's definition. */
      DbsDocument dbsDocument = (DbsDocument)
                                    dbsLibrarySession.createPublicObject(
                                    newDbsDocumentDefinition);
                                      
      logger.debug("dbsDocument className now is : "+dbsDocument.getClassname()+
                   " and " +dbsDocument.CLASS_NAME);
                    
      /* change the timesheet's document name , append the documentId b4 the 
       * extension so as to make it unique */
      String modifiedTimesheetName =dbsDocument.getAttribute("NAME1").getString(
                                    dbsLibrarySession)+dbsDocument.getId()+"."+
                                    ext;
      
      logger.debug("modifiedTimesheetName : "+modifiedTimesheetName);
      dbsDocument.setName(modifiedTimesheetName);

      DbsTransaction dbsUploadTransaction = dbsLibrarySession.beginTransaction();
      logger.debug("dbsUploadTransaction begins...");
      dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
      logger.debug("13 : finding target folder now...");
      DbsFolder targetFolder = (DbsFolder)
                                    dbsFileSystem.findPublicObjectByPath(
                                    folderDocInfo.getCurrentFolderPath().trim());
      timesheetRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                          "/"+folderDocInfo.getRtsBase()+"/"+
                                          userInfo.getTimesheetFolderName());

      logger.debug("14 : target folder found...");
      try{
        targetFolder.addItem(dbsDocument);
        logger.debug("15 : document uploaded to target folder successfully...");

        if( timesheetUploadForm.getLstAttachment()!=null  ){
          //String [] toBeSharedWith = docEditForm.getLstAttachment();
          String [] toBeSharedWith = (String[])PropertyUtils.getSimpleProperty(
                                                          form,"lstAttachment");
                                                          
          logger.debug("toBeSharedWith : "+toBeSharedWith.length);
          FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
          Long [] ids = new Long[1];
          ids[0] = dbsDocument.getId();
          for( int index = 0; index < toBeSharedWith.length; index++ ){
            DbsFolder targetFolderForLink = null;
            if( toBeSharedWith[index]!=null && 
                toBeSharedWith[index].trim().length()!=0 ){
            
              targetFolderForLink = (DbsFolder) 
                                    dbsFileSystem.findPublicObjectByPath(
                                    toBeSharedWith[index]);
              
              if( (targetFolderForLink.getId()!= targetFolder.getId()) && 
                  (targetFolderForLink.getId()!= timesheetRoot.getId()) ){
                  
                folderDoc.linkPOs(targetFolderForLink.getId(),ids);
              }
            
            }          
          
          }
        }
        
      }catch( DbsException dbsEx ){
        if( dbsEx.containsErrorCode(30002) ){
          dbsDocument = null;
          //targetFolder.addItem(dbsDocToEdit);
          dbsLibrarySession.abortTransaction(dbsUploadTransaction);
          dbsUploadTransaction = null;
          logger.debug("dbsUploadTransaction aborted...");
          ActionErrors actionErrors= new ActionErrors();
          ActionError actionError= new ActionError("errors.30002.duplicationerror");
          actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
          httpSession.setAttribute("ActionErrors",actionErrors);              
        }
      }catch( ExceptionBean exb ){
          dbsDocument = null;
          //targetFolder.addItem(dbsDocToEdit);
          dbsLibrarySession.abortTransaction(dbsUploadTransaction);
          dbsUploadTransaction = null;
          logger.debug("dbsUploadTransaction aborted...");
          logger.error(exb.getMessage());
          logger.debug(exb.getErrorTrace());
          httpSession.setAttribute("ActionErrors",exb.getActionErrors());
     }
        
     if(dbsUploadTransaction != null){
       dbsLibrarySession.completeTransaction(dbsUploadTransaction);
       dbsUploadTransaction = null;
       logger.debug("dbsUploadTransaction completes...");
     }
  }else{
   ActionErrors actionErrors= new ActionErrors();
   ActionError actionError= new ActionError("errors.QuotaExceeds");
   actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
   httpSession.setAttribute("ActionErrors",actionErrors);
  }
      
  }catch(DbsException dbsException){
    dbsException.printStackTrace();
  }catch(Exception ex){
    ex.printStackTrace();
    exceptionBean = new ExceptionBean(ex);
    logger.error(exceptionBean.getMessage());
    logger.debug(exceptionBean.getErrorTrace());
    httpSession.setAttribute("ActionErrors",exceptionBean.getActionErrors());
  }
  logger.info("Exiting NewTimesheetUploadAction ...");
  
  forward= (userPreferences.getNavigationType() == 
            UserPreferences.FLAT_NAVIGATION)?"timesheet":
                                             "timesheet4Tree";
  
  return mapping.findForward(forward);
}
}