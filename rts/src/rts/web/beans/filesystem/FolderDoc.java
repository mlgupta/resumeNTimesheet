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
 * $Id: FolderDoc.java,v 1.54 2006/01/06 10:29:40 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;

/**
 *	Purpose: To handle filesytem operations for resume,timesheet,document,folder
 *  @author             Suved Mishra
 *  @version            1.0
 * 	Date of creation:   20-06-2005
 * 	Last Modfied by :   Suved Mishra 
 * 	Last Modfied Date:  08-07-2005  
 */
/*rts package references */
import rts.beans.*;
import rts.web.beans.exception.ExceptionBean;
import rts.web.beans.user.UserPreferences;
import rts.web.beans.utility.GeneralUtil;
import rts.web.beans.utility.SearchUtil;
//Java API
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
/* cmsdk API */
import oracle.ifs.adk.filesystem.IfsFileSystem;
import oracle.ifs.beans.Document;
import oracle.ifs.beans.Family;
import oracle.ifs.beans.Format;
import oracle.ifs.beans.PublicObject;
import oracle.ifs.beans.VersionDescription;
import oracle.ifs.beans.VersionSeries;
//Struts API
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import rts.beans.DbsRelationship;

public class FolderDoc  {

    private DbsLibrarySession dbsLibrarySession = null;     //DbsLibrarySession Object
    private Logger logger = null;
    private Locale locale = null;
    private String[] replacementValues;
    private DbsTransaction dbsTransaction = null;
    private int drawerCount;
    private int documentCount;
    public ArrayList folderDocLists;
    private ArrayList publicObjectFoundLists;
    public DbsFolder folderWhenAdded;
    /**
     * Purpose : Contructs a FolderDoc Object for Given Librarysession
     * @param dbsLibrarySession - A Librarysession object to generate Tree
     */
    public FolderDoc(DbsLibrarySession dbsLibrarySession){
        this.dbsLibrarySession = dbsLibrarySession;
        //Initialize logger
        logger = Logger.getLogger("DbsLogger");
        //use default if locale not set
        locale = Locale.getDefault(); //
        replacementValues = new String[5];
        drawerCount = 0;
        documentCount = 0;
    }
  
