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
 * $Id: Version.java,v 1.11 2005/10/28 04:55:17 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;
/*rts package references */
import rts.beans.*;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;

//Java API
import java.io.*;
import java.util.*;
//import oracle.ifs.adk.filesystem.*;
//import oracle.ifs.beans.*;
//import oracle.ifs.common.*;
//import oracle.ifs.examples.api.utils.*;

//Struts API
import org.apache.log4j.*;
import org.apache.struts.upload.*;

public class Version  {
    public static final String STATUS_CHECKEDOUT = "Checked Out";
    public static final String STATUS_CHECKEDIN = "Checked In";
    public static final String STATUS_CREATED = "Created";

    private DbsLibrarySession dbsLibrarySession = null;     //DbsLibrarySession Object
    private Logger logger = null;
    private Locale locale = null;
    private String[] replacementValues;
    private DbsTransaction dbsTransaction = null;
    
    public Version(DbsLibrarySession dbsLibrarySession) {
        this.dbsLibrarySession = dbsLibrarySession;
        //Initialize logger
        logger = Logger.getLogger("DbsLogger");
        logger.debug("Hello...");
        //use default if locale not set
        locale = Locale.getDefault(); //
        replacementValues = new String[5];
    }


    /**
     * Purpose  : to make document versioned 
     * @param   : folderDocIds - list of folder and document
     * @returns : void
     * @throws  : DbsException - if operation fails
     */
    public void makeVersioned(Long[] folderDocIds) throws DbsException , IOException{
        DbsPublicObject dbsPublicObject = null;
        DbsFileSystem dbsFileSystem = null;
        String className;
        DbsPublicObject dbsVersionedPO = null;
        DbsVersionSeries primaryVersionSeries = null;
        DbsVersionDescription lastVd = null;
        try{
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            String name = null;
            for(int index = 0; index < folderDocIds.length ; index++){
                //dbsPublicObject = dbsLibrarySession.getPublicObject(folderDocIds[index]).getResolvedPublicObject();
                dbsPublicObject = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                name = dbsPublicObject.getName();
                
                className = dbsPublicObject.getClassname();
                if(className.equals(DbsFolder.CLASS_NAME)){
                    logger.info("DbsFolder Name : {" + dbsPublicObject.getName() + "}" );
                    makeVersionableRecursively((DbsFolder)dbsPublicObject,dbsFileSystem);
                }
                if((dbsPublicObject instanceof DbsDocument)){
                    //DbsAccessControlList aclForPO = ((DbsDocument)dbsPublicObject).getAcl();
                    //logger.debug("aclForPO : "+aclForPO);
                    logger.debug("dbsPublicObject id is: "+dbsPublicObject.getId());
                    logger.debug("dbsPublicObject description is: "+dbsPublicObject.getDescription());
                    dbsVersionedPO = dbsFileSystem.makeVersioned(dbsPublicObject);
                    logger.debug("dbsVersionedPO ClassName: " +dbsVersionedPO.getClassname());
                    //DbsFamily dbsFamily = (DbsFamily)dbsPublicObject;
                    //dbsPublicObject.setAcl(aclForPO);
                    DbsFamily dbsFam = null;
                    try{
                      if( dbsVersionedPO.isVersioned() ){
                        dbsFam = dbsVersionedPO.getFamily();
                        dbsFam.setDescription(
                              dbsFam.getResolvedPublicObject().getDescription());
                        primaryVersionSeries = dbsFam.getPrimaryVersionSeries();
                        if( primaryVersionSeries != null ){
                          primaryVersionSeries.setName(dbsFam.getName());
                          primaryVersionSeries.setDescription(
                                               dbsFam.getDescription());
                          lastVd = primaryVersionSeries.getLastVersionDescription();
                          if(lastVd != null){
                            lastVd.setName(dbsFam.getName());
                            lastVd.setDescription(dbsFam.getDescription());
                          }else{
                            logger.debug("Last VD could not be found");
                          }
                        }else{
                          logger.debug("Primary VS could not be found");  
                        }
                      }else{
                        logger.debug("Family could not be found");  
                      }
                    }catch( DbsException dbsEx ){
                      dbsEx.printStackTrace();
                    }catch( Exception ex ){
                      ex.printStackTrace();
                    }
                    logger.info("DbsDocument Name : {" + dbsVersionedPO.getName() + "}" );
                    logger.debug("dbsPublicObject is versioned: "+dbsVersionedPO.isVersioned());
                    logger.debug("dbsPublicObject classname is: "+dbsVersionedPO.getClassname());
                    logger.debug("dbsPublicObject description is: "+dbsVersionedPO.getDescription());
                }
                if(className.equals(DbsFamily.CLASS_NAME)){
                    //Do nothing if public object is a dbsFamily
                    //because it is already versioned
                }
            }
        }catch(DbsException dbsException){
            throw dbsException;
        }
    }

