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
 * $Id: DbsAttributeSearchSpecification.java,v 1.1.1.1 2005/05/26 12:31:42 suved Exp $
 *****************************************************************************
 */
package rts.beans;
 
/**
 *	Purpose: The encapsulate the functionality of AttributeSearchSpecification class provided by CMSDK API.
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:    25-02-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
import oracle.ifs.search.*;

public class DbsAttributeSearchSpecification extends DbsSearchSpecification{

    protected AttributeSearchSpecification attributeSearchSpecification=null; // to accept object of type AttributeSearchSpecification

    /**
     * Purpose  : Used to get the object of class AttributeSearchSpecification
     */
    public DbsAttributeSearchSpecification() {
        super();
        this.attributeSearchSpecification=new AttributeSearchSpecification();
    }

    /**
	   * Purpose : To create DbsAttributeSearchSpecification using AttributeSearchSpecification class
	   * @param  : attributeSearchSpecification - An AttributeSearchSpecification Object  
	   */    
    public DbsAttributeSearchSpecification(AttributeSearchSpecification attributeSearchSpecification) {
        super(attributeSearchSpecification);
        this.attributeSearchSpecification = attributeSearchSpecification;
    }
    
    /**
	   * Purpose : Gets SearchClassSpecification of this search. SearchClassSpecification encapsulates the 
     *           SELECT list and FROM list of this search.
	   * @param  : attributeSearchSpecification - An AttributeSearchSpecification Object  
	   */
    public DbsSearchClassSpecification getSearchClassSpecification() throws DbsException{
        try{
            return new DbsSearchClassSpecification(this.searchSpecification.getSearchClassSpecification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Gets the SearchSortSpecification for this object. SearchSortSpecification encapsulates 
     *           the ORDER BY clause of this search.
	   * @param  : attributeSearchSpecification - An AttributeSearchSpecification Object  
	   */
    public DbsSearchSortSpecification getSearchSortSpecification() throws DbsException{
        try{
            return new DbsSearchSortSpecification(this.attributeSearchSpecification.getSearchSortSpecification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchClassSpecification for this Search. SearchClassSpecification 
     *           is used to generate the SELECT list and FROM list of the search.
	   * @param  : dbsSearchClassSpecification - SearchClassSpecification
	   */
    public void setSearchClassSpecification(DbsSearchClassSpecification dbsSearchClassSpecification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchClassSpecification(dbsSearchClassSpecification.getSearchClassSpecification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }       
    }

    /**
	   * Purpose : Sets the SearchQualification. 
     *           SearchQualification is used to generate the WHERE condition of the search.
	   * @param  : dbsAttributeQualification - SearchQualification representing SearchTree 
	   */
    public void setSearchQualification(DbsAttributeQualification dbsAttributeQualification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchQualification(dbsAttributeQualification.getAttributeQualification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchQualification. 
     *           SearchQualification is used to generate the WHERE condition of the search.
	   * @param  : dbsSearchClause - SearchClause representing SearchTree 
	   */
     public void setSearchQualification(DbsSearchClause dbsSearchClause) throws DbsException{
        try{
            attributeSearchSpecification.setSearchQualification(dbsSearchClause.getSearchClause());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchQualification. 
     *           SearchQualification is used to generate the WHERE condition of the search.
	   * @param  : dbsContextQualification - ContextQualification representing SearchTree 
	   */    
     public void setSearchQualification(DbsContextQualification dbsContextQualification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchQualification(dbsContextQualification.getContextQualification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchQualification. 
     *           SearchQualification is used to generate the WHERE condition of the search.
	   * @param  : dbsFreeFormQualification - FreeFormQualification representing SearchTree 
	   */
     public void setSearchQualification(DbsFreeFormQualification dbsFreeFormQualification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchQualification(dbsFreeFormQualification.getFreeFormQualification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchQualification. 
     *           SearchQualification is used to generate the WHERE condition of the search.
	   * @param  : dbsJoinQualification - JoinQualification representing SearchTree 
	   */
    public void setSearchQualification(DbsJoinQualification dbsJoinQualification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchQualification(dbsJoinQualification.getJoinQualification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchQualification. 
     *           SearchQualification is used to generate the WHERE condition of the search.
	   * @param  : dbsFolderRestrictQualification - FolderRestrictQualification representing SearchTree 
	   */
    public void setSearchQualification(DbsFolderRestrictQualification dbsFolderRestrictQualification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchQualification(dbsFolderRestrictQualification.getFolderRestrictQualification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchQualification. 
     *           SearchQualification is used to generate the WHERE condition of the search.
	   * @param  : dbsExistenceQualification - ExistenceQualification representing SearchTree 
	   */
    public void setSearchQualification(DbsExistenceQualification dbsExistenceQualification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchQualification(dbsExistenceQualification.getExistenceQualification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchQualification. 
     *           SearchQualification is used to generate the WHERE condition of the search.
	   * @param  : dbsPropertyQualification - PropertyQualification representing SearchTree 
	   */
    public void setSearchQualification(DbsPropertyQualification dbsPropertyQualification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchQualification(dbsPropertyQualification.getPropertyQualification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchQualification. 
     *           SearchQualification is used to generate the WHERE condition of the search.
	   * @param  : dbsSearchQualification - SearchQualification representing SearchTree 
	   */
    public void setSearchQualification(DbsSearchQualification dbsSearchQualification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchQualification(dbsSearchQualification.getSearchQualification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose : Sets the SearchSortSpecification. SerachSortSpecification is used to generate the ORDER BY clause of this search.
     *           is used to generate the SELECT list and FROM list of the search.
	   * @param  : dbsSearchSortSpecification - SearchSortSpecification describing Sort behavior
	   */
    public void setSearchSortSpecification(DbsSearchSortSpecification dbsSearchSortSpecification) throws DbsException{
        try{
            attributeSearchSpecification.setSearchSortSpecification(dbsSearchSortSpecification.getSearchSortSpecification());
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }   
    }

    /**
	   * Purpose  : Used to get the object of class AttributeSearchSpecification
	   * @returns : AttributeSearchSpecification Object
	   */
    public AttributeSearchSpecification getAttributeSearchSpecification() {
        return this.attributeSearchSpecification;
    }    

    /**
	   * Purpose  : Returns SearchQualification
	   * @returns : DbsSearchQualification Object
	   */
    public DbsSearchQualification getSearchQualification() {
        return new DbsSearchQualification(this.attributeSearchSpecification.getSearchQualification());
    }    
}
