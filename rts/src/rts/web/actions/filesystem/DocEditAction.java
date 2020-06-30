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
import rts.beans.DbsTransaction;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
import rts.web.beans.utility.SearchUtil;
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
 * Purpose  : Action code for editing resume selected.
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   27-06-2005
 * 	Last Modified by :  Suved Mishra   
 * 	Last Modified Date: 16-07-2005   
 */
 
public class DocEditAction extends Action  {
    
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
  DocEditForm docEditFormOld = null;
  Long poToEditId = null;
  FolderDocInfo folderDocInfo = null;
  UserPreferences userPreferences = null;
  DbsLibrarySession dbsLibrarySession = null;
  DbsFileSystem dbsFileSystem = null;
  DbsFolder resumeRoot = null;
  
  try{
      httpSession = request.getSession(false);
      docEditFormOld = (DocEditForm)
                                httpSession.getAttribute("docEditFormOld");
                                
      DocEditForm docEditForm = (DocEditForm)form;
      poToEditId = (Long)httpSession.getAttribute("resumeIdToEdit");
      logger.debug("OldEditForm : " +docEditFormOld);
      logger.debug("NewEditForm : " +docEditForm);
      logger.debug("docEditForm :"+docEditForm);
      UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
      folderDocInfo = (FolderDocInfo)
                                  httpSession.getAttribute("FolderDocInfo");            
      userPreferences = (UserPreferences)
                                  httpSession.getAttribute("UserPreferences");
                                  
      dbsLibrarySession = userInfo.getDbsLibrarySession();
      logger.debug("1");
      
      FormFile frmFile= null;
      try{
        frmFile= docEditForm.getFileOne();
      }catch(Exception ex ){
        logger.debug("Error: "+ex.getMessage());
        ex.printStackTrace();
      }
      DbsPublicObject dbsPOToEdit = dbsLibrarySession.getPublicObject(
                                                                poToEditId);
      DbsDocument dbsDocToEdit = null;
      if( dbsPOToEdit.getClassname().equalsIgnoreCase("Resume1") ){
        dbsDocToEdit = (DbsDocument)dbsPOToEdit;
        logger.debug("dbsDocToEdit classname: "+dbsDocToEdit.getClassname());
      }else if(dbsPOToEdit.getClassname().equalsIgnoreCase(DbsFamily.CLASS_NAME)){
        /* if dbsPOToEdit is versioned,obtain most recent version */        
        dbsFam = (DbsFamily)dbsPOToEdit;
        logger.debug("Family ACL : "+dbsFam.getAcl().getName());
        rtsVersion = new RtsVersion(dbsLibrarySession);
        if(dbsFam!=null){
          logger.debug(" dbsFam is not null...");
          DbsVersionSeries dbsVs = rtsVersion.getPrimaryVersionSeries(dbsFam);
          DbsVersionDescription dbsLastVd = dbsVs.getLastVersionDescription();
          dbsPOToEdit = dbsLastVd.getDbsPublicObject();
          dbsDocToEdit = (DbsDocument)dbsPOToEdit;
          logger.debug("dbsDocToEdit classname: "+dbsDocToEdit.getClassname());
          logger.debug("Last Version ACL : "+dbsDocToEdit.getAcl().getName());
        }else{
          logger.debug("dbsFam is null...");
        }
        
      }
      /* create new document definition */
      DbsDocumentDefinition dbsDocDef = new DbsDocumentDefinition(
                                                        dbsLibrarySession);
      /* obtain "RESUME1" classObject */
      DbsClassObject dbsClassObject =
                        dbsLibrarySession.getClassObjectByName("RESUME1");
                        
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
        if( fileSizeInMb <= (userInfo.getAllocatedQuota() - userInfo.getUsedQuota())){
          iStream = frmFile.getInputStream();  
        }else{  // retain existing content
          isToUpload = false;
          iStream = dbsDocToEdit.getContentStream();
        }
      }else{    // retain existing content
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
        
        logger.debug("25 : document format set successfully...");
        
        dbsDocDef.setFormat(format);

        }
        /* set document's NAME attribute ,if name has been changed,
         * else retain existing NAME */
        String docName = dbsPOToEdit.getName();
        String fName = dbsPOToEdit.getName().substring(0,docName.lastIndexOf("."))+
                       "."+ext;

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
        logger.debug("4 name1: " +name1);
        String name1Old = docEditFormOld.getTxtName();
        logger.debug("5 name1Old: "+name1Old);
        if( name1!=null && name1.length()!=0 && !name1.equals(name1Old)){
          logger.debug("6");
          dbsDocDef.setAttribute("NAME1",
                                  DbsAttributeValue.newAttributeValue(name1));
          dbsDocDef.setAttribute(DbsDocument.NAME_ATTRIBUTE,
                                 DbsAttributeValue.newAttributeValue(
                                 name1+"."+ext));
        }else{
          logger.debug("7");
          dbsDocDef.setAttribute("NAME1",
                                  DbsAttributeValue.newAttributeValue(name1Old));
        }
        
