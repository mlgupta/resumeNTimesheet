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
 * $Id: DbsAccessLevel.java,v 1.1.1.1 2005/05/26 12:31:41 suved Exp $
 *****************************************************************************
 */
package rts.beans; 



/**

 *	Purpose: The encapsulate the functionality of AccessLevel class provided by CMSDK API.

 * 

 *  @author              Mishra Maneesh

 *  @version             1.0

 * 	Date of creation:   23-12-2003

 * 	Last Modfied by :     

 *	Last Modfied Date:    

 */



/*CMSDK API*/

import oracle.ifs.beans.*;

import oracle.ifs.common.*;



public class DbsAccessLevel {

    private static AccessLevel accessLevel = null; // to accept object of type AccessLevel
    public static long ACCESSLEVEL_ADDITEM = AccessLevel.ACCESSLEVEL_ADDITEM;
//              Numeric value that designates permission to add an item to a Folder. 
    public static long ACCESSLEVEL_ADDMEMBER = AccessLevel.ACCESSLEVEL_ADDMEMBER;
//              Numeric value that designates permission to add a DirectoryObject to a DirectoryGroup. 
    public static long ACCESSLEVEL_ADDRELATIONSHIP = AccessLevel.ACCESSLEVEL_ADDRELATIONSHIP;
//              Numeric value that designates permission to relate a PublicObject to another PublicObject. 
    public static long ACCESSLEVEL_ADDVERSION = AccessLevel.ACCESSLEVEL_ADDVERSION;
//              Numeric value that designates permission to add a new version to a VersionSeries. 
    public static long ACCESSLEVEL_ADDVERSIONSERIES = AccessLevel.ACCESSLEVEL_ADDVERSIONSERIES;
//              Numeric value that designates permission to add a VersionSeries to a Family. 
    public static long ACCESSLEVEL_ALL = AccessLevel.ACCESSLEVEL_ALL;
//              Numeric value that designates "all permissions". 
    public static long ACCESSLEVEL_CREATE = AccessLevel.ACCESSLEVEL_CREATE;
//              Numeric value that designates permission to create an instance of a Class. 
    public static long ACCESSLEVEL_DELETE = AccessLevel.ACCESSLEVEL_DELETE;
//              Numeric value that designates permission to delete, undelete or free an object. 
    public static long ACCESSLEVEL_DEQUEUE = AccessLevel.ACCESSLEVEL_DEQUEUE;
//              Numeric value that designates permission to dequeue messages from a queue. 
    public static long ACCESSLEVEL_DISCOVER = AccessLevel.ACCESSLEVEL_DISCOVER;
//              Numeric value that designates Discover permission. 
    public static long ACCESSLEVEL_ENQUEUE = AccessLevel.ACCESSLEVEL_ENQUEUE;
//              Numeric value that designates permission to enqueue messages on to a queue. 
    public static long ACCESSLEVEL_GET_CONTENT = AccessLevel.ACCESSLEVEL_GET_CONTENT;
//              Numeric value that designates permission to get content of a Document. 
    public static long ACCESSLEVEL_GRANT = AccessLevel.ACCESSLEVEL_GRANT;
//              Numeric value that designates permission to change a Publicobject's Owner, ACL AdministrationGroup to another user 
    public static long ACCESSLEVEL_LOCK = AccessLevel.ACCESSLEVEL_LOCK;
//              Numeric value that designates permission to lock or unlock PublicObjects. 
    public static long ACCESSLEVEL_NONE = AccessLevel.ACCESSLEVEL_NONE;
//              Numeric value that designates "no permissions". 
    public static long ACCESSLEVEL_REMOVEITEM = AccessLevel.ACCESSLEVEL_REMOVEITEM;
//              Numeric value that designates permission to remove an item from a Folder. 
    public static long ACCESSLEVEL_REMOVEMEMBER = AccessLevel.ACCESSLEVEL_REMOVEMEMBER;
//              Numeric value that designates permission to remove a DirectoryObject from a DirectoryGroup. 
    public static long ACCESSLEVEL_REMOVERELATIONSHIP = AccessLevel.ACCESSLEVEL_REMOVERELATIONSHIP;
//              Numeric value that designates permission to remove a Relationship between a PublicObject. 
    public static long ACCESSLEVEL_REMOVEVERSION = AccessLevel.ACCESSLEVEL_REMOVEVERSION;
//              Numeric value that designates permission to remove a version from a VersionSeries. 
    public static long ACCESSLEVEL_REMOVEVERSIONSERIES = AccessLevel.ACCESSLEVEL_REMOVEVERSIONSERIES;
//              Numeric value that designates permission to remove a VersionSeries from a Family. 
    public static long ACCESSLEVEL_SELECTORACCESS = AccessLevel.ACCESSLEVEL_SELECTORACCESS;
//              Numeric value that designates permission to search a Class in a Selector or a Search. 
    public static long ACCESSLEVEL_SET_ATTR = AccessLevel.ACCESSLEVEL_SET_ATTR;
//              Numeric value that designates permission to update an object attribute. 
    public static long ACCESSLEVEL_SET_CONTENT = AccessLevel.ACCESSLEVEL_SET_CONTENT;
//              Numeric value that designates permission to update content of a Document. 
    public static long ACCESSLEVEL_SETDEFAULTVERSION = AccessLevel.ACCESSLEVEL_SETDEFAULTVERSION;
//              Numeric value that designates permission to change the default version or VersionSeries of a Family or VersionSeries. 
    public static long ACCESSLEVEL_SETPOLICY = AccessLevel.ACCESSLEVEL_SETPOLICY;
//              Numeric value that designates permission to change the PolicyPropertyBundle associated with a PublicObject. 
    public static java.lang.String ACCESSLEVEL_TOSTRING_DELIMITER_KEY = AccessLevel.ACCESSLEVEL_TOSTRING_DELIMITER_KEY;
//              resourceBundle key used to get the permission delimiter for a String representation for an AccessLevel. 
    public static java.lang.String ACCESSLEVEL_TOSTRING_PREFIX_KEY = AccessLevel.ACCESSLEVEL_TOSTRING_PREFIX_KEY;
//              resourceBundle key used to get the prefix for a String representation for an AccessLevel. 
    public static java.lang.String ACCESSLEVEL_TOSTRING_SUFFIX_KEY = AccessLevel.ACCESSLEVEL_TOSTRING_SUFFIX_KEY;
//              resourceBundle key used to get the suffix for a String representation for an AccessLevel. 
    public static long ACCESSLEVEL_UNLOCK = AccessLevel.ACCESSLEVEL_UNLOCK;
//              Numeric value that designates permission to unlock PublicObjects. 

