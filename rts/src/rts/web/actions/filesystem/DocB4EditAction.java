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
 * $Id: NewDocUploadAction.java,v 1.6 2005/06/24 05:58:46 suved Exp $
 *****************************************************************************
 */
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
 * Purpose  : To populate DocEditForm with selected doc's attributes.
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   30-06-2005
 * 	Last Modified by :  Suved Mishra   
 * 	Last Modified Date: 02-07-2005   
 */
public class DocB4EditAction extends Action  {

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
  DbsFamily dbsFam = null;
  DbsFolder[] folderReferences = null; 
  String[] folderReferencesPath = null;
  DbsPublicObject dbsPOToEdit = null;
  DbsRelationship [] dbsRels = null;
  
  try{

    httpSession = request.getSession(false);
    
    FolderDocListForm folderDocListForm = (FolderDocListForm)form;
    Long[] publicObjectId = null;
    DocEditForm docEditForm = new DocEditForm();
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
    FolderDocInfo folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");            
    //UserPreferences userPreferences = (UserPreferences)httpSession.getAttribute("UserPreferences");
    
    DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
    docEditForm.setTxtPath(folderDocInfo.getCurrentFolderPath());
    logger.debug("2 : "  + folderDocInfo.getCurrentFolderPath());
    String currentFolderPath = docEditForm.getTxtPath();
    logger.debug("3");
    logger.info("Upload Location : " + currentFolderPath);
    
    DbsPublicObject dbsPublicObject = dbsLibrarySession.getPublicObject(
                                                        publicObjectId[0]);
    dbsPOToEdit = dbsPublicObject;
    String dbsPOClassName = dbsPublicObject.getClassname(); 
    
    if(dbsPublicObject!=null && 
      ( dbsPOClassName.equalsIgnoreCase("Resume1") || 
        dbsPOClassName.equalsIgnoreCase("Family") ) ){
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
            logger.debug("is RPO versioned:  " +dbsPublicObject.isVersioned());
          }else{
            logger.debug("dbsFam is null...");
          }
      }
    
    /* Next,find out the original folder for the shared resume. 
     * Incase the resume isn't shared , we will get its parent */
     
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
    
    
    /* set the "share with" listbox for edit_resume.jsp */
    if( folderReferencesPath!=null ){
      docEditForm.setLstAttachment(folderReferencesPath);
    }else{
      docEditForm.setLstAttachment(folderReferencesPath);
    }
     