    //to make documents in a folder versioned recursively
    private void makeVersionableRecursively(DbsFolder top,DbsFileSystem dbsFileSystem) throws DbsException {
      //get all the items present in the given folder
      DbsPublicObject[] dbsPublicObjects = top.getItems();
      DbsPublicObject dbsPublicObject;
      DbsPublicObject dbsVersionedPO = null;
      DbsVersionSeries primaryVersionSeries = null;
      DbsVersionDescription lastVd = null;
      String className;
      int length = (dbsPublicObjects == null) ? 0 : dbsPublicObjects.length;
      for (int i = 0; i < length; i++){
        // if the item is a folder, call this same method recursively
        dbsPublicObject = dbsPublicObjects[i];
        className = dbsPublicObject.getClassname();
        if(className.equals(DbsFolder.CLASS_NAME)){
            logger.info("DbsFolder Name : {" + dbsPublicObject.getName() + "}" );
            makeVersionableRecursively((DbsFolder)dbsPublicObject,dbsFileSystem);
        }
        if(className.equals(DbsDocument.CLASS_NAME)){
          logger.debug("dbsPublicObject id is: "+dbsPublicObject.getId());
          logger.debug("dbsPublicObject description is: "+dbsPublicObject.getDescription());
          dbsVersionedPO = dbsFileSystem.makeVersioned(dbsPublicObject);
          logger.debug("dbsVersionedPO ClassName: " +dbsVersionedPO.getClassname());
          //DbsFamily dbsFamily = (DbsFamily)dbsPublicObject;
          //dbsPublicObject.setAcl(aclForPO);
          DbsFamily dbsFam = null;
          try{
            if( dbsVersionedPO.isVersioned() ){
              dbsFam = dbsVersionedPO.getFamily();
              dbsFam.setDescription(
                    dbsFam.getResolvedPublicObject().getDescription());
              primaryVersionSeries = dbsFam.getPrimaryVersionSeries();
              if( primaryVersionSeries != null ){
                primaryVersionSeries.setName(dbsFam.getName());
                primaryVersionSeries.setDescription(
                                     dbsFam.getDescription());
                lastVd = primaryVersionSeries.getLastVersionDescription();
                if(lastVd != null){
                  lastVd.setName(dbsFam.getName());
                  lastVd.setDescription(dbsFam.getDescription());
                }else{
                  logger.debug("Last VD could not be found");
                }
              }else{
                logger.debug("Primary VS could not be found");  
              }
            }else{
              logger.debug("Family could not be found");  
            }
          }catch( DbsException dbsEx ){
            dbsEx.printStackTrace();
          }catch( Exception ex ){
            ex.printStackTrace();
          }
          logger.info("DbsDocument Name : {" + dbsVersionedPO.getName() + "}" );
          logger.debug("dbsPublicObject is versioned: "+dbsVersionedPO.isVersioned());
          logger.debug("dbsPublicObject classname is: "+dbsVersionedPO.getClassname());
          logger.debug("dbsPublicObject description is: "+dbsVersionedPO.getDescription());
        }
        if(className.equals(DbsFamily.CLASS_NAME)){
            //Do nothing if public object is a dbsFamily
            //because it is already versioned
        }
      }
    }