    /**
     * Purpose : To create collection of FolderDocListForm object
     * @return : Return a collection of FolderDocListForm object
     */    
    public ArrayList getFolderDocList(Long currentFolderId,
      FolderDocInfo folderDocInfo,UserPreferences userPreferences, 
      String davPath,boolean forMail) throws DbsException{
      
        //Variable Declaration
        //ArrayList folderDocLists;
        DbsFolder dbsFolder;
        DbsPublicObject dbsPublicObject;
        List searchQualificationList = new ArrayList();
        
        try{
            
            DbsAttributeSearchSpecification dbsAttributeSearchSpecification = 
              new DbsAttributeSearchSpecification();
              
            DbsSearchSortSpecification dbsSearchSortSpecification = 
              new DbsSearchSortSpecification();
              
            DbsFolderRestrictQualification dbsFolderRestrictQualification = 
              new DbsFolderRestrictQualification();
              
            DbsSearchClassSpecification dbsSearchClassSpecification = 
              new DbsSearchClassSpecification();
            
            dbsFolder = (DbsFolder)
              (new DbsFileSystem(dbsLibrarySession).findPublicObjectById((currentFolderId)));
            
            dbsFolderRestrictQualification.setStartFolder(dbsFolder);
            
            dbsFolderRestrictQualification.setSearchClassname(DbsPublicObject.CLASS_NAME);
            
            dbsFolderRestrictQualification.setMultiLevel(forMail);
            
            searchQualificationList.add(dbsFolderRestrictQualification);
            
            // array of class to be searched
            String [] searchClasses = new String[] {DbsPublicObject.CLASS_NAME};
            dbsSearchClassSpecification.addSearchClasses(searchClasses);
            dbsSearchClassSpecification.addResultClass(DbsPublicObject.CLASS_NAME);
            
            // Array of classes involved in the order by clause
            String [] sortClasses = new String[] {DbsPublicObject.CLASS_NAME};
             // Array of Attribute Names to match class names.
            String [] attNames = new String[] {"NAME"};
            // Order of Sort for each sort element
            boolean [] orders = new boolean[] {true};
            // Case insensitive Sort for each sort element
            String [] caseSorts = new String[] {"nls_upper"};
            dbsSearchSortSpecification.add(sortClasses,attNames,orders,caseSorts);
            
            DbsAttributeQualification folderDocIdAttrbQual = new DbsAttributeQualification();
            String searchColumn = "ID";
            folderDocIdAttrbQual.setAttribute(searchColumn);
            folderDocIdAttrbQual.setOperatorType(DbsAttributeQualification.NOT_EQUAL);
            folderDocIdAttrbQual.setValue(DbsAttributeValue.newAttributeValue(folderDocInfo.getCurrentFolderId()));
            searchQualificationList.add(folderDocIdAttrbQual);

            /* *************** **/
            /*DbsAttributeQualification folderDocNameAttrbQual = new DbsAttributeQualification();*/
            //String searchColumn = dbsPublicObject.DESCRIPTION_ATTRIBUTE;
            /*searchColumn = "DESCRIPTION"; //dbsPublicObject.DESCRIPTION_ATTRIBUTE;*/
            /*folderDocNameAttrbQual.setCaseIgnored(true);
            folderDocNameAttrbQual.setAttribute(searchColumn);
            folderDocNameAttrbQual.setOperatorType(DbsAttributeQualification.LIKE);*/
            //String searchDescription = advanceSearchForm.getTxtDocDescription().trim();
            /* searchDescription has been modified for Resume Class */
            /*String searchDescription = "R";
            searchDescription = searchDescription.replace('*' , '%');
            searchDescription = searchDescription.replace('?' , '_');
            searchDescription = searchDescription + ('%');
            folderDocNameAttrbQual.setValue(searchDescription);
            searchQualificationList.add(folderDocNameAttrbQual);*/
            /* ****************** */
            
            //And together all the dbsSearch qualifications
            DbsSearchQualification dbsSearchQualification = null;
            Iterator iterator = searchQualificationList.iterator();
            
            while (iterator.hasNext()){
                
                DbsSearchQualification nextSearchQualification = 
                  (DbsSearchQualification) iterator.next();
                  
                if (dbsSearchQualification == null) {
                
                    dbsSearchQualification = nextSearchQualification;
                    
                }else{
                
                    dbsSearchQualification = new DbsSearchClause(
                      dbsSearchQualification, nextSearchQualification, 
                      DbsSearchClause.AND);
                      
                }
            }

            dbsAttributeSearchSpecification.setSearchQualification(dbsSearchQualification);
            dbsAttributeSearchSpecification.setSearchClassSpecification(dbsSearchClassSpecification);
            dbsAttributeSearchSpecification.setSearchSortSpecification(dbsSearchSortSpecification);
            
            DbsSearch dbsSearch = 
              new DbsSearch(dbsLibrarySession,dbsAttributeSearchSpecification);
              
            folderDocLists = 
              buildFolderDocList(dbsSearch,FolderDocInfo.SIMPLE_LISTING,
                                 folderDocInfo,userPreferences, davPath,forMail);
                                 
        }catch(DbsException dbsException){
            
            throw dbsException;
            
        }
        
        logger.debug("folderDocLists.size() : " + folderDocLists.size());
        return folderDocLists;
    
    }

/**
   * Purpose : To filter drawers and docs for rts
   * @throws rts.beans.DbsException
   * @param dbsSearch
   */
private void filterDrawerDocs(DbsSearch dbsSearch ,int rtsType ,boolean forMail )
  throws DbsException {
  
    publicObjectFoundLists = new ArrayList();
    
    dbsSearch.open();
    
    int itemCount = dbsSearch.getItemCount();

    int folderCount = 0;

    String desc = null;
    String cOType = null;
    if( forMail ){
      desc = "R";
      cOType = "RESUME1";
    }else{
      /*desc = ( rtsType == 1 )?"R":"T";
      cOType = ( rtsType == 1 )?"RESUME1":"TIMESHEET"; */
      if( rtsType == 1 ){
        desc = "R";
        cOType = "RESUME1";
      }else if( rtsType == 2 ){
        desc = "T";
        cOType = "TIMESHEET";
      }else{
        desc = "P";
        cOType = DbsDocument.CLASS_NAME; 
      }
    }
    //int documentCount =0;
    
    logger.debug("itemCount: "+itemCount);
    
    DbsPublicObject dbsPublicObject;
    
    for( int index = 0; index < itemCount; index++ ){
    
      dbsPublicObject = ((DbsPublicObject)dbsSearch.next().getLibraryObject());
      
      if( dbsPublicObject instanceof DbsFolder ){
        
        if( dbsPublicObject.getDescription()!=null 
         && dbsPublicObject.getDescription().startsWith(desc) ){
          
            publicObjectFoundLists.add(folderCount,dbsPublicObject);
            folderCount++;
          
        }
        
      }else{
        
        if( dbsPublicObject.getClassname().equalsIgnoreCase("Family") && 
            dbsPublicObject.getResolvedPublicObject().getClassname().equalsIgnoreCase(cOType)){
          
          publicObjectFoundLists.add(dbsPublicObject);
          documentCount++;
          
        }else if( dbsPublicObject instanceof DbsDocument && 
                  dbsPublicObject.getClassname().equalsIgnoreCase(cOType)){
          
          publicObjectFoundLists.add(dbsPublicObject);
          documentCount++;
          
        }else{
          // do nothing
        }
        
      }
      
    }
   logger.debug("folderCount: "+folderCount);
   drawerCount = folderCount;
   //docCount = documentCount;
   logger.debug("documentCount: "+documentCount);
  
   //return publicObjectFoundLists;
  
}

/**
   * Purpose: build doc list
   * @throws rts.beans.DbsException
   * @return doc list 
   * @param davPath
   * @param userPreferences
   * @param folderDocInfo
   * @param listingType
   * @param dbsSearch
   * @param forMail
   */
public ArrayList buildFolderDocList(DbsSearch dbsSearch,byte listingType,
  FolderDocInfo folderDocInfo,UserPreferences userPreferences, 
  String davPath,boolean forMail) 
  throws DbsException{
  
    folderDocLists = new ArrayList();
    FolderDocList folderDocList;
    int folderCount = 0;
    DbsDocument dbsDocument;
    DbsPublicObject dbsPublicObject;
    int itemCount;
    boolean isToBeListed = true;
    
    try{
        filterDrawerDocs(dbsSearch,folderDocInfo.getRtsType(),forMail);

        if( userPreferences.getNavigationType() == 
            UserPreferences.FLAT_NAVIGATION ) { 
        // for flat nav, obtain only documents list here, drawers list will be
        // obtained seperately.
        if(!forMail){ // forMail = false, provide listing only.
        /* setting navigation-specific data begins...*/
        //initialize pageCount to 1
        folderDocInfo.setPageCount(new Integer(1));

        itemCount = publicObjectFoundLists.size();
        int startIndex, endIndex, pageCount;
        logger.debug("itemCount : " + itemCount);
        
        if (documentCount > 0 ){
            //set start index and end index of the records to be displayed
            startIndex = ( folderDocInfo.getPageNumber() ==0 )?
                          (userPreferences.getRecordsPerPage()+drawerCount):
                          (folderDocInfo.getPageNumber() -1) *
                          userPreferences.getRecordsPerPage()+drawerCount;
                          
            logger.debug("startIndex : " + startIndex);
            
            endIndex = startIndex + userPreferences.getRecordsPerPage()-1;
            
            logger.debug("endIndex : " + endIndex);
            if(endIndex >= itemCount-1){
                endIndex = itemCount-1;
            }
        
            logger.debug("endIndex : " + endIndex);
            //find page count 
              
            pageCount = (int)StrictMath.ceil((double)
                        documentCount / userPreferences.getRecordsPerPage());
            
            if(pageCount > 0){
            
                folderDocInfo.setPageCount(new Integer(pageCount));
                
            }
            /* setting navigation-specific data ends...*/
            DbsAttributeValue attrValue=null;
            
            for(int intCounter = startIndex; intCounter <= endIndex; intCounter++){
            // populate each folderDocList obj & add to collection,folderDocLists    
                logger.debug("intCounter: "+intCounter);
                
                dbsPublicObject = (DbsPublicObject)
                                    publicObjectFoundLists.get(intCounter);
                                    
                folderDocList = new FolderDocList();
                isToBeListed = true;
                Long id = dbsPublicObject.getId();
//                    logger.debug("id : " + id);
                folderDocList.setId(id);
                
                //folderDocList.setDescription(dbsPublicObject.getDescription());
                
                if(listingType == FolderDocInfo.SIMPLE_LISTING){
                    String name = dbsPublicObject.getResolvedPublicObject().getName();
                    logger.debug("name : " + name);
                    folderDocList.setName(name);
                }else{
                    String fullPath = null;
                    if(dbsPublicObject.getClassname().equalsIgnoreCase("FAMILY")){
                      logger.debug("dbsPublicObject is Versioned");
                      DbsFamily dbsFam = (DbsFamily)dbsPublicObject;
                      DbsFolder [] folderReferences = dbsFam.getFolderReferences();
                      StringBuffer folderPathBuf = new StringBuffer();
                      String docPath = null;
                      if( folderReferences!=null && folderReferences.length!=0 ){
                        folderPathBuf.append(folderReferences[0].getAnyFolderPath());
                        for(int index = 1; index < folderReferences.length; index++){
                          folderPathBuf.append(",");
                          folderPathBuf.append(folderReferences[index].getAnyFolderPath());
                        }
                        docPath = folderPathBuf.toString(); 
                      }
                      
                      
                      //String docPath = dbsPublicObject.getAnyFolderPath();
                      //DbsFamily dbsFam = (DbsFamily)dbsPublicObject;
                  /*  DbsVersionSeries dbsVs = dbsFam.getPrimaryVersionSeries();
                      DbsVersionDescription dbsVd = dbsVs.getLastVersionDescription();
                      DbsPublicObject dbsPO = dbsVd.getDbsPublicObject(); */
                      DbsPublicObject dbsPO = dbsFam.getResolvedPublicObject();
                      /* RPO on family is equivalent to obtaining lastVD's PO 
                       * provided any DefaultVersionDescription attribute on 
                       * the Family is not set. */ 
                      //fullPath = docPath.substring(0,docPath.lastIndexOf(File.separator)+1)
                                                    //+dbsPO.getName();
                      fullPath = docPath;                            
                      folderDocList.setName(dbsPO.getName());                              
                    }else{
                      //fullPath = dbsPublicObject.getAnyFolderPath();
                      DbsFolder [] folderReferences = dbsPublicObject.getFolderReferences();
                      StringBuffer folderPathBuf = new StringBuffer();
                      String docPath = null;
                      if( folderReferences!=null && folderReferences.length!=0 ){
                        folderPathBuf.append(folderReferences[0].getAnyFolderPath());
                        for(int index = 1; index < folderReferences.length; index++){
                          folderPathBuf.append(",");
                          folderPathBuf.append(folderReferences[index].getAnyFolderPath());
                        }
                        docPath = folderPathBuf.toString(); 
                      }
                      fullPath=docPath;
                    }
                    logger.debug("fullPath : " + fullPath);
                    folderDocList.setName(fullPath);
                }
                /*String modifiedDate = 
                GeneralUtil.getDateForDisplay(dbsPublicObject.getLastModifyDate(),locale);
//                    logger.debug("modifiedDate : " + modifiedDate);
                folderDocList.setModifiedDate(modifiedDate);*/
                String className = dbsPublicObject.getClassname();
                logger.debug("className : " + className);
                folderDocList.setType(className);
                folderDocList.setClassName(className);

                /*if(dbsPublicObject instanceof DbsFamily){
                  DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
                  boolean checkedOut = dbsFileSystem.isCheckedOut(dbsPublicObject);
//                        logger.debug("checkedOut : " + checkedOut);
                  folderDocList.setCheckedOut(checkedOut);
                }*/

                String docPath = null; 
                docPath = dbsPublicObject.getAnyFolderPath();
                //logger.debug("docPath: "+docPath);
                dbsPublicObject = dbsPublicObject.getResolvedPublicObject();
                // if dbsPublicObject is of "RESUME1" type,
                if(dbsPublicObject instanceof DbsDocument  &&  
                    dbsPublicObject.getClassname().equalsIgnoreCase("RESUME1")){
                      
                    dbsDocument = (DbsDocument)dbsPublicObject;
                    try{
                        /* additional code for Resume Class Documents */
                        attrValue= dbsDocument.getAttribute("NAME1");
                        // set NAME1,alongwith limiting display
                        if(attrValue!= null){
                          
                          logger.debug("NAME1: "
                            +attrValue.getString(dbsLibrarySession));
                            
                          if( attrValue.getString(dbsLibrarySession).length() >=30 ){
                            folderDocList.setName1(attrValue.getString(
                                          dbsLibrarySession).substring(0,26)+"...");  
                          }else{
                            folderDocList.setName1(attrValue.getString(
                                          dbsLibrarySession));
                          }
                        }
                        
                        attrValue = dbsDocument.getAttribute("EMAIL");
                        // set EMAIL alongwith limiting display
                        if(attrValue!= null){
                        
                          logger.debug("EMAIL: "
                            +attrValue.getString(dbsLibrarySession));
                          if( attrValue.getString(dbsLibrarySession).length() >= 25 ){
                            folderDocList.setTrimEmail(attrValue.getString(
                                          dbsLibrarySession).substring(0,21)+"...");  
                          }else{
                            folderDocList.setTrimEmail(attrValue.getString(dbsLibrarySession));
                          }
                          folderDocList.setEmail(attrValue.getString(dbsLibrarySession));
                        }else{
                          logger.debug("EMAIL not available ");
                          folderDocList.setEmail("N/A");
                          folderDocList.setTrimEmail("N/A");
                        }

                        attrValue = dbsDocument.getAttribute("ADDRESS");
                        // set ADDRESS alongwith limiting display
                        if(attrValue!= null){
                          
                          logger.debug("ADDRESS: "
                            +attrValue.getString(dbsLibrarySession));
                            
                          folderDocList.setAddress(attrValue.getString(dbsLibrarySession));
                          if( attrValue.getString(dbsLibrarySession).length() >= 55  ){
                            folderDocList.setTrimAddress(attrValue.getString(
                                          dbsLibrarySession).substring(0,51)+"...");  
                          }else{
                            folderDocList.setTrimAddress(attrValue.getString(
                                          dbsLibrarySession));
                          }
                        }

                        attrValue = dbsDocument.getAttribute("PHONE1");
                        // set PHONE1
                        if(attrValue!= null){
                          
                          logger.debug("PHONE: "
                            +attrValue.getString(dbsLibrarySession));
                          
                          folderDocList.setPhone1(attrValue.getString(dbsLibrarySession));
                          
                        }else{
                          logger.debug("PHONE not available ");
                          folderDocList.setPhone1("N/A");
                        }
                    }catch( DbsException dex ){
                        isToBeListed = false;
                        folderDocList = null;
                        logger.error("DbsException is: "+dex.getMessage());
                    }catch( Exception ex ){
                        isToBeListed = false;
                        folderDocList = null;
                        logger.error("Exception is: "+ex.getMessage());
                    }
                    
                }else if(dbsPublicObject instanceof DbsDocument  &&  
                    dbsPublicObject.getClassname().equalsIgnoreCase("TIMESHEET")){
                    // if dbsPublicObject is of "TIMESHEET" type  
                    dbsDocument = (DbsDocument)dbsPublicObject;
                    try{
                        /* additional code for Timesheet Class Documents */
                        attrValue= dbsDocument.getAttribute("NAME1");
                        if(attrValue!= null){
                        // set NAME1 alongwith limiting display 
                          logger.debug("NAME1: "
                            +attrValue.getString(dbsLibrarySession));
                            
                          if( attrValue.getString(dbsLibrarySession).length() >=45 ){
                            folderDocList.setName1(attrValue.getString(
                                          dbsLibrarySession).substring(0,41)+"...");  
                          }else{
                            folderDocList.setName1(attrValue.getString(
                                          dbsLibrarySession));
                          }

                        }

                        attrValue= dbsDocument.getAttribute("CSV");
                        if(attrValue!= null){
                        // set CSV  alongwith limiting display
                          logger.debug("CSV: "
                            +attrValue.getString(dbsLibrarySession));
                          if( attrValue.getString(dbsLibrarySession).length() >=90 ){
                            folderDocList.setTrimCSV(attrValue.getString(
                                          dbsLibrarySession).substring(0,86)+"...");
                          }else{
                            folderDocList.setTrimCSV(attrValue.getString(
                                          dbsLibrarySession));
                          }
                          folderDocList.setCsv(attrValue.getString(dbsLibrarySession));
                        }
                        
                    }catch( DbsException dex ){
                        isToBeListed = false;
                        folderDocList = null;
                        logger.error("DbsException is: "+dex.getMessage());
                    }catch( Exception ex ){
                        isToBeListed = false;
                        folderDocList = null;
                        logger.error("Exception is: "+ex.getMessage());
                    }
                    
                }else{  // if dbsPublicObject is of "PERSONAL" type,
                  //isToBeListed = false;
                  if( folderDocInfo.getRtsType() == FolderDocInfo.PERSONAL_TYPE 
                      && dbsPublicObject instanceof DbsDocument ){
                    dbsDocument = (DbsDocument)dbsPublicObject;
                    // set NAME alongwith limiting display
                    if( dbsDocument.getName().length() >= 45 ){
                      folderDocList.setName1(dbsDocument.getName().substring(
                                                                    0,41)+"...");  
                    }else{
                      folderDocList.setName1(dbsDocument.getName());
                    }
                    // set modified date 
                    try{
                      folderDocList.setModifiedDate(
                                    dbsDocument.getLastModifyDate().toString());
                      // set size and type of document              
                      double size = dbsDocument.getContentSize();
                      size /= 1024;
                      size = (Math.round((size * 100))/100.00);
                      folderDocList.setSize(new Double(size).toString());
                      folderDocList.setType(dbsDocument.getFormat().getMimeType());
                    }catch( DbsException dex ){
                        isToBeListed = false;
                        folderDocList = null;
                        logger.error("DbsException is: "+dex.getMessage());
                    }catch( Exception ex ){
                        isToBeListed = false;
                        folderDocList = null;
                        logger.error("Exception is: "+ex.getMessage());
                    }
                    
                  }
                }
                if( isToBeListed ){
                  folderDocLists.add(folderDocList);
                }
            }
        }
      }else{    //  forMail = true, this is used to provide documents list 
                //  for grabbing email ids. 
        
         getDocumentLists();
        
      }
    
    }else{ /* if Tree Nav */
      
      if( !forMail ){
        getDrawerDocList(folderDocInfo,userPreferences,listingType);
      }else{
        getDocumentLists();
      }
      
    }
    }catch(DbsException dex){
        logger.debug("Exception stack trace: ");
        dex.printStackTrace();        
        logger.error(dex.getMessage());
    }
    return folderDocLists;
}

    
private void getDocumentLists() throws DbsException{
  
  int startIndex = drawerCount;
  int endIndex = publicObjectFoundLists.size()-1;
  
  DbsPublicObject dbsPublicObject = null;
  DbsDocument dbsDocument = null;
  DbsAttributeValue attrValue=null;
  FolderDocList folderDocList = null;
  
  for(int intCounter = startIndex; intCounter <= endIndex; intCounter++){
      
      logger.debug("intCounter: "+intCounter);
      
      dbsPublicObject = (DbsPublicObject)
                          publicObjectFoundLists.get(intCounter);
                          
      folderDocList = new FolderDocList();
      Long id = dbsPublicObject.getId();
//                    logger.debug("id : " + id);
      folderDocList.setId(id);
      logger.debug("name : " + dbsPublicObject.getName());

      String className = dbsPublicObject.getClassname();
      logger.debug("className : " + className);
      folderDocList.setType(className);
      folderDocList.setClassName(className);


      String docPath = null; 
      docPath = dbsPublicObject.getAnyFolderPath();
      //logger.debug("docPath: "+docPath);
      dbsPublicObject = dbsPublicObject.getResolvedPublicObject();

      if(dbsPublicObject instanceof DbsDocument  &&  
          dbsPublicObject.getClassname().equalsIgnoreCase("RESUME1")){
            
          dbsDocument = (DbsDocument)dbsPublicObject;
          try{
              /* additional code for Resume Class Documents */
              attrValue= dbsDocument.getAttribute("NAME1");
              if(attrValue!= null){
                
                logger.debug("NAME1: "
                  +attrValue.getString(dbsLibrarySession));
                  
                folderDocList.setName1(attrValue.getString(dbsLibrarySession));
              }
              
              attrValue = dbsDocument.getAttribute("EMAIL");
              if(attrValue!= null){
              
                logger.debug("EMAIL: "
                  +attrValue.getString(dbsLibrarySession));
                  
                folderDocList.setEmail(attrValue.getString(dbsLibrarySession));
              
              }else{
                logger.debug("EMAIL not available ");
                folderDocList.setEmail("");
              }


              
          }catch( DbsException dex ){
              folderDocList = null;
              logger.error("DbsException is: "+dex.getMessage());
          }catch( Exception ex ){
              folderDocList = null;
              logger.error("Exception is: "+ex.getMessage());
          }
          
      }
      //if( isToBeListed ){
      folderDocLists.add(folderDocList);
      //}
  }
  
  logger.debug("folderDocLists.size(): " +folderDocLists.size());
  
}


private void getDrawerDocList(FolderDocInfo folderDocInfo,
                              UserPreferences userPreferences,
                              byte listingType)throws DbsException{

FolderDocList folderDocList;
int folderCount = 0;
DbsDocument dbsDocument;
DbsPublicObject dbsPublicObject;
int itemCount;
boolean isToBeListed = true;

//initialize pageCount to 1
folderDocInfo.setPageCount(new Integer(1));

itemCount = publicObjectFoundLists.size();
int startIndex, endIndex, pageCount;
logger.debug("itemCount : " + itemCount);
/* setting navigation-specific data begins...*/
if (itemCount > 0 ){
    //set start index and end index of the records to be displayed
    startIndex = ( folderDocInfo.getPageNumber() ==1 )?0:
                  (folderDocInfo.getPageNumber() -1) *
                  userPreferences.getRecordsPerPage();
                  
    logger.debug("startIndex : " + startIndex);
    
    endIndex = startIndex + userPreferences.getRecordsPerPage()-1;
    
    logger.debug("endIndex : " + endIndex);
    if(endIndex >= itemCount-1){
        endIndex = itemCount-1;
    }

    logger.debug("endIndex : " + endIndex);
    //find page count 
      
    pageCount = (int)StrictMath.ceil((double)
                itemCount / userPreferences.getRecordsPerPage());
    
    if(pageCount > 0){
    
        folderDocInfo.setPageCount(new Integer(pageCount));
        
    }
/* setting navigation-specific data ends...*/    
    DbsAttributeValue attrValue=null;
    
    for(int intCounter = startIndex; intCounter <= endIndex; intCounter++){
        
        logger.debug("intCounter: "+intCounter);
        
        dbsPublicObject = (DbsPublicObject)
                            publicObjectFoundLists.get(intCounter);
                            
        folderDocList = new FolderDocList();
        isToBeListed = true;
        Long id = dbsPublicObject.getId();
//                    logger.debug("id : " + id);
        folderDocList.setId(id);
        
        //folderDocList.setDescription(dbsPublicObject.getDescription());
        
        if(listingType == FolderDocInfo.SIMPLE_LISTING){
            String name = dbsPublicObject.getResolvedPublicObject().getName();
            logger.debug("name : " + name);
            folderDocList.setName(name);
            //folderDocList.setName1(name);
        }else{
            String fullPath = null;
            if(dbsPublicObject.getClassname().equalsIgnoreCase("FAMILY")){
              logger.debug("dbsPublicObject is Versioned");
              DbsFamily dbsFam = (DbsFamily)dbsPublicObject;
              DbsFolder [] folderReferences = dbsFam.getFolderReferences();
              StringBuffer folderPathBuf = new StringBuffer();
              String docPath = null;
              if( folderReferences!=null && folderReferences.length!=0 ){
                folderPathBuf.append(folderReferences[0].getAnyFolderPath());
                for(int index = 1; index < folderReferences.length; index++){
                  folderPathBuf.append(",");
                  folderPathBuf.append(folderReferences[index].getAnyFolderPath());
                }
                docPath = folderPathBuf.toString(); 
              }
              
              
              //String docPath = dbsPublicObject.getAnyFolderPath();
              //DbsFamily dbsFam = (DbsFamily)dbsPublicObject;
          /*  DbsVersionSeries dbsVs = dbsFam.getPrimaryVersionSeries();
              DbsVersionDescription dbsVd = dbsVs.getLastVersionDescription();
              DbsPublicObject dbsPO = dbsVd.getDbsPublicObject(); */
              DbsPublicObject dbsPO = dbsFam.getResolvedPublicObject();
              /* RPO on family is equivalent to obtaining lastVD's PO 
               * provided any DefaultVersionDescription attribute on 
               * the Family is not set. */ 
              //fullPath = docPath.substring(0,docPath.lastIndexOf(File.separator)+1)
                                            //+dbsPO.getName();
              fullPath = docPath;
              folderDocList.setName(dbsPO.getName());
            }else{
              //fullPath = dbsPublicObject.getAnyFolderPath();
              DbsFolder [] folderReferences = dbsPublicObject.getFolderReferences();
              StringBuffer folderPathBuf = new StringBuffer();
              String docPath = null;
              if( folderReferences!=null && folderReferences.length!=0 ){
                folderPathBuf.append(folderReferences[0].getAnyFolderPath());
                for(int index = 1; index < folderReferences.length; index++){
                  folderPathBuf.append(",");
                  folderPathBuf.append(folderReferences[index].getAnyFolderPath());
                }
                docPath = folderPathBuf.toString(); 
              }
              fullPath=docPath;
            }
            logger.debug("fullPath : " + fullPath);
            folderDocList.setName(fullPath);
        }
        if( !(dbsPublicObject instanceof DbsFolder) ){
          /*String modifiedDate = 
          GeneralUtil.getDateForDisplay(dbsPublicObject.getLastModifyDate(),locale);
  //                    logger.debug("modifiedDate : " + modifiedDate);
          folderDocList.setModifiedDate(modifiedDate);*/
          String className = dbsPublicObject.getClassname();
          logger.debug("className : " + className);
          folderDocList.setType(className);
          folderDocList.setClassName(className);
  
          /*if(dbsPublicObject instanceof DbsFamily){
            DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            boolean checkedOut = dbsFileSystem.isCheckedOut(dbsPublicObject);
  //                        logger.debug("checkedOut : " + checkedOut);
            folderDocList.setCheckedOut(checkedOut);
          }*/
  
          String docPath = null; 
          docPath = dbsPublicObject.getAnyFolderPath();
          //logger.debug("docPath: "+docPath);
          dbsPublicObject = dbsPublicObject.getResolvedPublicObject();
          // if dbsPublicObject is of "RESUME1" type,
          if(dbsPublicObject instanceof DbsDocument  &&  
              dbsPublicObject.getClassname().equalsIgnoreCase("RESUME1")){
                
              dbsDocument = (DbsDocument)dbsPublicObject;
              try{
                  /* additional code for Resume Class Documents */
                  // set NAME1 alongwith limiting display
                  attrValue= dbsDocument.getAttribute("NAME1");
                  if(attrValue!= null){
                    
                    logger.debug("NAME1: "
                      +attrValue.getString(dbsLibrarySession));
                    if( attrValue.getString(dbsLibrarySession).length() >=20 ){
                      folderDocList.setName1(attrValue.getString(
                                    dbsLibrarySession).substring(0,16)+"...");  
                    }else{
                      folderDocList.setName1(attrValue.getString(
                                    dbsLibrarySession));
                    }
                  }
                  
                  // set EMAIL alongwith limiting display
                  attrValue = dbsDocument.getAttribute("EMAIL");
                  if(attrValue!= null){
                  
                    logger.debug("EMAIL: "
                      +attrValue.getString(dbsLibrarySession));
                      
                    if( attrValue.getString(dbsLibrarySession).length() >= 25 ){
                      folderDocList.setTrimEmail(attrValue.getString(
                                    dbsLibrarySession).substring(0,21)+"...");  
                    }else{
                      folderDocList.setTrimEmail(attrValue.getString(dbsLibrarySession));
                    }
                    folderDocList.setEmail(attrValue.getString(dbsLibrarySession));
                  
                  }else{
                    logger.debug("EMAIL not available ");
                    folderDocList.setEmail("N/A");
                    folderDocList.setTrimEmail("N/A");
                  }
  
                  // set ADDRESS alongwith limiting display
                  attrValue = dbsDocument.getAttribute("ADDRESS");
                  if(attrValue!= null){
                    
                    logger.debug("ADDRESS: "
                      +attrValue.getString(dbsLibrarySession));
                      
                    folderDocList.setAddress(attrValue.getString(dbsLibrarySession));
                    if( attrValue.getString(dbsLibrarySession).length() >= 55  ){
                      folderDocList.setTrimAddress(attrValue.getString(
                                    dbsLibrarySession).substring(0,51)+"...");  
                    }else{
                      folderDocList.setTrimAddress(attrValue.getString(
                                    dbsLibrarySession));
                    }
                  
                  }
  
                  attrValue = dbsDocument.getAttribute("PHONE1");
                  if(attrValue!= null){
                    
                    logger.debug("PHONE: "
                      +attrValue.getString(dbsLibrarySession));
                    
                    folderDocList.setPhone1(attrValue.getString(dbsLibrarySession));
                    
                  }else{
                    logger.debug("PHONE not available ");
                    folderDocList.setPhone1("N/A");
                  }
              }catch( DbsException dex ){
                  isToBeListed = false;
                  folderDocList = null;
                  logger.error("DbsException is: "+dex.getMessage());
              }catch( Exception ex ){
                  isToBeListed = false;
                  folderDocList = null;
                  logger.error("Exception is: "+ex.getMessage());
              }
              
          }else if(dbsPublicObject instanceof DbsDocument  &&  
              dbsPublicObject.getClassname().equalsIgnoreCase("TIMESHEET")){
              // if dbsPublicObject is of "TIMESHEET" type,  
              dbsDocument = (DbsDocument)dbsPublicObject;
              try{
                  /* additional code for Timesheet Class Documents */
                  // set NAME1 alongwith limiting display
                  attrValue= dbsDocument.getAttribute("NAME1");
                  if(attrValue!= null){
                    
                    logger.debug("NAME1: "
                      +attrValue.getString(dbsLibrarySession));
                      
                    if( attrValue.getString(dbsLibrarySession).length() >=35 ){
                      folderDocList.setName1(attrValue.getString(
                                    dbsLibrarySession).substring(0,31)+"...");  
                    }else{
                      folderDocList.setName1(attrValue.getString(
                                    dbsLibrarySession));
                    }
                  }
  
                  // set CSV alongwith limiting display
                  attrValue= dbsDocument.getAttribute("CSV");
                  if(attrValue!= null){
                    
                    logger.debug("CSV: "
                      +attrValue.getString(dbsLibrarySession));
                      
                    if( attrValue.getString(dbsLibrarySession).length() >=75 ){
                      folderDocList.setTrimCSV(attrValue.getString(
                                    dbsLibrarySession).substring(0,71)+"...");
                    }else{
                      folderDocList.setTrimCSV(attrValue.getString(
                                    dbsLibrarySession));
                    }
                    folderDocList.setCsv(attrValue.getString(dbsLibrarySession));
                  }
                  
              }catch( DbsException dex ){
                  isToBeListed = false;
                  folderDocList = null;
                  logger.error("DbsException is: "+dex.getMessage());
              }catch( Exception ex ){
                  isToBeListed = false;
                  folderDocList = null;
                  logger.error("Exception is: "+ex.getMessage());
              }
              
          }else{  // if dbsPublicObject is of "PERSONAL" type,
            //isToBeListed = false;
            if( folderDocInfo.getRtsType() == FolderDocInfo.PERSONAL_TYPE && 
                dbsPublicObject instanceof DbsDocument ){
              // set NAME1 alongwith limiting display
              dbsDocument = (DbsDocument)dbsPublicObject;
              if( dbsDocument.getName().length() >= 35 ){
                folderDocList.setName1(dbsDocument.getName().substring(0,31)+"...");  
              }else{
                folderDocList.setName1(dbsDocument.getName());
              }
              try{
                // set modified date of document
                folderDocList.setModifiedDate(dbsDocument.getLastModifyDate().toString());
                // set size and type of document
                double size = dbsDocument.getContentSize();
                size /= 1024;
                size = (Math.round((size * 100))/100.00);
                folderDocList.setSize(new Double(size).toString());
                folderDocList.setType(dbsDocument.getFormat().getMimeType());
              }catch( DbsException dex ){
                  isToBeListed = false;
                  folderDocList = null;
                  logger.error("DbsException is: "+dex.getMessage());
              }catch( Exception ex ){
                  isToBeListed = false;
                  folderDocList = null;
                  logger.error("Exception is: "+ex.getMessage());
              }
              
            }
          }
        }else{  // if dbsPublicObject is of "FOLDER" type,
          folderDocList.setClassName(DbsFolder.CLASS_NAME);
          // set following as ""
          folderDocList.setAddress("");
          folderDocList.setCsv("");
          folderDocList.setEmail("");
          folderDocList.setTrimAddress("");
          folderDocList.setTrimEmail("");
          folderDocList.setTrimCSV("");
          // set NAME1 alongwith limiting display
          if( dbsPublicObject.getName().length() >=20 ){
            folderDocList.setName1(dbsPublicObject.getName().substring(0,16)+"...");            
          }else{
            folderDocList.setName1(dbsPublicObject.getName());
          }
          // set number of folder items
          folderDocList.setItem(((DbsFolder)dbsPublicObject).getItemCount());
        }
        if( isToBeListed ){
          folderDocLists.add(folderDocList);
        }
    }
}


  
}
    /**
     * Purpose  : to upload dbsDocument 
     * @param   : formFile - FormFile object which contains the uploaded files
     * @param   : currentFolderId - id if the folder under which new dbsDocument will be uploaded
     * @returns : void
     * @throws  : DbsException - if operation fails
     */
    public void uploadDocument(FormFile formFile ,String currentFolderPath, String docDesc) throws DbsException , IOException{
        DbsFileSystem dbsFileSystem = null;
        DbsDocument dbsDocument = null;
        try{
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            if(!formFile.getFileName().equals("")){
                // code for uploading document
                dbsDocument = dbsFileSystem.putDocument(formFile.getFileName(),
                              formFile.getInputStream(),currentFolderPath,null);
                logger.debug("InputStream closed");
                dbsDocument.setDescription(docDesc);
                logger.info("DbsDocument : " + dbsDocument.getName() );
                logger.debug("DbsDocument format is: "+ 
                              dbsDocument.getFormat().getName());
            }
        }catch(DbsException dbsException){
            throw dbsException;
        }catch(IOException io){
            throw io;

        }
    }