    docEditForm.setHdnTargetFolderPath(
                folderDocInfo.getCurrentFolderPath());
    /* setting of "resume"-specific attrs for edit_resume.jsp begins... */
    /* set NAME1 */
    DbsAttributeValue thisAttrVal = dbsPublicObject.getAttribute("NAME1");
    logger.debug("NAME1: "+thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtName(thisAttrVal.getString(dbsLibrarySession));
    
    /* set DESCRIPTION */
    thisAttrVal = dbsPublicObject.getAttribute("DESCRIPTION");
    String desc = thisAttrVal.getString(dbsLibrarySession);
    logger.debug("Description: "+desc);
    if(desc.equals("R")){
      docEditForm.setTxtFileDesc("");
    }else{
      docEditForm.setTxtFileDesc(desc.substring(1));
    }
    
    /* set EMAIL */
    thisAttrVal = dbsPublicObject.getAttribute("EMAIL");
    logger.debug("EMAIL: "+thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtEmail(thisAttrVal.getString(dbsLibrarySession));
    
    /* set ADDRESS */
    thisAttrVal = dbsPublicObject.getAttribute("ADDRESS");
    logger.debug("ADDRESS: "+thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxaAddress(thisAttrVal.getString(dbsLibrarySession));
    
    /* set PHONE1 */
    thisAttrVal = dbsPublicObject.getAttribute("PHONE1");
    logger.debug("PHONE1: "+thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtPhone1(thisAttrVal.getString(dbsLibrarySession));

    /* set PHONE2 */
    thisAttrVal = dbsPublicObject.getAttribute("PHONE2");
    logger.debug("PHONE2: "+thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtPhone2(thisAttrVal.getString(dbsLibrarySession));

    /* set COMMUNICATION_SKILL */
    thisAttrVal = dbsPublicObject.getAttribute("COMMUNICATION_SKILL");
    logger.debug("COMMUNICATION_SKILL: "
                +thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtCommunicationSkill(thisAttrVal.getString(
                                                    dbsLibrarySession));
      
    /* set AVAIL_DATE */
    thisAttrVal = dbsPublicObject.getAttribute("AVAIL_DATE");
    logger.debug("AVAIL_DATE: "
                +thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtAvailDate(thisAttrVal.getString(dbsLibrarySession));

    /* set BILLING_RATE */
    thisAttrVal = dbsPublicObject.getAttribute("BILLING_RATE");
    logger.debug("BILLING_RATE: "
                +thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtBillingRate(thisAttrVal.getString(dbsLibrarySession));

    /* set CUSTOM1_VALUE */
    thisAttrVal = dbsPublicObject.getAttribute("CUSTOM1_VALUE");
    logger.debug("CUSTOM1_VALUE : "+thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtCustom1Desc(thisAttrVal.getString(dbsLibrarySession));

    /* set CUSTOM2_VALUE */
    thisAttrVal = dbsPublicObject.getAttribute("CUSTOM2_VALUE");
    logger.debug("CUSTOM2_VALUE : "+thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtCustom2Desc(thisAttrVal.getString(dbsLibrarySession));

    /* set CUSTOM3_VALUE */
    thisAttrVal = dbsPublicObject.getAttribute("CUSTOM3_VALUE");
    logger.debug("CUSTOM3_VALUE : "+thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtCustom3Desc(thisAttrVal.getString(dbsLibrarySession));

    /* set CUSTOM4_VALUE */
    thisAttrVal = dbsPublicObject.getAttribute("CUSTOM4_VALUE");
    logger.debug("CUSTOM4_VALUE : "+thisAttrVal.getString(dbsLibrarySession));
    docEditForm.setTxtCustom4Desc(thisAttrVal.getString(dbsLibrarySession));
      
    /* obtain list of groups associated with current user */
    DbsDirectoryGroup[] listOfGroups = 
                                dbsLibrarySession.getUser().getAllAncestors();

    if( listOfGroups !=null ){
    /* obtain the values of custom labels set for the group currently in use */
    try{
    
      dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM1_LBL");
      logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
      
      docEditForm.setTxtCustom1Lbl(
                          (dbsAttrVal != null && 
                          dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                          dbsAttrVal.getString(dbsLibrarySession):"Custom1_Label");
      

      dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM2_LBL");
      logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
      
      docEditForm.setTxtCustom2Lbl(
                          (dbsAttrVal != null && 
                          dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                          dbsAttrVal.getString(dbsLibrarySession):"Custom2_Label");
                          
      dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM3_LBL");
      logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
      
      docEditForm.setTxtCustom3Lbl(
                          (dbsAttrVal != null && 
                          dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                          dbsAttrVal.getString(dbsLibrarySession):"Custom3_Label");
                          

      dbsAttrVal = listOfGroups[0].getAttribute("CUSTOM4_LBL");
      logger.debug("dbsAttrVal is null: "+((dbsAttrVal == null)?true:false));
      
      docEditForm.setTxtCustom4Lbl(
                          (dbsAttrVal != null && 
                          dbsAttrVal.getString(dbsLibrarySession).trim().length()!=0)?
                          dbsAttrVal.getString(dbsLibrarySession):"Custom4_Label");
    
    }catch( DbsException dbsEx ){
      logger.error("Error : "+dbsEx.getErrorMessage());
      docEditForm.setTxtCustom1Lbl("Custom1_Label");
      docEditForm.setTxtCustom2Lbl("Custom2_Label");
      docEditForm.setTxtCustom3Lbl("Custom3_Label");
      docEditForm.setTxtCustom4Lbl("Custom4_Label");
    }
          

    }else{
      docEditForm.setTxtCustom1Lbl("Custom1_Label");
      docEditForm.setTxtCustom2Lbl("Custom2_Label");
      docEditForm.setTxtCustom3Lbl("Custom3_Label");
      docEditForm.setTxtCustom4Lbl("Custom4_Label");
    }              
    
    //docEditForm.setTxtAvailDate("");
    //docEditForm.setTxtBillingRate("");
    docEditForm.setFileOne(null);
    /* setting of "resume"-specific attrs for edit_resume.jsp ends... */
    
    request.setAttribute("docEditForm",docEditForm);
    httpSession.setAttribute("docEditFormOld",docEditForm);
    request.setAttribute("resumeId",publicObjectId[0]);
    request.setAttribute("communicationSkill",
                          docEditForm.getTxtCommunicationSkill());
    httpSession.setAttribute("resumeIdToEdit",publicObjectId[0]);
    
    }else{
      logger.debug("dbsPublicObject classname: "+dbsPublicObject.getClassname());              
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