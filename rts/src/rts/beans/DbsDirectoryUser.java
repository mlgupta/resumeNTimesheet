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
 * $Id: DbsDirectoryUser.java,v 1.2 2005/07/20 12:21:00 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

/**
 *	Purpose: The encapsulate the functionality of DirectoryUser class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :    Suved Mishra 
 * 	Last Modfied Date:   10-01-2005 
 */
 
/*CMSDK API*/ 
import oracle.ifs.beans.DirectoryUser;
import oracle.ifs.common.*;
import oracle.ifs.beans.*;

public class DbsDirectoryUser extends DbsDirectoryObject {
    private DirectoryUser directoryUser=null; // to accept object of type DirectoryUser

    /**Name of this class. Useful for methods that take a class name argument.*/
    public static final java.lang.String CLASS_NAME=DirectoryUser.CLASS_NAME;
    /**The name by which the CredentialManager for this DirectoryUser refers to this user.*/
    public static final java.lang.String DISTINGUISHEDNAME_ATTRIBUTE=DirectoryUser.DISTINGUISHEDNAME_ATTRIBUTE; 
    /**An indicator of whether this DirectoryUser has administrative privileges and can enter administration mode.*/
    public static final java.lang.String ADMINENABLED_ATTRIBUTE=DirectoryUser.ADMINENABLED_ATTRIBUTE;
    /**An indicator of whether this DirectoryUser has system administrative privileges.*/
    public static final java.lang.String SYSTEMADMINENABLED_ATTRIBUTE=DirectoryUser.SYSTEMADMINENABLED_ATTRIBUTE; 
    /**The name of the CredentialManager for this DirectoryUser.*/    
    public static final java.lang.String CREDENTIALMANAGER_ATTRIBUTE=DirectoryUser.CREDENTIALMANAGER_ATTRIBUTE;

    public static final String ENCRYPTION_ENABLED = "ENCRYPTION_ENABLED" ; 
    /**
	   * Purpose : To create DbsDirectoryUser using DirectoryUser class
	   * @param  : directoryUser - An DirectoryUser Object  
	   */
    protected DbsDirectoryUser(DirectoryUser directoryUser) {
        super(directoryUser);
        this.directoryUser=directoryUser;
    }