    /**
     * Purpose  : to rename folder 
     * @param   : id - id of the DbsPublicObject to rename
     * @param   : name - new name of the DbsPublicObject to rename
     * @returns : void
     * @throws  : DbsException - if operation fails
     */
    public void renameFolderDoc(Long id[],String[] name ,String[] descs,int rtsType,
                                Treeview treeview ) throws DbsException, 
      Exception, ExceptionBean {
      
        DbsFileSystem dbsFileSystem = null;
        DbsPublicObject dbsPublicObject = null;
        DbsFolder dbsFolder = null;
        Long parentId = null;
        String oldName = null;
        String newName = null;
        String newDesc = null;
        String appendThis = null;
        try{
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            // this is to account for description
            if( rtsType == 1 || rtsType == 3 ){
              appendThis = "R";
            }else if( rtsType == 2 || rtsType == 4 ){
              appendThis = "T";
            }else{
              appendThis = "P";
            }
            // while renaming the folder,take care to capitulate the first letter
            // for description, take care to append suitable prefix
            for(int counter = 0 ; counter < id.length ; counter ++){
                dbsPublicObject = dbsFileSystem.findPublicObjectById(id[counter]);
                logger.debug("dbsPublicObjectId: "+dbsPublicObject.getId());
                logger.debug("id["+counter+"] "+id[counter]);
                oldName = dbsPublicObject.getName();
                newName = name[counter];
                newDesc = appendThis+descs[counter];
                newName = getCapitulatedName(newName);
                dbsPublicObject.setName(newName);
                dbsPublicObject.setDescription(newDesc);
                // update treeview , if any
                if( treeview != null ){
                  if (dbsPublicObject instanceof DbsFolder){
                      dbsFolder = (DbsFolder)dbsLibrarySession.getPublicObject(id[counter]);
                      treeview.ifFolderRenamed(id[counter]);
                  }                
                }
            }
        }catch(DbsException dbsException){
            if(dbsException.getErrorCode() == 30041){
                replacementValues[0] = oldName;
                dbsException.setMessageKey("errors.30041.folderdoc.rename.denied",replacementValues);
            }
            throw dbsException;
        }
    }