    public int checkOut(Long[] folderDocIds, String comment) throws DbsException , IOException{
        DbsPublicObject dbsPublicObject = null;
        DbsFileSystem dbsFileSystem = null;
        String className;
        //0 = no document checkout because it is not versioned or its already checked out
        //1 = checkout successful
        int resultCode = 0;
        try{
            dbsTransaction = dbsLibrarySession.beginTransaction();        
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            for(int index = 0; index < folderDocIds.length ; index++){
                dbsPublicObject = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                className = dbsPublicObject.getClassname();
                logger.debug("Name of doc: "+dbsPublicObject.getName());
                logger.debug("className: "+className);
                logger.debug("Description of doc: "+dbsPublicObject.getDescription());
                if(className.equals(DbsFolder.CLASS_NAME)){
                    logger.info("DbsFolder Name : {" + dbsPublicObject.getName() + "}");
                    int resultCodeTemp = checkOutRecursively((DbsFolder)dbsPublicObject,dbsFileSystem,comment);
                    switch (resultCodeTemp){
                    case 0 :
                        //do not modify resultcode
                        break;
                    case 1 :
                        resultCode = 1;
                        break;
                    }
                }
                if(className.equals(DbsDocument.CLASS_NAME)){
                    //Do nothing if public object is not a dbsFamily
                    //because it can not be checked out
                }
                if(className.equals(DbsFamily.CLASS_NAME)){
                    //false means that if dbsPublicObject is not versioned then don't version it and check it out
                    if(! dbsFileSystem.isCheckedOut(dbsPublicObject)){
                        checkOut(dbsPublicObject.getId(),comment);
                        logger.info("DbsDocument Name : {" + dbsPublicObject.getName() + "}" );
                        resultCode = 1;
                    }
                }
            }
            dbsLibrarySession.completeTransaction(dbsTransaction);
            dbsTransaction = null;
        }catch(DbsException dbsException){
            throw dbsException;
        }finally{
            if(dbsTransaction != null){
                dbsLibrarySession.abortTransaction(dbsTransaction);
            }
        }

        return  resultCode;
    }

    //to make documents in a folder versioned recursively
    private int checkOutRecursively(DbsFolder top,DbsFileSystem dbsFileSystem, String comment) throws DbsException {
        //get all the items present in the given folder
        DbsPublicObject[] dbsPublicObjects = top.getItems();
        DbsPublicObject dbsPublicObject;
        String className;
        int resultCode = 0;
        int length = (dbsPublicObjects == null) ? 0 : dbsPublicObjects.length;
        for (int i = 0; i < length; i++){
            // if the item is a folder, call this same method recursively
            dbsPublicObject = dbsPublicObjects[i];
            className = dbsPublicObject.getClassname();
            if(className.equals(DbsFolder.CLASS_NAME)){
                logger.info("DbsFolder Name : {" + dbsPublicObject.getName() + "}");
                
                int resultCodeTemp = checkOutRecursively((DbsFolder)dbsPublicObject,dbsFileSystem,comment);
                switch (resultCodeTemp){
                case 0 :
                    //do not modify resultcode
                    break;
                case 1 :
                    resultCode = 1;
                    break;
                }
            }
            if(className.equals(DbsDocument.CLASS_NAME)){
                //Do nothing if public object is a dbsFamily
                //because it can not be checked out
            }
            if(className.equals(DbsFamily.CLASS_NAME)){
                //false means that if dbsPublicObject is not versioned then don't version it and check it out
                if(! dbsFileSystem.isCheckedOut(dbsPublicObject)){
                    checkOut(dbsPublicObject.getId(),comment);
                    logger.info("DbsDocument Name : {" + dbsPublicObject.getName() + "}" );
                    resultCode = 1;
                }
            }
        }
        return  resultCode;
    }


//0 = no document checkin because it is not versioned or its not checked out
//1 = checkin successful
    
