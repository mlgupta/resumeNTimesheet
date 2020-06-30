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
 * $Id: DbsAccessControlEntry.java,v 1.1.1.1 2005/05/26 12:31:40 suved Exp $
 *****************************************************************************
 */
package rts.beans;  

  
/**
 *	Purpose: The encapsulate the functionality of AccessControlEntry class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/* CMSDK API*/
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsAccessControlEntry extends DbsSystemObject{
    private AccessControlEntry ace=null; // to accept object of type AccessControlEntry

    public static java.lang.String GRANTED_ATTRIBUTE=AccessControlEntry.GRANTED_ATTRIBUTE;

    /**
     * Purpose   : To create DbsAccessControlEntry using AccessControlEntry class
     * @param    : ace - An AccessControlEntryObject  Object
     */
    protected DbsAccessControlEntry(AccessControlEntry ace) {
        super(ace);
        this.ace=ace;
    }

    /**
     * Purpose   : Returns all PermissionBundles that were used to define the set of permissions for this ACE. 
     *             If the permissions were set explicitly using access levels, returns null.
     * @returns  : array of permission bundles.
     * @throws   : DbsException - if operation fails.
     */
    public DbsPermissionBundle[] getPermissionBundles() throws DbsException {
        DbsPermissionBundle[] dbsPermissionBundle=null;  
        try{
            int permissionBundleCount=this.ace.getPermissionBundles().length;
            dbsPermissionBundle=new DbsPermissionBundle[permissionBundleCount];
            PermissionBundle[] permissionBundles=this.ace.getPermissionBundles();
            for(int index=0;index<permissionBundleCount;index++){
                dbsPermissionBundle[index]=new DbsPermissionBundle(permissionBundles[index]);              
            }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
        return dbsPermissionBundle;
    }

    /**
     * Purpose   : Return the ACL object that contains this ACE.
     * @returns  : the ACL object that contains this ACE
     * @throws   : DbsException - if operation fails.
     */
    public DbsAccessControlList getAcl() throws DbsException {
        try{
            return new DbsAccessControlList(this.ace.getAcl());
        }catch(IfsException ifsError){
              throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns the directory object (grantee) that is associated with this ACE.
     * @returns  : the grantee for this ACE
     * @throws   : DbsException - if operation fails.
     */
    public DbsDirectoryObject getGrantee() throws DbsException{
        try{
            if((this.ace.getGrantee()) instanceof DirectoryUser){
                return new DbsDirectoryUser((DirectoryUser)this.ace.getGrantee());
            }else if((this.ace.getGrantee()) instanceof DirectoryGroup){
                return new DbsDirectoryGroup((DirectoryGroup)this.ace.getGrantee());
            }else{
                return new DbsDirectoryObject(this.ace.getGrantee());
            }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns the access level represented by this ACE without considering permission bundles.
     * @returns  : the access level represented by this ACE.
     * @throws   : DbsException - if operation fails.
     */
    public DbsAccessLevel getDistinctAccessLevel() throws DbsException {
        try{
            return new DbsAccessLevel(this.ace.getDistinctAccessLevel());  
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns the access level represented by this ACE including permission bundles.
     * @returns  : the access level represented by this ACE including permission bundles
     * @throws   : DbsException - if operation fails.
     */
    public DbsAccessLevel getMergedAccessLevel() throws DbsException {
        try{
            return new DbsAccessLevel(this.ace.getMergedAccessLevel());  
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns the Sort Sequence represented by this ACE. Sort sequnce determines the position of ACE 
     *             within an ACL and used in determining the order of ACEs to evaluate effective permissions defined by an ACL.
     * @returns  : the sort sequence represented by this ACE.
     * @throws   : DbsException - if operation fails.
     */
    public long getSortSequence() throws DbsException {
        try{
             return this.ace.getSortSequence();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns the boolean represented by this ACE. 
     * @returns  : the indication as to whether this ACE is a grant or revoke ACE
     * @throws   : DbsException - if operation fails.
     */
    public boolean isGrant() throws DbsException {
        try{
             return this.ace.isGrant();
          }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose : Used to get the object of class AccessControlEntry
     * @return AccessControlEntry Object
     */
    AccessControlEntry getAccessControlEntry() {
        return this.ace;
    }
}