    private String getCapitulatedName( String drawerName ){
      
      StringBuffer folderNameToCapitulate = new StringBuffer(drawerName.trim()); 
      logger.debug("folderName : " + folderNameToCapitulate);
      char toBeCapitulated = '0';
      toBeCapitulated = folderNameToCapitulate.charAt(0);
      boolean isALowerCaseAlphabet = true;
      int charReplacementIndex = 0;       /* index of the char to be replaced */
      
      /* loop through folderNameToCapitulate to obtain the first occurrence
       * of a lowercase aplhabet and break if one such occurrence is found.
       * if char is anything other than the aplhabet set , 
       * set isALowerCaseAlphabet = false */
      for( int index = 0; index < folderNameToCapitulate.length(); index++){ 
        toBeCapitulated = folderNameToCapitulate.charAt(index);
        if( ( toBeCapitulated < 97 ) || 
            ( toBeCapitulated >= 123 && toBeCapitulated <= 127)){
            
          isALowerCaseAlphabet = false;
          
        }else{
          charReplacementIndex = index;   /* set charReplacementIndex */
          break;
        }
      }
      /* if !isALowerCaseAlphabet, then the char for replacement is none
       * else, replace the char indexed by charReplacementIndex */
      if( !isALowerCaseAlphabet ){
      
      }else{  /* obtain the char to be capitulated */
        toBeCapitulated = folderNameToCapitulate.charAt(charReplacementIndex);
      }
      logger.debug("toBeCapitulated : "+toBeCapitulated);
      
      /* delete the char and insert the upperCase counterpart .*/
      if(isALowerCaseAlphabet){
        char [] charArray = new char[1];
        charArray[0] = toBeCapitulated;
        
        //logger.debug("charArray length: "+charArray.length);
        char capitulatedChar = new String(charArray).toUpperCase().charAt(0);
        logger.debug("capitulatedChar : "+capitulatedChar);
        
        folderNameToCapitulate = folderNameToCapitulate.deleteCharAt(
                                                          charReplacementIndex);
        folderNameToCapitulate = folderNameToCapitulate.insert(
                                          charReplacementIndex,capitulatedChar);                                                  
                                                
      }
      logger.debug("FolderName after capitulation: "+folderNameToCapitulate);
      String folderName = folderNameToCapitulate.toString();
      folderNameToCapitulate = null;
      return folderName;
    }
    /**
     * Purpose : To delete folder and dbsDocument
     * @param  : folderDocInfo,to obtain current folder Id
     * @param  : array of Long id
     * @param  : boolean recursively,for recursive deletion
     * @param  : treeview,to be refreshed
     * @return : void
     */    

