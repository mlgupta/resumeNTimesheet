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
 * $Id: DbsSearchSortSpecification.java,v 1.1.1.1 2005/05/26 12:31:57 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of SearchSortSpecification class provided by CMSDK API.
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
public class DbsSearchSortSpecification {
    private SearchSortSpecification searchSortSpecification=null;// to accept object of type SearchSortSpecification

    /**
	   * Purpose : To create DbsSearchSortSpecification using SearchSortSpecification class
	   */
    public DbsSearchSortSpecification(){
        this.searchSortSpecification= new SearchSortSpecification();
    }

    /**
	   * Purpose : To create DbsSearchSortSpecification using SearchSortSpecification class
	   * @param  : SearchSortSpecification - An SearchSortSpecification Object  
	   */    
    public DbsSearchSortSpecification(SearchSortSpecification searchSortSpecification) {
        this.searchSortSpecification = searchSortSpecification;
    }

    /**
	   * Purpose  : Constructs a SearchSortSpecification. The sort list is set to an empty list. 
     *            User can specify the default Class for the attributes.
	   * @param   : dbsDefaultClass - default Class for sort Attributes
	   */
    public DbsSearchSortSpecification(java.lang.String dbsDefaultClass){
        this.searchSortSpecification = new SearchSortSpecification(dbsDefaultClass);
    }

    /**
	   * Purpose  : Constructs a SearchSortSpecification. 
     *            Initializes Sort list with specified attributes. 
     *            Call the constructor that takes classes, attributes, orders and functions as parameters. 
     *            Passes null for the functions parameter.
	   * @param   : dbsClasses - default Class for sort Attributes
	   * @param   : dbsAttributes - Sort Attributes
	   * @param   : dbsOrders - Sort Order for each attribute, true implies ascending, false implies descending
	   */
    public DbsSearchSortSpecification(java.lang.String[] dbsClasses, java.lang.String[] dbsAttributes, boolean[] dbsOrders)
                                                                                      throws DbsException{
        try{
            this.searchSortSpecification = new SearchSortSpecification(dbsClasses,dbsAttributes,dbsOrders);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }           
    }

    /**
	   * Purpose  : Constructs a SearchSortSpecification. Initializes Sort list with specified attributes. Calls add(classes, attributes, orders, functions)
	   * @param   : dbsClasses - default Class for sort Attributes
	   * @param   : dbsAttributes - Sort Attributes
	   * @param   : dbsOrders - Sort Order for each attribute, true implies ascending, false implies descending
     * 
     *  @param  : dbsFunctions - Sql function that is to be wrapped around each function
	   */
    
