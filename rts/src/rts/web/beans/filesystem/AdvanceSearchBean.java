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
 * $Id: AdvanceSearchBean.java,v 1.20 2005/11/07 10:40:40 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;
/*rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.actions.filesystem.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.utility.*;
import rts.web.beans.user.*;
import rts.web.beans.scheduler.*;
/* Java API */
import java.util.*;
import java.text.*;
/* Logger API */
import org.apache.log4j.*;
/* cmsdk API */
import oracle.ifs.common.*;


public class AdvanceSearchBean { 

  private static String DATE_FORMAT = "MM/dd/yyyy";
  private DbsLibrarySession dbsLibrarySession;
  private DbsPublicObject dbsPublicObject;
  private DbsFolder dbsFolder;
  private Logger logger;
  private Locale locale;
  
  public AdvanceSearchBean(DbsLibrarySession dbsLibrarySession){
    this.dbsLibrarySession = dbsLibrarySession;
    //Initialize logger
    logger = Logger.getLogger("DbsLogger");
    //use default if locale not set
    locale = Locale.getDefault(); //
  }
  
  public DbsSearch getSearchObject(AdvanceSearchForm advanceSearchForm,int rtsType) throws DbsException, ParseException, ExceptionBean{
    ArrayList folderDocCompleteLists= null;   
    DbsSearch dbsSearch = null;
    List publicObjects = null;
    List searchQualificationList = new ArrayList();
    try{
      // To restrict the search to the particular folder
      DbsFolderRestrictQualification folderRestrictQual = null;
      DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);
      DbsFolder dbsSearchFolder = null;
      if (advanceSearchForm.getTxtLookinFolderPath() != null){
        folderRestrictQual = new DbsFolderRestrictQualification();
        dbsSearchFolder = (DbsFolder)dbsFileSystem.findPublicObjectByPath(advanceSearchForm.getTxtLookinFolderPath());
        folderRestrictQual.setStartFolder(dbsSearchFolder);
        folderRestrictQual.setMultiLevel(true);
        folderRestrictQual.setSearchClassname(DbsPublicObject.CLASS_NAME);
        searchQualificationList.add(folderRestrictQual);
      }else{
        ExceptionBean exceptionBean = new ExceptionBean();
        exceptionBean.setMessage("No folder selected for searching");
        exceptionBean.setMessageKey("errors.lookin.folder.not.selected");
        throw exceptionBean;
      }
  
      DbsAttributeQualification folderDocIdAttrbQual = new DbsAttributeQualification();
      String searchColumnId = "ID";
      folderDocIdAttrbQual.setAttribute(searchColumnId);
      folderDocIdAttrbQual.setOperatorType(DbsAttributeQualification.NOT_EQUAL);
      folderDocIdAttrbQual.setValue(DbsAttributeValue.newAttributeValue(dbsSearchFolder.getId()));
      searchQualificationList.add(folderDocIdAttrbQual);
      
  // To build search based on the Attribute like name of the document        
      if (advanceSearchForm.getTxtFolderOrDocName() != null && advanceSearchForm.getTxtFolderOrDocName().trim().length() != 0 )	{
        // To build search based on the Attribute like name of the document
        DbsAttributeQualification folderDocNameAttrbQual = new DbsAttributeQualification();
        String searchColumn = DbsPublicObject.NAME_ATTRIBUTE;
        
        folderDocNameAttrbQual.setCaseIgnored(true);
        folderDocNameAttrbQual.setAttribute(searchColumn);
        folderDocNameAttrbQual.setOperatorType(DbsAttributeQualification.LIKE);
        String searchValue = advanceSearchForm.getTxtFolderOrDocName().trim();
        searchValue = searchValue + ('%');
        searchValue = searchValue.replace('*' , '%');
        searchValue = searchValue.replace('?' , '_');
        logger.debug("searchValue: "+searchValue);
        folderDocNameAttrbQual.setValue(searchValue);
        searchQualificationList.add(folderDocNameAttrbQual);
      }
      
      
      
  
  // To build search based on the Attribute like description of the document
      DbsAttributeQualification folderDocNameAttrbQual = null;
      DbsSearchQualification combinedDescAttrQual = null;
      //DbsAttributeQualification versionDescAttrQual = new DbsAttributeQualification();
      if (advanceSearchForm.getTxtDocDescription() != null && advanceSearchForm.getTxtDocDescription().trim().length() != 0 )	{
          folderDocNameAttrbQual = new DbsAttributeQualification();
          //versionDescAttrQual = new DbsAttributeQualification();
          String searchDescription="%";
          String prefix = null;
          StringTokenizer tokenizer = new StringTokenizer(advanceSearchForm.getTxtDocDescription().trim());
          while (tokenizer.hasMoreTokens()) {
            folderDocNameAttrbQual = new DbsAttributeQualification();
            String currentToken = tokenizer.nextToken();
            String searchColumn = DbsPublicObject.DESCRIPTION_ATTRIBUTE;
            //String searchColForFam = DbsFamily.DESCRIPTION_ATTRIBUTE;
            folderDocNameAttrbQual.setCaseIgnored(true);
            //versionDescAttrQual.setCaseIgnored(true);
            folderDocNameAttrbQual.setAttribute(searchColumn);
            //versionDescAttrQual.setAttribute(searchColForFam);
            folderDocNameAttrbQual.setOperatorType(DbsAttributeQualification.LIKE);
            //versionDescAttrQual.setOperatorType(DbsAttributeQualification.LIKE);
            
            if( rtsType == 1 ){
              prefix = "R";
            }else if( rtsType == 2 ){
              prefix = "T";
            }else{
              prefix = "P";
            }
            //searchDescription = prefix+advanceSearchForm.getTxtDocDescription().trim();
            currentToken = currentToken.replace('*','%');
            currentToken = currentToken.replace('?','_');
            
            searchDescription = prefix + ('%') + currentToken + ('%');
            logger.debug("searchDescription: "+searchDescription);
            folderDocNameAttrbQual.setValue(searchDescription);
            if (combinedDescAttrQual == null) {
                combinedDescAttrQual = folderDocNameAttrbQual;
            }else{
                combinedDescAttrQual = new DbsSearchClause(combinedDescAttrQual, folderDocNameAttrbQual, DbsSearchClause.OR);
            }
            folderDocNameAttrbQual = null;
            
          }  
          //searchDescription = prefix + searchDescription;
          //logger.debug("searchDescription: "+searchDescription);
          
          //versionDescAttrQual.setValue(searchDescription);
          searchQualificationList.add(combinedDescAttrQual);
          //searchQualificationList.add(versionDescAttrQual);
      }
  
  // To build content based search          
      DbsAttributeSearchSpecification attributeSearchSpec = null;
      DbsContextSearchSpecification contextSearchSpec = null;
      if (advanceSearchForm.getTxtContainingText() != null && advanceSearchForm.getTxtContainingText().trim().length() != 0)	{
          // search on content
          DbsContextQualification contextQualification = new DbsContextQualification();
          contextQualification.setQuery(advanceSearchForm.getTxtContainingText().trim());
          searchQualificationList.add(contextQualification);

          // build ContextSearchSpecification
          contextSearchSpec = new DbsContextSearchSpecification();
          contextSearchSpec.setContextClassname(DbsContentObject.CLASS_NAME);
          //attributeSearchSpec = contextSearchSpec;
          String [] classes = new String[] {DbsDocument.CLASS_NAME,DbsContentObject.CLASS_NAME,DbsPublicObject.CLASS_NAME};
          if (folderRestrictQual != null) {
              folderRestrictQual.setSearchClassname(DbsPublicObject.CLASS_NAME);
          }
          DbsSearchClassSpecification searchClassSpec = 	new DbsSearchClassSpecification(classes);
          searchClassSpec.addResultClass(DbsPublicObject.CLASS_NAME);
          //attributeSearchSpec.setSearchClassSpecification(searchClassSpec);
          contextSearchSpec.setSearchClassSpecification(searchClassSpec);

          // join DbsDocument and DbsContentObject
          DbsJoinQualification documentContentJoinQuali = new DbsJoinQualification();
          documentContentJoinQuali.setLeftAttribute(DbsDocument.CLASS_NAME, DbsDocument.CONTENTOBJECT_ATTRIBUTE);
          documentContentJoinQuali.setRightAttribute(DbsContentObject.CLASS_NAME, null);
          searchQualificationList.add(documentContentJoinQuali);

          DbsJoinQualification versionDocJoinQuali = new DbsJoinQualification();
          versionDocJoinQuali.setLeftAttribute(DbsPublicObject.CLASS_NAME, DbsPublicObject.RESOLVEDPUBLICOBJECT_ATTRIBUTE);
          versionDocJoinQuali.setRightAttribute(DbsDocument.CLASS_NAME, null);
          searchQualificationList.add(versionDocJoinQuali);

      }else{
          attributeSearchSpec = new DbsAttributeSearchSpecification();
          DbsSearchClassSpecification searchClassSpec;
          // build AttributeSearchSpecification
          String [] classes = new String[] {DbsPublicObject.CLASS_NAME};
          searchClassSpec = new DbsSearchClassSpecification(classes);
          attributeSearchSpec.setSearchClassSpecification(searchClassSpec);

      }
  
      //And together all the search qualifications
      DbsSearchQualification searchQual = null;
      Iterator iterator = searchQualificationList.iterator();
      while (iterator.hasNext()){
        DbsSearchQualification nextSearchQualification = (DbsSearchQualification) iterator.next();
        if (searchQual == null) {
          searchQual = nextSearchQualification;
        }else{
          searchQual = new DbsSearchClause(searchQual, nextSearchQualification, DbsSearchClause.AND);
        }
      }
      
      if(contextSearchSpec!=null){
          contextSearchSpec.setSearchQualification((DbsSearchClause)searchQual);
          DbsSearchSortSpecification searchSortSpec = new DbsSearchSortSpecification();
          searchSortSpec.add(DbsPublicObject.NAME_ATTRIBUTE, true);
          contextSearchSpec.setSearchSortSpecification(searchSortSpec);
  
          // create and run the search
          dbsSearch = new DbsSearch(dbsLibrarySession, contextSearchSpec);
      }else{
          attributeSearchSpec.setSearchQualification(searchQual);
          DbsSearchSortSpecification searchSortSpec = new DbsSearchSortSpecification();
          searchSortSpec.add(DbsPublicObject.NAME_ATTRIBUTE, true);
          attributeSearchSpec.setSearchSortSpecification(searchSortSpec);
          
          // create and run the search
          dbsSearch = new DbsSearch(dbsLibrarySession, attributeSearchSpec);
      }
      //logger.debug("Query : "+dbsSearch.getSQL());
  }catch(DbsException dbsException){
      throw dbsException;
  }
  return dbsSearch;
  }
  
  
  public DbsSearch getSearchDrawerObjects(AdvanceSearchForm advanceSearchForm,int rtsType) throws DbsException, ParseException, ExceptionBean{
    ArrayList folderDocCompleteLists= null;   
    DbsSearch dbsSearch = null;
    List publicObjects = null;
    List searchQualificationList = new ArrayList();
    try{
      // To restrict the search to the particular folder
      DbsFolderRestrictQualification folderRestrictQual = null;
      DbsFileSystem dbsFileSystem = new DbsFileSystem(dbsLibrarySession);            
      if (advanceSearchForm.getTxtLookinFolderPath() != null){
        folderRestrictQual = new DbsFolderRestrictQualification();
        DbsFolder dbsSearchFolder = (DbsFolder)dbsFileSystem.findPublicObjectByPath(advanceSearchForm.getTxtLookinFolderPath());
        folderRestrictQual.setStartFolder(dbsSearchFolder);
        folderRestrictQual.setMultiLevel(true);
        folderRestrictQual.setSearchClassname(DbsFolder.CLASS_NAME);
        searchQualificationList.add(folderRestrictQual);
      }else{
        ExceptionBean exceptionBean = new ExceptionBean();
        exceptionBean.setMessage("No folder selected for searching");
        exceptionBean.setMessageKey("errors.lookin.folder.not.selected");
        throw exceptionBean;
      }
  
      DbsAttributeQualification folderDocIdAttrbQual = new DbsAttributeQualification();
      String searchColumnId = "ID";
      folderDocIdAttrbQual.setAttribute(searchColumnId);
      folderDocIdAttrbQual.setOperatorType(DbsAttributeQualification.NOT_EQUAL);
      folderDocIdAttrbQual.setValue(DbsAttributeValue.newAttributeValue(advanceSearchForm.getCurrentFolderId()));
      searchQualificationList.add(folderDocIdAttrbQual);
      
  // To build search based on the Attribute like name of the folder        
      if (advanceSearchForm.getTxtFolderOrDocName() != null && advanceSearchForm.getTxtFolderOrDocName().trim().length() != 0 )	{
        // To build search based on the Attribute like name of the document
        DbsAttributeQualification folderDocNameAttrbQual = new DbsAttributeQualification();
        //String searchColumn = "TYPE";
        String searchColumn = DbsFolder.NAME_ATTRIBUTE;
        
        folderDocNameAttrbQual.setCaseIgnored(true);
        folderDocNameAttrbQual.setAttribute(searchColumn);
        folderDocNameAttrbQual.setOperatorType(DbsAttributeQualification.LIKE);
        String searchValue = advanceSearchForm.getTxtFolderOrDocName().trim();
        logger.debug("searchValue: "+searchValue);
        //String searchValue = "RESUME1";
        searchValue = searchValue.replace('*' , '%');
        searchValue = searchValue.replace('?' , '_');
        folderDocNameAttrbQual.setValue(searchValue);
        searchQualificationList.add(folderDocNameAttrbQual);
      }
  
  // To build search based on the Attribute like description of the drawer        
        DbsAttributeQualification folderDocNameAttrbQual =null;
        DbsSearchQualification combinedDescAttrQual = null;
        if (advanceSearchForm.getTxtDocDescription() != null && advanceSearchForm.getTxtDocDescription().trim().length() != 0 )	{
            folderDocNameAttrbQual = new DbsAttributeQualification();
            String searchDescription="%";
            String prefix = null;
            StringTokenizer tokenizer = new StringTokenizer(advanceSearchForm.getTxtDocDescription().trim());
            while (tokenizer.hasMoreTokens()) {
              String currentToken = tokenizer.nextToken();
              String searchColumn = DbsFolder.DESCRIPTION_ATTRIBUTE;
              folderDocNameAttrbQual.setCaseIgnored(true);
              folderDocNameAttrbQual.setAttribute(searchColumn);
              folderDocNameAttrbQual.setOperatorType(DbsAttributeQualification.LIKE);
              
              if( rtsType == 3 ){
                prefix = "R";
              }else if( rtsType == 4 ){
                prefix = "T";
              }else{
                prefix = "P";
              }
  
              //searchDescription = prefix+advanceSearchForm.getTxtDocDescription().trim();
              currentToken = currentToken.replace('*','%');
              currentToken = currentToken.replace('?','_');
              searchDescription = prefix + ('%') + currentToken + ('%');
              logger.debug("searchDescription: "+searchDescription);
              folderDocNameAttrbQual.setValue(searchDescription);
              if (combinedDescAttrQual == null) {
                  combinedDescAttrQual = folderDocNameAttrbQual;
              }else{
                  combinedDescAttrQual = new DbsSearchClause(combinedDescAttrQual, folderDocNameAttrbQual, DbsSearchClause.OR);
              }
              folderDocNameAttrbQual = null;
            }  
            searchQualificationList.add(combinedDescAttrQual);
        }else{
            folderDocNameAttrbQual = new DbsAttributeQualification();
            String searchDescription=null;
            String searchColumn = DbsFolder.DESCRIPTION_ATTRIBUTE;
            folderDocNameAttrbQual.setCaseIgnored(true);
            folderDocNameAttrbQual.setAttribute(searchColumn);
            folderDocNameAttrbQual.setOperatorType(DbsAttributeQualification.LIKE);
            String prefix = null;
            if( rtsType == 3 ){
              prefix = "R";
            }else if( rtsType == 4 ){
              prefix = "T";
            }else{
              prefix = "P";
            }
            
            searchDescription = prefix + ('%');
            logger.debug("searchDescription :" + searchDescription);
            folderDocNameAttrbQual.setValue(searchDescription);
            searchQualificationList.add(folderDocNameAttrbQual);
        }
      DbsAttributeSearchSpecification attributeSearchSpec = null;
      //DbsContextSearchSpecification contextSearchSpec = null;
  
      attributeSearchSpec = new DbsAttributeSearchSpecification();
      DbsSearchClassSpecification searchClassSpec;
  
      String [] classes = new String[] {DbsFolder.CLASS_NAME};
      searchClassSpec = new DbsSearchClassSpecification(classes);
  
      attributeSearchSpec.setSearchClassSpecification(searchClassSpec);                
  
      //And together all the search qualifications
      DbsSearchQualification searchQual = null;
      Iterator iterator = searchQualificationList.iterator();
      while (iterator.hasNext()){
        DbsSearchQualification nextSearchQualification = (DbsSearchQualification) iterator.next();
        if (searchQual == null) {
          searchQual = nextSearchQualification;
        }else{
          searchQual = new DbsSearchClause(searchQual, nextSearchQualification, DbsSearchClause.AND);
        }
      }
      
      attributeSearchSpec.setSearchQualification(searchQual);
      DbsSearchSortSpecification searchSortSpec = new DbsSearchSortSpecification();
      searchSortSpec.add(DbsFolder.NAME_ATTRIBUTE, true);
      attributeSearchSpec.setSearchSortSpecification(searchSortSpec);
      
      // create and run the search
      dbsSearch = new DbsSearch(dbsLibrarySession, attributeSearchSpec);
      
  }catch(DbsException dbsException){
      throw dbsException;
  }
  return dbsSearch;
  }
}