    public int checkIn(Long[] folderDocIds, String comment,boolean keepCheckedOut) throws DbsException , IOException{
        DbsPublicObject dbsPublicObject = null;
        DbsFileSystem dbsFileSystem = null;
        String className;
        int checkInSuccess = 0;
        try{
            dbsTransaction = dbsLibrarySession.beginTransaction();
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            for(int index = 0; index < folderDocIds.length ; index++){
                dbsPublicObject = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                if(dbsPublicObject instanceof DbsFolder){
                    logger.info("DbsFolder Name : {" + dbsPublicObject.getName() + "}" );
                    
                    int checkingSuccessTemp = checkInRecursively((DbsFolder)dbsPublicObject,dbsFileSystem,comment,keepCheckedOut);
                    switch (checkingSuccessTemp){
                        case 0:
                            break;
                        case 1:
                            checkInSuccess = 1;
                            break;
                    }
                }
                if(dbsPublicObject instanceof DbsDocument){
                    //Do nothing if public object is not a dbsFamily
                    //because it can not be checked out
                }
                if(dbsPublicObject instanceof DbsFamily){
                    if(dbsFileSystem.isCheckedOut(dbsPublicObject)){
                        dbsFileSystem.checkIn(dbsPublicObject,comment);
                        if(keepCheckedOut){
                            checkOut(dbsPublicObject.getId(),comment);
                        }
                        logger.info("DbsDocument Name : {" + dbsPublicObject.getName() + "}" );
                        checkInSuccess = 1;
                    }
                }
            }
            dbsLibrarySession.completeTransaction(dbsTransaction);
            dbsTransaction = null;
        }catch(DbsException dbsException){
            throw dbsException;
        }finally{
            if(dbsTransaction != null){
                dbsLibrarySession.abortTransaction(dbsTransaction);
            }
        }

        return checkInSuccess;
    }

    //to make documents in a folder versioned recursively
    private int checkInRecursively(DbsFolder top,DbsFileSystem dbsFileSystem,String comment, boolean keepCheckedOut) throws DbsException {
        //get all the items present in the given folder
        DbsPublicObject[] dbsPublicObjects = top.getItems();
        DbsPublicObject dbsPublicObject;
        String className;
        int checkInSuccess = 0;
        int length = (dbsPublicObjects == null) ? 0 : dbsPublicObjects.length;
        for (int i = 0; i < length; i++){
            // if the item is a folder, call this same method recursively
            dbsPublicObject = dbsPublicObjects[i];
            className = dbsPublicObject.getClassname();
            if(className.equals(DbsFolder.CLASS_NAME)){
                logger.info("DbsFolder Name : {" + dbsPublicObject.getName() + "}");
                int checkInSuccessTemp = checkInRecursively((DbsFolder)dbsPublicObject,dbsFileSystem,comment,keepCheckedOut);
                switch (checkInSuccessTemp){
                    case 0:
                        break;
                    case 1:
                        checkInSuccess = 1;
                        break;
                }
            }
            if(className.equals(DbsDocument.CLASS_NAME)){
                //Do nothing if public object is a dbsFamily
                //because it can not be checked out
            }
            if(className.equals(DbsFamily.CLASS_NAME)){
                if(dbsFileSystem.isCheckedOut(dbsPublicObject)){
                    dbsFileSystem.checkIn(dbsPublicObject,comment);
                    if(keepCheckedOut){
                        checkOut(dbsPublicObject.getId(),comment);
                    }
                    logger.info("DbsDocument Name : {" + dbsPublicObject.getName() + "}");
                    checkInSuccess = 1;
                }
            }
        }
        return checkInSuccess;
    }

    public int cancelCheckout(Long[] folderDocIds) throws DbsException ,IOException{
        DbsPublicObject dbsPublicObject = null;
        DbsFileSystem dbsFileSystem = null;
        String className;
        int cancelCheckOutSuccess = 0;
        try{
            dbsTransaction = dbsLibrarySession.beginTransaction();
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            for(int index = 0; index < folderDocIds.length ; index++){
                dbsPublicObject = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                className = dbsPublicObject.getClassname();
                if(className.equals(DbsFolder.CLASS_NAME)){
                    logger.info("DbsFolder Name : {" + dbsPublicObject.getName() + "}" );
                    int cancelCheckOutSuccessTemp = cancelCheckoutRecursively((DbsFolder)dbsPublicObject,dbsFileSystem);
                    switch (cancelCheckOutSuccessTemp){
                        case 0:
                            break;
                        case 1:
                            cancelCheckOutSuccess = 1;
                            break;
                    }
                }
                if(className.equals(DbsDocument.CLASS_NAME)){
                    //Do nothing if public object is a dbsFamily
                    //because it can not be checked out
                }
                if(className.equals(DbsFamily.CLASS_NAME)){
                    if(dbsFileSystem.isCheckedOut(dbsPublicObject)){
                        dbsFileSystem.cancelCheckout(dbsPublicObject);
                        logger.info("DbsDocument Name : {" + dbsPublicObject.getName() + "}" );
                        cancelCheckOutSuccess = 1;
                    }
                }
            }
            dbsLibrarySession.completeTransaction(dbsTransaction);
            dbsTransaction = null;
        }catch(DbsException dbsException){
            throw dbsException;
        }finally{
            if(dbsTransaction != null){
                dbsLibrarySession.abortTransaction(dbsTransaction);
            }
        }
        return cancelCheckOutSuccess;
    }

