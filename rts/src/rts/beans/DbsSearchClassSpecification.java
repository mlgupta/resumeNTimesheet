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
 * $Id: DbsSearchClassSpecification.java,v 1.1.1.1 2005/05/26 12:31:55 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of SearchClassSpecification class provided by CMSDK API.
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

public class DbsSearchClassSpecification {
    protected SearchClassSpecification searchClassSpecification=null;// to accept object of type SearchClassSpecification

    /**
	   * Purpose : To create DbsSearchClassSpecification using SearchClassSpecification class
	   * @param  : searchClassSpecification - An SearchClassSpecification Object  
	   */
    public DbsSearchClassSpecification()throws DbsException{
       try{
          this.searchClassSpecification=new SearchClassSpecification();
       }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }  
     }

    /**
	   * Purpose : To create DbsSearchClassSpecification using SearchClassSpecification class
	   * @param  : searchClassSpecification - An SearchClassSpecification Object  
	   */    
    public DbsSearchClassSpecification(SearchClassSpecification searchClassSpecification) {
        this.searchClassSpecification = searchClassSpecification;
    }

    /**
	   * Purpose : Construct SearchClassSpecification, initializing with the set of classes supplied in the parameter. 
     *           Calls SearchClassSpecification(sClasses, null, null, null)
	   * @param  : searchClasses - list of classes to search over.
	   */
    public DbsSearchClassSpecification(java.lang.String[] dbsClasses) throws DbsException {
        try{
            this.searchClassSpecification = new SearchClassSpecification(dbsClasses);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }        
    }

    /**
	   * Purpose : Construct SearchClassSpecification, initializing it with the supplied set of classes, 
     *           delete behavior, and recursive behavior. Calls SearchClassSpecification(sClasses, null,
     *           delBehavior, recBehavior) The length of arrays for delBehavior and recBehavior should 
     *           be same as that of searchClasses or arrays themselves should be null.
	   * @param  : searchClasses - list of classes to search over.
	   * @param  : delBehavior - array of boolean describing delete behavior for each class.
	   * @param  : recBehavior - array of boolean describing recursive behavior for each class.
	   */
    protected DbsSearchClassSpecification(java.lang.String[] dbsClasses, boolean[] dbsDelBehavior, boolean[] dbsRecBehavior) throws DbsException{
        try{
            this.searchClassSpecification = new SearchClassSpecification(dbsClasses,dbsDelBehavior,dbsRecBehavior);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }           
    }

    /**
	   * Purpose : Construct SearchClassSpecification, initializing it with the set of supplied 
     *           classes, aliases, delete behavior, and recursive behavior. Length of aliases,
     *           delBehavior and recBehavior arrays should match that of the class array or 
     *           they should be null.
	   * @param  : dbsClasses - list of classes to search over.
	   * @param  : dbsAliases - Aliases for the classes
	   * @param  : dbsDelBehavior - array of boolean describing delete behavior for each class.
	   * @param  : dbsRecBehavior - array of boolean describing recursive behavior for each class.
	   */
    protected DbsSearchClassSpecification(java.lang.String[] dbsClasses, java.lang.String[] dbsAliases,
                                        boolean[] dbsDelBehavior, boolean[] dbsRecBehavior) throws DbsException{
        try{
            this.searchClassSpecification = new SearchClassSpecification(dbsClasses,dbsAliases,dbsDelBehavior,dbsRecBehavior);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose : Add search classes to list of classes. Calls addSearchClass for each entry in the array.
	   * @param  : dbsClasses - list of classes.
	   */
    public void addSearchClasses(java.lang.String[] dbsClasses) throws DbsException{
        try{
            this.searchClassSpecification.addSearchClasses(dbsClasses);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose : add search classes to list of classes. Calls addSearchClass for each entry in the array.
	   * @param  : dbsClasses - list of classes.
     * @param  : dbsAliases - Aliases for the classes. For unspecified values, alias is assumed to be null.
	   */
    public void addSearchClasses(java.lang.String[] dbsClasses, java.lang.String[] dbsAliases) throws DbsException{
        try{
            this.searchClassSpecification.addSearchClasses(dbsClasses,dbsAliases);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose : Adds the class to the list of Searched Classes.
	   * @param  : dbsClasses - list of classes.
	   */
    public void addSearchClass(java.lang.String dbsClass) throws DbsException{
        try{
            this.searchClassSpecification.addSearchClass(dbsClass);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose : Adds the class to the list of Searched Classes.
	   * @param  : dbsClass - the iFS class being searched
     * @param  : dbsAlias - the Alias for the Class, if this is null an internal alias is 
     *           generated. User must use ClassName in Quals, SortSpec etc.
	   */
    public void addSearchClass(java.lang.String dbsClass, java.lang.String dbsAlias) throws DbsException{
        try{
            this.searchClassSpecification.addSearchClass(dbsClass,dbsAlias);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose   : Returns list of search classes
	   * @returns  : list of search classes
	   */
    public java.lang.String[] getSearchClassnames(){
        return this.searchClassSpecification.getSearchClassnames();
    }

    /**
	   * Purpose   : Gets list of search aliases.
	   * @returns  : list of search aliases.
	   */
    public java.lang.String[] getSearchClassAliases() throws DbsException{
        try{
            return this.searchClassSpecification.getSearchClassAliases();
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose   : Returns recursive behavior of classes
	   * @returns  : recursive behavior of classes
	   */
    public boolean[] getRecursiveBehavior(){
        return this.searchClassSpecification.getRecursiveBehavior();
    }

    /**
	   * Purpose   : Add name to the ResultClass list. Name must be a user specified alias or classname.
	   * @returns  : dbsName - For classes w/o aliases, this should be the className; o.w. this should be the alias.
	   */
    public void addResultClass(java.lang.String dbsName) throws DbsException{
        try{
            this.searchClassSpecification.addResultClass(dbsName);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose   : Gets list of result classes
	   * @returns  : list of result classes
	   */
    public java.lang.String[] getResultClassnames() throws DbsException{
        try{
            return this.searchClassSpecification.getResultClassnames();
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose  : Used to get the object of class SearchClassSpecification
	   * @returns : SearchClassSpecification Object
	   */
    public SearchClassSpecification getSearchClassSpecification() {
        return this.searchClassSpecification;
    }

    /**
	   * Purpose  : Set remove duplicates behavior.
	   * @returns : isDistinct - if true, add DISTINCT to select clause.
	   */
    public void setDistinct(boolean isDistinct) throws DbsException{
        try{
            this.searchClassSpecification.setDistinct(isDistinct);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose  : Get remove duplicates behavior.
	   * @returns : true if set, false otherwise.
	   */
    public boolean isDistinct() throws DbsException{
        try{
            return this.searchClassSpecification.isDistinct();
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }
}