    /**

     * Purpose   : To create DbsAccessLevel using AccessLevel class

     * @throws   : DbsException - if operation fails.

     */

    public DbsAccessLevel()throws DbsException{

        try{

            this.accessLevel=new AccessLevel();

        }catch(IfsException ifsError){

            throw new DbsException(ifsError);

        }

    }



    /**
     * Purpose : To create DbsAccessLevel using AccessLevel class
	   * @param  : accessLevel - An AccessLevel Object  
	   */
    public DbsAccessLevel(AccessLevel accessLevel) {
        this.accessLevel=accessLevel;
    }

    /**
     * Purpose : To create DbsAccessLevel using AccessLevel Id
	   * @param  : accessLevel - An AccessLevel Object  
	   */
    public DbsAccessLevel(long access_level) {
        this.accessLevel=new AccessLevel(access_level);
    }

    public boolean isSufficientlyEnabled(DbsAccessLevel level, DbsLibrarySession sess) throws DbsException{
      try{
        return accessLevel.isSufficientlyEnabled(level.getAccessLevel(),sess.getLibrarySession());
      }catch(IfsException iex){
        throw new DbsException(iex);
      }
    }
 
    public void add(DbsAccessLevel level, DbsLibrarySession sess) throws DbsException{
      try{
        accessLevel.add(level.getAccessLevel(), sess.getLibrarySession());
      }catch(IfsException iex){
        throw new DbsException(iex);
      }
    }
 
    /**

	   * Purpose : Used to get the object of class AccessLevel

	   * @return AccessLevel Object

	   */

    public AccessLevel getAccessLevel() {

        return this.accessLevel;

    }



    /**

	   * Purpose   : return the array of all defined standard permission localized labels.

	   * @param    : dbsLibrarySession - a LibrarySession, used to determine the Localizer

     * @returns  : all defined standard permission String values.

     * @throws   : DbsException - if operation fails.

     */

    public static java.lang.String[] getAllDefinedStandardPermissionNames(DbsLibrarySession dbsLibrarySession) throws DbsException{

        try{

            return accessLevel.getAllDefinedStandardPermissionNames(dbsLibrarySession.getLibrarySession());

        }catch(IfsException ifsError){

            throw new DbsException(ifsError);

        }

    }



    /**

	   * Purpose : return the array of the names of all standard permission enabled in this instance. The names are localized using the specified LibrarySession's Localizer

	   * @param  : dbsLibrarySession - a LibrarySession, used to determine the Localizer

     * @returns: the names of all standard permissions that are enabled.

     * @throws   : DbsException - if operation fails.

     */

    public java.lang.String[] getEnabledStandardPermissionNames(DbsLibrarySession dbsLibrarySession) throws DbsException {

        try{

            return accessLevel.getEnabledStandardPermissionNames(dbsLibrarySession.getLibrarySession());

        }catch(IfsException ifsError){

            throw new DbsException(ifsError);

        }

    }