    //to make documents in a folder versioned recursively
    private int cancelCheckoutRecursively(DbsFolder top,DbsFileSystem dbsFileSystem) throws DbsException {
        //get all the items present in the given folder
        DbsPublicObject[] dbsPublicObjects = top.getItems();
        DbsPublicObject dbsPublicObject;
        String className;
        int cancelCheckOutSuccess = 0;
        int length = (dbsPublicObjects == null) ? 0 : dbsPublicObjects.length;
        for (int i = 0; i < length; i++){
            // if the item is a folder, call this same method recursively
            dbsPublicObject = dbsPublicObjects[i];
            className = dbsPublicObject.getClassname();
            if(className.equals(DbsFolder.CLASS_NAME)){
                logger.info("DbsFolder Name : {" + dbsPublicObject.getName() + "}" );
                int cancelCheckOutSuccessTemp = cancelCheckoutRecursively((DbsFolder)dbsPublicObject,dbsFileSystem);
                switch(cancelCheckOutSuccessTemp){
                    case 0:
                        break;
                    case 1:
                        cancelCheckOutSuccess = 1;
                        break;
                }
            }
            if(className.equals(DbsDocument.CLASS_NAME)){
                //Do nothing if public object is a dbsFamily
                //because it can not be checked out
            }
            if(className.equals(DbsFamily.CLASS_NAME)){
                if(dbsFileSystem.isCheckedOut(dbsPublicObject)){
                    dbsFileSystem.cancelCheckout(dbsPublicObject);
                    logger.info("DbsDocument Name : {" + dbsPublicObject.getName() + "}" );
                    cancelCheckOutSuccess = 1;
                }
            }
        }
        return cancelCheckOutSuccess;
    }

  /**
  * Purpose : To list versions of a specific document 
  * @param  : docId whose versions are to be viewed 
  * @return : ArrayList of various versions
  */    

    public ArrayList getDocumentHistoryDetails(Long docId) throws DbsException{
        //docid = 158299
        DocumentHistoryDetail dhd = null;
        ArrayList documentHistoryDetails = new ArrayList();
        DbsVersionSeries dbsVersionSeries;
        DbsVersionDescription[] dbsVersionDescriptions; 
        DbsPublicObject dbsPublicObject;
        DbsFamily dbsFamily;
        DbsFileSystem dbsFileSystem;
        try{
            dbsPublicObject = dbsLibrarySession.getPublicObject(docId);
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            if(dbsPublicObject.getClassname().equals(DbsFamily.CLASS_NAME)){
                dbsFamily = (DbsFamily)dbsPublicObject;
                dbsVersionSeries = dbsFamily.getPrimaryVersionSeries();
                dbsVersionDescriptions = dbsVersionSeries.getVersionDescriptions();

                if(dbsFileSystem.isCheckedOut(dbsFamily)){
                    dhd = new DocumentHistoryDetail();
                    dhd.setVersionNumber(dbsVersionSeries.getLastVersionDescription().getVersionNumber() + 1);
                    dhd.setDocId(null);
                    dhd.setDocName(dbsFamily.getName());
                    dhd.setVersionDate(GeneralUtil.getDateForDisplay(dbsVersionSeries.getReservationDate(),locale));
                    dhd.setUserName(dbsVersionSeries.getReservor().getName());
                    dhd.setComment(dbsVersionSeries.getReservationComment());                    
                    dhd.setActionType(STATUS_CHECKEDOUT);
                    documentHistoryDetails.add(dhd);
                }
                for(int index = dbsVersionDescriptions.length - 1 ; index >= 0 ; index--){
                    dhd = new DocumentHistoryDetail();
                    dhd.setVersionNumber(dbsVersionDescriptions[index].getVersionNumber());
                    dbsPublicObject = dbsVersionDescriptions[index].getDbsPublicObject();
                    dhd.setDocId(dbsPublicObject.getId());
                    dhd.setDocName(dbsFamily.getName());
                    dhd.setVersionDate(GeneralUtil.getDateForDisplay(dbsPublicObject.getCreateDate(),locale));
                    dhd.setUserName(dbsPublicObject.getCreator().getName());
                    dhd.setComment(dbsVersionDescriptions[index].getRevisionComment());
                    dhd.setActionType(STATUS_CHECKEDIN);
                    documentHistoryDetails.add(dhd);
                }
                dhd = (DocumentHistoryDetail)documentHistoryDetails.get(documentHistoryDetails.size() -1);
                dhd.setActionType(STATUS_CREATED);
            }
        }catch(DbsException dbsException){
            throw dbsException;
        }
        return documentHistoryDetails;
    }