        /* set ADDRESS attribute ,if address has been changed,
         * else retain existing ADDRESS */       
        String address = ((String)PropertyUtils.getSimpleProperty(
                                                      form,"txaAddress")).trim();
        logger.debug("8 address: " +address);
        String addressOld = docEditFormOld.getTxaAddress();
        logger.debug("9 addressOld: "+addressOld);
        if( address!=null && address.length()!=0 && !address.equals(addressOld)){
          logger.debug("10");
          dbsDocDef.setAttribute("ADDRESS",
                                  DbsAttributeValue.newAttributeValue(address));
        }else{
          logger.debug("11");
          dbsDocDef.setAttribute("ADDRESS",
                                  DbsAttributeValue.newAttributeValue(addressOld));
        }
        
        /* set document's PHONE1 attribute ,if phone1 has been changed,
         * else retain existing PHONE1 */       
        String phone = ((String)PropertyUtils.getSimpleProperty(
                                                      form,"txtPhone1")).trim();
        logger.debug("12 phone1: "+phone);
        String phoneOld = docEditFormOld.getTxtPhone1();
        logger.debug("13 phone1Old: "+phoneOld);            
        if( phone!=null && phone.length()!=0 && !phone.equals(phoneOld)){
          logger.debug("14");
          dbsDocDef.setAttribute("PHONE1",
                                  DbsAttributeValue.newAttributeValue(phone));
        }else{
          logger.debug("15");
          dbsDocDef.setAttribute("PHONE1",
                                  DbsAttributeValue.newAttributeValue(phoneOld));
        }
        
        /* set document's PHONE2 attribute ,if phone2 has been changed,
         * else retain existing PHONE2 */
        phone = ((String)PropertyUtils.getSimpleProperty(
                                                      form,"txtPhone2")).trim();
        logger.debug("12A phone2: "+phone);
        phoneOld = docEditFormOld.getTxtPhone2();
        logger.debug("13A phone2Old: "+phoneOld);            
        if( phone!=null && phone.length()!=0 && !phone.equals(phoneOld)){
          logger.debug("14A");
          dbsDocDef.setAttribute("PHONE2",
                                  DbsAttributeValue.newAttributeValue(phone));
        }else{
          logger.debug("15A");
          dbsDocDef.setAttribute("PHONE2",
                                  DbsAttributeValue.newAttributeValue(phoneOld));
        }

        /* set document's AVAIL_DATE attribute ,if it has been changed,
         * else retain existing AVAIL_DATE */
        String availDate = ((String)PropertyUtils.getSimpleProperty(
                                                    form,"txtAvailDate")).trim();
        logger.debug("12B txtAvailDate: "+availDate);
        String availDateOld = docEditFormOld.getTxtAvailDate();
        logger.debug("13B availDateOld: "+availDateOld);            
        if( availDate!=null && availDate.length()!=0 &&  
            !availDate.equals(availDateOld)){
            
          logger.debug("14B");
          dbsDocDef.setAttribute("AVAIL_DATE",
                                  DbsAttributeValue.newAttributeValue(availDate));
        }else{
          logger.debug("15B");
          dbsDocDef.setAttribute("AVAIL_DATE",
                                  DbsAttributeValue.newAttributeValue(availDateOld));
        }

        /* set document's BILLING_RATE attribute ,if it has been changed,
         * else retain existing BILLING_RATE */
        String billingRate = ((String)PropertyUtils.getSimpleProperty(
                                                  form,"txtBillingRate")).trim();
        logger.debug("12C txtBillingRate: "+billingRate);
        String billingRateOld = docEditFormOld.getTxtBillingRate();
        logger.debug("13C billingRateOld: "+billingRateOld);            
        if( billingRate!=null && billingRate.length()!=0 && 
            !billingRate.equals(billingRateOld)){
          
          logger.debug("14C");
          dbsDocDef.setAttribute("BILLING_RATE",
                                  DbsAttributeValue.newAttributeValue(billingRate));
        }else{
          logger.debug("15C");
          dbsDocDef.setAttribute("BILLING_RATE",
                                  DbsAttributeValue.newAttributeValue(billingRateOld));
        }