    /**

	   * Purpose  : Disable a standard perrmission.

	   * @param   : permission - the standard permission to disable.

     * @throws  : DbsException - if operation fails.

     */

    public void disableStandardPermission(long permission)throws DbsException {

        try{

            accessLevel.disableStandardPermission(permission);

        }catch(IfsException ifsError){

            throw new DbsException(ifsError);

        }

    }



    /**

	   * Purpose  : Disable a standard perrmission.

	   * @param   : permission - the standard permission to disable.

     * @throws  : DbsException - if operation fails.

     */

    public void enableStandardPermission(long permission)throws DbsException {

        try{

            accessLevel.enableStandardPermission(permission);

        }catch(IfsException ifsError){

            throw new DbsException(ifsError);

        }

    }



    /**

	   * Purpose  : To get long for Fields

	   * @param   : name - String

     */

    public long getLong(String name) {

        if(name.equals("Discover")){

            return  AccessLevel.ACCESSLEVEL_DISCOVER;

        }else if(name.equals("GetContent")){

            return  AccessLevel.ACCESSLEVEL_GET_CONTENT;

        }else if(name.equals("SetAttribute")){

            return  AccessLevel.ACCESSLEVEL_SET_ATTR;

        }else if(name.equals("SetContent")){

            return  AccessLevel.ACCESSLEVEL_SET_CONTENT;

        }else if(name.equals("Lock")){

            return  AccessLevel.ACCESSLEVEL_LOCK;

        }else if(name.equals("AddMember")){

            return  AccessLevel.ACCESSLEVEL_ADDMEMBER;

        }else if(name.equals("RemoveMember")){

            return  AccessLevel.ACCESSLEVEL_REMOVEMEMBER;

        }else if(name.equals("AddItem")){

            return  AccessLevel.ACCESSLEVEL_ADDITEM;

        }else if(name.equals("RemoveItem")){

            return  AccessLevel.ACCESSLEVEL_REMOVEITEM;

        }else if(name.equals("AddRelationship")){

            return  AccessLevel.ACCESSLEVEL_ADDRELATIONSHIP;

        }else if(name.equals("RemoveRelationship")){

            return  AccessLevel.ACCESSLEVEL_REMOVERELATIONSHIP;

        }else if(name.equals("AddVersionSeries")){

            return  AccessLevel.ACCESSLEVEL_ADDVERSIONSERIES;

        }else if(name.equals("RemoveVersionSeries")){

            return  AccessLevel.ACCESSLEVEL_REMOVEVERSIONSERIES;

        }else if(name.equals("AddVersion")){

            return  AccessLevel.ACCESSLEVEL_ADDVERSION;

        }else if(name.equals("RemoveVersion")){

            return  AccessLevel.ACCESSLEVEL_REMOVEVERSION;

        }else if(name.equals("SetDefaultVersion")){

            return  AccessLevel.ACCESSLEVEL_SETDEFAULTVERSION;

        }else if(name.equals("SetPolicy")){

            return  AccessLevel.ACCESSLEVEL_SETPOLICY;

        }else if(name.equals("Enqueue")){

            return  AccessLevel.ACCESSLEVEL_ENQUEUE;

        }else if(name.equals("Dequeue")){

            return  AccessLevel.ACCESSLEVEL_DEQUEUE;

        }else if(name.equals("Unlock")){

            return  AccessLevel.ACCESSLEVEL_UNLOCK;

        }else if(name.equals("Create")){

            return  AccessLevel.ACCESSLEVEL_CREATE;

        }else if(name.equals("SelectorAccess")){

            return  AccessLevel.ACCESSLEVEL_SELECTORACCESS;

        }else if(name.equals("Delete")){

            return  AccessLevel.ACCESSLEVEL_DELETE;

        }else if(name.equals("Grant")){

            return  AccessLevel.ACCESSLEVEL_GRANT;

        }else{

            return 0;

        }

    }



    /**

     * Purpose : return the array of all defined standard permission numeric values

     * @return : all defined standard permission numeric values.

     * @throws : DbsException - if operation fails.

     */

    public static long[] getAllDefinedStandardPermissions() throws DbsException {

        try{

            return accessLevel.getAllDefinedStandardPermissions();

        }catch(IfsException ifsError){

            throw new DbsException(ifsError);

        }

    }


    /**
     * Purpose : return the array of all enabled permission numeric values
     * @return : all enabled permission numeric values.
     * @throws : DbsException - if operation fails.
     */

    public static long[] getEnabledStandardPermissions() throws DbsException {
        try{
            return accessLevel.getEnabledStandardPermissions();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

}
