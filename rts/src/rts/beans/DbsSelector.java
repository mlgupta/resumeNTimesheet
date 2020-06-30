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
 * $Id: DbsSelector.java,v 1.2 2005/06/20 11:40:39 ias Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of Selector class provided by CMSDK API.
 * 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
public class DbsSelector {
    private Selector selector=null; // to accept object of type Selector

    /**
     * Purpose   : To create DbsSelector using Selector class
     * @param    : dbsSession - An Selector Object
     * @throws   : DbsException - if operation fails
     */
    public DbsSelector(DbsLibrarySession dbsSession) throws DbsException{
        try{
            this.selector=new Selector(dbsSession.getLibrarySession());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
    
    /**
     * Purpose   : Sets the search class.
     * @param    : classname - the search class.
     * @throws   : DbsException - if operation fails
     */
    public void  setSearchClassname(String classname)throws DbsException{
        try{
           this.selector.setSearchClassname(classname); 
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Sets the search selection.
     * @param    : searchSelection - the search selection.
     * @throws   : DbsException - if operation fails
     */
    public void  setSearchSelection(String objectname)throws DbsException{
        try{
            this.selector.setSearchSelection(objectname); 
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns the number of items in the search results. 
     *             If the items have already been fetched then this simply returns the size of the locally stored vector. 
     *             Otherwise this gets the count from the IFS server.
     * @returns  : the number of items.
     * @throws   : DbsException - if operation fails
     */
    public int getItemCount()throws DbsException{
        try{
            return this.selector.getItemCount();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns a single search result item from the search results with an index relative the the search sort order. 
     *             If the Selector instance was used with only nextItem() style of access prior to this, 
     *             then the cursor will be re-opened.
     * @param    : index - the index of a search result.            
     * @returns  : a search result.
     * @throws   : DbsException - if operation fails
     */
    public DbsLibraryObject getItems(int index)throws DbsException{
        DbsLibraryObject dbsLibraryObject=null;
        try{
            LibraryObject libraryObject=this.selector.getItems(index);
            if(libraryObject instanceof DirectoryGroup){
                return new DbsDirectoryGroup((DirectoryGroup)libraryObject);
            }
            else if(libraryObject instanceof DirectoryUser){
                return new DbsDirectoryUser((DirectoryUser)libraryObject);
            }
            else if(libraryObject instanceof AccessControlList){
                return new DbsAccessControlList((AccessControlList)libraryObject);
            }
            else if(libraryObject instanceof PermissionBundle){
                return new DbsPermissionBundle((PermissionBundle)libraryObject);
            }
            else if(libraryObject instanceof ExtendedUserProfile){
                return new DbsExtendedUserProfile((ExtendedUserProfile)libraryObject);
            }
            else if(libraryObject instanceof ValueDefault){
                return new DbsValueDefault((ValueDefault)libraryObject);
            }
            else if(libraryObject instanceof Format){
                return new DbsFormat((Format)libraryObject);
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsLibraryObject;
    }

    /**
	   * Purpose  : Used to get the object of class Selector
	   * @returns : Selector Object
	   */
    public Selector getSelector(){
        return this.selector; 
    }

    /**
     * Purpose   : Sets the Sort Specification
     * @param    : the dbsSortSpecification - sort specification.
     * @returns  : a search result.
     * @throws   : DbsException - if operation fails
     */
    public void setSortSpecification(DbsSortSpecification dbsSortSpecification) throws DbsException {
        try{
            this.selector.setSortSpecification(dbsSortSpecification.getSortSpecification());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }
}
