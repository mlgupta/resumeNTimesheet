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
 * $Id: DbsSearch.java,v 1.1.1.1 2005/05/26 12:31:54 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of Search class provided by CMSDK API.
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:   03-3-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
import oracle.ifs.search.*;

public class DbsSearch {
    private Search search=null; // to accept object of type Search
    private SearchSpecification searchSpecification=null; // to accept object of type SearchSpecification
    private SearchResultObject searchResultObject=null; // to accept object of type SearchResultObject
    public DbsSearch() {}

    /**
     * Purpose   : Constructs a DbsSearch object.
     * @param    : dbsSession - LibrarySession in which Search will execute.
     * @param    : dbsSearchSpecification - DbsSearchSpecificationSearchSpecification defining DbsSearch. 
     * @throws   : DbsException - if operation fails
     */
    public DbsSearch(DbsLibrarySession dbsSession,DbsSearchSpecification dbsSearchSpecification) throws DbsException{
        try{
            this.search =new Search(dbsSession.getLibrarySession(),dbsSearchSpecification.getSearchSpecification());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Constructs a Search object.
     * @param    : dbsSession - LibrarySession in which Search will execute.
     * @param    : dbsAttributeSearchSpecification - DbsAttributeSearchSpecification defining DbsSearch. 
     * @throws   : DbsException - if operation fails
     */
     public DbsSearch(DbsLibrarySession dbsSession,DbsAttributeSearchSpecification dbsAttributeSearchSpecification) throws DbsException{
        try{
            this.search =new Search(dbsSession.getLibrarySession(),dbsAttributeSearchSpecification.getAttributeSearchSpecification());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Constructs a Search object.
     * @param    : dbsSession - LibrarySession in which Search will execute.
     * @param    : dbsContextSearchSpecification - DbsContextSearchSpecification defining DbsSearch. 
     * @throws   : DbsException - if operation fails
     */
    public DbsSearch(DbsLibrarySession dbsSession,DbsContextSearchSpecification dbsContextSearchSpecification) throws DbsException{
        try{
            this.search =new Search(dbsSession.getLibrarySession(),dbsContextSearchSpecification.getContextSearchSpecification());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Set the SearchSpecification. If the search was previosly opened, 
     *             exisitng cached items and cursors are closed. The SearchSpecification is cloned. 
     *             Therefore, any changes to it after calling this method will not affect the outcome 
     *             of the search.
     * @param    : dbsSearchSpecification - SearchSpecification defining Search. 
     */
    public void setSearchSpecification(SearchSpecification dbsSearchSpecification) throws DbsException{
        try{
            this.search.setSearchSpecification(dbsSearchSpecification);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns the SearchSpecification of this Search.
     * @returns  : the SearchSpecification
     */
    public DbsSearchSpecification getSearchSpecification() throws DbsException{
        try{
            if(this.search.getSearchSpecification() instanceof AttributeSearchSpecification){
             return new DbsAttributeSearchSpecification((AttributeSearchSpecification)this.search.getSearchSpecification());
            }else if(this.search.getSearchSpecification() instanceof ContextSearchSpecification){
              return new DbsContextSearchSpecification((ContextSearchSpecification)this.search.getSearchSpecification());  
            }else{
              return new DbsSearchSpecification(this.search.getSearchSpecification());
            }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

  /**
	 * Purpose   : Closes the search. You cannot get any more search results after calling this method. 
   *             Closes the database cursor held by the search. 
   *             All open() calls should have matching close() calls.
	 */
    public void close() throws DbsException{
        try{
            this.search.close();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Opens the Search. This executes the Search and sets the cursor to the top of the ResultSet. 
     *             Calls open(null, null). Assumes no bind values and language defaults to the sessions default.
     * @returns  : the SearchSpecification
     */
    public void open() throws DbsException{
        try{
            this.search.open();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Opens the Search. This executes the Search and sets the cursor to the top of the ResultSet.
     *             This allows a specific session language to be used for searches..
     * @param    : langauge - language to be used for search
     */
    public void open(java.lang.String langauge) throws DbsException{
        try{
            this.search.open(langauge);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }        
    }

    /**
     * Purpose   : Opens the Search. This executes the Search and sets the cursor to the top of the ResultSet. 
     *             Users can specify the bind values. Default language will be used. Calls open(bindValues, null).
     * @param    : bindValues - array of late bound values    
     */
    public void open(DbsAttributeValue[] bindValues) throws DbsException{
        
        try{
            if(bindValues!=null && bindValues.length > 0){
              int length=bindValues.length;
              AttributeValue[] attrVal=new AttributeValue[length];
              for(int index=0;index < length ;index++){
                attrVal[index]=bindValues[index].getAttributeValue();
              }            
             this.search.open(attrVal);
           }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
     * Purpose   : Opens the Search. This executes the Search and sets the cursor to the top of the ResultSet.
     *             Users can specify the bind values and the session language.
     * @param    : bindValues - array of late bound values    
     * @param    : language - language to be used for the search
     */
    public void open(DbsAttributeValue[] bindValues,java.lang.String language) throws DbsException{
        try{
            if(bindValues!=null && bindValues.length > 0){
              int length=bindValues.length;
              AttributeValue[] attrVal=new AttributeValue[length];
              for(int index=0;index < length ;index++){
                attrVal[index]=bindValues[index].getAttributeValue();
              }            
             this.search.open(attrVal,language);
           }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
     * Purpose   : Opens the Search. This executes the Search and sets the cursor to the top of the ResultSet.
     *             Users can specify the bind values, session language and timeout value.
     * @param    : bindValues - array of late bound values    
     * @param    : language - language to be used for the search
     * @param    : timeout - search will timeout after this number of seconds
     */
    public void open(DbsAttributeValue[] bindValues,java.lang.String language,int timeout) throws DbsException{
        try{
            if(bindValues!=null && bindValues.length > 0){
              int length=bindValues.length;
              AttributeValue[] attrVal=new AttributeValue[length];
              for(int index=0;index < length ;index++){
                attrVal[index]=bindValues[index].getAttributeValue();
              }            
             this.search.open(attrVal,language,timeout);
           }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
     * Purpose   : Returns the number of rows in search result. Calls getItemCount(null, null). 
     *             Assumes no bind values and language defaults to the sessions default. 
     *             Runs a select count(*) query to get the count. 
     *             This does NOT cache the result. 
     *             Repeated calls to this method in the same search object result in database query everytime. The number of items returned here is not guaranteed to match the number of items found, when the actual search is run. This is because, objects might be created or deleted between the time when getItemCount() is called and the actual search is run by calling open(). When getItemCount() is called on a Search that is setup to remove duplicates, the number returned is the total number of distinct first result class type objects in the result set.
     * @returns  : Number of items in search result set.
     */

    public int getItemCount() throws DbsException{
        try{
            return this.search.getItemCount();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
     * Purpose   : Returns the number of rows in search result. 
     *             This allows a specific session language to be used. 
     *             Runs a select count(*) query to get the count. 
     *             This does NOT cache the result. 
     *             Repeated calls to this method in the same search object result in database 
     *             query everytime. The number of items returned here is not guaranteed to 
     *             match the number of items found, when the actual search is run. 
     *             This is because, objects might be created or deleted between the time 
     *             when getItemCount() is called and the actual search is run by calling open().
     * @param    : langauge - language to be used for search
     * @returns  : Number of items in search result set.
     */
    public int getItemCount(java.lang.String language) throws DbsException{
        try{
            return this.search.getItemCount(language);
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
     * Purpose   : Returns the number of rows in search result. 
     *             Users can specify the bind values. Default language will be used. 
     *             Calls getItemCount(bindValues, null). Runs a select count(*) query 
     *             to get the count. This does NOT cache the result. Repeated calls 
     *             to this method in the same search object result in database query everytime. 
     *             The number of items returned here is not guaranteed to match the number of 
     *             items found, when the actual search is run. This is because, objects might 
     *             be created or deleted between the time when getItemCount() is called and 
     *             the actual search is run by calling open().
     * @param    : bindValues - array of late bound values
     * @returns  : Number of items in search result set.
     */
    public int getItemCount(DbsAttributeValue[] bindValues) throws DbsException{
        try{
           if(bindValues!=null && bindValues.length > 0){
              int length=bindValues.length;
              AttributeValue[] attrVal=new AttributeValue[length];
              for(int index=0;index < length ;index++){
                attrVal[index]=bindValues[index].getAttributeValue();
              }          
              
             return this.search.getItemCount(attrVal);
           }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }     
        return 0;
    }

    /**
     * Purpose   : Returns the number of rows in search result. 
     *             Users can specify the bind values and the session language. 
     *             Runs a select count(*) query to get the count. This does NOT cache the result. 
     *             Repeated calls to this method in the same search object result in database query everytime. 
     *             The number of items returned here is not guaranteed to match the number of items found, 
     *             when the actual search is run. 
     *             This is because, objects might be created or deleted between the time when getItemCount() 
     *             is called and the actual search is run by calling open().
     * @param    : bindValues - array of late bound values
     * @param    : language - language to be used for the search
     * @returns  : Number of items in search result set.
     */
    public int getItemCount(DbsAttributeValue[] bindValues, java.lang.String language) throws DbsException{
        try{
             if(bindValues!=null && bindValues.length > 0){
              int length=bindValues.length;
              AttributeValue[] attrVal=new AttributeValue[length];
              for(int index=0;index < length ;index++){
                attrVal[index]=bindValues[index].getAttributeValue();
              }
              return this.search.getItemCount(attrVal,language);
             }
             
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        } 
        return 0;
    }

    /**
     * Purpose   : Returns the number of rows in search result. 
     *             Users can specify the bind values, session language and timeout value. 
     *             Runs a select count(*) query to get the count. This does NOT cache the result. 
     *             Repeated calls to this method in the same search object result in database query everytime. 
     *             The number of items returned here is not guaranteed to match the number of items found, 
     *             when the actual search is run. This is because, objects might be created or deleted 
     *             between the time when getItemCount() is called and the actual search is run by calling open().
     * @param    : bindValues - array of late bound values
     * @param    : language - language to be used for the search
     * @param    : timeout - search will timeout after this amount of time
     * @returns  : Number of items in search result set.
     */
    public int getItemCount(DbsAttributeValue[] bindValues,java.lang.String language,int timeout) throws DbsException{
        try{
             if(bindValues!=null && bindValues.length > 0){
              int length=bindValues.length;
              AttributeValue[] attrVal=new AttributeValue[length];
              for(int index=0;index < length ;index++){
                attrVal[index]=bindValues[index].getAttributeValue();
              }
              return this.search.getItemCount(attrVal,language,timeout);
             }
             
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        } 
        return 0;
    }

    /**
     * Purpose   : Returns the SQL query generated by the search. Developers can use this to debug their search tree. 
     *             The SQL generated does not have bind values filled in. These include the bind values used to handle security.
     * @param    : the SQL generated by the search.
     */
    public java.lang.String getSQL() throws DbsException {
        try{
            return this.search.getSQL();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
     * Purpose   : Returns the next result row. The result row is returned as a SearchResultObject. 
     *             Depending on the SearchSepcification, the result row can contain one or more LibraryObjects,
     *             it also contains information about Context Scores.
     * @param    : SearchResultObject corresponding to the next row of the cursor.    
     */
    public DbsSearchResultObject next() throws DbsException{
         DbsSearchResultObject dbsSrchResultObject=null;
         try{
            SearchResultObject srchResultObject=this.search.next();
            if(srchResultObject!=null){
              dbsSrchResultObject=new DbsSearchResultObject(srchResultObject);
            }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }    
        return dbsSrchResultObject;
    }

    /**
     * Purpose   : Disposes this Search.Dispose a search to fully release its resources. 
     *             If opened, the Search is closed. Once disposed, the Search cannot be reopened.
     */
    public void dispose() throws DbsException {
        try{
            this.search.dispose();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Used to get the object of class Search.
	   * @return : search object.
	   */
    public Search getSearch() {
        return this.search;
    }  
}