    public void deleteFolderDoc(FolderDocInfo folderDocInfo,Long id[], 
      boolean recursively,Treeview treeview)throws DbsException,Exception{
        
        DbsFileSystem dbsFileSystem = null;
        DbsPublicObject dbsPublicObject = null;
        Long parentId = null;
        String folderDocName = "";
        String className;
        DbsFolder targetFolder = null;
        
        try{
            dbsTransaction = dbsLibrarySession.beginTransaction();
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            // loop through to delete POs.                            
            for(int counter = 0 ; counter < id.length ; counter ++){
                dbsPublicObject = dbsFileSystem.findPublicObjectById(id[counter]);
                /* find target folder. The reason for using getFolderReferences()
                   is to deal with links,which can be at multiple locations. */ 
                targetFolder = dbsPublicObject.getFolderReferences(0);
                
                logger.debug("targetFolderName: "+targetFolder.getName()+"Path: "
                              +targetFolder.getAnyFolderPath());
                              
                className = dbsPublicObject.getClassname();
                folderDocName = dbsPublicObject.getName();
                logger.info("DbsFolder Or DbsDocument Name : "
                  + dbsPublicObject.getAnyFolderPath() + "/" + folderDocName);
                /* for FOLDER type, remove Folder Relationship.*/  
                if (className.equalsIgnoreCase(DbsFolder.CLASS_NAME)){
                    parentId = dbsPublicObject.getFolderReferences()[0].getId();
                    dbsFileSystem.removeFolderRelationship(targetFolder,
                                                          dbsPublicObject,recursively);
                    /* update treeview , if any */
                    if( treeview !=null ){
                      treeview.ifFolderDeleted(id[counter],parentId);
                    }
                }
                /* for "RESUME1", "TIMESHEET","DOCUMENT" types,remove Folder 
                 * Relationship. This will take care of links, since deletion
                 * of one link will not affect existence of the others */
                if(className.equalsIgnoreCase("Resume1") || 
                  className.equalsIgnoreCase("TIMESHEET") ||
                  className.equals(DbsDocument.CLASS_NAME)){
                    
                  if( folderDocInfo.getListingType() == 
                      FolderDocInfo.SIMPLE_LISTING ){
                    targetFolder = (DbsFolder)dbsFileSystem.findPublicObjectById(
                                            folderDocInfo.getCurrentFolderId());
                    dbsFileSystem.removeFolderRelationship(targetFolder,
                                                          dbsPublicObject,false);
                  }else{
                    dbsFileSystem.delete(dbsPublicObject);
                  }
                }
                if(className.equalsIgnoreCase(DbsFamily.CLASS_NAME)){
                  if( folderDocInfo.getListingType() == 
                      FolderDocInfo.SIMPLE_LISTING ){
                    targetFolder = (DbsFolder)dbsFileSystem.findPublicObjectById(
                                              folderDocInfo.getCurrentFolderId());
                    
                    dbsFileSystem.removeFolderRelationship(targetFolder,
                                                          dbsPublicObject,false);
                  }else{    
                  /* for search listing, use delete to remove all instances
                   * links existing. For documents,it is the same as remove
                   * folder relationships.*/
                    dbsFileSystem.delete(dbsPublicObject);
                  }
                }
            }
            dbsLibrarySession.completeTransaction(dbsTransaction);
            dbsTransaction = null;
        }catch(DbsException dbsException){
        
            if(dbsException.getErrorCode() == 30033){
                
                replacementValues[0] = folderDocName;
                
                dbsException.setMessageKey("errors.30033.folderdoc.delete.denied",
                replacementValues);
                
            }
            throw dbsException;
        }finally{
            if(dbsTransaction != null){
                dbsLibrarySession.abortTransaction(dbsTransaction);
            }
        }
    }
  
    /**
     * Purpose  : to create new folder 
     * @param   : folderName - name of the folder to create
     * @param   : currentFolderId - folderId under which new folder is to be created 
     * @param   : treeview - to refresh the treeview  
     * @returns : void
     * @throws  : DbsException - if operation fails
     */
    public void newFolder(String folderName,String folderDesc,Long  
           currentFolderId,Treeview treeview )throws DbsException,Exception{  
        
      DbsFileSystem dbsFileSystem = null;
      DbsFolder dbsFolder = null;
      try{
        // create new folder and capitulate first letter
        dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
        dbsFolder = (DbsFolder)dbsFileSystem.findPublicObjectById(currentFolderId);
        folderName = getCapitulatedName(folderName);
        dbsFolder = dbsFileSystem.createFolder(folderName,dbsFolder,true,null);
        // set description
        dbsFolder.setDescription(folderDesc);
        this.setFolderWhenAdded(dbsFolder);
        // update treeview , if any
        if(treeview!=null){
          treeview.ifFolderAdded(dbsFolder.getId(),currentFolderId);
        }
      }catch(DbsException dbsException){
//            DbsException dex = new DbsException(ex);
        if(dbsException.getErrorCode() == 30002){
          replacementValues[0] = folderName;
          dbsException.setMessageKey("errors.30002.folderdoc.newFolder.denied",
                                      replacementValues);
        }else{
          if(dbsException.containsErrorCode(68005)){
            replacementValues[0] = folderName;
            dbsException.setMessageKey("errors.68005.folderdoc.folderexist",
                                        replacementValues);
          }
        }
        throw dbsException;
      }
    }

    /**
     * Purpose  : to copy folder or dbsDocument 
     * @param   : targetFolderId - target folder id
     * @param   : folderDocIds - list of folder and dbsDocument
     * @param   : overwrite -  whether to overwrite the folder and dbsDocument 
     *            for detail refer cmsdk IfsFilesystem.copy() function
     * @param   : treeview - to refresh the treeview  
     * @returns : void
     * @throws  : DbsException - if operation fails
     */

    public void copy(Long targetFolderId, Long[] folderDocIds, Boolean overwrite,
      Treeview treeview ) throws DbsException ,IOException ,Exception{
        
        DbsFileSystem dbsFileSystem = null;
        DbsDocument dbsDocument = null;
        DbsPublicObject dbsPublicObject = null;
        DbsPublicObject newPublicObject = null;
        DbsFolder targetFolder = null;
        boolean docExists = true; /* boolean variable added for checking existence of dbsDocument*/

        try{
            
            logger.debug("Entering FolderDoc.copy() with overwrite set to: "
              +overwrite.booleanValue());
              
            dbsTransaction = dbsLibrarySession.beginTransaction();
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            // find target folder
            targetFolder = (DbsFolder)
                              dbsLibrarySession.getPublicObject(targetFolderId);
            /* loop through for copying POs */
            for (int counter = 0; counter < folderDocIds.length; counter++){
            
              dbsPublicObject = dbsLibrarySession.getPublicObject(
                                                        folderDocIds[counter]);
              
                /* perform doc copy operation */
                newPublicObject=dbsFileSystem.copy(dbsPublicObject,targetFolder,
                  null,overwrite.booleanValue());

              logger.debug("newPublicObject Name: "+newPublicObject.getName());
              logger.debug("newPublicObject Id: "+newPublicObject.getId());
              logger.debug("dbsPublicObject Id: "+dbsPublicObject.getId());
              /* update treeview , if any */
              if( treeview != null ){
                if(newPublicObject.getResolvedPublicObject() instanceof DbsFolder){    
                    logger.info("DbsFolder : " + newPublicObject.getName() );
                    treeview.ifFolderAdded(newPublicObject.getId(),targetFolderId);
                }
              }  
            }
            
            dbsLibrarySession.completeTransaction(dbsTransaction);
            dbsTransaction = null;
            
        }catch(DbsException dbsException){
            logger.debug("DbsException: "+dbsException.getMessage());
            throw dbsException;
        }finally{
            if(dbsTransaction != null){
                logger.debug("Transaction aborted... in finally clause");
                dbsLibrarySession.abortTransaction(dbsTransaction);
            }
        }
    }

