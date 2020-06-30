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
 * $Id: NewDocUploadAction.java,v 1.28 2006/01/06 10:25:07 suved Exp $
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
import org.apache.commons.beanutils.PropertyUtils;
/**
 * Purpose  : Action code for uploading resume with appropriate checks.
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   27-06-2005
 * 	Last Modified by :  Suved Mishra   
 * 	Last Modified Date: 16-07-2005   
 */

public class NewDocUploadAction extends Action  {
    
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
    double fileSizeInMb = 0;
    FolderDocInfo folderDocInfo = null;
    UserPreferences userPreferences = null;
    try{
        httpSession = request.getSession(false);
        DocUploadForm docUploadForm = (DocUploadForm)form;
        logger.debug("NewDocUploadForm : " + docUploadForm);
        FormFile frmFile= null;
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
        
        folderDocInfo = (FolderDocInfo)
                                  httpSession.getAttribute("FolderDocInfo");            
        
        userPreferences = (UserPreferences)
                                httpSession.getAttribute("UserPreferences");
        
        DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
        
        logger.debug("1 :Entering NewDocUploadAction ...");
        /*set the path where the doc is to be uploaded*/
        docUploadForm.setTxtPath(folderDocInfo.getCurrentFolderPath());
        logger.debug("2 : "  + folderDocInfo.getCurrentFolderPath());
        String currentFolderPath = docUploadForm.getTxtPath();
        logger.debug("3");
        logger.info("Upload Location : " + currentFolderPath);
        logger.debug("currentFolderPath : " + currentFolderPath);
        /*obtain the destination folder's id*/
        Long currentFolderId = folderDocInfo.getCurrentFolderId();
        logger.debug("currentFolderId : " + currentFolderId);
        
        /*obtain FormFile ,fileSizeInMb and the resp description string */ 
        frmFile= docUploadForm.getFileOne();
        fileSizeInMb = frmFile.getFileSize();
        fileSizeInMb = fileSizeInMb/(1024*1024);
        logger.debug("fileSizeInMb b4 rounding off : "+fileSizeInMb);
        fileSizeInMb = (Math.round(fileSizeInMb*100)/100.00);
        logger.debug("fileSizeInMb : "+fileSizeInMb);
        /* fileSizeInMb <= remaining space, go ahead and upload file */
        if( fileSizeInMb <= (userInfo.getAllocatedQuota() - userInfo.getUsedQuota())){
          
        logger.debug("4 : " + docUploadForm.getTxtName());
        /* create new document definition */
        DbsDocumentDefinition newDbsDocumentDefinition= 
                                new DbsDocumentDefinition(dbsLibrarySession);
        
        
        logger.debug("5 : newDbsDocumentDefinition"+
                                        "instance created successfully...");
        /* obtain "RESUME1" classObject */                                
        DbsClassObject dbsClassObject = 
                          dbsLibrarySession.getClassObjectByName("RESUME1"); 
        
         
        logger.debug("6 : resume classobject obtained successfully");			
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
        if(docName!=null && docName.length()!=0){ // if content uploaded
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
        }else{  // if no content uploaded
          newDbsDocumentDefinition.setContent("No Document Uploaded "
                                              +"For This Resume.");
          ext = "txt";
        }
        
        /* set document's NAME attribute */  
        newDbsDocumentDefinition.setAttribute(DbsDocument.NAME_ATTRIBUTE,	
                                        DbsAttributeValue.newAttributeValue(
                                        docUploadForm.getTxtName()+"."+ext));
        /* set document's NAME1 attribute */                                
        newDbsDocumentDefinition.setAttribute("NAME1",	
                                        DbsAttributeValue.newAttributeValue(
                                        docUploadForm.getTxtName()));
                                        
        logger.debug("9 : document name and resume holder's name set successfully...");
        /* set document's DESCRIPTION attribute */
        newDbsDocumentDefinition.setAttribute("DESCRIPTION", 
                                        DbsAttributeValue.newAttributeValue(
                                        "R" + docUploadForm.getTxtFileDesc()));
                                        
        logger.debug("10 : description set successfully... ");
        
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
                                         DbsAttributeValue.newAttributeValue(
                                         compACL));
        }
        logger.debug("10A : ACL set successfully... ");
        
