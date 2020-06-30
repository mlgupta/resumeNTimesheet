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
 * $Id: DbsAccessControlList.java,v 1.1.1.1 2005/05/26 12:31:40 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of AccessControlList class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

/* CMSDK API */
import oracle.ifs.beans.*;
import oracle.ifs.common.IfsException;

public class DbsAccessControlList extends DbsPublicObject{

    /** This class name for this class. Useful for methods that take a class name argument. */
    public static final java.lang.String CLASS_NAME=AccessControlList.CLASS_NAME;                                
    /** An indicator of whether more than one PublicObject refers to this AccessControlList. */
    public static final java.lang.String SHARED_ATTRIBUTE=AccessControlList.SHARED_ATTRIBUTE;                    
    /** A system-set attribute used to ensure no ACLs owned by a user have duplicate names. */
    public static final java.lang.String OWNERUNIQUENAME_ATTRIBUTE=AccessControlList.OWNERUNIQUENAME_ATTRIBUTE;  
    /** The component ACLs for a composite ACL. */
    public static final java.lang.String COMPONENTACLS_ATTRIBUTE=AccessControlList.COMPONENTACLS_ATTRIBUTE;

    private AccessControlList accessControlList=null;  // to accept object of type AccessControlList

    /**
	   * Purpose : To create DbsAccessControlList using AccessControlList class
	   * @param  : accessControlList - An AccessControlList Object  
	   */
    public DbsAccessControlList(AccessControlList accessControlList){
        super(accessControlList);
        this.accessControlList=accessControlList;
    }