    /**
     * Purpose  : to move folder or dbsDocument 
     * @param   : targetFolderId - target folder id
     * @param   : folderDocIds - list of folder and dbsDocument
     * @param   : overwrite -  whether to overwrite the folder and dbsDocument 
     *            for detail refer cmsdk IfsFilesystem.move() function
     * @param   : treeview - to refresh the treeview  
     * @returns : void
     * @throws  : DbsException - if operation fails
     */

    public void move(Long targetFolderId, Long[] folderDocIds, Boolean overwrite,
      Treeview treeview ) throws DbsException , IOException,Exception{
        
      DbsFileSystem dbsFileSystem = null;
      DbsDocument dbsDocument = null;
      DbsPublicObject dbsPublicObject = null;
      DbsPublicObject newPublicObject = null;
      DbsFolder targetFolder = null;
      DbsFolder sourceFolder = null;
      Long sourceFolderId = null;
      
      try{
          
        dbsTransaction = dbsLibrarySession.beginTransaction();
        dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
        // find target folder
        targetFolder = (DbsFolder)
                          dbsLibrarySession.getPublicObject(targetFolderId);
        // find source folder
        sourceFolder = (DbsFolder)
            dbsLibrarySession.getPublicObject(folderDocIds[0]).getFolderReferences(0);
        
        sourceFolderId = sourceFolder.getId();
        /* loop through for moving POs */
        for (int counter = 0; counter < folderDocIds.length; counter++){
        
          dbsPublicObject = dbsLibrarySession.getPublicObject(
                                                    folderDocIds[counter]);
          /* perform doc move operation */
          newPublicObject=dbsFileSystem.move(sourceFolder, targetFolder, 
                            dbsPublicObject,null,null,overwrite.booleanValue());
          
          if (newPublicObject.getResolvedPublicObject() instanceof DbsFolder){
            logger.info("DbsFolder : " + newPublicObject.getName() );
            /* update treeview , if any */
            if( treeview !=null ){
              treeview.ifFolderDeleted(folderDocIds[counter],sourceFolderId);
              treeview.ifFolderAdded(newPublicObject.getId(),targetFolderId);
            }
          }else{
            if (newPublicObject.getResolvedPublicObject() instanceof DbsDocument){
              logger.info("DbsDocument : " + newPublicObject.getName() );
              logger.debug("dbsPublicObject Path: "
                  +dbsPublicObject.getResolvedPublicObject().getClass());
            }
          }
        }
            
        dbsLibrarySession.completeTransaction(dbsTransaction);
        dbsTransaction = null;
          
      }catch(DbsException dbsException){
        throw dbsException;
      }finally{
        if(dbsTransaction!=null){
            dbsLibrarySession.abortTransaction(dbsTransaction);
        }
      }
    }


    /**
     * Purpose  : to link a PO within another folder
     * @param   : targetFolderId - target folder id
     * @param   : folderDocIds - list of folder and dbsDocument
     * @param   : overwrite -  whether to overwrite the folder and dbsDocument 
     *            for detail refer cmsdk IfsFilesystem.copy() function
     * @param   : treeview - to refresh the treeview  
     * @returns : void
     * @throws  : DbsException - if operation fails
     */