        /* set document's EMAIL attribute */
        newDbsDocumentDefinition.setAttribute("EMAIL",
                                        DbsAttributeValue.newAttributeValue(
                                        docUploadForm.getTxtEmail()));
                                        
        logger.debug("11 : email-id set successfully...");
        /* set document's ADDRESS attribute */
        newDbsDocumentDefinition.setAttribute("ADDRESS", 
                                        DbsAttributeValue.newAttributeValue(
                                        docUploadForm.getTxaAddress().trim()));

        logger.debug("12 : address set successfully...");
        /* set document's PHONE1 attribute */
        newDbsDocumentDefinition.setAttribute("PHONE1", 
                                DbsAttributeValue.newAttributeValue(
                                (docUploadForm.getTxtPhone1()!=null &&
                                 docUploadForm.getTxtPhone1().trim().length()!=0)?
                                 docUploadForm.getTxtPhone1():""));
                                        
        logger.debug("13 : phone no. set successfully...");
        /* set document's PHONE2 attribute */
        newDbsDocumentDefinition.setAttribute("PHONE2",                                    
                                DbsAttributeValue.newAttributeValue(
                                (docUploadForm.getTxtPhone2()!=null &&
                                 docUploadForm.getTxtPhone2().trim().length()!=0)?
                                 docUploadForm.getTxtPhone2():""));

        logger.debug("13A : optional phone no. set successfully...");
        /* set COMMUNICATION_SKILL attribute */
        String commSkill = ((docUploadForm.getTxtCommunicationSkill() != null) 
                            && (docUploadForm.getTxtCommunicationSkill().length()!=0))
                            ? docUploadForm.getTxtCommunicationSkill():"MEDIUM";
                            
        newDbsDocumentDefinition.setAttribute("COMMUNICATION_SKILL",
                                DbsAttributeValue.newAttributeValue(commSkill));

       logger.debug("14: commSkill set successfully... "+commSkill);            
       /* set document's AVAIL_DATE attribute */ 
        newDbsDocumentDefinition.setAttribute("AVAIL_DATE",                                    
                                DbsAttributeValue.newAttributeValue(
                                (docUploadForm.getTxtAvailDate()!=null &&
                                 docUploadForm.getTxtAvailDate().trim().length()!=0)?
                                 docUploadForm.getTxtAvailDate():""));

        logger.debug("15:Avail Date set successfully... ");            
        /* set document's BILLING_RATE attribute */
        newDbsDocumentDefinition.setAttribute("BILLING_RATE",                                    
                                DbsAttributeValue.newAttributeValue(
                                (docUploadForm.getTxtBillingRate()!=null &&
                                 docUploadForm.getTxtBillingRate().trim().length()!=0)?
                                 docUploadForm.getTxtBillingRate():""));

        logger.debug("16: Billing Rate set successfully...");            
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
        logger.debug("17 : document format set successfully...");
        newDbsDocumentDefinition.setFormat(format);
        /* set document's CUSTOM1_VALUE attribute */
        newDbsDocumentDefinition.setAttribute("CUSTOM1_VALUE",
                                DbsAttributeValue.newAttributeValue(
                                (docUploadForm.getTxtCustom1Desc()!=null &&
                                 docUploadForm.getTxtCustom1Desc().trim().length()!=0)?
                                 docUploadForm.getTxtCustom1Desc():""));
        /* set document's CUSTOM2_VALUE attribute */                         
        newDbsDocumentDefinition.setAttribute("CUSTOM2_VALUE",
                                DbsAttributeValue.newAttributeValue(
                                (docUploadForm.getTxtCustom2Desc()!=null &&
                                 docUploadForm.getTxtCustom2Desc().trim().length()!=0)?
                                 docUploadForm.getTxtCustom2Desc():""));
        /* set document's CUSTOM3_VALUE attribute */
        newDbsDocumentDefinition.setAttribute("CUSTOM3_VALUE",
                                DbsAttributeValue.newAttributeValue(
                                (docUploadForm.getTxtCustom3Desc()!=null &&
                                 docUploadForm.getTxtCustom3Desc().trim().length()!=0)?
                                 docUploadForm.getTxtCustom3Desc():""));
        /* set document's CUSTOM4_VALUE attribute */
        newDbsDocumentDefinition.setAttribute("CUSTOM4_VALUE",
                                DbsAttributeValue.newAttributeValue(
                                (docUploadForm.getTxtCustom4Desc()!=null &&
                                 docUploadForm.getTxtCustom4Desc().trim().length()!=0)?
                                 docUploadForm.getTxtCustom4Desc():""));
        
