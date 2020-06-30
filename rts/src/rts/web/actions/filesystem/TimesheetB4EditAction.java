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
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;
/**
 * Purpose  : To populate TimesheetEditForm with selected doc's attributes.
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   09-08-2005
 * 	Last Modified by :  Suved Mishra   
 * 	Last Modified Date: 16-08-2005   
 */

public class TimesheetB4EditAction extends Action  {
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
  DbsAttributeValue dbsAttrVal = null;
  DbsLibrarySession dbsLibrarySession =null;
  DbsFamily dbsFam = null;
  DbsFolder[] folderReferences = null; 
  String[] folderReferencesPath = null;
  DbsPublicObject dbsPOToEdit = null;
  DbsRelationship [] dbsRels = null;
  
  try{

    httpSession = request.getSession(false);
    
    FolderDocListForm folderDocListForm = (FolderDocListForm)form;
    
    TimesheetEditForm timesheetEditForm = new TimesheetEditForm();
    
    Long[] publicObjectId = null;
    if( request.getParameter("link").equals("true") ){
      //logger.debug("true,ID is:  "+request.getParameter("documentId"));
      publicObjectId = new Long[1];
      publicObjectId[0] = (new Long((String)request.getParameter("documentId")));  
    }else{
      publicObjectId = folderDocListForm.getChkFolderDocIds();
    }
    logger.debug("publicObjectId for edit :"+publicObjectId[0]);
    
    //FormFile frmFile= null;
    UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
    FolderDocInfo folderDocInfo = (FolderDocInfo)httpSession.getAttribute(
                                                          "FolderDocInfo");
                                                          
    //UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
    
    dbsLibrarySession = userInfo.getDbsLibrarySession();
    timesheetEditForm.setTxtPath(folderDocInfo.getCurrentFolderPath());
    logger.debug("2 : "  + folderDocInfo.getCurrentFolderPath());
    String currentFolderPath = timesheetEditForm.getTxtPath();
    logger.debug("3");
    logger.info("Upload Location : " + currentFolderPath);
    
    DbsPublicObject dbsPublicObject = dbsLibrarySession.getPublicObject(
                                                        publicObjectId[0]);
    dbsPOToEdit = dbsPublicObject;                                                    
    String dbsPOClassName = dbsPublicObject.getClassname(); 
    
    if(dbsPublicObject!=null && 
      (dbsPOClassName.equalsIgnoreCase("Timesheet") || 
      dbsPOClassName.equalsIgnoreCase("Family"))){
      /* if dbsPublicObject is versioned,obtain most recent version */
      if(dbsPOClassName.equalsIgnoreCase("Family")){
          //dbsPublicObject = dbsPublicObject.getFamily().getResolvedPublicObject();
          dbsFam = (DbsFamily)dbsPublicObject;  
          dbsPOToEdit = dbsFam;
          RtsVersion rtsVersion = new RtsVersion(dbsLibrarySession);
          if(dbsFam!=null){
            logger.debug(" dbsFam is not null...");
            DbsVersionSeries dbsVs = rtsVersion.getPrimaryVersionSeries(dbsFam);
            DbsVersionDescription dbsLastVd = dbsVs.getLastVersionDescription();
            dbsPublicObject = dbsLastVd.getDbsPublicObject();
            
          }else{
            logger.debug("dbsFam is null...");
          }
      }

      /* Next,find out the original folder for the shared resume. 
       * Incase the timesheet isn't shared , we will get its parent */
       
      DbsPublicObject originalFolder = null;
      dbsRels = dbsPOToEdit.getLeftwardRelationships("FOLDERPATHRELATIONSHIP");
      if( dbsRels != null ){
        /*for( int index = 0; index < dbsRels.length; index++ ){
          logger.debug("FolderName : "+dbsRels[index].getLeftObject().getName());
          logger.debug("SortSeq Number : "+dbsRels[index].getSortSequence());
        }*/
        originalFolder = dbsRels[0].getLeftObject();
        logger.debug("originalFolder Name : "+originalFolder.getName());
        httpSession.setAttribute("originalFolderId",originalFolder.getId());
      }    
      
      /* obtain folderReferences to dbsPublicObject */
      if( !dbsPublicObject.isVersioned() ){  
        folderReferences = dbsPublicObject.getFolderReferences();
      }else{
        folderReferences = dbsFam.getFolderReferences();
      }
      
      if( (folderReferences != null) && (folderReferences.length > 1 ) ){
        FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
        folderReferences = folderDoc.getShareFolders(folderReferences,
                                                     originalFolder.getId());
                                                     
        folderReferencesPath = new String[folderReferences.length];
        for( int index = 0; index < folderReferences.length; index++ ){
          logger.debug("folder["+index+"] : "+
                        folderReferences[index].getName());
          folderReferencesPath[index] = folderReferences[index].getAnyFolderPath();
        }
      }
      
      /* set the "share with" listbox for edit_timesheet.jsp */
      if( folderReferencesPath!=null ){
        timesheetEditForm.setLstAttachment(folderReferencesPath);
      }else{
        timesheetEditForm.setLstAttachment(folderReferencesPath);
      }
       
      timesheetEditForm.setHdnTargetFolderPath(
                              folderDocInfo.getCurrentFolderPath());
      /* setting of "timesheet"-specific attrs for edit_timesheet.jsp begins... */
      /* set NAME1 */
      DbsAttributeValue thisAttrVal = dbsPublicObject.getAttribute("NAME1");
      logger.debug("NAME1: "+thisAttrVal.getString(dbsLibrarySession));
      timesheetEditForm.setTxtName(thisAttrVal.getString(dbsLibrarySession));
      
      /* set DESCRIPTION */
      thisAttrVal = dbsPublicObject.getAttribute("DESCRIPTION");
      String desc = thisAttrVal.getString(dbsLibrarySession);
      logger.debug("Description: "+desc);
      
      if(desc.equals("T")){
        timesheetEditForm.setTxtFileDesc("");
      }else{
        timesheetEditForm.setTxtFileDesc(desc.substring(1));
      }
      
      /* set CSV */
      thisAttrVal = dbsPublicObject.getAttribute("CSV");
      logger.debug("ADDRESS: "+thisAttrVal.getString(dbsLibrarySession));
      timesheetEditForm.setTxaCSV(thisAttrVal.getString(dbsLibrarySession));
      timesheetEditForm.setFileOne(null);
      /* setting of "timesheet"-specific attrs for edit_timesheet.jsp ends... */
      
      request.setAttribute("timesheetEditForm",timesheetEditForm);
      httpSession.setAttribute("timesheetEditFormOld",timesheetEditForm);
      request.setAttribute("timesheetId",publicObjectId[0]);
      httpSession.setAttribute("timesheetIdToEdit",publicObjectId[0]);
    
    }else{
      logger.debug("dbsPublicObject classname: "+
                    dbsPublicObject.getClassname());              
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
  logger.info("B4Edit operation Complete");
  return mapping.findForward(forward);
}

}