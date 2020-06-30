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
 * $Id: DbsCollection.java,v 1.2 2005/06/20 11:39:56 ias Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: To encapsulate the functionality of Collection class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

import java.util.*;

/* CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsCollection {
    private oracle.ifs.common.Collection collection; // to accept object of type Collection

    /**
	   * Purpose   : To create DbsCollection using Collection class
	   * @param    : ace - An Collection  Object
	   */
    public DbsCollection(oracle.ifs.common.Collection collection) {
        this.collection=collection;
    }
 
    /**
     * Purpose  : Gets the number of items in this Collection.
     * @returns : the number of items in this Collection
     * @throws  : DbsException - if operation fails
     */
    public int getItemCount() throws DbsException {
        int itemCount ;
        try {
            itemCount=collection.getItemCount();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return itemCount;
    }

    /**
     * Purpose  : Gets an array containing the items in this Collection.
     * @returns : the items in this Collection
     * @throws  : DbsException - if operation fails
     */
    public java.lang.Object[] getItems() throws DbsException {
        Object[] items=null ;
        try {
            items=collection.getItems();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return items;
    }

    /**
     * Purpose  : Gets an array containing the Directory Users in this Collection.
     * @param   : array
     * @returns : Collection of Directory User
     * @throws  : DbsException - if operation fails
     */
    public DbsDirectoryUser[] getDbsDirectoryUsers(Object[] directoryUserObjects) throws DbsException {
        DbsDirectoryUser[] dbsDirectoryUsers=null;
        int userCount=directoryUserObjects.length;
        dbsDirectoryUsers=new DbsDirectoryUser[userCount];
        for(int index=0;index<userCount;index++) {
            dbsDirectoryUsers[index]=new DbsDirectoryUser((DirectoryUser)directoryUserObjects[index]);
        }
        return dbsDirectoryUsers;
    }

    /**
     * Purpose  : Gets an containing the Directory Users in this Collection.
     * @param   : Object: Directory User 
     * @returns : DbsDirectoryUser
     * @throws  : DbsException - if operation fails
     */
    public DbsDirectoryUser getDbsDirectoryUser(Object directoryUserObject) throws DbsException {
        DbsDirectoryUser dbsDirectoryUser=null;  
        dbsDirectoryUser=new DbsDirectoryUser((DirectoryUser)directoryUserObject);  
        return dbsDirectoryUser;
    }

    /**
     * Purpose  : Gets an containing the Directory Users in this Collection.
     * @param   : Object: Directory User 
     * @returns : DbsAccessControlList
     * @throws  : DbsException - if operation fails
     */
    public DbsAccessControlList getSystemAccessControlList(Object aclObject) throws DbsException {
        DbsAccessControlList dbsSystemAccessControlList=null;  
        dbsSystemAccessControlList=new DbsAccessControlList((AccessControlList)aclObject);  
        return dbsSystemAccessControlList;
    }

    /**
     * Purpose  : Gets the specified item in this Collection.
     * @param   : index - the zero-based index of the item
     * @returns : the item
     * @throws  : DbsException - if operation fails
     */
    public java.lang.Object getItems(int index) throws DbsException{
        Object item=null ;
        try{
            item=collection.getItems(index);
            
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return item;
    }

    /**
     * Purpose  : Gets the specified item in this Collection.
     * @param   : index - the zero-based index of the item
     * @returns : the item
     * @throws  : DbsException - if operation fails
     */
    public java.lang.Object getItems(java.lang.String name) throws DbsException {
        Object item=null ;
        try{
            item=collection.getItems(name);
            if(item instanceof ClassObject){
              return new DbsClassObject((ClassObject)item);
            }else if(item instanceof DirectoryUser){
              return new DbsDirectoryUser((DirectoryUser)item);
            }else if(item instanceof Format){
              return new DbsFormat((Format)item);
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return item;
    }

    /**
     * Purpose  : Gets a vector containing the items in this Collection. The order of the items in 
     *            the vector is identical to the order of the items in the array returned by getItems().
     * @returns : a vector containing the items in this Collection
     * @throws  : DbsException - if operation fails
     */
    public java.util.Vector getItemsVector() throws DbsException{
        Vector items=null ;
        try{
            items=collection.getItemsVector();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return items;
    }
}