        logger.debug("custom1Value: "+docUploadForm.getTxtCustom1Desc()+"\n"+
                     "custom2Value: "+docUploadForm.getTxtCustom2Desc()+"\n"+
                     "custom3Value: "+docUploadForm.getTxtCustom3Desc()+"\n"+
                     "custom4Value: "+docUploadForm.getTxtCustom4Desc());
                     
        logger.debug(" Custom values set successfully...");
        /* All attrs set. Now create document from it's definition. */
        DbsDocument dbsDocument = (DbsDocument)
                                      dbsLibrarySession.createPublicObject(
                                      newDbsDocumentDefinition);
                                        
        logger.debug("dbsDocument className now is : " +
                      dbsDocument.getClassname()+" and " +
                      dbsDocument.CLASS_NAME);

      /* change the resume's document name , append the documentId b4 the 
       * extension so as to make it unique */
        String modifiedResumeName = dbsDocument.getAttribute("NAME1").getString(
                                    dbsLibrarySession)+dbsDocument.getId()+"."+
                                    ext;
        
        logger.debug("modifiedResumeName : "+modifiedResumeName);
        dbsDocument.setName(modifiedResumeName);
        
        DbsFileSystem dbsFileSystem = null;
        DbsTransaction dbsUploadTransaction = dbsLibrarySession.beginTransaction();
        logger.debug("dbsUploadTransaction begins...");
        dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
        DbsFolder resumeRoot = (DbsFolder)dbsFileSystem.findPublicObjectByPath(
                                          "/"+folderDocInfo.getRtsBase()+"/"+
                                          userInfo.getResumeFolderName());
                                          
        logger.debug("18 : finding target folder now...");
        DbsFolder targetFolder = (DbsFolder)
                                      dbsFileSystem.findPublicObjectByPath(
                                      folderDocInfo.getCurrentFolderPath().trim());

        logger.debug("19 : target folder found...");
        try{
          targetFolder.addItem(dbsDocument);
          logger.debug("20 : document uploaded to target folder successfully...");
          if( docUploadForm.getLstAttachment()!=null  ){
            //String [] toBeSharedWith = docEditForm.getLstAttachment();
            String [] toBeSharedWith = (String[])
                                        PropertyUtils.getSimpleProperty(
                                        form,"lstAttachment");
                                        
            logger.debug("toBeSharedWith : "+toBeSharedWith.length);
            FolderDoc folderDoc = new FolderDoc(dbsLibrarySession);
            Long [] ids = new Long[1];
            ids[0] = dbsDocument.getId();
            for( int index = 0; index < toBeSharedWith.length; index++ ){
              DbsFolder targetFolderForLink = null;
              //logger.debug("toBeSharedWith["+index+"] : "+toBeSharedWith[index]);
              
              if( toBeSharedWith[index]!=null && 
                  toBeSharedWith[index].trim().length()!=0 ){
                
                targetFolderForLink = (DbsFolder) 
                                      dbsFileSystem.findPublicObjectByPath(
                                      toBeSharedWith[index]);
                
                if( (targetFolderForLink.getId()!= targetFolder.getId()) &&
                    (targetFolderForLink.getId()!= resumeRoot.getId()) ){
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
    logger.info("Exiting NewDocUploadAction ...");
    forward= (userPreferences.getNavigationType() == 
              UserPreferences.FLAT_NAVIGATION)?"resume":
                                               "resume4Tree";
    return mapping.findForward(forward);
}
}