    /**
     * Purpose : To delete a document from the document history
     * @param  : Long id of the document to be deleted
     * @return : void
     */    
    public void deleteDocHistory(Long id) throws DbsException, Exception{
        DbsPublicObject dbsPublicObject = null;
        try{
            dbsPublicObject = dbsLibrarySession.getPublicObject(id);
            DbsFamily dbsFamily = dbsPublicObject.getFamily();
            DbsVersionSeries vs = dbsFamily.getPrimaryVersionSeries();
            DbsVersionDescription[] vd = vs.getVersionDescriptions();
            for(int index = 0; index < vd.length; index ++){
                if(vd[index].getDbsPublicObject().getId().longValue() == dbsPublicObject.getId().longValue()){
                    //if vd is not latest version description then free it
                    if(!vd[index].isLatestVersionDescription()){
                        logger.info("Version {" + vd[index].getVersionNumber() + "} of {" + dbsPublicObject.getName() + "} deleted successfully");
                        vd[index].free();
                    }
                    break;
                }
            }
        }catch(DbsException dbsException){
            throw dbsException;
        }
    }

    /**
    * Purpose : To rollback the history to a point where the document exist
    * @param  : Long id of the document to be deleted
    * @return : void
    */    
    public void rollbackDocHistory(Long id) throws DbsException, Exception{
        DbsPublicObject dbsPublicObject = null;
        try{
            dbsPublicObject = dbsLibrarySession.getPublicObject(id);
            DbsFamily dbsFamily = dbsPublicObject.getFamily();
            DbsVersionSeries vs = dbsFamily.getPrimaryVersionSeries();
            DbsVersionDescription[] vd = vs.getVersionDescriptions();
            for(int index = 0; index < vd.length; index ++){
                if(vd[index].getVersionNumber() > dbsPublicObject.getVersionNumber()){
                    logger.info("Version {" + vd[index].getVersionNumber() + "} of {" + dbsPublicObject.getName() + "} deleted successfully");                    
                    vd[index].free();
                }
            }
        }catch(DbsException dbsException){
//            DbsException dex = new DbsException(ex);
            throw dbsException;
        }
    }

    public void checkOut(Long familyId, String comment) throws DbsException{
        DbsFamily dbsFamily = (DbsFamily)dbsLibrarySession.getPublicObject(familyId);
        DbsVersionSeries vs = dbsFamily.getPrimaryVersionSeries();
        if (!vs.isReserved() && !dbsFamily.isLocked()) {
           vs.reserveNext(null, comment);
        }
    }

  /**
  * Purpose : To list details of a specific version of a document 
  * @param  : familyId and docId 
  * @return : DocumentHistoryDetail
  */    