    public void linkPOs(Long targetFolderId, Long[] folderDocIds) 
      throws DbsException,IOException,Exception,ExceptionBean{
        
        DbsFileSystem dbsFileSystem = null;
        DbsDocument dbsDocument = null;
        DbsPublicObject dbsPublicObject = null;
        DbsPublicObject newPublicObject = null;
        DbsFolder targetFolder = null;
        int counter = 0;
        
        try{
            
            logger.debug("Entering FolderDoc.linkPOs() ");
              
            dbsTransaction = dbsLibrarySession.beginTransaction();
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            // find target folder
            targetFolder = (DbsFolder)
                              dbsLibrarySession.getPublicObject(targetFolderId);
            /* loop through for linking POs */
            for (counter = 0; counter < folderDocIds.length; counter++){
            
              dbsPublicObject = dbsLibrarySession.getPublicObject(
                                                        folderDocIds[counter]);
              /* perform linking operation */
              dbsFileSystem.addFolderRelationship(targetFolder,dbsPublicObject);
            }
            
            dbsLibrarySession.completeTransaction(dbsTransaction);
            dbsTransaction = null;
            
        }catch(DbsException dbsException){
            logger.debug("DbsException: "+dbsException.getMessage());
            if( dbsException.containsErrorCode(68010) ){
              replacementValues[0] = dbsPublicObject.getName();
              replacementValues[1] = targetFolder.getName();
              ExceptionBean exceptionBean = new ExceptionBean(dbsException);
              exceptionBean.setMessageKey("errors.68010.folderdoc.itemexist",
                                          replacementValues);
              throw exceptionBean;
            }else{
              throw dbsException;
            }
        }finally{
            if(dbsTransaction != null){
                logger.debug("Transaction aborted... in finally clause");
                dbsLibrarySession.abortTransaction(dbsTransaction);
            }
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale newLocale) {
        locale = newLocale;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger newLogger) {
        logger = newLogger;
    }

    /**
     * Purpose  : to apply dbsAccessControlList on folder and dbsDocument and 
     *            log event in dbsDocument attribute namely, "AUDIT_LOG"
     * @param   : aclId - dbsAccessControlList Id to be applied
     * @param   : recursively - whether to apply recursively
     * @returns : void
     * @throws  : DbsException - if operation fails
     */
    /*
    public void applyAcl(Long[] folderDocIds,Long aclId,boolean recursively) 
      throws DbsException , IOException{
        
        DbsAccessControlList  newAccessControlList = null;
        String newAclName = null;
        String oldAclName = null;
        DbsPublicObject dbsPublicObject = null;
        DbsPublicObject[] items = null;
        DbsFolder dbsFolder;
        SearchUtil searchUtil = null;
        
        try{
            newAccessControlList = (DbsAccessControlList)
                                      dbsLibrarySession.getPublicObject(aclId);
                                      
            newAclName = newAccessControlList.getName();
            logger.info("New ACL Name : " + newAclName);
            
            for(int index = 0; index < folderDocIds.length ; index++){
                dbsPublicObject = dbsLibrarySession.getPublicObject(folderDocIds[index]);
                logger.info("DbsFolder Or Doc Name : " + dbsPublicObject.getName());
                oldAclName = dbsPublicObject.getAcl().getName();
                dbsPublicObject.setAcl(newAccessControlList);
                
                if(dbsPublicObject instanceof DbsFolder){
                    if (recursively == true){
                        
                        changeFolderAclRecursively((DbsFolder)dbsPublicObject,
                                                    newAccessControlList);
                        
                    }
                }
            }
        }catch(DbsException dbsException){
            if(dbsException.getErrorCode() == 30041){
                
                replacementValues[0] = dbsPublicObject.getName();
                
                dbsException.setMessageKey("errors.30041.folderdoc.insufficient.access",  
                  replacementValues);
                  
            }        
            throw dbsException;
        }
    }*/

    //to change dbsAccessControlList of a folder recursively
    public void changeFolderAclRecursively(DbsFolder top, 
                 DbsAccessControlList dbsAccessControlList) throws DbsException {
        // change the ACL of the specified folder,
        // and set all of the items in the folder to that same ACL
        top.setAcl(dbsAccessControlList);
        DbsPublicObject[] items = top.getItems();
        int length = (items == null) ? 0 : items.length;
        for (int i = 0; i < length; i++){
        
            logger.info("Public Object Name : " + items[i].getName());
            // if the item is a folder, call this same method recursively
            if (items[i] instanceof DbsFolder)	{
            
                DbsFolder f = (DbsFolder)items[i];
                changeFolderAclRecursively(f, dbsAccessControlList);
                
            }else{
            
                // simply change the item's ACL
                String oldAclName = items[i].getAcl().getName();
                if( items[i] instanceof DbsFamily ){
                  DbsFamily dbsFam = (DbsFamily)items[i];
                  DbsVersionSeries primaryVersionSeries = dbsFam.getPrimaryVersionSeries();
                  DbsVersionDescription lastVd = primaryVersionSeries.getLastVersionDescription();
                  dbsFam.setAcl(dbsAccessControlList);
                  primaryVersionSeries.setAcl(dbsAccessControlList);
                  lastVd.setAcl(dbsAccessControlList);
                  dbsFam.setDescription(dbsFam.getResolvedPublicObject().getDescription());
                }
                items[i].setAcl(dbsAccessControlList);
               
            }
            
        }
        
    }

   /**
     * Purpose  : to update a versioned document format  
     * @param   : familyId - id of the family
     * @returns : void
     * @throws  : DbsException,Exception - if operation fails
     */

    public void updateFormat(Long familyId) throws DbsException, Exception{
        DbsFileSystem dbsFileSystem = null;
        DbsPublicObject dbsPublicObject = null;
        DbsDocument dbsDocument=null;
        DbsVersionSeries dbsVersionSeries;
        DbsVersionDescription[] dbsVersionDescriptions; 
        DbsFamily dbsFamily;
        
        try{
          //
            dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
            dbsPublicObject = dbsFileSystem.findPublicObjectById(familyId);            
            dbsFamily = dbsPublicObject.getFamily();//
            if(dbsFamily != null){
             //System.out.println(0);
              dbsVersionSeries = dbsFamily.getPrimaryVersionSeries();
              //System.out.println(1);
              dbsVersionDescriptions = dbsVersionSeries.getVersionDescriptions();
              //System.out.println(2);
              dbsPublicObject = dbsPublicObject.getResolvedPublicObject();
              //System.out.println(3);
              dbsDocument = (DbsDocument)dbsPublicObject;
              //System.out.println(4);
              
              DbsFormat dbsFormat = dbsDocument.getFormat();
              //System.out.println(5);
              if(dbsFormat==null){
                
                DbsPublicObject dbsPreviousPublicObject = 
                        dbsVersionDescriptions[dbsVersionDescriptions.length - 2].getDbsPublicObject();
                        
                //System.out.println(6);
                DbsDocument dbsPreviousDocument = (DbsDocument)dbsPreviousPublicObject;
                //System.out.println(7);
                DbsFormat dbsPreviousFormat = dbsPreviousDocument.getFormat();
                //System.out.println(8);
                dbsDocument.setFormat(dbsPreviousFormat);
                //System.out.println(9);
              }
            }
            
            //////////////////////////////////////
            
           /* IfsFileSystem dbsFileSystem = null;
            PublicObject dbsPublicObject = null;
            Document dbsDocument=null;
            VersionSeries dbsVersionSeries;
            VersionDescription[] dbsVersionDescriptions; 
            dbsFileSystem = new IfsFileSystem(dbsLibrarySession.getLibrarySession());
            dbsPublicObject = dbsFileSystem.findPublicObjectById(familyId);
            Family dbsFamily;
            dbsFamily = dbsPublicObject.getFamily();//
            dbsVersionSeries = dbsFamily.getPrimaryVersionSeries();
            System.out.println(1);
            dbsVersionDescriptions = dbsVersionSeries.getVersionDescriptions();
            System.out.println(2);
            dbsPublicObject = dbsPublicObject.getResolvedPublicObject();
            System.out.println(3);
            dbsDocument = (Document)dbsPublicObject;
            System.out.println(4);
            System.out.println(dbsDocument.getId());
            Format dbsFormat = dbsDocument.getFormat();
            System.out.println(5);
            if(dbsFormat==null){
              PublicObject dbsPreviousPublicObject = (dbsVersionDescriptions[dbsVersionDescriptions.length - 2].getPublicObject());
              System.out.println(6);
              Document dbsPreviousDocument = (Document)dbsPreviousPublicObject;
               System.out.println(dbsPreviousDocument.getId());
              System.out.println(7);
              Format dbsPreviousFormat = dbsPreviousDocument.getFormat();
              System.out.println(dbsPreviousFormat.getMimeType());
              dbsDocument.setFormat(dbsPreviousFormat);
             
              System.out.println(9);
            }
            
            */
            //////////////////////////////////////
          //}
        }catch(Exception dbsException){
          dbsException.printStackTrace();
          throw dbsException;
        }
}

  public int getDocumentCount() {
    return documentCount;
  }

  public int getDrawerCount() {
    return drawerCount;
  }

  public ArrayList getPublicObjectFoundLists() {
    return publicObjectFoundLists;
  }

  public int getPublicObjectFoundListsSize() {
    return publicObjectFoundLists.size();
  }

/**
   * Purpose: To obtain drawer list during navigation
   * @throws rts.beans.DbsException
   * @return  drawer list
   * @param listingType
   * @param recordsPerPage
   * @param setNo
   */
  public ArrayList getNextDrawerList( ArrayList pOLists ,
          int setNo, int recordsPerPage,byte listingType ) throws DbsException{
    
      int index = 0;
      ArrayList drawerLists = new ArrayList();
      recordsPerPage = 10;
      int startIndex = ( setNo == 0 )?0:(setNo-1)*recordsPerPage;
      int endIndex = startIndex + recordsPerPage -1;

      setDrawerCount(pOLists);

      if( endIndex >= drawerCount-1 )
        endIndex = drawerCount-1;
        
      logger.debug("startIndex: "+startIndex);
      logger.debug("endIndex: "+endIndex);    
    
      for( index=startIndex ; index <= endIndex ; index++ ){
        
        DbsPublicObject dbsPO=(DbsPublicObject)pOLists.get(index);
                                
        FolderDocList folderDocList = new FolderDocList();
        
        if( dbsPO instanceof DbsFolder ){

          folderDocList.setName(dbsPO.getName());

          folderDocList.setPath(dbsPO.getAnyFolderPath());

          folderDocList.setId(dbsPO.getId());
          
          drawerLists.add(folderDocList);
          
        }else{
        
          folderDocList = null;
          
          break;
          
        }
        
      }
    
      logger.debug("drawerLists.size: "+drawerLists.size());
      
      return drawerLists;
    
  }


  public ArrayList getDrawers() throws DbsException{
    
      int index = 0;
      ArrayList drawerLists = new ArrayList();
      ArrayList poFoundLists=getPublicObjectFoundLists();
      int endIndex = drawerCount-1;
    
      for( index=0 ; index <= endIndex ; index++ ){
        
        DbsPublicObject dbsPo=(DbsPublicObject)poFoundLists.get(index);
        logger.debug("Name: " +dbsPo.getName() );                        
        FolderDocList folderDocList = new FolderDocList();
        
        if( dbsPo instanceof DbsFolder ){

          folderDocList.setName(dbsPo.getName());

          folderDocList.setPath(dbsPo.getAnyFolderPath());

          folderDocList.setId(dbsPo.getId());
          
          drawerLists.add(folderDocList);
          
        }else{
        
          folderDocList = null;
          
          break;
          
        }
        
      }
    
      logger.debug("drawerLists.size: "+drawerLists.size());
      
      return drawerLists;
    
  }


  private void setDrawerCount( ArrayList poFoundLists ) 
    throws DbsException{
      
      DbsPublicObject dbsPO = null;
      int itemCount = poFoundLists.size();
      int folderCount = 0;
      
      logger.debug("itemCount: "+itemCount);

      for( int index =0; index<itemCount; index++ ){
        
        dbsPO = (DbsPublicObject)poFoundLists.get(index);
        
        if( dbsPO instanceof DbsFolder ){
          
          folderCount++;
          
        }else{
          
          break;
          
        }
        
      }
      
      drawerCount = folderCount;
      
      logger.debug("drawerCount: "+drawerCount);
      
    }


/**
 * Purpose : To obtain a string of emailIds(if any) within a folder specified
 * @return : Return a String containing emailIds.
 */    

  public String getEmailIds( ArrayList docLists )
    throws DbsException{
      
      StringBuffer emailIdsBuf = new StringBuffer();
      int index = 0;

        FolderDocList folderDocList = (FolderDocList)docLists.get(index);
  
        emailIdsBuf.append(folderDocList.getEmail());
        
        for(  index=1; index<docLists.size(); index++ ){
        
          emailIdsBuf.append(",");
          
          folderDocList = (FolderDocList)docLists.get(index);
  
          emailIdsBuf.append(folderDocList.getEmail());
          
        }
        
        logger.debug("emailIdsBuf.length(): "+emailIdsBuf.length());
        String emailIds = emailIdsBuf.toString();
        emailIds.trim();
        logger.debug("emailIds: "+emailIds);
        return emailIds; 

    }
    
//--------------------------------------------------------------------------------

    /**
     * Purpose : To create collection of FolderDocListForm object
     * @return : Return a collection of FolderDocListForm object
     */    
    public ArrayList getDrawerList(Long currentFolderId,
      FolderDocInfo folderDocInfo,UserPreferences userPreferences ) throws DbsException{
      
        //Variable Declaration
        ArrayList drawerListings=new ArrayList();
        DbsFolder dbsFolder;

        List searchQualificationList = new ArrayList();
        
        try{
            
            DbsAttributeSearchSpecification dbsAttributeSearchSpecification = 
              new DbsAttributeSearchSpecification();
              
            DbsSearchSortSpecification dbsSearchSortSpecification = 
              new DbsSearchSortSpecification();
              
            DbsFolderRestrictQualification dbsFolderRestrictQualification = 
              new DbsFolderRestrictQualification();
              
            DbsSearchClassSpecification dbsSearchClassSpecification = 
              new DbsSearchClassSpecification();
            
            dbsFolder = (DbsFolder)
              (new DbsFileSystem(dbsLibrarySession).findPublicObjectById((currentFolderId)));
            
            dbsFolderRestrictQualification.setStartFolder(dbsFolder);
            
            dbsFolderRestrictQualification.setSearchClassname(DbsFolder.CLASS_NAME);
            
            dbsFolderRestrictQualification.setMultiLevel(false);
            
            searchQualificationList.add(dbsFolderRestrictQualification);
            
            // array of class to be searched
            String [] searchClasses = new String[] {DbsFolder.CLASS_NAME};
            dbsSearchClassSpecification.addSearchClasses(searchClasses);
            dbsSearchClassSpecification.addResultClass(DbsFolder.CLASS_NAME);
            
            // Array of classes involved in the order by clause
            String [] sortClasses = new String[] {DbsFolder.CLASS_NAME};
             // Array of Attribute Names to match class names.
            String [] attNames = new String[] {"NAME"};
            // Order of Sort for each sort element
            boolean [] orders = new boolean[] {true};
            // Case insensitive Sort for each sort element
            String [] caseSorts = new String[] {"nls_upper"};
            dbsSearchSortSpecification.add(sortClasses,attNames,orders,caseSorts);
            
            DbsAttributeQualification folderDocIdAttrbQual = new DbsAttributeQualification();
            String searchColumn = "ID";
            folderDocIdAttrbQual.setAttribute(searchColumn);
            folderDocIdAttrbQual.setOperatorType(DbsAttributeQualification.NOT_EQUAL);
            folderDocIdAttrbQual.setValue(DbsAttributeValue.newAttributeValue(folderDocInfo.getCurrentFolderId()));
            searchQualificationList.add(folderDocIdAttrbQual);

            DbsAttributeQualification folderDocDescAttrbQual = new DbsAttributeQualification();
        
            searchColumn = DbsFolder.DESCRIPTION_ATTRIBUTE;
            folderDocDescAttrbQual.setCaseIgnored(true);
            folderDocDescAttrbQual.setAttribute(searchColumn);
            folderDocDescAttrbQual.setOperatorType(DbsAttributeQualification.LIKE);
            /* searchDescription has been modified for Resume Class */
            String searchDescription=null;
          
            //searchDescription = (folderDocInfo.getRtsType() == 3)?"R":"T";
            if( folderDocInfo.getRtsType() == 3){
              searchDescription = "R";
            }else if( folderDocInfo.getRtsType() == 4 ){
              searchDescription = "T";
            }else{
              searchDescription = "P";
            }
            logger.debug("searchDescription: "+searchDescription);
          
            searchDescription = searchDescription.replace('*' , '%');
            searchDescription = searchDescription.replace('?' , '_');
            searchDescription = searchDescription + ('%');
            logger.debug("searchDescription final: "+searchDescription);
            folderDocDescAttrbQual.setValue(searchDescription);
            searchQualificationList.add(folderDocDescAttrbQual);
            
            //And together all the dbsSearch qualifications
            DbsSearchQualification dbsSearchQualification = null;
            Iterator iterator = searchQualificationList.iterator();
            
            while (iterator.hasNext()){
                
                DbsSearchQualification nextSearchQualification = 
                  (DbsSearchQualification) iterator.next();
                  
                if (dbsSearchQualification == null) {
                
                    dbsSearchQualification = nextSearchQualification;
                    
                }else{
                
                    dbsSearchQualification = new DbsSearchClause(
                      dbsSearchQualification, nextSearchQualification, 
                      DbsSearchClause.AND);
                      
                }
            }

            dbsAttributeSearchSpecification.setSearchQualification(dbsSearchQualification);
            dbsAttributeSearchSpecification.setSearchClassSpecification(dbsSearchClassSpecification);
            dbsAttributeSearchSpecification.setSearchSortSpecification(dbsSearchSortSpecification);
            
            DbsSearch dbsSearch = 
              new DbsSearch(dbsLibrarySession,dbsAttributeSearchSpecification);
              
            drawerListings = 
              buildDrawerList(dbsSearch,FolderDocInfo.SIMPLE_LISTING,
                                 folderDocInfo,userPreferences);
              logger.debug("inside getDrawerList");
            for(int index=0; index<drawerListings.size(); index++){
              
              logger.debug(((DrawerList)drawerListings.get(index)).getName());
            }
                                 
        }catch(DbsException dbsException){
            
            throw dbsException;
            
        }
        
        logger.debug("drawerListings.size() : " + drawerListings.size());
        return drawerListings;
    
    }

/**
   * Purpose: build doc list
   * @throws rts.beans.DbsException
   * @return doc list 
   * @param userPreferences
   * @param folderDocInfo
   * @param listingType
   * @param dbsSearch
   */
    public ArrayList buildDrawerList(DbsSearch dbsSearch,byte listingType,
      FolderDocInfo folderDocInfo,UserPreferences userPreferences) 
      throws DbsException{

      int folderCount = 0;
      DbsFolder dbsFolder;
      ArrayList drawerFoundLists=new ArrayList();
      ArrayList publicObjectFoundLists = new ArrayList();
      DrawerList drawerList=null;
      String desc = null;
      try{
        dbsSearch.open();
        int itemCount = dbsSearch.getItemCount();
        
        logger.debug("itemCount: "+itemCount);
        DbsPublicObject dbsPublicObject;
        //String desc = (folderDocInfo.getRtsType() == 3 )?"R":"T";
        if( folderDocInfo.getRtsType() == 3){
          desc = "R";
        }else if( folderDocInfo.getRtsType() == 4 ){
          desc = "T";
        }else{
          desc = "P";
        }
        
        for( int index = 0; index < itemCount; index++ ){
          drawerList= new DrawerList();
          dbsPublicObject = ((DbsPublicObject)dbsSearch.next().getLibraryObject());
          if( dbsPublicObject instanceof DbsFolder ){
            if( dbsPublicObject.getDescription()!=null 
             && dbsPublicObject.getDescription().startsWith(desc) ){
                publicObjectFoundLists.add(dbsPublicObject);
                folderCount++;
              }
            }
          }

          //initialize pageCount to 1
          folderDocInfo.setDrawerPageCount(new Integer(1));

          itemCount = dbsSearch.getItemCount();
          int startIndex, endIndex, pageCount;
          logger.debug("itemCount : " + itemCount);
          if (itemCount > 0 ){
              //set start index and end index of the records to be displayed
              startIndex = (folderDocInfo.getDrawerPageNumber() -1) * userPreferences.getRecordsPerPage();
              logger.debug("startIndex : " + startIndex);
              endIndex = startIndex + userPreferences.getRecordsPerPage();
              logger.debug("endIndex : " + endIndex);
              if(endIndex >= itemCount){
                  endIndex = itemCount;
              }
              logger.debug("endIndex : " + endIndex);
              //find page count 
              pageCount = (int)StrictMath.ceil((double)itemCount / userPreferences.getRecordsPerPage());
              if(pageCount > 0){
                  folderDocInfo.setDrawerPageCount(new Integer(pageCount));
              }

              for(int intCounter = startIndex; intCounter < endIndex; intCounter++){
                dbsPublicObject = (DbsPublicObject)publicObjectFoundLists.get(intCounter);
                drawerList= new DrawerList();
                drawerList.setId(dbsPublicObject.getId());
                
                drawerList.setName(dbsPublicObject.getName());
                drawerList.setDrawerPath(dbsPublicObject.getAnyFolderPath());
                String drawerTempDescription= dbsPublicObject.getDescription();
                String drawerDescription=null;
                if (folderDocInfo.getRtsType()==3){
                  drawerDescription=drawerTempDescription.replaceFirst("R","");
                }else if (folderDocInfo.getRtsType()==4){
                  drawerDescription=drawerTempDescription.replaceFirst("T","");
                }else{
                  drawerDescription=drawerTempDescription.replaceFirst("P","");
                }
                drawerList.setDescription(drawerDescription);
                drawerList.setModifiedDate(dbsPublicObject.getLastModifyDate().toString());
                drawerFoundLists.add(drawerList);
              }
          }

//         logger.debug("folderCount: "+folderCount);
         drawerCount = folderCount;
        
        }catch(DbsException dex){
            logger.debug("Exception stack trace: ");
            dex.printStackTrace();        
            logger.error(dex.getMessage());
        }catch( Exception ex ){
          drawerFoundLists = null;
          logger.error("Exception is: "+ex.getMessage());
      }
        return drawerFoundLists;
    }


/**
 * Purpose : Find size of folders and documents for a given public object id
 * @return : Return TotalSizeFoldersDocs object
 */    

    public TotalSizeFoldersDocs findTotalSizeFoldersDocs(Long publicObjectId) throws DbsException {
        // change the ACL of the specified folder,
        // and set all of the items in the folder to that same ACL
        TotalSizeFoldersDocs total = new TotalSizeFoldersDocs();
        DbsPublicObject dbsPublicObject;
        DbsDocument dbsDocument;
        try{        
            dbsPublicObject = dbsLibrarySession.getPublicObject(publicObjectId);
            
            if(dbsPublicObject.getResolvedPublicObject() instanceof DbsDocument){
                dbsDocument = (DbsDocument)dbsPublicObject.getResolvedPublicObject();
                total.setSize(dbsDocument.getContentSize());
                total.setDocumentCount(1);
                total.setFolderDocCount(1);
            }else{
                if(dbsPublicObject instanceof DbsFolder){            
                    total.setFolderCount(1);
                    total.setFolderDocCount(1);
            
                    DbsFolder topFolder = (DbsFolder)dbsPublicObject;
                    DbsPublicObject[] itemsInTheFolder = topFolder.getItems();
                    if(itemsInTheFolder != null ){
                        for (int index = 0; index < itemsInTheFolder.length; index++){
                            TotalSizeFoldersDocs tempTotal = findTotalSizeFoldersDocs(itemsInTheFolder[index].getId());
                            total.setSize(total.getSize() + tempTotal.getSize());
                            total.setDocumentCount(total.getDocumentCount() + tempTotal.getDocumentCount());
                            total.setFolderCount(total.getFolderCount() + tempTotal.getFolderCount());                        
                            total.setFolderDocCount(total.getFolderDocCount() + tempTotal.getFolderDocCount());
                        }
                    }
                }
            }                
        }catch(DbsException dbsException){
            throw dbsException;
        }
        return total;
    }

  public DbsFolder getFolderWhenAdded() {
    return folderWhenAdded;
  }

  public void setFolderWhenAdded(DbsFolder folderWhenAdded) {
    this.folderWhenAdded = folderWhenAdded;
  }

  public DbsPublicObject findOriginalFolder(DbsRelationship[] rels) throws DbsException {
    DbsPublicObject originalFolder = null;
    DbsRelationship relWithLeastSortSeq = null;
    int numberOfRelationships = rels.length;
    if( numberOfRelationships == 1 ){
      return (rels[0].getLeftObject());
    }
    relWithLeastSortSeq = getLeastSortSequence(rels);
    originalFolder = relWithLeastSortSeq.getLeftObject();
    return originalFolder;
  }

  private DbsRelationship getLeastSortSequence(DbsRelationship[] rels) 
  throws DbsException {
    int numberOfRelationships = rels.length;
    DbsRelationship swapRel = null;
    for( int i = 0 ; i < rels.length ; i++  ){
      for( int j = 1 ; j < rels.length ; j++ ){
        if( rels[i].getSortSequence() > rels[j].getSortSequence() ){
          swapRel = rels[j];
          rels[j] = rels[i];
          rels[i] = swapRel;
        }
      }
    }
    return rels[0];
  }

  public DbsFolder[] getShareFolders( DbsFolder[] folderReferences, 
         Long originalFolderId) throws DbsException{
    
    ArrayList arrayOfFolders = new ArrayList();
    /* add folders to arrayList */
    for( int index = 0; index < folderReferences.length; index++ ){
      arrayOfFolders.add(0,folderReferences[index]);
    }
    /* remove the original folder */
    for( int index = 0; index < arrayOfFolders.size(); index++ ){
      if( ((DbsFolder)arrayOfFolders.get(index)).getId() == originalFolderId ){
        arrayOfFolders.remove(index);
        break;
      }
    }
    /* reconstruct folder[] and return them */
    DbsFolder[] toReturn = new DbsFolder[arrayOfFolders.size()];
    for( int index = 0; index < arrayOfFolders.size(); index++ ){
      toReturn[index] = (DbsFolder)arrayOfFolders.get(index);
    }
    
    return toReturn;
  }
 
 
    
//--------------------------------------------------------------------------------
}