    public DbsSearchSortSpecification(java.lang.String[] dbsClasses, java.lang.String[] dbsAttributes, 
                                    boolean[] dbsOrders, java.lang.String[] dbsFunctions) throws DbsException {
        try{
            this.searchSortSpecification = new SearchSortSpecification(dbsClasses,dbsAttributes,dbsOrders,dbsFunctions);
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose  : Add Attributes to sort list. Attributes are added to the end of the existing list. 
     *            Calls add on each attribute in the list.
	   * @param   : dbsSortClasses - list of classes, each entry specifies the class of the corresponding entry in the attribute list.
	   * @param   : dbsSortAttributes - list of sort Attributes
	   * @param   : dbsSortOrders - order of sorting attribute, true implies ascending, false implies descending. 
     *            If this null, then ascending is assumed for all attributes.
     * @param   : dbsSortFunctions - sql functions that need to be wrapped around the attributes
	   */
    public void add(java.lang.String[] dbsSortClasses, java.lang.String[] dbsSortAttributes,
                    boolean[] dbsSortOrders, java.lang.String[] dbsSortFunctions) throws DbsException{
        try{
            this.searchSortSpecification.add(dbsSortClasses,dbsSortAttributes,dbsSortOrders,dbsSortFunctions); 
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose  : Add Attributes to sort list. Attributes are added to the end of the existing list. 
     *            Calls add on each attribute in the list.
	   * @param   : dbsSortClasses - list of classes, each entry specifies the class of the corresponding entry in the attribute list.
	   * @param   : dbsSortAttributes - list of sort Attributes
	   * @param   : dbsSortOrders - order of sorting attribute, true implies ascending, false implies descending. 
     *            If this null, then ascending is assumed for all attributes.
	   */   
    public void add(java.lang.String[] dbsSortClasses,java.lang.String[] dbsSortAttributes,
                    boolean[] dbsSortOrders) throws DbsException{
        try{
            this.searchSortSpecification.add(dbsSortClasses,dbsSortAttributes,dbsSortOrders); 
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose  : Add Attributes to sort list. Attributes are added to the end of the existing list. 
     *            Calls add on each attribute in the list.
	   * @param   : dbsSortClasses - list of classes, each entry specifies the class of the corresponding entry in the attribute list.
	   * @param   : dbsSortAttributes - list of sort Attributes
	   * @param   : dbsSortOrders - order of sorting attribute, true implies ascending, false implies descending. 
     *            If this null, then ascending is assumed for all attributes.
	   */   
    public void add(java.lang.String[] dbsSortAttributes,boolean[] dbsSortOrders) throws DbsException{
        try{
            this.searchSortSpecification.add(dbsSortAttributes,dbsSortOrders); 
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose  : Add an attribute to the end of the sort list.
	   * @param   : dbsSortClass - Class of attribute, defaultClass is used if this is null.
	   * @param   : dbsSortAttribute - sort Attribute
	   * @param   : dbsSortOrder - order of sorting, true implies ascending, false implies descending
     * @param   : dbsSqlFunction - any sql function call that needs to be wrapped around the attribute; 
     *            Use "nls_upper" function to sort case insensitively.
	   */
    public void add(java.lang.String dbsSortClass,java.lang.String dbsSortAttribute,
                    boolean dbsSortOrder,java.lang.String dbsSqlFunction) throws DbsException{
        try{
            this.searchSortSpecification.add(dbsSortClass,dbsSortAttribute,dbsSortOrder,dbsSqlFunction); 
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose  : Add an attribute to the end of the sort list.
	   * @param   : dbsSortClass - Class of attribute, defaultClass is used if this is null.
	   * @param   : dbsSortAttribute - sort Attribute
	   * @param   : dbsSortOrder - order of sorting, true implies ascending, false implies descending
	   */
    public void add(java.lang.String dbsSortClass, java.lang.String dbsSortAttribute,boolean dbsSortOrder) 
                                                                            throws DbsException{
        try{
            this.searchSortSpecification.add(dbsSortClass,dbsSortAttribute,dbsSortOrder); 
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose  : Add an attribute to the end of the sort list.
	   * @param   : dbsSortAttribute - sort Attribute
	   * @param   : dbsSortOrder - order of sorting, true implies ascending, false implies descending
	   */
    public void add(java.lang.String dbsSortAttribute, boolean dbsSortOrder) throws DbsException{
        try{
            this.searchSortSpecification.add(dbsSortAttribute,dbsSortOrder); 
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose    : Returns the Class names of the sort Attributes. The entries correspond to the entries 
     *              in the array returned by getAttributeNames.
	   * @returns   : String array representing classes of Sort Attributes.
	   */
    public java.lang.String[] getClassnames() throws DbsException{
        try{
            return this.searchSortSpecification.getClassnames();
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }   
    }

    /**
	   * Purpose    : Gets the list of Sort Attribute names.
	   * @returns   : list of Sort Attribute names.
	   */
    public java.lang.String[] getAttributeNames() throws DbsException{
        try{
            return this.searchSortSpecification.getAttributeNames();
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose    : Gets the list of sql function names.
	   * @returns   : list of sql function names.
	   */
    public java.lang.String[] getSqlFunctions() throws DbsException{
        try{
            return this.searchSortSpecification.getSqlFunctions();
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose  : Returns the sort Orders of the Sort Attributes. The entries correspond to the entries 
     *            in the array returned by getAttributeNames.
	   * @returns : boolean array representing sort order of Sort Attributes.
	   */
    public boolean[] getOrders() throws DbsException{
        try{
            return this.searchSortSpecification.getOrders();
        }catch(IfsException ifsError){
            throw new DbsException (ifsError);
        }       
    }

    /**
	   * Purpose  : Used to get the object of class SearchSortSpecification
	   * @returns : SearchSortSpecification Object
	   */
    public SearchSortSpecification getSearchSortSpecification() {
        return this.searchSortSpecification;
    }  
}