    public DocumentHistoryDetail getVersionedDocProperty(Long familyId, Long docId,byte rtsType) throws DbsException{
        DocumentHistoryDetail dhd = null;
        DbsVersionSeries dbsVersionSeries;
        DbsVersionDescription[] dbsVersionDescriptions; 
        DbsPublicObject dbsPublicObject;
        DbsFamily dbsFamily;
        try{
            dbsFamily = (DbsFamily)dbsLibrarySession.getPublicObject(familyId);
            dbsVersionSeries = dbsFamily.getPrimaryVersionSeries();
            dbsVersionDescriptions = dbsVersionSeries.getVersionDescriptions();
            dhd = new DocumentHistoryDetail();
            
            if(docId.longValue() == 0){
                dhd.setVersionNumber(dbsVersionSeries.getLastVersionDescription().getVersionNumber() + 1);
                dhd.setDocId(null);
                dhd.setDocName(dbsFamily.getName());
                dhd.setVersionDate(GeneralUtil.getDateForDisplay(dbsVersionSeries.getReservationDate(),locale));
                dhd.setUserName(dbsVersionSeries.getReservor().getName());
                dhd.setComment(dbsVersionSeries.getReservationComment());
                dhd.setActionType(STATUS_CHECKEDOUT);
            }else{
                for(int index = dbsVersionDescriptions.length - 1 ; index >= 0 ; index--){
                    dbsPublicObject = dbsVersionDescriptions[index].getDbsPublicObject();
                    if(dbsPublicObject.getId().longValue() == docId.longValue()){
                        dhd.setVersionNumber(dbsVersionDescriptions[index].getVersionNumber());
                        dhd.setDocId(dbsPublicObject.getId());
                        dhd.setDocName(dbsPublicObject.getName());
                        logger.debug("Description: "+dbsPublicObject.getDescription());
                        dhd.setDescription(dbsPublicObject.getDescription().substring(1));
                        dhd.setVersionDate(GeneralUtil.getDateForDisplay(dbsPublicObject.getCreateDate(),locale));
                        dhd.setUserName(dbsPublicObject.getCreator().getName());
                        dhd.setComment(dbsVersionDescriptions[index].getRevisionComment());
                        dhd.setCandidateName(dbsPublicObject.getAttribute("NAME1").getString(dbsLibrarySession));
                        if( rtsType == 1 ){
                          dhd.setCandidateEmailId(dbsPublicObject.getAttribute("EMAIL").getString(dbsLibrarySession));
                          dhd.setCandidatePhnNo(dbsPublicObject.getAttribute("PHONE1").getString(dbsLibrarySession));
                          dhd.setCommunicationSkills(dbsPublicObject.getAttribute("COMMUNICATION_SKILL").getString(dbsLibrarySession));
                          dhd.setCandidateAddress(dbsPublicObject.getAttribute("ADDRESS").getString(dbsLibrarySession));
                        }else{
                          //reserved for timesheet attributes
                        }
                        if(index == 0){
                            dhd.setActionType(STATUS_CREATED);
                        }else{
                            dhd.setActionType(STATUS_CHECKEDIN);
                        }
                        break;
                    }
                }
            }
        }catch(DbsException dbsException){
            throw dbsException;
        }
        return dhd;
    }
    
    
    
	public void putContent(DbsFamily dbsFamily, DbsDocument dbsDocument) throws DbsException{
		
    logger.debug("Updating content on " );
    logger.debug("1");
		//DbsDocument dbsDocument = null;
    
    /*
		LibrarySession session = getSession();
		DocumentUtilities docutils = new DocumentUtilities(session, getLogger());*/
    
    try{
      // Get the primary version series for the family
      DbsVersionSeries vs = getPrimaryVersionSeries(dbsFamily);
      logger.debug("2");
      if( vs != null){
        logger.debug("vs not null...");
        logger.debug("vs name: "+vs.getName());
        if( vs.isReserved()){
          // The doc is secured by the family
          dbsDocument.setSecuringPublicObject(dbsFamily);
          logger.debug("Securing po set to dbsFamily");
        }
      }
    }catch( DbsException dex ){
        dex.printStackTrace();
    }
			// check in the new file
			//checkIn(family, "New version from checkin", doc);
		//}

		
	}
    
	public DbsVersionSeries getPrimaryVersionSeries(DbsPublicObject po) throws DbsException{
		DbsVersionSeries vs = null;

		DbsFamily dbsfamily = null;
    try{
      if (po instanceof DbsFamily)
      {
        dbsfamily = (DbsFamily)po;
      }
      else
      {
        dbsfamily = po.getFamily();
      }
  
      // Get the primary version series of this family
      // The  primary version series is the most active/desirable
      // version series to be used in versioning operations;
      if (dbsfamily != null)
      {
        vs = dbsfamily.getPrimaryVersionSeries();
      }
    }catch( DbsException dex ){
        dex.printStackTrace();
    }
		return vs;
	}
    
}
