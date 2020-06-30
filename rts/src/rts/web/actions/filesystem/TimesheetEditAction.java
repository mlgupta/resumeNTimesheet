package rts.web.actions.filesystem;

/*rts package references*/
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
/*java API*/
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/*Struts API*/
import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.apache.struts.upload.*;
/**
 * Purpose  : Action code for editing timesheet selected.
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   09-08-2005
 * 	Last Modified by :  Suved Mishra   
 * 	Last Modified Date: 16-08-2005   
 */

public class TimesheetEditAction extends Action  {

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
  logger.info("Uploading File");
  
  //variable declaration
  ExceptionBean exceptionBean;
  String forward = "success";
  String fileNames =new String();
  HttpSession httpSession = null;
  Integer errorIndex=new Integer(-1);
  RtsVersion rtsVersion = null;
  DbsFamily dbsFam = null;
  double fileSizeInMb = 0;
  boolean isToUpload  = true;
  FolderDocInfo folderDocInfo = null;
  UserPreferences userPreferences = null;
  DbsLibrarySession dbsLibrarySession = null;
  DbsFileSystem dbsFileSystem = null;
  DbsFolder timesheetRoot = null;
  
  try{
    httpSession = request.getSession(false);
    TimesheetEditForm timesheetEditFormOld = (TimesheetEditForm)
                              httpSession.getAttribute("timesheetEditFormOld");
                              
    TimesheetEditForm timesheetEditForm = (TimesheetEditForm)form;
    Long poToEditId = (Long)httpSession.getAttribute("timesheetIdToEdit");
    logger.debug("OldEditForm : " +timesheetEditFormOld);
    logger.debug("NewEditForm : " +timesheetEditForm);
    
    UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
    folderDocInfo = (FolderDocInfo)
                                httpSession.getAttribute("FolderDocInfo");            
    userPreferences = (UserPreferences)
                            httpSession.getAttribute("UserPreferences");
    dbsLibrarySession = userInfo.getDbsLibrarySession();
    logger.debug("1");
    
    FormFile frmFile= null;
    try{
      frmFile= timesheetEditForm.getFileOne();
    }catch(Exception ex ){
      logger.debug("Error: "+ex.getMessage());
      ex.printStackTrace();
    }
    DbsPublicObject dbsPOToEdit = dbsLibrarySession.getPublicObject(
                                                              poToEditId);
    DbsDocument dbsDocToEdit = null;
    
    if( dbsPOToEdit.getClassname().equalsIgnoreCase("Timesheet") ){
      dbsDocToEdit = (DbsDocument)dbsPOToEdit;
      logger.debug("dbsDocToEdit classname: "+dbsDocToEdit.getClassname());
    }else if(dbsPOToEdit.getClassname().equalsIgnoreCase(DbsFamily.CLASS_NAME)){
      /* if dbsPOToEdit is versioned,obtain most recent version */
      dbsFam = (DbsFamily)dbsPOToEdit;
      rtsVersion = new RtsVersion(dbsLibrarySession);
      if(dbsFam!=null){
        logger.debug(" dbsFam is not null...");
        DbsVersionSeries dbsVs = rtsVersion.getPrimaryVersionSeries(dbsFam);
        DbsVersionDescription dbsLastVd = dbsVs.getLastVersionDescription();
        dbsPOToEdit = dbsLastVd.getDbsPublicObject();
        dbsDocToEdit = (DbsDocument)dbsPOToEdit;
      }else{
        logger.debug("dbsFam is null...");
      }
      
    }
    /* create new document definition */
    DbsDocumentDefinition dbsDocDef = new DbsDocumentDefinition(
                                                      dbsLibrarySession);
    /* obtain "TIMESHEET" classObject */
    DbsClassObject dbsClassObject =
                      dbsLibrarySession.getClassObjectByName("TIMESHEET");
                      
    logger.debug(" obtained resume classobject successfully");			
    
    /* set classObject for document definition */
    if (dbsClassObject != null) {
      dbsDocDef.setClassObject(dbsClassObject);
      logger.debug("setting dbsDocDef classobject now ");
    }
    
    
    String fileName = null;
    InputStream iStream = null;
    // if there is new content to be uploaded...
    /* Obtain formFile object,calculate filesize and check if quota has 
     * been exceeded */      
    if(frmFile!=null && frmFile.getFileName().trim().length()!=0){
      logger.debug("2");
      fileName = frmFile.getFileName();
      fileSizeInMb = frmFile.getFileSize();
      fileSizeInMb = fileSizeInMb/(1024*1024);
      logger.debug("fileSizeInMb b4 rounding off : "+fileSizeInMb);
      fileSizeInMb = (Math.round(fileSizeInMb*100)/100.00);
      logger.debug("fileSizeInMb : "+fileSizeInMb);
      /* if quota limit isn't violated, obtain file's i/p stream */
      if( fileSizeInMb <= (userInfo.getAllocatedQuota() - 
                           userInfo.getUsedQuota())){
        iStream = frmFile.getInputStream();  
      }else{    // retain existing content
        isToUpload = false;
        iStream = dbsDocToEdit.getContentStream();
      }
      
    }else{      // retain existing content
      logger.debug("3");
      fileName = dbsPOToEdit.getName();
      iStream = dbsDocToEdit.getContentStream();
    }

    /* if quota has not been exceeded , go ahead and set attrs for this new 
     * document definition */
    if( isToUpload ){
    
      logger.debug("fileName: "+fileName);
      DbsFormat format=null;
      String ext = null;
      /* obtain document format and set it */
      if(fileName!=null){
       
       ext = fileName.substring(
                          (fileName.lastIndexOf(".")+1),fileName.length());
       
       logger.debug("doc extension is: "+ext);
       
       if(ext == null ){
        ext = "bin";
       }
       
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
      
      logger.debug("4 : document format set successfully...");
      
      dbsDocDef.setFormat(format);

      }

      String docName = dbsPOToEdit.getName();
      String fName = dbsPOToEdit.getName().substring(0,docName.lastIndexOf("."))+
                     "."+ext;
      /* set document's NAME attribute ,if name has been changed,
       * else retain existing NAME */
      dbsDocDef.setAttribute(DbsDocument.NAME_ATTRIBUTE,
                              DbsAttributeValue.newAttributeValue(fName));
      
      dbsDocDef.setContentStream(iStream);
      if( iStream != null ){
        try{
          iStream.close();
          logger.debug("InputStream Closed");
        }catch(IOException ioEx){
          logger.debug(ioEx.toString());
        }
        iStream = null;
      }else{
        logger.debug("No InputStream Found");
      }
      
      String name1 = ((String)PropertyUtils.getSimpleProperty(
                                                form,"txtName")).trim();
      logger.debug("5 name1: " +name1);
      String name1Old = timesheetEditFormOld.getTxtName();
      logger.debug("6 name1Old: "+name1Old);
      if( name1!=null && name1.length()!=0 && !name1.equals(name1Old)){
        logger.debug("7");
        dbsDocDef.setAttribute("NAME1",
                                DbsAttributeValue.newAttributeValue(
                                name1));
                                
        dbsDocDef.setAttribute(DbsDocument.NAME_ATTRIBUTE,
                               DbsAttributeValue.newAttributeValue(
                               name1+"."+ext));
      }else{
        logger.debug("8");
        dbsDocDef.setAttribute("NAME1",
                                DbsAttributeValue.newAttributeValue(
                                name1Old));
      }
      
      /* set document's CSV attribute ,if name has been changed,
       * else retain existing NAME */        
      String address = ((String)PropertyUtils.getSimpleProperty(
                                                  form,"txaCSV")).trim();
                        
      logger.debug("9 address: " +address);
      String addressOld = timesheetEditFormOld.getTxaCSV();
      logger.debug("10 addressOld: "+addressOld);
      if( address!=null && address.length()!=0 && 
          !address.equals(addressOld)){
          
        logger.debug("11");
        dbsDocDef.setAttribute("CSV",
                                DbsAttributeValue.newAttributeValue(
                                address));
      }else{
        logger.debug("12");
        dbsDocDef.setAttribute("CSV",
                                DbsAttributeValue.newAttributeValue(
                                addressOld));
      }

      /* set document's DESCRIPTION attribute ,if name has been changed,
       * else retain existing NAME */        
      String description = ((String)PropertyUtils.getSimpleProperty(
                                            form,"txtFileDesc")).trim();
      
      logger.debug("13 description: "+description);
      String descriptionOld = timesheetEditFormOld.getTxtFileDesc();
      logger.debug("14 descriptionOld: "+descriptionOld);
      
      if( description!=null && description.length()!=0 && 
          !description.equals(descriptionOld)){
        
        logger.debug("15");
        dbsDocDef.setAttribute("DESCRIPTION",
                                DbsAttributeValue.newAttributeValue(
                                "T"+description));
      }else{
        logger.debug("16");
        dbsDocDef.setAttribute("DESCRIPTION",
                                DbsAttributeValue.newAttributeValue(
                                "T"+descriptionOld));
      }
      logger.debug("17");

      DbsDocument dbsEditedDoc = (DbsDocument)
                                  dbsLibrarySession.createPublicObject(
                                  dbsDocDef);
                                  
      logger.debug("18 dbsEditedDoc : "+dbsEditedDoc.getName());

      /* change the timesheet's document name , append the documentId b4 the 
       * extension so as to make it unique */
      String modifiedTimesheetName =dbsEditedDoc.getAttribute("NAME1").getString(
                                    dbsLibrarySession)+dbsEditedDoc.getId()+"."+
                                    ext;
      
      logger.debug("modifiedTimesheetName : "+modifiedTimesheetName);
      dbsEditedDoc.setName(modifiedTimesheetName);

      DbsDirectoryGroup [] groups=dbsLibrarySession.getUser().getAllAncestors();
      DbsDirectoryGroup companyGroup = null;
      DbsAccessControlList compACL = null;
      if( groups!=null ){
        companyGroup = groups[0];
        System.out.println("Company Group: "+companyGroup.getName());
        compACL = companyGroup.getAcl();
        logger.debug("compACL : "+compACL.getName());
        // set ACL to companyACL
        dbsEditedDoc.setAcl(compACL);
      }

      /* Obtain the original Parent for the timesheet being edited */
      Long originalFolderId = (Long)httpSession.getAttribute("originalFolderId");
      DbsFolder originalFolder = (DbsFolder)dbsLibrarySession.getPublicObject(
                                            originalFolderId);
      httpSession.removeAttribute("originalFolderId");
      
      if(dbsFam!=null){
        
        String [] linkToBeSharedWith = null; /* indicates folders with 
                                              * whom link must be 
                                              * established */
                                              
        String [] linkToBeRemovedFrom = null;/* indicates folders from 
                                              * where link must be 
                                              * removed */
        FolderDoc folderDoc = null;
        DbsFolder targetFolderForLink = null;
        
        try{
          long newVersionId = dbsFam.getVersionNumber()+1;
          rtsVersion.checkOut(dbsFam,"checked out for version: "+
                              newVersionId);
                              
          rtsVersion.checkIn(dbsFam,"checked in for version: "+
                              newVersionId , dbsEditedDoc);

            
          linkToBeRemovedFrom = timesheetEditFormOld.getLstAttachment();
          folderDoc = new FolderDoc(dbsLibrarySession);
          dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
          
          /* if there is a list of folders 4 share */
          if( timesheetEditForm.getLstAttachment()!=null  ){

            
            timesheetRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                  "/"+folderDocInfo.getRtsBase()+"/"+
                                  userInfo.getTimesheetFolderName());
            
            linkToBeSharedWith = (String[])
                                      PropertyUtils.getSimpleProperty(
                                      form,"lstAttachment");
            
            logger.debug("linkToBeSharedWith : "+
                          linkToBeSharedWith.length);
            
            Long [] ids = new Long[1];
            ids[0] = dbsFam.getId();
            
            if( linkToBeRemovedFrom != null ){
            /* if linkToBeRemovedFrom[] is not null,then first remove link 
             * from folders in linkToBeRemovedFrom[].*/
              for(int index = 0; index < linkToBeRemovedFrom.length; index++){
                targetFolderForLink = (DbsFolder) 
                                      dbsFileSystem.findPublicObjectByPath(
                                      linkToBeRemovedFrom[index]);
                logger.debug("linkToBeRemovedFrom["+index+"] : "+
                              linkToBeRemovedFrom[index]);
                              
                try {
                  dbsFileSystem.removeFolderRelationship(
                                        targetFolderForLink,dbsFam,false);
                }
                catch (DbsException dbsEx) {
                  logger.error("Error: "+dbsEx.getErrorMessage());
                }
              }
            }                
            
            /* then,share link with folders in linkToBeSharedWith[] */
            for( int index = 0; index < linkToBeSharedWith.length; index++ ){

              if( linkToBeSharedWith[index]!=null && 
                  linkToBeSharedWith[index].trim().length()!=0 ){
                
                targetFolderForLink = (DbsFolder) 
                                      dbsFileSystem.findPublicObjectByPath(
                                      linkToBeSharedWith[index]);

                if(targetFolderForLink.getId()!= timesheetRoot.getId() &&
                   targetFolderForLink.getId()!= originalFolder.getId() ){

                  logger.debug("linkToBeSharedWith["+index+"] : "+
                                linkToBeSharedWith[index]);
                
                  folderDoc.linkPOs(targetFolderForLink.getId(),ids);
                }
              }
            }
          }else{ /* if no folders 4 share, remove link from 
                  * linkToBeRemovedFrom[]*/
            
            Long [] ids = new Long[1];
            ids[0] = dbsFam.getId();
            
            /* remove link from folders in linkToBeRemovedFrom[] */
            if( linkToBeRemovedFrom != null ){
            /* if linkToBeRemovedFrom[] is not null,then first remove link 
             * from folders in linkToBeRemovedFrom[].*/
              for(int index = 0; index < linkToBeRemovedFrom.length; index++){
                targetFolderForLink = (DbsFolder) 
                                      dbsFileSystem.findPublicObjectByPath(
                                      linkToBeRemovedFrom[index]);
                logger.debug("linkToBeRemovedFrom["+index+"] : "+
                              linkToBeRemovedFrom[index]);
                              
                try {
                  dbsFileSystem.removeFolderRelationship(
                                        targetFolderForLink,dbsFam,false);
                }
                catch (DbsException dbsEx) {
                  logger.error("Error: "+dbsEx.getErrorMessage());
                }
              }
            }                
          }
        
        }catch(DbsException dbsEx){
          logger.error("Exception: "+dbsEx.getErrorMessage());
          dbsEx.printStackTrace();
        }catch( ExceptionBean exb ){
          dbsEditedDoc = null;
          //targetFolder.addItem(dbsDocToEdit);
          logger.error(exb.getMessage());
          logger.debug(exb.getErrorTrace());
          httpSession.setAttribute("ActionErrors",exb.getActionErrors());
        }
        
      }else{
        DbsTransaction dbsEditTransction = dbsLibrarySession.beginTransaction();
        logger.debug("dbsEditTransaction begins...");
        dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
        timesheetRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                    "/"+folderDocInfo.getRtsBase()+"/"+
                                    userInfo.getTimesheetFolderName());
        
        logger.debug("19 : finding target folder now...");
        DbsFolder targetFolder = originalFolder;
                                    
        logger.debug("20 : target folder found..."); 
        dbsFileSystem.delete(dbsDocToEdit);
        logger.debug("21 : doc deleted...");            
        try{
          targetFolder.addItem(dbsEditedDoc);
          logger.debug("22 : document edited successfully...");
          if( timesheetEditForm.getLstAttachment()!=null  ){
            //String [] toBeSharedWith = docEditForm.getLstAttachment();
            String [] toBeSharedWith = (String[])
                                        PropertyUtils.getSimpleProperty(
                                        form,"lstAttachment");
                                        
            logger.debug("toBeSharedWith : "+toBeSharedWith.length);
            FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
            Long [] ids = new Long[1];
            ids[0] = dbsEditedDoc.getId();
            for( int index = 0; index < toBeSharedWith.length; index++ ){
              DbsFolder targetFolderForLink = null;
              
              if( toBeSharedWith[index]!=null && 
                  toBeSharedWith[index].trim().length()!=0 ){
              
                targetFolderForLink = (DbsFolder) 
                                      dbsFileSystem.findPublicObjectByPath(
                                      toBeSharedWith[index]);
                /* link this document with other folders 
                 * mentioned in toBeSharedWith[] */                      
                if( (targetFolderForLink.getId()!= targetFolder.getId()) &&
                    (targetFolderForLink.getId()!= timesheetRoot.getId()) ){
                  folderDoc.linkPOs(targetFolderForLink.getId(),ids);
                }
              
              }
            
            }
          }
        }catch( DbsException dbsEx ){
          if( dbsEx.containsErrorCode(30002) ){
            dbsEditedDoc = null;
            //targetFolder.addItem(dbsDocToEdit);
            dbsLibrarySession.abortTransaction(dbsEditTransction);
            dbsEditTransction = null;
            logger.debug("dbsEditTransaction aborted...");
            ActionErrors actionErrors= new ActionErrors();
            ActionError actionError= new ActionError("errors.30002.duplicationerror");
            actionErrors.add(ActionErrors.GLOBAL_ERROR,actionError);
            httpSession.setAttribute("ActionErrors",actionErrors);              
          }
        }catch( ExceptionBean exb ){
          dbsEditedDoc = null;
          //targetFolder.addItem(dbsDocToEdit);
          dbsLibrarySession.abortTransaction(dbsEditTransction);
          dbsEditTransction = null;
          logger.debug("dbsEditTransaction aborted...");
          logger.error(exb.getMessage());
          logger.debug(exb.getErrorTrace());
          httpSession.setAttribute("ActionErrors",exb.getActionErrors());
        }
        if(dbsEditTransction != null){
          dbsLibrarySession.completeTransaction(dbsEditTransction);
          dbsEditTransction = null;
          logger.debug("dbsEditTransaction completes...");
        }
      }
    }else{
     dbsDocDef = null;
     dbsClassObject = null;
     ActionErrors actionErrors= new ActionErrors();
     ActionError actionError= new ActionError("errors.QuotaExceedsEdit");
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
  logger.info("Uploading File Complete");
  forward= (userPreferences.getNavigationType() == 
            UserPreferences.FLAT_NAVIGATION)?"timesheet":
                                             "timesheet4Tree";
  
  return mapping.findForward(forward);
}

}