    /**
	   * Purpose  : Gets this DirectoryUser.
	   * @returns : array containing this one DirectoryUser
     * @throws  : DbsException - if operation fails
	   */
    public DbsDirectoryUser[] getAllUserMembers() throws DbsException {
        DbsDirectoryUser dbsDirectoryUsers[]=null;
        try {
            int numUsers=(directoryUser.getAllUserMembers()).length;
            DirectoryUser directoryUsers[]=directoryUser.getAllUserMembers();
            dbsDirectoryUsers=new DbsDirectoryUser[numUsers];
            for(int index=0;index < numUsers;index++) {
                dbsDirectoryUsers[index]=new DbsDirectoryUser(directoryUsers[index]);
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDirectoryUsers;
    }

    /**
	   * Purpose  : Gets this DirectoryUser at specified index. 
     *            This is an override of the method in DirectoryObject. 
     *            Since a DirectoryUser does not have any other members, the index must be 0.
	   * @param   : index - must be zero to get this DirectoryUser
	   * @returns : this DirectoryUser
     * @throws  : DbsException - if operation fails
	   */                             
    public DbsDirectoryUser getAllUserMembers(int index) throws DbsException {
        DbsDirectoryUser dbsDirectoryUser=null;
        try {
            dbsDirectoryUser=new DbsDirectoryUser(directoryUser.getAllUserMembers(index));
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsDirectoryUser;
    }

    /**
	   * Purpose  : Gets the DirectoryGroups.
	   * @returns : array containing DirectoryGroups
     * @throws  : DbsException - if operation fails
	   */

    public DbsDirectoryGroup[] getAllAncestors() throws DbsException {
      
      DbsDirectoryGroup[] dbsDirectoryGroups = null;
      
      try{
        
        DirectoryGroup[] directoryGroups = directoryUser.getAllAncestors();
        //System.out.println("dobj's length : "+directoryGroups.length);
        dbsDirectoryGroups = (directoryGroups!=null && directoryGroups.length !=0)?
                              new DbsDirectoryGroup[directoryGroups.length]:null;
        if( dbsDirectoryGroups != null ){
          for( int index=0; index<directoryGroups.length; index++ ){
            //System.out.println("directoryGroups["+index+"] : "+directoryGroups[index]);
            dbsDirectoryGroups[index] = new DbsDirectoryGroup(directoryGroups[index]);
          }
        }
        
      }catch( IfsException ifsEx ){
        throw new DbsException(ifsEx);
      }
      
      return dbsDirectoryGroups;
      
    }
    /**
	   * Purpose  : Gets the distinguished name for this DirectoryUser.
	   * @returns : the distinguished name
     * @throws  : DbsException - if operation fails
	   */                        
    public java.lang.String getDistinguishedName() throws DbsException {
        String name=null;
        try {
            name=directoryUser.getDistinguishedName();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return name;
    }

    /**
	   * Purpose  : Checks if this DirectoryUser has admin privileges.
	   * @returns : whether this DirectoryUser is admin enabled
     * @throws  : DbsException - if operation fails
	   */     
    public boolean isAdminEnabled() throws DbsException {
        boolean adminEnabled;
        try {
            adminEnabled=directoryUser.isAdminEnabled();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return adminEnabled;
    }

    /**
	   * Purpose  : Checks if this DirectoryUser has system admin privileges.
	   * @returns : whether this DirectoryUser is system admin enabled
     * @throws  : DbsException - if operation fails
	   */                 
    public boolean isSystemAdminEnabled() throws DbsException {
        boolean sysAdminEnabled;
        try {
            sysAdminEnabled=directoryUser.isSystemAdminEnabled();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return sysAdminEnabled;
    }

    /**
	   * Purpose  : Gets the credential manager of this DirectoryUser
	   * @returns : the credential manager
     * @throws  : DbsException - if operation fails
	   */                    
    public java.lang.String getCredentialManager() throws DbsException {
        String credentialManager=null;
        try {
            credentialManager=directoryUser.getCredentialManager();
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return credentialManager;
     }
                                
    /**
	   * Purpose  : Sets admin privileges of this DirectoryUser.
	   * @param   : value - true if enabling admin mode
     * @throws  : DbsException - if operation fails
	   */ 
    public void setAdminEnabled(boolean value)  throws DbsException {
        try{
            directoryUser.setAdminEnabled(value);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Sets system admin privileges of this DirectoryUser.
	   * @param   : value - true if enabling system admin mode
     * @throws  : DbsException - if operation fails
	   */             
    public void setSystemAdminEnabled(boolean value) throws DbsException {
        try {
            directoryUser.setSystemAdminEnabled(value);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
     }

    /**
	   * Purpose  : Sets the distinguished name of this DirectoryUser.
	   * @param   : name - the distinguished name
     * @throws  : DbsException - if operation fails
	   */                          
    public void setDistinguishedName(java.lang.String name) throws DbsException {
        try {
            directoryUser.setDistinguishedName(name);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
	   * Purpose  : Sets the credential manager of this DirectoryUser.
	   * @param   : credentialManager - the name of the credential manager
     * @throws  : DbsException - if operation fails
	   */                       
    public void setCredentialManager(java.lang.String credentialManager) throws DbsException {
        try {
            directoryUser.setCredentialManager(credentialManager);
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
    }

    /**
     * Purpose   : Returns array of DirectoryObject that define the list of all members for this instance. 
     *             This list is the full set of DirectoryObject that are members (directly or indirectly) of this DirectoryGroup.
     * @returns  : array of member DirectoryObject
     * @throws   : DbsException - if operation fails
     */               
    public DbsDirectoryObject[] getAllMembers() throws DbsException {
        DbsDirectoryObject dbsMembers[]=null;
        try{
            int memberCount= (directoryUser.getAllMembers()).length;
            dbsMembers=new DbsDirectoryObject[memberCount];
            DirectoryObject members[]=directoryUser.getAllMembers();
            for(int index=0 ; index< memberCount ; index++) {
                if(members[index] instanceof DirectoryUser){
                    dbsMembers[index]=new DbsDirectoryUser((DirectoryUser)members[index]);                  
                }else if(members[index] instanceof DirectoryGroup){                   
                    dbsMembers[index]=new DbsDirectoryGroup((DirectoryGroup)members[index]);
                }else{                
                    dbsMembers[index]=new DbsDirectoryObject(members[index]);                 
                }
            }
        }catch(IfsException ifsError) {
            throw new DbsException(ifsError);
        }
        return dbsMembers ;
    }

   /**
	  * Purpose : Used to get the object of class DirectoryUser
	  * @return DirectoryUser Object
	  */
    public DirectoryUser getDirectoryUser() {
        return this.directoryUser;
    }
    
    /**
	   * Purpose : Gets the PrimaryUserProfile of this DirectoryUser.
	   * @return : the PrimaryUserProfile
     * @throws   : DbsException - if operation fails
	   */
    public DbsPrimaryUserProfile getPrimaryUserProfile() throws DbsException {
       try{
            if(directoryUser.getPrimaryUserProfile() != null){
                return new DbsPrimaryUserProfile(directoryUser.getPrimaryUserProfile());    
            }else{
                return null;
            }
            
        }catch(IfsException ifsError){
            throw new DbsException(ifsError);
        }
    }
}
