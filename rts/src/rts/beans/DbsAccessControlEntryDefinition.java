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
 * $Id: DbsAccessControlEntryDefinition.java,v 1.1.1.1 2005/05/26 12:31:40 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of AccessControlEntryDefinition class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;
 
public class DbsAccessControlEntryDefinition extends DbsSystemObjectDefinition{
    private AccessControlEntryDefinition accessControlEntryDef = null;// to accept object of type AccessControlEntryDefinition

    /**
     * Purpose : To create DbsAccessControlEntryDefinition using AccessControlEntryDefinition class
     * @param  : dbsSession - the session
     * @throws : dbsException - if the operation fails.
     */
    public DbsAccessControlEntryDefinition(DbsLibrarySession dbsSession) throws DbsException {
        super(dbsSession);
        try {
            this.accessControlEntryDef = new AccessControlEntryDefinition(dbsSession.getLibrarySession());  
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Add a permission bundle to this definition.
     * @param    : dbsPermissionBundle - bundle to be added.
     * @throws   : DbsException - if operation fails.
     */
    public void addPermissionBundle(DbsPermissionBundle dbsPermissionBundle)throws DbsException{
        try {
           this.accessControlEntryDef.addPermissionBundle(dbsPermissionBundle.getPermissionBundle());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Set the grantee to be associated with this ACE.
     * @param    : dbsUser - The directory object to be associated with this instance.
     * @throws   : DbsException - if operation fails.
     */
    public void setGrantee(DbsDirectoryUser dbsUser)throws DbsException{
        try {
           this.accessControlEntryDef.setGrantee(dbsUser.getDirectoryUser());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Set the grantee to be associated with this ACE.
     * @param    : dbsGroup - The directory object to be associated with this instance.
     * @throws   : DbsException - if operation fails.
     */
    public void setGrantee(DbsDirectoryGroup dbsGroup)throws DbsException{
        try {
           this.accessControlEntryDef.setGrantee(dbsGroup.getDirectoryGroup());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

   /**
	  * Purpose  : Used to get the object of class AccessControlEntryDefinition
	  * @returns : AccessControlEntryDefinition Object
	  */
    public AccessControlEntryDefinition getAccessControlEntryDefinition() {
        return this.accessControlEntryDef ;
    }

    /**
     * Purpose   : Returns the grantee to be associated with this definition.
     * @returns  : dirobject The directory object to be associated with this instance.
     * @throws   : DbsException - if operation fails.
     */
    public DbsDirectoryObject getGrantee() throws DbsException{
        try{
            if((this.accessControlEntryDef.getGrantee()) instanceof DirectoryUser){
                return new DbsDirectoryUser((DirectoryUser)this.accessControlEntryDef.getGrantee());
            }else if((this.accessControlEntryDef.getGrantee()) instanceof DirectoryGroup){
                return new DbsDirectoryGroup((DirectoryGroup)this.accessControlEntryDef.getGrantee());
            }else{
                return new DbsDirectoryObject(this.accessControlEntryDef.getGrantee());
            }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns the indication as to whether this ACE is a grant or revoke ACE.
     * @returns  : the indication as to whether this ACE is a grant or revoke ACE; returns null if no indication has been made
     * @throws   : DbsException - if operation fails.
     */
    public boolean isGranted() throws DbsException{
        boolean isGranted;
        try {
            isGranted=accessControlEntryDef.isGranted().booleanValue();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
        return isGranted;
    }

    /**
     * Purpose   : Set the grantee to be associated with this ACE.
     * @param    : dbsDirObject - The directory object to be associated with this instance.
     * @throws   : DbsException - if operation fails.
     */
    public void setGrantee(DbsDirectoryObject dbsDirObject) throws DbsException{
         try{
            this.accessControlEntryDef.setGrantee(dbsDirObject.getDirectoryObject());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns set of PermissionBundles.
     * @returns  : dbsPermissionBundle - set of PermissionBundles currently set.
     * @throws   : DbsException - if operation fails.
     */
    public DbsPermissionBundle[] getPermissionBundles() throws DbsException{
        DbsPermissionBundle[] dbsPermissionBundle=null;  
        try{
            int permissionBundleCount=this.accessControlEntryDef.getPermissionBundles().length;
            dbsPermissionBundle=new DbsPermissionBundle[permissionBundleCount];
            PermissionBundle[] permissionBundles=this.accessControlEntryDef.getPermissionBundles();
            for(int index=0;index<permissionBundleCount;index++){
              dbsPermissionBundle[index]=new DbsPermissionBundle(permissionBundles[index]);              
            }
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
        return dbsPermissionBundle;
    }

/*    public void addPermissionBundles(DbsPermissionBundle[] dbsPbs) throws DbsException{
        DbsPermissionBundle dbsPermissionBundle=null;
        try{
            int permissionBundleCount=this.accessControlEntryDef.addPermissionBundles[]();
            dbsPermissionBundle=new DbsPermissionBundle[permissionBundleCount];
            PermissionBundle[] permissionBundles=this.accessControlEntryDef.addPermissionBundles();
            for(int index=0;index<permissionBundleCount;index++){
                dbsPermissionBundle[index]=new DbsPermissionBundle(permissionBundles[index]);              
            }
            
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
        return dbsPermissionBundle;
    }
*/    
}