        /* set document's EMAIL attribute ,if it has been changed,
         * else retain existing EMAIL */        
        String email = ((String)PropertyUtils.getSimpleProperty(
                                                        form,"txtEmail")).trim();
        logger.debug("16 email: "+email);
        String emailOld = docEditFormOld.getTxtEmail();
        logger.debug("17 emailOld: "+emailOld);            
        if( email!=null && email.length()!=0 && !email.equals(emailOld)){
          logger.debug("18");
          dbsDocDef.setAttribute("EMAIL",
                                  DbsAttributeValue.newAttributeValue(email));
        }else{
          logger.debug("19");
          dbsDocDef.setAttribute("EMAIL",
                                  DbsAttributeValue.newAttributeValue(emailOld));
        }
        
        /* set document's DESCRIPTION attribute ,if it has been changed,
         * else retain existing DESCRIPTION.Note that DESCRIPTION for 
         * resumes must always commence with "R".This way resumes could be
         * distinguished from timesheets and personal files. */        
        String description = ((String)PropertyUtils.getSimpleProperty(
                                                    form,"txtFileDesc")).trim();
        logger.debug("20 description: "+description);
        String descriptionOld = docEditFormOld.getTxtFileDesc();
        logger.debug("21 descriptionOld: "+descriptionOld);
        if( description!=null && description.length()!=0 && 
            !description.equals(descriptionOld)){
          
          logger.debug("22");
          dbsDocDef.setAttribute("DESCRIPTION",
                                  DbsAttributeValue.newAttributeValue(
                                  "R"+description));
        }else{
          logger.debug("23");
          dbsDocDef.setAttribute("DESCRIPTION",
                                  DbsAttributeValue.newAttributeValue(
                                  "R"+descriptionOld));
        }
        logger.debug("24");

        /* set document's COMMUNICATION_SKILL attribute ,if it has been changed,
         * else retain existing COMMUNICATION_SKILL */
        String commSkills = ((String)PropertyUtils.getSimpleProperty(
                                          form,"txtCommunicationSkill")).trim();
        logger.debug("25 commSkills: "+commSkills);
        String commSkillsOld = docEditFormOld.getTxtCommunicationSkill();
        logger.debug("26 commSkillsOld: "+commSkillsOld);
        if( commSkills!=null && commSkills.length()!=0 && 
            !commSkills.equals(commSkillsOld)){
          
          logger.debug("27");
          dbsDocDef.setAttribute("COMMUNICATION_SKILL",
                                  DbsAttributeValue.newAttributeValue(commSkills));
        }else{
          logger.debug("28");
          dbsDocDef.setAttribute("COMMUNICATION_SKILL",
                                  DbsAttributeValue.newAttributeValue(commSkillsOld));
        }
        logger.debug("29");

        /* set document's CUSTOM1_VALUE attribute ,if it has been changed,
         * else retain existing CUSTOM1_VALUE */
        String customValue = ((String)PropertyUtils.getSimpleProperty(
                                                  form,"txtCustom1Desc")).trim();
        logger.debug("custom1Value: "+customValue);
        String customValueOld = docEditFormOld.getTxtCustom1Desc();
        logger.debug("custom1ValueOld: "+customValueOld);
        if( customValue!=null && customValue.length()!=0 && 
            !customValue.equals(customValueOld)){
          
          logger.debug("30");
          dbsDocDef.setAttribute("CUSTOM1_VALUE",
                                  DbsAttributeValue.newAttributeValue(customValue));
        }else{
          logger.debug("31");
          dbsDocDef.setAttribute("CUSTOM1_VALUE",
                                  DbsAttributeValue.newAttributeValue(customValueOld));
        }
        logger.debug("32");

        /* set document's CUSTOM2_VALUE attribute ,if it has been changed,
         * else retain existing CUSTOM2_VALUE */
        customValue = ((String)PropertyUtils.getSimpleProperty(
                                                  form,"txtCustom2Desc")).trim();
        logger.debug("custom2Value: "+customValue);
        customValueOld = docEditFormOld.getTxtCustom2Desc();
        logger.debug("custom2ValueOld: "+customValueOld);
        if( customValue!=null && customValue.length()!=0 && 
            !customValue.equals(customValueOld)){
            
          logger.debug("33");
          dbsDocDef.setAttribute("CUSTOM2_VALUE",
                                  DbsAttributeValue.newAttributeValue(customValue));
        }else{
          logger.debug("34");
          dbsDocDef.setAttribute("CUSTOM2_VALUE",
                                  DbsAttributeValue.newAttributeValue(customValueOld));
        }
        logger.debug("35");            