    /**
	   * Purpose  : Returns indication as to whether this is a shared ACL, meaning more than one PublicObject refers to it.
	   * @returns : true if more than one PublicObject refers to this ACL
     * @throws  : DbsException - if operation fails
	   */
    public boolean isShared() throws DbsException {
        boolean isShared;
        try {
            isShared=accessControlList.isShared();
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
        return isShared;
    }

    /**
     * Purpose  : Returns all AccessControlLists that are components of the target ACL. 
     *            If there are one or more component ACLs, the target ACL is considered a "composite ACL". 
     *            If the target ACL is not a composite ACL, this method returns null.
     * @returns : the set of component AccessControlLists
     * @throws  : DbsException - if operation fails
     */
    public DbsAccessControlList[] getComponentAcls() throws DbsException {
        DbsAccessControlList dbsAcls[]=null;
        try {
            int aclCount=accessControlList.getComponentAcls().length;
            dbsAcls=new DbsAccessControlList[aclCount];
            AccessControlList acls[]= accessControlList.getComponentAcls();
            for(int index=0;index<aclCount;index++) {
                dbsAcls[index]=new DbsAccessControlList(acls[index]);
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAcls;
    }

    /**
     * Purpose  : Returns indication as to whether the target ACL is considered a "composite ACL", 
     *            that is if it has one of more component ACLs.
     * @returns : true if the target ACL is a composite ACL
     * @throws  : DbsException - if operation fails
     */
    public boolean isComposite() throws DbsException {
        boolean isComposite;
        try {
            isComposite=accessControlList.isComposite();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isComposite;
    }

    /**
     * Purpose  : Gets the set of composite ACLs which reference the target ACL as a "component".
     * @returns : the set of composite ACLs that include the target ACL as a component
     * @throws  : DbsException - if operation fails
     */
    public DbsAccessControlList[] getCompositeAcls() throws DbsException {
        DbsAccessControlList dbsAcls[]=null;
        try {
            int aclCount=accessControlList.getCompositeAcls().length;
            dbsAcls=new DbsAccessControlList[aclCount];
            AccessControlList acls[]= accessControlList.getCompositeAcls();
            for(int index=0;index<aclCount;index++) {
                dbsAcls[index]=new DbsAccessControlList(acls[index]);
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAcls;
    }

    /**
     * Purpose  : Sets the AccessControlLists that are components of the target ACL. 
     *            If the array specified is not null or empty, the target ACL will become a 
     *            "composite ACL" composed of the specified ACLs. Otherwise, any previous 
     *            component ACLs will be cleared, and the target ACL will no longer be a composite ACL.
     * @returns : dbsAcls - the set of component AccessControlLists, or null if none
     * @throws  : DbsException - if operation fails
     */
    public void setComponentAcls(DbsAccessControlList[] dbsAcls) throws DbsException {
        try {
            int aclCount=dbsAcls.length;
            AccessControlList acls[]=new AccessControlList[aclCount];
            for(int index=0;index < aclCount;index++) {
                acls[index]=dbsAcls[index].getAccessControlList();
            }
            accessControlList.setComponentAcls(acls);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Returns the access level on this object for the current user.
     * @returns : the AccessLevel set for the current user.
     * @throws  : DbsException - if operation fails
     */
    public DbsAccessLevel getGrantedAccessLevel() throws DbsException {
        DbsAccessLevel dbsAccessLevel=null;
        try {
            dbsAccessLevel=new DbsAccessLevel(accessControlList.getGrantedAccessLevel());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAccessLevel;
    }

    /**
     * Purpose  : Returns the access level on this object for a specified DirectoryObject.
     * @param   : dbsobj - the DirectoryObject
     * @returns : the AccessLevel set for the specified DirectoryObject.
     * @throws  : DbsException - if operation fails
     */
    public DbsAccessLevel getGrantedAccessLevel(DbsDirectoryObject  dbsObj) throws DbsException {
        DbsAccessLevel dbsAccessLevel=null;
        try {
            dbsAccessLevel=new DbsAccessLevel(accessControlList.getGrantedAccessLevel(dbsObj.getDirectoryObject()));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAccessLevel;
    }

    /**
     * Purpose  : Check if current user has the specified access level on this object.
     * @param   : dbsLevel - specified level.
     * @returns : the AccessLevel set for the specified DirectoryObject.
     * @throws  : DbsException - if operation fails
     */
    public boolean checkGrantedAccess(DbsAccessLevel dbsLevel) throws DbsException {
        boolean isAccessGranted;
        try {
            isAccessGranted=accessControlList.checkGrantedAccess(dbsLevel.getAccessLevel());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isAccessGranted;
    }

    /**
     * Purpose  : Check to see if specified user/group has the specified access level on this object.
     * @param   : dbsObj - the DirectoryObject
     * @param   : dbsLevel - specified level.
     * @returns : true if specified user has at least the specified access.
     * @throws  : DbsException - if operation fails
     */
    public boolean checkGrantedAccess(DbsDirectoryObject dbsObj,DbsAccessLevel dbsLevel) throws DbsException  {
        boolean isAccessGranted;
        try {
            isAccessGranted=accessControlList.checkGrantedAccess(dbsObj.getDirectoryObject(),dbsLevel.getAccessLevel());
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return isAccessGranted;
    }

    /**
     * Purpose  : Returns the effective access level on this object for the specified user. 
     *            This override will include the Grant standard permission if grant is available 
     *            to the specified user in the defined set of ACEs.
     * @param   : user - specified user on which to check Access Control
     * @returns : the AccessLevel set for the specified user
     * @throws  : DbsException - if operation fails
     */ 
    public DbsAccessLevel getEffectiveAccessLevel(DbsDirectoryObject obj) throws DbsException {
        DbsAccessLevel effectiveAccessLevel=null;
        try {
            DirectoryObject dirObj=obj.getDirectoryObject();
            effectiveAccessLevel=new DbsAccessLevel(accessControlList.getEffectiveAccessLevel(dirObj));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return effectiveAccessLevel;
    }

    /**
     * Purpose  : Check to see if specified user has the specified access level on this object.
     * @param   : user - specified user on which to check Access Control
     * @param   : level - specified level.
     * @returns : true if specified user has at least the specified access.
     * @throws  : DbsException - if operation fails
     */ 
    public boolean checkEffectiveAccess(DbsDirectoryObject obj,DbsAccessLevel level) throws DbsException {
        boolean effectiveAccess;
        try {
            effectiveAccess=accessControlList.checkEffectiveAccess(obj.getDirectoryObject(),level.getAccessLevel()) ;
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return effectiveAccess;
    }

    /**
     * Purpose  : Returns an array of AccessControlEntry objects that are part of this ACL.
     * @returns : array of AccessControlEntrys
     * @throws  : DbsException - if operation fails
     */ 
    public DbsAccessControlEntry[] getAccessControlEntrys() throws DbsException {
        DbsAccessControlEntry dbsAces[]=null;
        try {
            if(accessControlList.getAccessControlEntrys()!=null){
              int aceCount=(accessControlList.getAccessControlEntrys()).length;
              AccessControlEntry aces[]=accessControlList.getAccessControlEntrys();
              dbsAces=new DbsAccessControlEntry[aceCount];    
              for(int index=0 ; index < aceCount ; index++) {
                  dbsAces[index]=new DbsAccessControlEntry(aces[index]);
              }
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAces ;
    }

    /**
     * Purpose  : Returns the ACE at specified index in array of ACEs.
     * @param   : index - index into the array of AccessControlEntrys
     * @returns : the requested AccessControlEntry
     * @throws  : DbsException - if operation fails
     */                                       
    public DbsAccessControlEntry getAccessControlEntrys(int index) throws DbsException {
        DbsAccessControlEntry dbsAce=null;
        try {      
              AccessControlEntry ace=accessControlList.getAccessControlEntrys(index);
              dbsAce=new DbsAccessControlEntry(ace);    
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAce ;
    }
    
    /**
     * Purpose  : Add or extend an ACE for this ACL. This adds an ACE which specifies grant access. 
     *            This method implicitly sets the "granted" indication on the AccessControlEntryDefinition to true.
     * @param   : dbsAceDef - AccessControlEntry definition
     * @returns : newly added AccessControlEntry
     * @throws  : DbsException - if operation fails
     */                                      
    public DbsAccessControlEntry grantAccess(DbsAccessControlEntryDefinition dbsAceDef) throws DbsException {
        DbsAccessControlEntry dbsAce=null;
        try {
            AccessControlEntryDefinition aceDef= dbsAceDef.getAccessControlEntryDefinition();
            dbsAce=new DbsAccessControlEntry(accessControlList.grantAccess(aceDef));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAce;
    }
        
    /**
     * Purpose  : Remove or restrict an ACE for this ACL. This adds a revoke ACE to this ACL. 
     *            This method implicitly sets the "granted" indication on the AccessControlEntryDefinition to false.
     * @param   : dbsAceDef - AccessControlEntry definition
     * @returns : newly added AccessControlEntry
     * @throws  : DbsException - if operation fails
     */                                       
    public DbsAccessControlEntry revokeAccess(DbsAccessControlEntryDefinition dbsAceDef) throws DbsException {
        DbsAccessControlEntry dbsAce=null;
        try {
            AccessControlEntryDefinition aceDef= dbsAceDef.getAccessControlEntryDefinition();
            dbsAce=new DbsAccessControlEntry(accessControlList.revokeAccess(aceDef));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsAce;
    }

    /**
     * Purpose  : Remove all ACEs for this ACL.
     * @throws  : DbsException - if operation fails
     */
    public void revokeAllAccess() throws DbsException {
        try {
            accessControlList.revokeAllAccess();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Remove an ACE from this ACL.
     * @param   : dbsAce - AccessControlEntry to be removed
     * @throws  : DbsException - if operation fails
     */                                                          
    public void removeAccessControlEntry(DbsAccessControlEntry dbsAce) throws DbsException {
        try {
            AccessControlEntry ace= dbsAce.getAccessControlEntry();
            accessControlList.removeAccessControlEntry(ace);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose  : Update an ACE in this ACL.
     * @param   : dbsAce - AccessControlEntry to be updated
     * @param   : dbsAceDef - the definition of the updates
     * @throws  : DbsException - if operation fails
     */                         
    public void updateAccessControlEntry(DbsAccessControlEntry dbsAce, DbsAccessControlEntryDefinition dbsAceDef) throws DbsException {
        try {
            AccessControlEntry ace = dbsAce.getAccessControlEntry();
            AccessControlEntryDefinition aceDef=dbsAceDef.getAccessControlEntryDefinition();
            accessControlList.updateAccessControlEntry(ace,aceDef);    
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose : Used to get the object of class AccessControlList
     * @return AccessControlList Object
     */
    public AccessControlList getAccessControlList() {
        return accessControlList;
    }
}