        /* set document's CUSTOM3_VALUE attribute ,if it has been changed,
         * else retain existing CUSTOM3_VALUE */        
        customValue = ((String)PropertyUtils.getSimpleProperty(
                                                  form,"txtCustom3Desc")).trim();
        logger.debug("custom3Value: "+customValue);
        customValueOld = docEditFormOld.getTxtCustom3Desc();
        logger.debug("custom3ValueOld: "+customValueOld);
        if( customValue!=null && customValue.length()!=0 && 
            !customValue.equals(customValueOld)){
          logger.debug("36");
          dbsDocDef.setAttribute("CUSTOM3_VALUE",
                                  DbsAttributeValue.newAttributeValue(customValue));
        }else{
          logger.debug("37");
          dbsDocDef.setAttribute("CUSTOM3_VALUE",
                                  DbsAttributeValue.newAttributeValue(customValueOld));
        }
        logger.debug("38");            
        
        /* set document's CUSTOM4_VALUE attribute ,if it has been changed,
         * else retain existing CUSTOM4_VALUE */        
        customValue = ((String)PropertyUtils.getSimpleProperty(
                                                  form,"txtCustom4Desc")).trim();
        logger.debug("custom4Value: "+customValue);
        customValueOld = docEditFormOld.getTxtCustom4Desc();
        logger.debug("custom4ValueOld: "+customValueOld);
        if( customValue!=null && customValue.length()!=0 && 
            !customValue.equals(customValueOld)){
          logger.debug("39");
          dbsDocDef.setAttribute("CUSTOM4_VALUE",
                                  DbsAttributeValue.newAttributeValue(customValue));
        }else{
          logger.debug("40");
          dbsDocDef.setAttribute("CUSTOM4_VALUE",
                                  DbsAttributeValue.newAttributeValue(customValueOld));
        }
        logger.debug("41");            
        /* set ACL for the new doc defn */
        DbsDirectoryGroup [] groups=dbsLibrarySession.getUser().getAllAncestors();
        DbsDirectoryGroup companyGroup = null;
        DbsAccessControlList compACL = null;
        if( groups!=null ){
          companyGroup = groups[0];
          logger.debug("Company Group: "+companyGroup.getName());
          compACL = companyGroup.getAcl();
          logger.debug("compACL : "+compACL.getName());
          // set ACL to companyACL
          dbsDocDef.setAttribute(DbsDocument.ACL_ATTRIBUTE,
                                 DbsAttributeValue.newAttributeValue(compACL));
        }

        /* All attrs set. Now create document from it's definition. */
        DbsDocument dbsEditedDoc = (DbsDocument)
                                   dbsLibrarySession.createPublicObject(dbsDocDef);
        logger.debug("42 dbsEditedDoc : "+dbsEditedDoc.getName());

      /* change the resume's document name , append the documentId b4 the 
       * extension so as to make it unique */
        String modifiedResumeName = dbsEditedDoc.getAttribute("NAME1").getString(
                                    dbsLibrarySession)+dbsEditedDoc.getId()+"."+
                                    ext;
        
        logger.debug("modifiedResumeName : "+modifiedResumeName);
        dbsEditedDoc.setName(modifiedResumeName);

        /* Obtain the original Parent for the resume being edited */
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

              
            linkToBeRemovedFrom = docEditFormOld.getLstAttachment();
            folderDoc = new FolderDoc(dbsLibrarySession);
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            
            /* if there is a list of folders 4 share */
            if( docEditForm.getLstAttachment()!=null  ){
  
              
              resumeRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                    "/"+folderDocInfo.getRtsBase()+"/"+
                                    userInfo.getResumeFolderName());
              
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
  
                  if(targetFolderForLink.getId()!= resumeRoot.getId() &&
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
          resumeRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                      "/"+folderDocInfo.getRtsBase()+"/"+
                                      userInfo.getResumeFolderName());
          
          logger.debug("43 : finding target folder now...");
          DbsFolder targetFolder = originalFolder;
                                      
          logger.debug("44 : target folder found..."); 
          dbsFileSystem.delete(dbsDocToEdit);
          logger.debug("45 : doc deleted...");            
          try{
            targetFolder.addItem(dbsEditedDoc);
            logger.debug("46 : document edited successfully...");
            if( docEditForm.getLstAttachment()!=null  ){
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
                      (targetFolderForLink.getId()!= resumeRoot.getId()) ){
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
  httpSession.removeAttribute("docEditFormOld");
  httpSession.removeAttribute("resumeIdToEdit");
  logger.info("Uploading File Complete");

  forward= (userPreferences.getNavigationType() == 
            UserPreferences.FLAT_NAVIGATION)?"resume":
                                             "resume4Tree";
  
  return mapping.findForward(forward);
}
}